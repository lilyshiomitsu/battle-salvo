package cs3500.pa03.model;

import java.util.List;

/**
 * represents a ship in the game
 */
public class Ship {
  private int size;
  private int hitsTaken;
  private List<Coord> location;

  /**
   * Constructs a ship with the specified size, hits taken, and location.
   *
   * @param size        The size of the ship.
   * @param hitsTaken   The number of hits taken by the ship.
   * @param location    The list of coordinates representing the ship's  location.
   */
  public Ship(int size, int hitsTaken, List<Coord> location) {
    this.size = size;
    this.hitsTaken = hitsTaken;
    this.location = location;

  }

  /**
   * Checks if the ship has sunk based on the hit count.
   *
   * @param hitCount The number of hits received by the ship.
   * @return True if the ship has sunk, false otherwise.
   */
  public boolean hasSunk(int hitCount) {
    return size == hitCount;
  }

  /**
   * Retrieves the location of the ship.
   *
   * @return The list of coordinates representing the ship's location.
   */
  public List<Coord> getLocation() {
    return this.location;
  }

  /**
   * Retrieves the size of the ship.
   *
   * @return The size of the ship.
   */
  public int getSize() {
    return this.size;
  }



}
