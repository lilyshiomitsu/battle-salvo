package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.model.AiPlayerDependency;
import cs3500.pa04.model.ShipType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * tests for the dependency information class
 */
public class DependencyInformationTest {

  @Test
  public void testShipsLeft() {
    Map<ShipType, Integer> specs = new HashMap<>();
    specs.put(ShipType.CARRIER, 1);
    specs.put(ShipType.DESTROYER, 1);
    specs.put(ShipType.BATTLESHIP, 1);
    specs.put(ShipType.SUBMARINE, 1);
    AiPlayerDependency aiPlayerDependency = new AiPlayerDependency();
    aiPlayerDependency.getAiPlayer().setup(6, 6, specs);
    // assert that there are 4 ships
    assertEquals(4, aiPlayerDependency.getInformation().shipsLeft());
  }
}
