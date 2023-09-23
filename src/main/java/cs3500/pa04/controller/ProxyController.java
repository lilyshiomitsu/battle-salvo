package cs3500.pa04.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.CoordAdapter;
import cs3500.pa04.json.CoordinatesBean;
import cs3500.pa04.json.EndGameBean;
import cs3500.pa04.json.FleetBean;
import cs3500.pa04.json.JoinBean;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.SetupBean;
import cs3500.pa04.json.ShipAdapter;
import cs3500.pa04.json.ShipBean;
import cs3500.pa04.model.AiPlayerDependency;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.Ship;
import cs3500.pa04.model.ShipType;
import cs3500.pa04.model.Status;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The {@code ProxyController} class represents a proxy controller that interacts with a server
 * and delegates messages received from the server to the appropriate methods.
 */
public class ProxyController {
  private AiPlayerDependency player;
  private Socket server;
  private final InputStream in;
  private final PrintStream out;
  private final ObjectMapper mapper = new ObjectMapper();
  private static final JsonNode VOID_RESPONSE =
      new ObjectMapper().getNodeFactory().textNode("void");

  /**
   * Constructs a {@code ProxyController} object with the specified server and player.
   *
   * @param server the server socket to communicate with
   * @param player the model for the local player
   * @throws IOException if an I/O error occurs while opening the input and output streams
   */
  public ProxyController(Socket server, AiPlayerDependency player) throws IOException {
    this.server = server;
    this.in = server.getInputStream();
    this.out = new PrintStream(server.getOutputStream());
    this.player = player;
  }

