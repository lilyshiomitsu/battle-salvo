package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The {@code EndGameBean} class represents a bean class used to store
 * information about the end of a game.
 * It includes a result and a reason for the end of the game.
 */
public record EndGameBean(
    @JsonProperty("result") String result,
    @JsonProperty("reason") String reason
) {
}
