package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.model.Coord;
import cs3500.pa04.model.Status;
import org.junit.jupiter.api.Test;

/**
 * test for the Coord class
 */
public class CoordTest {
  @Test
  public void testCoord() {
    Coord c = new Coord(1, 2);
    assertEquals(c.getStatus(), null);
    c.setStatus(Status.EMPTY);
    assertEquals(c.getStatus(), Status.EMPTY);
    assertEquals(c.getCoordX(), 1);
    assertEquals(c.getCoordY(), 2);
  }

}
