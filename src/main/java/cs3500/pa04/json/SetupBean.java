package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The {@code SetupBean} class represents a bean class used to store setup information for a game.
 * It includes the width and height of the game board, as well as the fleet specification.
 */
public record SetupBean(
    @JsonProperty("width") int width,
    @JsonProperty("height") int height,
    @JsonProperty("fleet-spec") SpecBean specBean
) {
}
