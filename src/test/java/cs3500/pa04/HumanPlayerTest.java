package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.model.Board;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.HumanPlayer;
import cs3500.pa04.model.ShipType;
import cs3500.pa04.model.Status;
import cs3500.pa04.view.PlayerView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * test for the HumanPlayer class
 */
public class HumanPlayerTest {

  @Test
  public void setOpponentBoardTest() {
    HumanPlayer player = new HumanPlayer();

    Board board = new Board(6, 6);
    player.setOpponentBoard(board);

    // assert that the board equals player's opponentBoard
    assertEquals(board, player.getOpponentBoard());
  }

  @Test
  public void getCoordTest() {
    HumanPlayer test = new HumanPlayer();
    Board board = new Board(6, 6);
    test.setOpponentBoard(board);
    Coord c1 = test.getCoord(0, 0, test.getOpponentBoard());
    assertEquals(c1, test.getOpponentBoard().getTotalCoords().get(0));
  }

  @Test
  public void testSetup2() {
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 1);
    HumanPlayer hp = new HumanPlayer();
    hp.setup(6, 6, specifications);
    PlayerView view = new PlayerView();
    view.displayBoard(hp.getBoard());
  }


  @Test
  public void testReportDamage() {

    HumanPlayer player = new HumanPlayer();
    player.setBoard(new Board(10, 10));
    List<Coord> opponentShots = new ArrayList<>();
    opponentShots.add(new Coord(0, 0));
    opponentShots.add(new Coord(3, 2));
    opponentShots.add(new Coord(2, 5));

    List<Coord> damage = player.reportDamage(opponentShots);

    assertEquals(0, damage.size());
    for (Coord coord : damage) {
      Coord hitCoord = player.getCoord(coord.getCoordX(), coord.getCoordY(), player.getBoard());
      Assertions.assertTrue(
          hitCoord.getStatus() == Status.HIT || hitCoord.getStatus() == Status.MISSED);
    }
  }

  @Test
  public void takeShotsTest() {

  }

  @Test
  public void shipsLeftTest() {
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 1);
    HumanPlayer hp = new HumanPlayer();
    hp.setup(6, 6, specifications);
    // assert that there are 4 ships
    assertEquals(4, hp.getShipLocations().size());
  }

  /**
   * tests the emptyCoordCount() method
   */
  @Test
  public void testEmptyCoordCount() {
    HumanPlayer hp = new HumanPlayer();
    hp.setBoard(new Board(6, 6));
    assertEquals(36, hp.emptyCoordCount());
    Coord c = hp.getCoord(3, 4, hp.getBoard());
    c.setStatus(Status.HIT);
    assertEquals(35, hp.emptyCoordCount());
    Coord c2 = hp.getCoord(3, 2, hp.getBoard());
    c2.setStatus(Status.OCCUPIED);
    assertEquals(35, hp.emptyCoordCount());
  }
}
