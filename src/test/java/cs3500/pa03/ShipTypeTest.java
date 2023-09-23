package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import org.junit.jupiter.api.Test;

/**
 * test the ShipType enum
 */

public class ShipTypeTest {

  /**
   * tests the functionality of the ShipType enum and the size getter
   */
  @Test
  public void testGetShipTypeSize() {
    assertEquals(3, ShipType.SUBMARINE.getSize());
    assertEquals(4, ShipType.DESTROYER.getSize());
    assertEquals(5, ShipType.BATTLESHIP.getSize());
    assertEquals(6, ShipType.CARRIER.getSize());
    assertNotEquals(3, ShipType.BATTLESHIP.getSize());
  }
}
