package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * represents human player model
 */
public class HumanPlayer implements Player {

  private Board board;
  private Board opponentBoardData;
  private List<Ship> shipLocations = new ArrayList<>();
  private List<Coord> inputs;
  private AiPlayer aiPlayer = new AiPlayer();
  private Board opponentBoard = aiPlayer.getBoard();
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
    this.shipLocations = new ArrayList<>();
    board = new Board(height, width);
    int carrierCount = specifications.get(ShipType.CARRIER);
    int battleshipCount = specifications.get(ShipType.BATTLESHIP);
    int destroyerCount = specifications.get(ShipType.DESTROYER);
    int submarineCount = specifications.get(ShipType.SUBMARINE);
    shipLocations.addAll(board.addShip(carrierCount, ShipType.CARRIER.getSize()));
    shipLocations.addAll(board.addShip(battleshipCount, ShipType.BATTLESHIP.getSize()));
    shipLocations.addAll(board.addShip(destroyerCount, ShipType.DESTROYER.getSize()));
    shipLocations.addAll(board.addShip(submarineCount, ShipType.SUBMARINE.getSize()));
    opponentBoardData = new Board(height, width);
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
    for (Coord c : inputs) {
      // if coord on opponent board with same coord x and y status is ship, then add to successful
      if (getCoord(c.getCoordX(), c.getCoordY(), opponentBoard).getStatus() == Status.OCCUPIED) {
        Coord a = getCoord(c.getCoordX(), c.getCoordY(), opponentBoardData);
        a.setStatus(Status.HIT);
      } else if (getCoord(c.getCoordX(), c.getCoordY(),
          opponentBoard).getStatus() == Status.EMPTY) {
        Coord b = getCoord(c.getCoordX(), c.getCoordY(), opponentBoardData);
        b.setStatus(Status.MISSED);
      }
    }
    aiPlayer.reportDamage(inputs);
    return inputs;
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
   * returns number of ships left
   *
   * @return number of ships left
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
    System.out.println("not needed rn");

  }

  /**
   * Retrieves the count of empty coordinates on the player's board.
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

  /**
   * Retrieves the game board of the human player.
   *
   * @return The game board.
   */
  public Board getBoard() {
    return board;
  }

  /**
   * Sets the opponent's game board.
   *
   * @param board The opponent's game board.
   */
  public void setOpponentBoard(Board board) {
    opponentBoard = board;
  }

  /**
   * Retrieves the opponent's game board.
   *
   * @return The opponent's game board.
   */
  public Board getOpponentBoard() {
    return opponentBoard;
  }

  /**
   * Retrieves the data representation of the opponent's game board.
   *
   * @return The opponent's game board data.
   */
  public Board getOpponentBoardData() {
    return opponentBoardData;
  }

  /**
   * Sets the shots received by the human player.
   *
   * @param inputs The shots received.
   */
  public void receiveShots(List<Coord> inputs) {
    this.inputs = inputs;
  }

  /**
   * Retrieves the coordinate at the specified x and y value
   *
   * @param x the x-coordinate
   * @param y the y-coordinate
   * @param board the board from which to retrieve the coordinate from
   * @return the coordinate at teh specified x and y value from the given board
   */
  public Coord getCoord(int x, int y, Board board) {
    return board.getTotalCoords().get(y * board.getWidth() + x);
  }

  /**
   * Retrieves the ship locations of the human player.
   *
   * @return The ship locations.
   */
  public List<Ship> getShipLocations() {
    return this.shipLocations;
  }

  /**
   * Sets the AI player for the human player.
   *
   * @param aiPlayer The AI player.
   */
  public void setAiPlayer(AiPlayer aiPlayer) {
    this.aiPlayer = aiPlayer;
  }

  /**
   * sets the board for the human player
   *
   * @param board the board
   */
  public void setBoard(Board board) {
    this.board = board;
  }
}
