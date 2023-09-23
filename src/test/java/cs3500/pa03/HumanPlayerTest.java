package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.model.Board;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.HumanPlayer;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.model.Status;
import cs3500.pa03.view.PlayerView;
import java.util.ArrayList;
import java.util.Arrays;
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
  public void testReportDamageHits() {

    Map<ShipType, Integer> specs = new HashMap<>();
    specs.put(ShipType.CARRIER, 6);
    specs.put(ShipType.BATTLESHIP, 0);
    specs.put(ShipType.SUBMARINE, 0);
    specs.put(ShipType.DESTROYER, 0);
    HumanPlayer player = new HumanPlayer();
    player.setup(6, 6, specs);
    List<Coord> opponentShots = new ArrayList<>();
    opponentShots.add(new Coord(0, 0));
    opponentShots.add(new Coord(3, 2));
    opponentShots.add(new Coord(2, 5));

    List<Coord> damage = player.reportDamage(opponentShots);

    assertEquals(3, damage.size());

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

  @Test
  public void testReportDamage2() {
    HumanPlayer humanPlayer = new HumanPlayer();
    Board board = new Board(6, 6);
    board.setCoordStatus(0, 0, Status.OCCUPIED);
    board.setCoordStatus(0, 1, Status.EMPTY);
    humanPlayer.setBoard(board);
    List<Coord> list = Arrays.asList(new Coord(0, 0), new Coord(0, 1));
    List<Coord> damage = humanPlayer.reportDamage(list);
    assertEquals(damage.size(), 1);

  }
}
