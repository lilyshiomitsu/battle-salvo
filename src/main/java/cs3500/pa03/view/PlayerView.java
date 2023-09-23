package cs3500.pa03.view;

import cs3500.pa03.model.Board;
import cs3500.pa03.model.Coord;

/**
 * The PlayerView class is responsible for displaying game-related information to the player.
 */
public class PlayerView {

  /**
   * Displays the game board.
   *
   * @param board The board to be displayed.
   */
  public void displayBoard(Board board) {
    String boardView = "";
    for (Coord c : board.getTotalCoords()) {
      boardView += c.getStatus().getIcon() + " ";
      if (c.getCoordX() == board.getWidth() - 1) {
        boardView += "\n";
      }
    }
    System.out.println(boardView);
  }

  /**
   * Displays a prompt or message to the player.
   *
   * @param str The prompt or message to be displayed.
   */
  public void displayPrompt(String str) {
    System.out.println(str);
  }
}
