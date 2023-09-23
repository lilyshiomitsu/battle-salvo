package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON represents a fleet
 *
 * @param fleet array of ships
 */
public record FleetBean(
    @JsonProperty("fleet") ShipBean[] fleet) {}
