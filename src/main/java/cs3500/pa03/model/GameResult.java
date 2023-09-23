package cs3500.pa03.model;

/**
 * represents an enum of the possible game results
 */
public enum GameResult {
  WON("Congratulations, you won!"),
  LOST("Sorry, you lost."),
  TIE("You tied.");

  private final String message;

  /**
   * constructor for the game result enum
   *
   * @param message to print to user
   */
  GameResult(String message) {
    this.message = message;
  }

  /**
   * returns the message associated with this enum
   *
   * @return message to print to user
   */
  public String getMessage() {
    return this.message;
  }
}
