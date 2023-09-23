package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.model.AiPlayer;
import cs3500.pa04.model.AiPlayerDependency;
import cs3500.pa04.model.Board;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.DependencyInformation;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.ShipType;
import cs3500.pa04.model.Status;
import cs3500.pa04.view.PlayerView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * test for the AIPlayer class
 */
public class AiPlayerTest {

  @Test
  public void testSetup() {
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 1);
    AiPlayerDependency aiPlayerDependency = new AiPlayerDependency();
    aiPlayerDependency.getAiPlayer().setup(6, 6, specifications);
    PlayerView view = new PlayerView();
    // confirm that ships are placed correctly through view
    view.displayBoard(aiPlayerDependency.getInformation().getBoard());
  }

  @Test
  public void testTakeShots() {
    // Create a sample AIPlayer object
    AiPlayerDependency aiPlayerDependency = new AiPlayerDependency();
    aiPlayerDependency.getInformation().setEnemyBoard(new Board(10, 10));
    Map<ShipType, Integer> specs = new HashMap<>();
    specs.put(ShipType.CARRIER, 1);
    specs.put(ShipType.DESTROYER, 1);
    specs.put(ShipType.BATTLESHIP, 1);
    specs.put(ShipType.SUBMARINE, 1);
    aiPlayerDependency.getAiPlayer().setup(10, 10, specs);
    System.out.println(aiPlayerDependency.getInformation().shipsLeft());

    // Call the takeShots method
    List<Coord> shots = aiPlayerDependency.getAiPlayer().takeShots();

    // Verify the result
    assertEquals(aiPlayerDependency.getInformation().shipsLeft(), shots.size());

    for (Coord shot : shots) {
      assertTrue(aiPlayerDependency.getInformation().getTotalShots().contains(shot));
      assertTrue(shot.getStatus() == Status.HIT || shot.getStatus() == Status.MISSED);
    }
  }

  @Test
  public void testEndGame() {
    AiPlayerDependency aiPlayerDependency = new AiPlayerDependency();
    AiPlayer aiPlayer = aiPlayerDependency.getAiPlayer();
    assertFalse(aiPlayer.endGame(GameResult.WIN, "string"));
  }

}
