package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Ship;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * test for the Ship class
 */
public class ShipTest {

  /**
   * tests the hasSunk() method
   */
  @Test
  public void testHasSunk() {
    Ship s = new Ship(3, 3, new ArrayList<>());
    assertTrue(s.hasSunk(3));
    assertFalse(s.hasSunk(2));
  }

  @Test
  public void testGetLocation() {
    List<Coord> location = new ArrayList<>();
    Ship s = new Ship(3, 3, location);
    assertEquals(location, s.getLocation());
  }

  @Test
  public void testGetSize() {
    Ship s = new Ship(3, 3, new ArrayList<>());
    assertEquals(3, s.getSize());
  }
}
