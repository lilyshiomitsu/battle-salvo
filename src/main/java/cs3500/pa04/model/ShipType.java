package cs3500.pa04.model;

/**
 * represents the types of ships in the game
 */
public enum ShipType {
  CARRIER(6),
  BATTLESHIP(5),
  DESTROYER(4),
  SUBMARINE(3);

  private final int size;

  /**
   * Constructs a ShipType enum with the specified size.
   *
   * @param size The size of the ship type.
   */
  ShipType(int size) {
    this.size = size;
  }

  /**
   * Retrieves the size of the ship type.
   *
   * @return The size of the ship type.
   */
  public int getSize() {
    return size;
  }
}
