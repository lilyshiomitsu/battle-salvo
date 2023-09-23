package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.controller.ProxyController;
import cs3500.pa04.json.CoordAdapter;
import cs3500.pa04.json.CoordinatesBean;
import cs3500.pa04.json.Direction;
import cs3500.pa04.json.EndGameBean;
import cs3500.pa04.json.FleetBean;
import cs3500.pa04.json.JoinBean;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.SetupBean;
import cs3500.pa04.json.SpecBean;
import cs3500.pa04.model.AiPlayer;
import cs3500.pa04.model.AiPlayerDependency;
import cs3500.pa04.model.Board;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.Ship;
import cs3500.pa04.model.ShipType;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * tests the proxycontroller class
 */
public class ProxyControllerTest {

  private ByteArrayOutputStream testLog;
  private ProxyController controller;


  /**
   * Reset the test log before each test is run.
   */
  @BeforeEach
  public void setup() {
    this.testLog = new ByteArrayOutputStream(2048);
    assertEquals("", logToString());
  }

  /**
   * tests the handle join method
   */
  @Test
  public void testHandleJoin() {
    // prepare sample message
    String name = "name";
    String gameType = "SINGLE";
    JoinBean response = new JoinBean(name, gameType);
    JsonNode sampleMessage = createSampleMessage("join", response);

    // create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(sampleMessage.toString()));

    // create a controller
    try {
      this.controller = new ProxyController(socket, new AiPlayerDependency());
    } catch (IOException e) {
      fail();
    }

    // run the dealer and verify the response
    this.controller.run();

    String expected = "{\"method-name\":\"join\",\"arguments\":{\"name\":"
        + "\"nullpointerexception\",\"game-type\":\"SINGLE\"}}\n";
    assertEquals(expected, logToString());
  }

  /**
   * tests the handle setup method in proxy controller
   */
  @Test
  public void testHandleSetup() {
    // Prepare sample arguments
    int width = 10;
    int height = 10;
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 2);
    specifications.put(ShipType.DESTROYER, 3);
    specifications.put(ShipType.SUBMARINE, 4);
    SpecBean specBean = new SpecBean(
        specifications.get(ShipType.CARRIER),
        specifications.get(ShipType.BATTLESHIP),
        specifications.get(ShipType.DESTROYER),
        specifications.get(ShipType.SUBMARINE)
    );
    SetupBean response = new SetupBean(width, height, specBean);
    JsonNode sampleMessage = createSampleMessage("setup", response);

    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(sampleMessage.toString()));

    // Create a controller
    try {
      this.controller = new ProxyController(socket, new AiPlayerDependency());
    } catch (IOException e) {
      fail();
    }

    // Run the controller and verify response
    this.controller.run();
    // responseToClass(FleetBean.class);
  }

  /**
   * tests the handle report damage method
   */
  @Test
  public void testHandleReportDamage() {
    // Prepare sample message
    CoordAdapter[] coords = new CoordAdapter[] {
        new CoordAdapter(0, 0),
        new CoordAdapter(1, 1),
        new CoordAdapter(2, 2)
    };
    CoordinatesBean coordinatesBean = new CoordinatesBean(coords);
    JsonNode sampleMessage = createSampleMessage("report-damage", coordinatesBean);

    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(sampleMessage.toString()));

    // Create a controller
    try {
      AiPlayerDependency test = new AiPlayerDependency();
      test.getInformation().setBoard(new Board(10, 10));
      this.controller = new ProxyController(socket, test);
    } catch (IOException e) {
      fail();
    }

    // Run the controller and verify the response
    this.controller.run();

  }

  @Test
  public void testHandleSuccessfulHits() {
    // Prepare sample message
    CoordAdapter[] coords = new CoordAdapter[] {
        new CoordAdapter(0, 0),
        new CoordAdapter(1, 1),
        new CoordAdapter(2, 2)
    };
    CoordinatesBean coordinatesBean = new CoordinatesBean(coords);
    JsonNode sampleMessage = createSampleMessage("successful-hits", coordinatesBean);

    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(sampleMessage.toString()));

    // Create a controller
    try {
      AiPlayerDependency test = new AiPlayerDependency();
      test.getInformation().setBoard(new Board(10, 10));
      test.getInformation().setEnemyBoard(new Board(10, 10));
      this.controller = new ProxyController(socket, test);
    } catch (IOException e) {
      fail();
    }

    // Run the controller and verify the response
    this.controller.run();
  }

  @Test
  public void testHandleEndGame() {
    EndGameBean endGameBean = new EndGameBean(GameResult.WIN.toString(), "WIN");
    JsonNode sampleMessage = createSampleMessage("end-game", endGameBean);

    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(sampleMessage.toString()));

    // Create a controller
    try {
      AiPlayerDependency test = new AiPlayerDependency();
      test.getInformation().setBoard(new Board(10, 10));
      test.getInformation().setEnemyBoard(new Board(10, 10));
      this.controller = new ProxyController(socket, test);
    } catch (IOException e) {
      fail();
    }

    // Run the controller and verify the response
    this.controller.run();
  }

  @Test
  public void testHandleTakeShots() {
    // Prepare sample message
    CoordAdapter[] coords = new CoordAdapter[] {
        new CoordAdapter(0, 0),
        new CoordAdapter(1, 1),
        new CoordAdapter(2, 2)
    };
    CoordinatesBean coordinatesBean = new CoordinatesBean(coords);
    JsonNode sampleMessage = createSampleMessage("take-shots", coordinatesBean);

    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(sampleMessage.toString()));

    // Create a controller
    try {
      AiPlayerDependency test = new AiPlayerDependency();
      test.getInformation().setBoard(new Board(10, 10));
      test.getInformation().setEnemyBoard(new Board(10, 10));
      this.controller = new ProxyController(socket, test);
    } catch (IOException e) {
      fail();
    }

    // Run the controller and verify the response
    this.controller.run();


  }

  /**
   * Converts the ByteArrayOutputStream log to a string in UTF_8 format
   *
   * @return String representing the current log buffer
   */
  private String logToString() {
    return testLog.toString(StandardCharsets.UTF_8);
  }

  /**
   * Try converting the current test log to a string of a certain class.
   *
   * @param classRef Type to try converting the current test stream to.
   * @param <T>      Type to try converting the current test stream to.
   */
  private <T> void responseToClass(@SuppressWarnings("SameParameterValue") Class<T> classRef) {
    try {
      JsonParser jsonParser = new ObjectMapper().createParser(logToString());
      jsonParser.readValueAs(classRef);
      // No error thrown when parsing to a GuessJson, test passes!
    } catch (IOException e) {
      // Could not read
      // -> exception thrown
      // -> test fails since it must have been the wrong type of response.
      fail();
    }
  }

  /**
   * Create a MessageJson for some name and arguments.
   *
   * @param messageName   name of the type of message; "hint" or "win"
   * @param messageObject object to embed in a message json
   * @return a MessageJson for the object
   */
  private JsonNode createSampleMessage(String messageName, Record messageObject) {
    MessageJson messageJson =
        new MessageJson(messageName, JsonUtils.serializeRecord(messageObject));
    return JsonUtils.serializeRecord(messageJson);
  }
}
