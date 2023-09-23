package cs3500.pa04;

import cs3500.pa04.model.Board;
import cs3500.pa04.view.PlayerView;
import org.junit.jupiter.api.Test;

/**
 * tests the functionality of the player view class
 */
public class PlayerViewTest {

  @Test
  public void testDisplayBoard() {
    PlayerView view = new PlayerView();
    Board board = new Board(6, 6);
    view.displayBoard(board);
    String expected = "O O O O O O \n"
        + "O O O O O O \n"
        + "O O O O O O \n"
        + "O O O O O O \n"
        + "O O O O O O \n"
        + "O O O O O O ";
  }

  @Test
  public void testDisplayPrompt() {
    PlayerView vew = new PlayerView();
    String str = "hello";
    vew.displayPrompt(str);
  }
}
