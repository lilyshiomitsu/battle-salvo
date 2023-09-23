package cs3500.pa04.model;


/**
 * represents a single coordinate pair on the board
 */
public class Coord {
  private int coordX;
  private int coordY;
  private Status status;

  /**
   * Constructs a Coord object with the specified x and y coordinates and initial status.
   *
   * @param coordX      The x-coordinate of the coordinate.
   * @param coordY      The y-coordinate of the coordinate.
   */

  public Coord(int coordX, int coordY) {
    this.coordX = coordX;
    this.coordY = coordY;
  }

  /**
   * Sets the status of the coordinate.
   *
   * @param status The status to set for the coordinate.
   */
  public void setStatus(Status status) {
    this.status = status;
  }

  /**
   * Retrieves the status of the coordinate.
   *
   * @return The status of the coordinate.
   */
  public Status getStatus() {
    return this.status;
  }

  /**
   * Retrieves the x-coordinate of the coordinate.
   *
   * @return The x-coordinate.
   */
  public int getCoordX() {
    return this.coordX;
  }

  /**
   * Retrieves the y-coordinate of the coordinate.
   *
   * @return The y-coordinate.
   */
  public int getCoordY() {
    return this.coordY;
  }
}
