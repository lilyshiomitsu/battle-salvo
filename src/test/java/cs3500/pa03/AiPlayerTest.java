package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.HumanPlayer;
import cs3500.pa03.model.ShipType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * test for the AIPlayer class
 */
public class AiPlayerTest {

  /*@Test
  public void testSetup() {
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 1);
    AiPlayer ai = new AiPlayer();
    ai.setup(6, 6, specifications);
    PlayerView view = new PlayerView();
    // confirm that ships are placed correctly through view
    view.displayBoard(ai.getBoard());
  }*/

  @Test
  public void testTakeShots() {
    // Create a sample AIPlayer object
    Map<ShipType, Integer> specs = new HashMap<>();
    specs.put(ShipType.CARRIER, 1);
    specs.put(ShipType.DESTROYER, 1);
    specs.put(ShipType.BATTLESHIP, 1);
    specs.put(ShipType.SUBMARINE, 1);
    AiPlayer aiPlayer = new AiPlayer();
    aiPlayer.setup(10, 10, specs);
    System.out.println(aiPlayer.shipsLeft());

    // Call the takeShots method
    HumanPlayer humanPlayer = new HumanPlayer();
    aiPlayer.setHumanPlayer(humanPlayer);
    List<Coord> coordList = Arrays.asList(new Coord(1, 2), new Coord(3, 4),
        new Coord(3, 1), new Coord(5, 4));
    aiPlayer.receiveShots(coordList);
    //aiPlayer.setBoard(new Board(10, 10));
    humanPlayer.setup(10, 10, specs);
    List<Coord> shots = aiPlayer.takeShots();

    // Verify the result
    assertEquals(4, shots.size());

  }
}
