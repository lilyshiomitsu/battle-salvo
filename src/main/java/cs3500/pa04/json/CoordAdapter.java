package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.Coord;

/**
 * The {@code CoordAdapter} class is an adapter class used to convert a {@link Coord}
 * object into a JSON-friendly format.
 * It provides methods to get the x and y coordinates of the adapted {@link Coord} object.
 */
public class CoordAdapter {

  private int coordX;
  private int coordY;

  /**
   * Constructs a new {@code CoordAdapter} object by adapting the given {@link Coord} object.
   *
   * @param c the {@link Coord} object to be adapted
   */
  public CoordAdapter(Coord c) {
    this.coordX = c.getCoordX();
    this.coordY = c.getCoordY();
  }

  /**
   * Constructs a new {@code CoordAdapter} object with the specified x and y coordinates.
   *
   * @param x the x coordinate
   * @param y the y coordinate
   */
  @JsonCreator
  public CoordAdapter(@JsonProperty("x") int x,
                      @JsonProperty("y") int y) {
    this.coordX = x;
    this.coordY = y;
  }

  /**
   * Returns the x coordinate of the adapted {@link Coord} object.
   *
   * @return the x coordinate
   */
  public int getX() {
    return coordX;
  }

  /**
   * Returns the y coordinate of the adapted {@link Coord} object.
   *
   * @return the y coordinate
   */
  public int getY() {
    return coordY;
  }
}
