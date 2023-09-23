package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.view.PlayerView;
import cs3500.pa04.controller.AiController;
import cs3500.pa04.controller.HumanController;
import cs3500.pa04.model.Board;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.HumanPlayer;
import cs3500.pa04.model.ShipType;
import cs3500.pa04.model.Status;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * test the HumanController class
 */
public class HumanControllerTest {
  private AiController aiController;
  private HumanController humanController;
  private final InputStream sysInBackup = System.in;
  private final PlayerView playerView = new PlayerView();

  /**
   * sets up the ai controller and human controller before each test
   */
  @BeforeEach
  public void setUp() {
    aiController = new AiController();
    //TODO: make this have enough inputs so we dont run out of shots just in case
    humanController = new HumanController(new StringReader("6\n6\n1\n1\n1\n1\n"));
  }

  /**
   * Creates a board with a set layout
   *
   * @return Board with a predetermined layout
   */
  public Board mockBoard() {
    Board board = new Board(6, 6);
    for (int i = 0; i < 4; i++) {
      for (int j = i; j < 6; j++) {
        board.getCoord(i, j).setStatus(Status.OCCUPIED);
      }
    }
    return board;
  }

  @Test
  public void testRunWin() {
    // Create a test input string
    String inputString = "6\n18\nd\n6\n15\n1\n1\n1\n"
        + "1\n1\n1\n1\n"
        + "0\n0\n0\n1\n0\n2\n0\n3\n0\n4\n0\n5\n1\n0\n1\n1\n1\n2\n1\n3\n1\n4\n1\n5\n"
        + "2\n0\n2\n1\n2\n2\n2\n3\n2\n4\n2\n5\n3\n0\n3\n1\n3\n2\n3\n3\n3\n4\n3\n5\n4\n"
        + "0\n4\n1\n4\n2\n4\n3\n4\n4\n4\n5\n"
        + "5\n0\n5\n1\n5\n2\n5\n3\n5\n4\n5\n5\n"
        + "0\n4\n1\n4\n2\n4\n3\n4\n4\n4\n5\n";
    // Create a controller with the test input
    Readable input = new StringReader(inputString);
    HumanController controller = new HumanController(input);

    controller.run();
  }

  @Test
  public void testUpdateShotsLoss() {
    HashMap<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 1);
    AiController aiPlayer = new AiController();
    aiPlayer.getDependencyModel().getAiPlayer().setup(6, 6, specifications);
    aiPlayer.getDependencyModel().getInformation().setShots(new ArrayList<>());
    aiPlayer.getDependencyModel().getInformation().setEnemyBoard(new Board(6, 6));
    HumanPlayer player = new HumanPlayer();
    player.receiveShots(new ArrayList<>());
    Board playerBoard = new Board(6, 6);
    Board enemyBoard = new Board(6, 6);
    player.setBoard(playerBoard);
    player.setOpponentBoard(enemyBoard);
    Readable input = new StringReader("");
    HumanController human = new HumanController(input, player, aiPlayer);
    human.updateShots(0);
  }

  @Test
  public void testUpdateShotsDraw() {
    HashMap<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 0);
    specifications.put(ShipType.BATTLESHIP, 0);
    specifications.put(ShipType.DESTROYER, 0);
    specifications.put(ShipType.SUBMARINE, 0);
    AiController aiPlayer = new AiController();
    aiPlayer.getDependencyModel().getAiPlayer().setup(6, 6, specifications);
    aiPlayer.getDependencyModel().getInformation().setShots(new ArrayList<>());
    aiPlayer.getDependencyModel().getInformation().setEnemyBoard(new Board(6, 6));
    HumanPlayer player = new HumanPlayer();
    player.receiveShots(new ArrayList<>());
    Board playerBoard = new Board(6, 6);
    Board enemyBoard = new Board(6, 6);
    player.setBoard(playerBoard);
    player.setOpponentBoard(enemyBoard);
    Readable input = new StringReader("");
    HumanController human = new HumanController(input, player, aiPlayer);
    human.updateShots(0);
  }

  @Test
  public void testUpdateShotsOverflow() {
    HashMap<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 0);
    specifications.put(ShipType.BATTLESHIP, 0);
    specifications.put(ShipType.DESTROYER, 0);
    specifications.put(ShipType.SUBMARINE, 0);
    HashMap<ShipType, Integer> specificationsPlayer = new HashMap<>();
    specificationsPlayer.put(ShipType.CARRIER, 6);
    specificationsPlayer.put(ShipType.BATTLESHIP, 0);
    specificationsPlayer.put(ShipType.DESTROYER, 0);
    specificationsPlayer.put(ShipType.SUBMARINE, 0);
    AiController aiPlayer = new AiController();
    aiPlayer.getDependencyModel().getAiPlayer().setup(6, 6, specifications);
    aiPlayer.getDependencyModel().getInformation().setShots(new ArrayList<>());
    aiPlayer.getDependencyModel().getInformation().setEnemyBoard(new Board(6, 6));
    HumanPlayer player = new HumanPlayer();
    player.receiveShots(new ArrayList<>());
    player.setup(6, 6, specificationsPlayer);
    Readable input = new StringReader("");
    HumanController human = new HumanController(input, player, aiPlayer);
    human.updateShots(0);
  }

  @Test
  public void testIsValidTarget() {
    String inputString = "";
    Readable input = new StringReader(inputString);
    HumanController controller = new HumanController(input);
    Coord c = new Coord(3, 2);
    assertTrue(controller.isValidTarget(c, 6, 6));
    assertFalse(controller.isValidTarget(new Coord(15, 15), 6, 6));
    assertFalse(controller.isValidTarget(new Coord(-1, -1), 5, 5));
    assertFalse(controller.isValidTarget(new Coord(7, -1), 4, 4));
    assertFalse(controller.isValidTarget(new Coord(7, 15), 4, 4));
    c.setStatus(Status.MISSED);
    assertTrue(controller.isValidTarget(c, 5, 5));
  }
}



