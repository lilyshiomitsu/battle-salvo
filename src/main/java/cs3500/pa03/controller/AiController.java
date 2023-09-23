package cs3500.pa03.controller;

import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.HumanPlayer;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.model.Status;
import cs3500.pa03.view.PlayerView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * represents the controller for the ai player
 */
public class AiController implements PlayerController {
  private AiPlayer model;
  private HumanPlayer humanPlayer;
  private PlayerView view;
  private ArrayList<Integer> input;
  private int shipCount;

  /**
   * constructor for the ai controller
   */
  public AiController() {
    this.model = new AiPlayer();
    this.view = new PlayerView();
    this.input = new ArrayList<>();
  }

  /**
   * Runs the AI controller to set up the game and initialize the players.
   * The method takes input parameters and configures the game accordingly.
   * It sets up the ship specifications and assigns them to the corresponding ship types.
   * Then, it sets up the model using the provided input parameters and ship specifications.
   * It also sets the human player and assigns the opponent's board to the human player.
   */
  @Override
  public void run() {
    int carrierCount = input.get(2);
    int battleshipCount = input.get(3);
    int destroyerCount = input.get(4);
    int submarineCount = input.get(5);
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, carrierCount);
    specifications.put(ShipType.BATTLESHIP, battleshipCount);
    specifications.put(ShipType.DESTROYER, destroyerCount);
    specifications.put(ShipType.SUBMARINE, submarineCount);
    model.setup(input.get(0), input.get(1), specifications);
    model.setHumanPlayer(humanPlayer);
    humanPlayer.setOpponentBoard(model.getBoard());
  }

  /**
   * Generates random shots for the AI player.
   * The method determines the number of remaining ships for the AI player.
   * It checks if the ship count is greater than the number of empty coordinates on the board.
   * If so, it sets the ship count to the number of empty coordinates.
   * The method then generates random coordinates within the board's dimensions.
   * It checks if the target coordinate is a valid target for shooting.
   * If not, it generates new random coordinates until a valid target is found.
   * The generated valid targets are added to a list of shots.
   * The AI model receives the shots and takes the shots.
   */
  public void generateShots() {
    shipCount = model.shipsLeft();
    if (shipCount > model.emptyCoordCount()) {
      shipCount = model.emptyCoordCount();
    }
    List<Coord> shots = new ArrayList<>();
    for (int i = 0; i < shipCount; i++) {
      int x = (int) (Math.random() * model.getBoard().getWidth());
      int y = (int) (Math.random() * model.getBoard().getHeight());
      Coord c = humanPlayer.getCoord(x, y, humanPlayer.getBoard());
      while (!(this.isValidTarget(c))) {
        x = (int) (Math.random() * model.getBoard().getWidth());
        y = (int) (Math.random() * model.getBoard().getHeight());
        c = humanPlayer.getCoord(x, y, humanPlayer.getBoard());
      }
      shots.add(c);
    }
    model.receiveShots(shots);
    model.takeShots();
  }

  /**
   * Checks if the given coordinate is a valid target for shooting.
   * A coordinate is considered a valid target if its status is neither "HIT" nor "MISSED".
   *
   * @param c The coordinate to be checked for validity.
   * @return {@code true} if the coordinate is a valid target, {@code false} otherwise.
   */
  private boolean isValidTarget(Coord c) {
    return !(c.getStatus() == Status.HIT && c.getStatus() == Status.MISSED);
  }

  /**
   * Retrieves the current ship count.
   *
   * @return The ship count.
   */
  public int getShipCount() {
    return this.shipCount;
  }

  /**
   * Receives user input to be used in the AI controller.
   *
   * @param input The user input as an ArrayList of integers.
   */
  public void receiveUserInput(ArrayList<Integer> input) {
    this.input = input;
  }

  /**
   * Sets the human player for the AI controller.
   *
   * @param humanPlayer The HumanPlayer object representing the human player.
   */
  public void setHumanPlayer(HumanPlayer humanPlayer) {
    this.humanPlayer = humanPlayer;
  }

  /**
   * Retrieves the AI player model.
   *
   * @return The AIPlayer model.
   */
  public AiPlayer getModel() {
    return this.model;
  }

  /**
   * sets the ship count
   *
   * @param count ship count
   */

  public void setShipCount(int count) {
    this.shipCount = count;
  }

}

