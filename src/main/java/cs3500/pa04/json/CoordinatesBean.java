package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The {@code CoordinatesBean} class represents a bean class used to store an
 * array of {@link CoordAdapter} objects
 * as a property named "coordinates".
 */
public record CoordinatesBean(
    @JsonProperty("coordinates") CoordAdapter[] coordinates) {}
