package cs3500.pa04.model;

import java.util.ArrayList;
import java.util.List;

/**
 * class for dependency information
 */
public class DependencyInformation {
  private List<Coord> shots;
  private Board enemyBoard;
  private Board board;
  private List<Ship> shipLocations = new ArrayList<>();
  private int shipCount;
  private List<Coord> totalShots;

  /**
   * constructor for dependency information
   */
  public DependencyInformation() {
    this.shots = new ArrayList<>();
    this.shipLocations = new ArrayList<>();
    this.shipCount = shipsLeft();
    this.totalShots = new ArrayList<>();
  }

  /**
   * Returns the list of shots.
   *
   * @return The list of shots.
   */
  public List<Coord> getShots() {
    return shots;
  }

  /**
   * Sets the list of total shots.
   *
   * @param shots The list of total shots.
   */
  public void setTotalShots(List<Coord> shots) {
    this.totalShots = totalShots;
  }

  /**
   * Returns the list of total shots on the opponents board.
   *
   * @return The list of total shots on the opponents board.
   */
  public List<Coord> getTotalShots() {
    return totalShots;
  }

  /**
   * Sets the list of shots.
   *
   * @param shots The list of shots.
   */
  public void setShots(List<Coord> shots) {
    this.shots = shots;
  }


  /**
   * Sets the board for the enemy
   *
   * @param enemyBoard The enemy board
   */
  public void setEnemyBoard(Board enemyBoard) {
    this.enemyBoard = enemyBoard;
  }

  /**
   * Returns the number of ships that have not yet sunk.
   *
   * @return The number of ships remaining.
   */
  public int getShipCount() {
    return shipCount;
  }

  /**
   * Returns the game board.
   *
   * @return The game board.
   */
  public Board getBoard() {
    return board;
  }

  /**
   * Sets the game board.
   *
   * @param board The game board.
   */
  public void setBoard(Board board) {
    this.board = board;
  }

  /**
   * Returns the list of ship locations.
   *
   * @return The list of ship locations.
   */
  public List<Ship> getShipLocations() {
    return shipLocations;
  }

  /**
   * Returns the count of ships that have not yet sunk.
   *
   * @return The count of ships remaining.
   */
  public int shipsLeft() {
    int count = shipLocations.size();
    for (Ship s : shipLocations) {
      int hitCount = 0;
      for (Coord c : s.getLocation()) {
        if (this.getCoord(c.getCoordX(), c.getCoordY(), board).getStatus() == Status.HIT) {
          hitCount++;
        }
      }
      if (s.hasSunk(hitCount)) {
        count--;
      }
    }
    shipCount = count;
    return count;
  }

  /**
   * Retrieves the coordinate at the specified (x, y) position on the given game board.
   *
   * @param x     The x-coordinate.
   * @param y     The y-coordinate.
   * @param board The game board.
   * @return The coordinate at the specified position.
   */
  public Coord getCoord(int x, int y, Board board) {
    return board.getTotalCoords().get(y * board.getWidth() + x);
  }

  /**
   * Returns the enemy board
   *
   * @return The enemy board
   */
  public Board getEnemyBoard() {
    return this.enemyBoard;
  }

}
