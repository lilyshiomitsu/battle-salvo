package cs3500.pa03.model;

/**
 * Represents the status of a coordinate on the game board.
 */
public enum Status {
  EMPTY("O"),
  OCCUPIED("S"),
  HIT("X"),
  MISSED("M");

  private final String icon;

  /**
   * Constructs a Status enum with the specified icon.
   *
   * @param icon The icon representation of the status.
   */
  Status(String icon) {
    this.icon = icon;
  }

  /**
   * Retrieves the icon representation of the status.
   *
   * @return The icon representation of the status.
   */
  public String getIcon() {
    return icon;
  }
}
