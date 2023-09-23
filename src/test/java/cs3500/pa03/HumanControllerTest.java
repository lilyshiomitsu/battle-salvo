package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.controller.AiController;
import cs3500.pa03.controller.HumanController;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.HumanPlayer;
import cs3500.pa03.model.ShipType;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * test the HumanController class
 */
public class HumanControllerTest {
  @Test
  public void testContainsCoord() {
    String inputString = "";
    Readable input = new StringReader(inputString);
    HumanController hc = new HumanController(input);
    List<Coord> list = new ArrayList<>();
    list.add(new Coord(2, 1));
    list.add(new Coord(1, 1));
    assertTrue(hc.containsCoord(list, new Coord(1, 1)));
    assertFalse(hc.containsCoord(list, new Coord(5, 5)));
  }

  @Test
  public void testRun() {
    // Create a test input string
    String inputString = "6\n18\nd\n6\n15\n1\n1\n1\n"
        + "1\n1\n1\n1\n"
        + "0\n0\n0\n1\n0\n2\n0\n3\n0\n4\n0\n5\n1\n0\n1\n1\n1\n2\n1\n3\n1\n4\n1\n5\n"
        + "2\n0\n2\n1\n2\n2\n2\n3\n2\n4\n2\n5\n3\n0\n3\n1\n3\n2\n3\n3\n3\n4\n3\n5\n4\n"
        + "0\n4\n1\n4\n2\n4\n3\n4\n4\n4\n5\n"
        + "5\n0\n5\n1\n5\n2\n5\n3\n5\n4\n5\n5\n";
    // Create a controller with the test input
    Readable input = new StringReader(inputString);
    HumanController controller =
        new HumanController(input);

    controller.run();
  }


  @Test
  public void testUpdateShotsLoss() {
    HashMap<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 1);
    HashMap<ShipType, Integer> specificationsPlayer = new HashMap<>();
    specificationsPlayer.put(ShipType.CARRIER, 0);
    specificationsPlayer.put(ShipType.BATTLESHIP, 0);
    specificationsPlayer.put(ShipType.DESTROYER, 0);
    specificationsPlayer.put(ShipType.SUBMARINE, 0);
    AiController aiPlayer = new AiController();
    aiPlayer.getModel().setup(6, 6, specifications);
    HumanPlayer player = new HumanPlayer();
    player.receiveShots(new ArrayList<>());
    player.setup(6, 6, specificationsPlayer);
    aiPlayer.setHumanPlayer(player);
    aiPlayer.getModel().setHumanPlayer(player);
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
    HashMap<ShipType, Integer> specificationsPlayer = new HashMap<>();
    specificationsPlayer.put(ShipType.CARRIER, 0);
    specificationsPlayer.put(ShipType.BATTLESHIP, 0);
    specificationsPlayer.put(ShipType.DESTROYER, 0);
    specificationsPlayer.put(ShipType.SUBMARINE, 0);
    AiController aiPlayer = new AiController();
    aiPlayer.getModel().setup(6, 6, specifications);
    HumanPlayer player = new HumanPlayer();
    player.receiveShots(new ArrayList<>());
    player.setup(6, 6, specificationsPlayer);
    aiPlayer.setHumanPlayer(player);
    aiPlayer.getModel().setHumanPlayer(player);
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
    aiPlayer.getModel().setup(6, 6, specifications);
    HumanPlayer player = new HumanPlayer();
    player.receiveShots(new ArrayList<>());
    player.setup(6, 6, specificationsPlayer);
    aiPlayer.setHumanPlayer(player);
    aiPlayer.getModel().setHumanPlayer(player);
    Readable input = new StringReader("");
    HumanController human = new HumanController(input, player, aiPlayer);
    human.updateShots(0);
  }

}
