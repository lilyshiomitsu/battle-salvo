package cs3500.pa04;

import cs3500.pa04.model.Board;
import cs3500.pa04.model.HumanPlayer;
import cs3500.pa04.model.ShipType;
import cs3500.pa04.model.Status;
import cs3500.pa04.view.PlayerView;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * test the Board class
 */
public class BoardTest {

  @Test
  public void isValidPlacementTest() {
    Map<ShipType, Integer> specs = new HashMap<>();
    specs.put(ShipType.CARRIER, 1);
    specs.put(ShipType.DESTROYER, 1);
    specs.put(ShipType.BATTLESHIP, 1);
    specs.put(ShipType.SUBMARINE, 1);
    HumanPlayer help = new HumanPlayer();
    PlayerView view = new PlayerView();
    help.setup(7, 7, specs);
    view.displayBoard(help.getBoard());
  }

  @Test
  public void boardConstructorTest() {
    Board b = new Board(6, 6);
    PlayerView view = new PlayerView();
    view.displayBoard(b);
  }

  @Test
  public void addShipTest() {
    Board board = new Board(6, 6);
    board.addShip(1, 3);
    board.addShip(1, 4);
    board.addShip(1, 5);
    board.addShip(1, 6);
    PlayerView view = new PlayerView();
    view.displayBoard(board);

  }

  @Test
  public void testSetCoordStatus() {
    Board board = new Board(10, 10);
    board.setCoordStatus(11, 12, Status.EMPTY);
    // tests the null branch
  }

}
