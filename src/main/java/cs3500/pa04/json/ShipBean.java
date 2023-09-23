package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The {@code FleetBean} class represents a bean class used to store information about a fleet.
 * It includes the coordinate, length, and direction of the fleet.
 */
public record ShipBean(
    @JsonProperty("coord") CoordAdapter coord,
    @JsonProperty("length") int length,
    @JsonProperty("direction") String direction) {

  /**
   * Constructs a new {@code FleetBean} object with the specified coordinate, length, and direction.
   *
   * @param coord     the coordinate of the fleet
   * @param length    the length of the fleet
   * @param direction the direction of the fleet
   * @return a new {@code FleetBean} object
   */
  public ShipBean shipBean(CoordAdapter coord, int length, Direction direction) {
    return new ShipBean(coord, length, direction.toString());
  }
}
