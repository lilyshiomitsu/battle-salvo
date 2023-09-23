package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The {@code JoinBean} class represents a bean class used to store information for joining a game.
 * It includes the player name and the game type.
 */
public record JoinBean(
    @JsonProperty("name") String name,
    @JsonProperty("game-type") String gameType) {
  /**
   * Constructs a new {@code JoinBean} object with the specified game type and player name.
   *
   * @param gameType the game type
   * @param name     the player name
   * @return a new {@code JoinBean} object
   */
  public JoinBean joinBean(GameType gameType, String name) {
    return new JoinBean(name, gameType.toString());
  }
}


