package cs3500.pa04.model;

import java.util.ArrayList;
import java.util.List;

/**
 * represents the players' boards
 */
public class Board {

  private int height;
  private int width;

  private List<Coord> totalCoords;

  /**
   * constructor for the board class
   *
   * @param height the height of the board
   * @param width  the width of the board
   */
  public Board(int height, int width) {
    this.height = height;
    this.width = width;
    totalCoords = new ArrayList<>();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Coord c = new Coord(j, i);
        c.setStatus(Status.EMPTY);
        totalCoords.add(c);
      }
    }
  }

  /**
   * Adds ships to the board with the specified count and size.
   *
   * @param shipCount The number of ships to add.
   * @param shipSize  The size of each ship.
   * @return A list of Ship objects representing the added ships.
   */
  public List<Ship> addShip(int shipCount, int shipSize) {
    List<Ship> shipLocations = new ArrayList<>();
    for (int i = 0; i < shipCount; i++) {
      int direction = (int) (Math.random() * 2);
      int randomX = (int) (Math.random() * width);
      int randomY = (int) (Math.random() * height);
      List<Coord> location = new ArrayList<>();
      while (!(this.isValidPlacement(randomX, randomY, shipSize, direction))) {
        randomX = (int) (Math.random() * width);
        randomY = (int) (Math.random() * height);
        direction = (int) (Math.random() * 2);
      }
      if (direction == 0) { // vertical direction
        for (int j = 0; j < shipSize; j++) {
          Coord c = new Coord(randomX, randomY + j);
          this.setCoordStatus(randomX, randomY + j, Status.OCCUPIED);
          location.add(c);
        }
        Ship s = new Ship(shipSize, 0, location);
        shipLocations.add(s);
      } else if (direction == 1) { // horizontal direction
        location = new ArrayList<Coord>();
        for (int j = 0; j < shipSize; j++) {
          Coord c = new Coord(randomX + j, randomY);
          this.setCoordStatus(randomX + j, randomY, Status.OCCUPIED);
          location.add(c);
        }
        Ship s = new Ship(shipSize, 0, location);
        shipLocations.add(s);
      }
    }
    return shipLocations;
  }

  /**
   * Checks if a ship can be placed at the specified coordinates and direction on the board.
   *
   * @param x         The x-coordinate of the starting position.
   * @param y         The y-coordinate of the starting position.
   * @param shipSize  The size of the ship.
   * @param direction The direction of the ship (0 for vertical, 1 for horizontal).
   * @return {@code true} if the ship placement is valid, {@code false} otherwise.
   */
  public boolean isValidPlacement(int x, int y, int shipSize, int direction) {
    if (direction == 0) {
      if (y + shipSize > height) {
        return false;
      }
      for (Coord c : totalCoords) {
        for (int i = 0; i < shipSize; i++) {
          if (c.getCoordX() == x && c.getCoordY() == y + i && c.getStatus() == Status.OCCUPIED) {
            return false;
          }
        }
      }
    } else if (direction == 1) {
      if (x + shipSize > width) {
        return false;
      }
      for (Coord c : totalCoords) {
        for (int i = 0; i < shipSize; i++) {
          if (c.getCoordX() == x + i && c.getCoordY() == y && c.getStatus() == Status.OCCUPIED) {
            return false;
          }
        }
      }
    }
    return true;
  }

  /**
   * Retrieves the height of the board.
   *
   * @return The height of the board.
   */
  public int getHeight() {
    return height;
  }

  /**
   * Sets the status of the coordinate at the specified (x, y) position on the board.
   *
   * @param x      The x-coordinate.
   * @param y      The y-coordinate.
   * @param status The status to set for the coordinate.
   */
  public void setCoordStatus(int x, int y, Status status) {
    Coord newCoord = null;
    for (Coord c : totalCoords) {
      if (c.getCoordX() == x && c.getCoordY() == y) {
        newCoord = c;
        break;
      }
    }
    if (newCoord != null) {
      newCoord.setStatus(status);
    }
  }

  /**
   * Retrieves the width of the board.
   *
   * @return The width of the board.
   */
  public int getWidth() {
    return width;
  }

  /**
   * Retrieves the list of all coordinates on the board.
   *
   * @return The list of all coordinates.
   */
  public List<Coord> getTotalCoords() {
    return this.totalCoords;
  }

  public Coord getCoord(int x, int y) {
    return totalCoords.get(y * width + x);
  }

  /**
   * Counts the number of empty coordinates on the AIPlayer's game board.
   *
   * @return The count of empty coordinates.
   */
  public int emptyCoordCount() {
    int count = 0;
    for (Coord c : this.getTotalCoords()) {
      if (c.getStatus() == Status.EMPTY || c.getStatus() == Status.OCCUPIED) {
        count++;
      }
    }
    return count;
  }

}
