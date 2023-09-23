package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * represents the model for the AIPlayer
 */
public class AiPlayer implements Player {
  private Board board;
  private HumanPlayer humanPlayer;
  private List<Coord> shots;
  private List<Ship> shipLocations = new ArrayList<>();
  private List<Coord> damage = new ArrayList<>();

  /**
   * Get the player's name.
   *
   * @return the player's name
   */

  @Override
  public String name() {
    return null;
  }

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    board = new Board(height, width);
    int carrierCount = specifications.get(ShipType.CARRIER);
    int battleshipCount = specifications.get(ShipType.BATTLESHIP);
    int destroyerCount = specifications.get(ShipType.DESTROYER);
    int submarineCount = specifications.get(ShipType.SUBMARINE);
    shipLocations.addAll(board.addShip(carrierCount, ShipType.CARRIER.getSize()));
    shipLocations.addAll(board.addShip(battleshipCount, ShipType.BATTLESHIP.getSize()));
    shipLocations.addAll(board.addShip(destroyerCount, ShipType.DESTROYER.getSize()));
    shipLocations.addAll(board.addShip(submarineCount, ShipType.SUBMARINE.getSize()));
    return shipLocations;
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    for (Coord c : shots) {
      if (getCoord(c.getCoordX(), c.getCoordY(),
          humanPlayer.getBoard()).getStatus() == Status.OCCUPIED) {
        Coord a = getCoord(c.getCoordX(), c.getCoordY(), humanPlayer.getBoard());
        a.setStatus(Status.HIT);
      } else if (getCoord(c.getCoordX(), c.getCoordY(),
          humanPlayer.getBoard()).getStatus() == Status.EMPTY) {
        Coord b = getCoord(c.getCoordX(), c.getCoordY(), humanPlayer.getBoard());
        b.setStatus(Status.MISSED);
      }
    }
    humanPlayer.reportDamage(shots);
    return shots;
  }

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit a
   *     ship on this board
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    for (Coord opponentShot : opponentShotsOnBoard) {
      Coord hit = getCoord(opponentShot.getCoordX(), opponentShot.getCoordY(), board);
      if (hit.getStatus() == Status.OCCUPIED) {
        hit.setStatus(Status.HIT);
        damage.add(hit);
      } else if (hit.getStatus() == Status.EMPTY) {
        hit.setStatus(Status.MISSED);
      }
    }
    return damage;
  }

  /**
   * Returns the number of ships that have not yet sunk
   *
   * @return the number of ships remaining
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
    return count;
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {

  }

  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {

  }

  /**
   * Retrieves the game board associated with the AIPlayer.
   *
   * @return The game board.
   */
  public Board getBoard() {
    return this.board;
  }

  /**
   * sets the board as the board for the AIPlayer
   *
   * @param board the board
   */

  public void setBoard(Board board) {
    this.board = board;
  }


  /**
   * Sets the human player as the opponent for the AIPlayer.
   *
   * @param humanPlayer The human player object.
   */
  public void setHumanPlayer(HumanPlayer humanPlayer) {
    this.humanPlayer = humanPlayer;
  }

  /**
   * Receives a list of coordinates representing shots fired by the opponent.
   *
   * @param shots The list of shot coordinates.
   */
  public void receiveShots(List<Coord> shots) {
    this.shots = shots;
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
   * Counts the number of empty coordinates on the AIPlayer's game board.
   *
   * @return The count of empty coordinates.
   */
  public int emptyCoordCount() {
    int count = 0;
    for (Coord c : board.getTotalCoords()) {
      if (c.getStatus() == Status.EMPTY || c.getStatus() == Status.OCCUPIED) {
        count++;
      }
    }
    return count;
  }

}
