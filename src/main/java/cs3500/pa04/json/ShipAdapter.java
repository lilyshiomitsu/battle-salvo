package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.Ship;
import java.util.List;

/**
 * The {@code ShipAdapter} class represents an adapter for converting a
 * {@link Ship} object to a JSON-compatible format.
 * It provides methods to retrieve the adapted ship's coordinate, length, and direction.
 */
public class ShipAdapter {

  private CoordAdapter coord;
  private int length;
  private Direction direction;

  /**
   * Constructs a new {@code ShipAdapter} object by adapting the given {@link Ship} object.
   *
   * @param myShip the ship to adapt
   */
  public ShipAdapter(Ship myShip) {
    this.coord = new CoordAdapter(myShip.getLocation().get(0));
    this.length = myShip.getLocation().size();
    this.direction = determineDirection(myShip.getLocation());
  }

  /**
   * Constructs a new {@code ShipAdapter} object using the given fields
   *
   * @param coord the first coordinate of the ship
   * @param length the length of the ship
   * @param direction the direction of the ship
   */
  @JsonCreator
  public ShipAdapter(
      @JsonProperty("coord") CoordAdapter coord,
      @JsonProperty("length") int length,
      @JsonProperty("direction") Direction direction) {
    this.coord = coord;
    this.length = length;
    this.direction = direction;
  }

  /**
   * Returns the adapted ship's coordinate.
   *
   * @return the adapted ship's coordinate
   */
  public CoordAdapter getCoordAdapter() {
    return this.coord;
  }

  /**
   * Returns the adapted ship's length.
   *
   * @return the adapted ship's length
   */
  public int getLength() {
    return this.length;
  }

  /**
   * Returns the adapted ship's direction.
   *
   * @return the adapted ship's direction
   */
  public Direction getDirection() {
    return this.direction;
  }

  /**
   * Determines the direction of the ship based on its location coordinates.
   * If the ship's coordinates are vertically aligned, returns {@link Direction#VERTICAL}.
   * If the ship's coordinates are horizontally aligned, returns {@link Direction#HORIZONTAL}.
   *
   * @param location the location coordinates of the ship
   * @return the direction of the ship
   */
  private Direction determineDirection(List<Coord> location) {
    if (location.get(0).getCoordX() == location.get(1).getCoordX()) {
      return Direction.VERTICAL;
    } else if (location.get(0).getCoordY() == location.get(1).getCoordY()) {
      return Direction.HORIZONTAL;
    }
    return null;
  }
}
