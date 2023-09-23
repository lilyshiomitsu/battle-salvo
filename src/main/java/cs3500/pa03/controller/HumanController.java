package cs3500.pa03.controller;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.HumanPlayer;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.model.Status;
import cs3500.pa03.view.PlayerView;
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
  private ArrayList<Integer> inputList = new ArrayList<Integer>();
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
    while (shipCount > Math.min(height, width)) {
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
    model.setAiPlayer(aiController.getModel());
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
  public void handleShots(int shipCount) {
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
      while (c.getStatus() == Status.HIT || c.getStatus() == Status.MISSED) {
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
   * Updates the game state after the user takes shots.
   * The method takes shots using the AI model and generates shots for the AI player.
   * It updates the ship count for the user.
   * If the user's ship count reaches zero while the AI player still has remaining ships,
   * the program ends with a lost game result.
   * If both the user's and the AI player's ship counts reach zero, the program ends with
   * a tie game result.
   * If the user's ship count is non-zero while the AI player's ship count reaches zero,
   * the program ends with a won game result.
   * If none of the above conditions are met, the method continues the game by handling the
   * user's shots,
   * sending the game state to the AI, and recursively calling itself.
   *
   * @param shipCount The current ship count of the user.
   */
  public void updateShots(int shipCount) {
    model.takeShots();
    aiController.generateShots(); // issue because cant use the same number, need to differentiate
    shipCount = model.shipsLeft();
    if (shipCount == 0 && aiController.getShipCount() > 0) { // and AI shipcount is not 0) {
      endProgram(GameResult.LOST);
    } else if (shipCount == 0 && aiController.getShipCount() == 0) {
      endProgram(GameResult.TIE);
    } else if (shipCount > 0 && aiController.getShipCount() == 0) {
      endProgram(GameResult.WON);
    } else {
      if (shipCount > model.emptyCoordCount()) {
        shipCount = model.emptyCoordCount();
      }
      handleShots(shipCount);
      sendToAi();
    }
  }

  /**
   * Checks if a given coordinate is present in the provided list of coordinates.
   *
   * @param list The list of coordinates to be searched.
   * @param c    The coordinate to search for.
   * @return {@code true} if the coordinate is found in the list, {@code false} otherwise.
   */
  public boolean containsCoord(List<Coord> list, Coord c) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getCoordX() == c.getCoordX() && list.get(i).getCoordY() == c.getCoordY()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Ends the program and displays the provided game result message.
   *
   * @param result The game result.
   */
  public void endProgram(GameResult result) {
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
