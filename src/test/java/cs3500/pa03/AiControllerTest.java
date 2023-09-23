package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.controller.AiController;
import cs3500.pa03.model.Board;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.HumanPlayer;
import cs3500.pa03.model.Status;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * test for the AIController class
 */
public class AiControllerTest {
  private AiController aiController;

  @BeforeEach
  public void setUp() {
    aiController = new AiController();
  }

  @Test
  public void run() {
    // Set up the input parameters
    ArrayList<Integer> input = new ArrayList<>(Arrays.asList(10, 10, 1, 2, 3, 4));

    // Create a human player
    HumanPlayer humanPlayer = new HumanPlayer();

    // Set the human player in the AI controller
    aiController.setHumanPlayer(humanPlayer);

    // Receive user input
    aiController.receiveUserInput(input);

    // Run the AI controller
    aiController.run();

    // Assertions
    assertNotNull(aiController.getModel());
    assertNotNull(aiController.getModel().getBoard());
    assertNotNull(humanPlayer.getOpponentBoard());
  }


  @Test
  public void testGenerateShots() {
    ArrayList<Integer> input = new ArrayList<>();
    input.add(10); // width
    input.add(10); // height
    input.add(1);  // carrier count
    input.add(2);  // battleship count
    input.add(3);  // destroyer count
    input.add(4);  // submarine count

    aiController.receiveUserInput(input);
    HumanPlayer humanPlayer = new HumanPlayer();
    aiController.setHumanPlayer(humanPlayer);
    humanPlayer.setBoard(new Board(10, 10));
    aiController.run();

    aiController.generateShots();

    assertTrue(aiController.getShipCount() >= 0);
    assertTrue(aiController.getShipCount() <= aiController.getModel().emptyCoordCount());
  }
}

