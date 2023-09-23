package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import cs3500.pa04.controller.AiController;
import cs3500.pa04.model.HumanPlayer;
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
    assertNotNull(aiController.getDependencyModel().getAiPlayer());
    assertNotNull(aiController.getDependencyModel().getInformation().getBoard());
    assertNotNull(humanPlayer.getOpponentBoard());
  }
}
