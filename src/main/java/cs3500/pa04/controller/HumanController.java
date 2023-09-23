package cs3500.pa04.controller;

import cs3500.pa04.model.Coord;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.HumanPlayer;
import cs3500.pa04.model.ShipType;
import cs3500.pa04.model.Status;
import cs3500.pa04.view.PlayerView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * represents the controller for the human player
 */
public class HumanController implements PlayerController {

  private HumanPlayer model;
  private ArrayList<Integer> inputList = new ArrayList<>();
  private PlayerView view;
  private Scanner sc;
  private AiController aiController;

  /**
   * constructor for the human controller class
   *
   * @param input Readable to handle user input
   */
  public HumanController(Readable input) {
    this.model = new HumanPlayer();
    this.view = new PlayerView();
    sc = new Scanner(input);
    aiController = new AiController();
  }

  /**
   * constructor for the human controller class
   *
   * @param input Readable to handle user input
   */
  public HumanController(Readable input, HumanPlayer player, AiController aiController) {
    this.model = player;
    this.view = new PlayerView();
    sc = new Scanner(input);
    this.aiController = aiController;
  }

  /**
   * Runs the Human Controller to interact with the user and set up the game.
   * The method displays welcome messages and prompts the user to enter the
   * board's height and width.
   * It handles the user's input for the height and width, ensuring they are within valid range.
   * Then, it prompts the user to enter the fleet composition in the order
   * [Carrier, Battleship, Destroyer, Submarine].
   * The fleet sizes are validated to ensure they do not exceed the minimum of the height and width.
   * If invalid fleet sizes are entered, the user is prompted again to enter valid fleet sizes.
   * The method sets up the ship specifications based on the user's input.
   * It then sets up the model using the provided height, width, and ship specifications.
   * The AI player is set as the opponent for the model.
   * The method sends the initial game state to the AI.
   * Finally, it handles the user's shots by taking input and processing them.
   */
  @Override
  public void run() {
    view.displayPrompt("Hello! Welcome to the OOD BattleSalvo Game!");
    view.displayPrompt("Please enter a valid height and width below [6-15]: ");
    int height = handleUserInput(6, 15);
    int width = handleUserInput(6, 15);
    view.displayPrompt("Please enter your fleet in the order [Carrier, Battleship, "
        + "Destroyer, Submarine]. Your fleet may not exceed size " + Math.min(height, width));
    int carrierCount = handleUserInput(1, 15);
    int battleshipCount = handleUserInput(1, 15);
    int destroyerCount = handleUserInput(1, 15);
    int submarineCount = handleUserInput(1, 15);
    int shipCount = carrierCount + battleshipCount + destroyerCount + submarineCount;
    while (Math.min(height, width) < shipCount) {
      for (int i = 0; i < 4; i++) {
        inputList.remove(inputList.size() - 1);
      }
      view.displayPrompt("You've entered invalid fleet sizes.\n"
          + "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].");
      view.displayPrompt("Remember, your fleet may not exceed size " + Math.min(height, width));
      carrierCount = handleUserInput(1, 15);
      battleshipCount = handleUserInput(1, 15);
      destroyerCount = handleUserInput(1, 15);
      submarineCount = handleUserInput(1, 15);
      shipCount = carrierCount + battleshipCount + destroyerCount + submarineCount;
    }
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, carrierCount);
    specifications.put(ShipType.BATTLESHIP, battleshipCount);
    specifications.put(ShipType.DESTROYER, destroyerCount);
    specifications.put(ShipType.SUBMARINE, submarineCount);
    model.setup(height, width, specifications);
    model.setAiDependencyModel(aiController.getDependencyModel());
    sendToAi();
    handleShots(shipCount);
  }

  /**
   * Handles user input within a specified range.
   *
   * @param rangeBottom The lower bound of the input range (inclusive).
   * @param rangeTop    The upper bound of the input range (inclusive).
   * @return The user's input within the specified range.
   * @throws InputMismatchException If the user enters an invalid input (non-numeric).
   */
  private int handleUserInput(int rangeBottom, int rangeTop) {
    int input = -1;
    boolean isValidInput = false;
    while (!isValidInput) {
      try {
        input = sc.nextInt();
        if (input >= rangeBottom && input <= rangeTop) {
          isValidInput = true;
          inputList.add(input);
        } else {
          view.displayPrompt("Please enter an option between " + rangeBottom + " and " + rangeTop);
        }
      } catch (InputMismatchException e) {
        sc.nextLine(); // Consume the invalid input
        view.displayPrompt("Invalid input. Please enter a number.");
      }
    }
    return input;
  }

  /**
   * Handles the user's shot inputs during the gameplay.
   *
   * @param shipCount The number of shots to be taken by the user.
   */
  private void handleShots(int shipCount) {
    List<Integer> inputs = new ArrayList<>();
    view.displayPrompt("Opponent's Board:");
    view.displayBoard(model.getOpponentBoardData());
    view.displayPrompt("Your Board:");
    view.displayBoard(model.getBoard());
    view.displayPrompt("Please enter " + shipCount + " shots");
    for (int i = 0; i < shipCount * 2; i++) {
      if (i % 2 == 0) {
        int input = handleUserInput(0, model.getBoard().getWidth());
        inputs.add(input);
      } else if (i % 2 == 1) {
        int input = handleUserInput(0, model.getBoard().getHeight());
        inputs.add(input);
      }
    }
    List<Coord> temp = new ArrayList<>();
    for (int i = 0; i < inputs.size(); i += 2) {
      Coord c = model.getCoord(inputs.get(i), inputs.get(i + 1),
          model.getOpponentBoardData());
      while (!(this.isValidTarget(c, model.getBoard().getHeight(),
          model.getBoard().getWidth()))) {
        view.displayPrompt("Shots not valid. Please try again.");
        inputs = new ArrayList<>();
        handleShots(shipCount);
      }
      temp.add(c);
    }
    model.receiveShots(temp);
    updateShots(shipCount);
  }


  /**
   * Checks if the given coordinate is a valid target for shooting.
   * A coordinate is considered a valid target if it falls within the
   * specified height and width bounds and its status is neither "HIT" nor "MISSED".
   *
   * @param c      The coordinate to be checked for validity.
   * @param height The height of the game board.
   * @param width  The width of the game board.
   * @return {@code true} if the coordinate is a valid target, {@code false} otherwise.
   */
  public boolean isValidTarget(Coord c, int height, int width) {
    return (c.getCoordX() >= 0 && c.getCoordX() < width)
        && (c.getCoordY() >= 0 && c.getCoordY() < height)
        && !(c.getStatus() == Status.HIT && c.getStatus() == Status.MISSED);
  }

  /**
   * Updates the game state after the user takes shots.
   * The method takes shots using the AI model and generates shots for the AI player.
   * It updates the ship count for the user.
   * If the user's ship count reaches zero while the AI player still has remaining ships,
   * the program ends with a lost game result.
   * If both the user's and the AI player's ship counts reach zero, the program ends with a
   * tie game result.
   * If the user's ship count is non-zero while the AI player's ship count reaches zero,
   * the program ends with a won game result.
   * If none of the above conditions are met, the method continues the game by handling
   * the user's shots,sending the game state to the AI, and recursively calling itself.
   *
   * @param shipCount The current ship count of the user.
   */
  public void updateShots(int shipCount) {
    aiController.getDependencyModel().getAiPlayer().reportDamage(model.takeShots());
    aiController.getDependencyModel().getAiPlayer()
        .successfulHits(model.reportDamage(
            aiController.generateShots()));
    shipCount = model.shipsLeft();
    if (shipCount == 0 && aiController.getShipCount() > 0) {
      endProgram(GameResult.LOSE);
    } else if (shipCount == 0 && aiController.getShipCount() == 0) {
      endProgram(GameResult.DRAW);
    } else if (shipCount > 0 && aiController.getShipCount() == 0) {
      endProgram(GameResult.WIN);
    } else {
      if (shipCount > model.emptyCoordCount()) {
        shipCount = model.emptyCoordCount();
      }
      handleShots(shipCount);
      sendToAi();
    }
  }

  /**
   * Ends the program and displays the provided game result message.
   *
   * @param result The game result.
   */
  private void endProgram(GameResult result) {
    view.displayPrompt(result.getMessage());
  }

  /**
   * Sends the current game state to the AI controller.
   * The method provides the user's input list to the AI controller,
   * sets the user as the AI's opponent,
   * and runs the AI controller.
   */
  private void sendToAi() {
    aiController.receiveUserInput(inputList);
    aiController.setHumanPlayer(model);
    aiController.run();
  }
}