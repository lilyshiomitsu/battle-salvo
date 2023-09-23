package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.ShipType;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code SpecBean} class represents a bean class used to store specifications
 * for different ship types.
 * It includes the number of each ship type: carrier, battleship, destroyer, and submarine.
 */
public record SpecBean(
    @JsonProperty("CARRIER") int carrier,
    @JsonProperty("BATTLESHIP") int battleship,
    @JsonProperty("DESTROYER") int destroyer,
    @JsonProperty("SUBMARINE") int submarine) {

  /**
   * Returns a map of ship type to the corresponding number of ships specified.
   *
   * @return a map of ship type to the corresponding number of ships
   */
  public Map<ShipType, Integer> specifications() {
    Map<ShipType, Integer> specs = new HashMap<>();
    specs.put(ShipType.CARRIER, carrier);
    specs.put(ShipType.BATTLESHIP, battleship);
    specs.put(ShipType.DESTROYER, destroyer);
    specs.put(ShipType.SUBMARINE, submarine);
    return specs;
  }

}