package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.GameResult;
import org.junit.jupiter.api.Test;

/**
 * tests the GameResult enums
 */
public class GameResultTest {

  /**
   * tests the getMessage() method functionality
   */
  @Test
  public void testGetMessage() {
    String message = "Congratulations, you won!";
    assertEquals(GameResult.WON.getMessage(), message);
    String message2 = "Sorry, you lost.";
    assertEquals(GameResult.LOST.getMessage(), message2);
    String message3 = "You tied.";
    assertEquals(GameResult.TIE.getMessage(), message3);
  }
}