  /**
   * Listens for messages from the server as JSON in the format of a MessageJSON. When a complete
   * message is sent by the server, the message is parsed and then delegated to the corresponding
   * helper method for each message. This method stops when the connection to the server is closed
   * or an IOException is thrown from parsing malformed JSON.
   */
  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);
      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        delegateMessage(message);
      }
    } catch (IOException e) {
      // Disconnected from server or parsing exception
    }
  }

  /**
   * Delegates a received message to the appropriate handler method based on the message name.
   *
   * @param message the received message
   */
  public void delegateMessage(MessageJson message) throws IOException {
    String name = message.methodName();
    JsonNode arguments = message.arguments();

    if ("join".equals(name)) {
      handleJoin();
    } else if ("setup".equals(name)) {
      handleSetup(arguments);
    } else if ("take-shots".equals(name)) {
      handleTakeShots();
    } else if ("report-damage".equals(name)) {
      handleReportDamage(arguments);
    } else if ("successful-hits".equals(name)) {
      handleSuccessfulHits(arguments);
    } else if ("end-game".equals(name)) {
      handleEndGame(arguments);
    } else {
      throw new IllegalStateException("Invalid message name");
    }
  }

  /**
   * Handles the "join" message from the server by sending the join response to the server.
   */
  public void handleJoin() {
    String name = player.getAiPlayer().name();
    String gameType = "SINGLE";
    JoinBean response = new JoinBean(name, gameType);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    MessageJson responder = new MessageJson("join", jsonResponse);
    JsonNode output = JsonUtils.serializeRecord(responder);
    this.out.println(output);
  }

  /**
   * Handles the "setup" message from the server by performing the ship setup and sending the fleet
   * information to the server.
   *
   * @param args the setup arguments received from the server
   */
  public void handleSetup(JsonNode args) {
    SetupBean setUpArgs = this.mapper.convertValue(args, SetupBean.class);
    Map<ShipType, Integer> specifications = setUpArgs.specBean().specifications();
    List<Ship> ships = player.getAiPlayer().setup(setUpArgs.height(), setUpArgs.width(),
        specifications);
    ShipBean[] fleet = new ShipBean[ships.size()];
    for (int i = 0; i < ships.size(); i++) {
      ShipAdapter shipAdapter = new ShipAdapter(ships.get(i));
      CoordAdapter coordAdapter = shipAdapter.getCoordAdapter();
      ShipBean shipBean = new ShipBean(coordAdapter, shipAdapter.getLength(),
          shipAdapter.getDirection().toString());
      fleet[i] = shipBean;
    }
    FleetBean fleetBean = new FleetBean(fleet);
    JsonNode jsonResponse = this.mapper.valueToTree(fleetBean);
    MessageJson responder = new MessageJson("setup", jsonResponse);
    JsonNode output = JsonUtils.serializeRecord(responder);
    this.out.println(output);
  }

  /**
   * Handles the "take-shots" message from the server by performing the shot-taking and sending the
   * shot coordinates to the server.
   */
  public void handleTakeShots() {
    List<Coord> coordList = player.getAiPlayer().takeShots();
    CoordAdapter[] coordAdapters = new CoordAdapter[coordList.size()];
    for (int i = 0; i < coordList.size(); i++) {
      CoordAdapter c = new CoordAdapter(coordList.get(i));
      coordAdapters[i] = c;
    }
    CoordinatesBean coordinatesBean = new CoordinatesBean(coordAdapters);
    JsonNode jsonResponse = this.mapper.valueToTree(coordinatesBean);
    MessageJson responder = new MessageJson("take-shots", jsonResponse);
    JsonNode output = JsonUtils.serializeRecord(responder);
    this.out.println(output);
  }

  /**
   * Handles the "report-damage" message from the server by reporting the damage received and
   * sending the damaged coordinates to the server.
   *
   * @param args the damaged coordinates received from the server
   */
  public void handleReportDamage(JsonNode args) {
    CoordinatesBean coordinatesBean = this.mapper.convertValue(args, CoordinatesBean.class);
    CoordAdapter[] coords = coordinatesBean.coordinates();
    List<Coord> coordList = new ArrayList<>();
    for (int i = 0; i < coords.length; i++) {
      CoordAdapter coordAdapter = coords[i];
      Coord c = new Coord(coordAdapter.getX(), coordAdapter.getY());
      c.setStatus(Status.EMPTY);
      coordList.add(c);
    }
    List<Coord> damageCoord = player.getAiPlayer().reportDamage(coordList);
    CoordAdapter[] damage = new CoordAdapter[damageCoord.size()];
    for (int i = 0; i < damageCoord.size(); i++) {
      CoordAdapter coordAdapter = new CoordAdapter(damageCoord.get(i));
      damage[i] = coordAdapter;
    }
    CoordinatesBean damageList = new CoordinatesBean(damage);
    JsonNode jsonResponse = this.mapper.valueToTree(damageList);
    MessageJson responder = new MessageJson("report-damage", jsonResponse);
    JsonNode output = JsonUtils.serializeRecord(responder);
    this.out.println(output);
  }

  /**
   * Handles the "successful-hits" message from the server by marking the successful hits received.
   *
   * @param args the successful hit coordinates received from the server
   */
  public void handleSuccessfulHits(JsonNode args) {
    CoordinatesBean coordinatesBean = this.mapper.convertValue(args, CoordinatesBean.class);
    CoordAdapter[] coords = coordinatesBean.coordinates();
    List<Coord> coordList = new ArrayList<>();
    for (int i = 0; i < coords.length; i++) {
      CoordAdapter coordAdapter = coords[i];
      Coord c = new Coord(coordAdapter.getX(), coordAdapter.getY());
      c.setStatus(Status.EMPTY);
      coordList.add(c);
    }
    player.getAiPlayer().successfulHits(coordList);
    MessageJson response = new MessageJson("successful-hits", VOID_RESPONSE);
    JsonNode output = JsonUtils.serializeRecord(response);
    this.out.println(output);
  }

  /**
   * Handles the "end-game" message from the server by ending the game and sending a void response
   * to the server.
   *
   * @param args the end game details received from the server
   */
  public void handleEndGame(JsonNode args) throws IOException {
    EndGameBean endGame = this.mapper.convertValue(args, EndGameBean.class);
    String result = endGame.result();
    GameResult gameResult = null;
    if (result == GameResult.WIN.toString()) {
      gameResult = GameResult.WIN;
    } else if (result == GameResult.LOSE.toString()) {
      gameResult = GameResult.LOSE;
    } else if (result == GameResult.DRAW.toString()) {
      gameResult = GameResult.DRAW;
    }
    String reason = endGame.reason();
    boolean close = player.getAiPlayer().endGame(gameResult, reason);
    if (close) {
      server.close();
    }
    MessageJson response = new MessageJson("end-game", VOID_RESPONSE);
    JsonNode output = JsonUtils.serializeRecord(response);
    this.out.println(output);
  }
}
