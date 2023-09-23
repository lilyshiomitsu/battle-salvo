package cs3500.pa04.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * represents the model for the AIPlayer
 */
public class AiPlayer implements Player {
  private DependencyInformation information;

  public AiPlayer(DependencyInformation information) {
    this.information = information;
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return "nullpointerexception";
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
    information.setBoard(new Board(height, width));
    // if (information.getBoard() == null) {
    int carrierCount = specifications.get(ShipType.CARRIER);
    int battleshipCount = specifications.get(ShipType.BATTLESHIP);
    int destroyerCount = specifications.get(ShipType.DESTROYER);
    int submarineCount = specifications.get(ShipType.SUBMARINE);
    information.getShipLocations().addAll(information.getBoard().addShip(carrierCount,
        ShipType.CARRIER.getSize()));
    information.getShipLocations().addAll(information.getBoard().addShip(battleshipCount,
        ShipType.BATTLESHIP.getSize()));
    information.getShipLocations().addAll(information.getBoard().addShip(destroyerCount,
        ShipType.DESTROYER.getSize()));
    information.getShipLocations().addAll(information.getBoard().addShip(submarineCount,
        ShipType.SUBMARINE.getSize()));
    //   }
    information.setEnemyBoard(new Board(height, width));
    return information.getShipLocations();
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's information.getBoard()
   */
  @Override
  public List<Coord> takeShots() {
    int shipCount = information.shipsLeft();
    if (shipCount > information.getEnemyBoard().emptyCoordCount()) {
      shipCount = information.getEnemyBoard().emptyCoordCount();
    }
    List<Coord> shots = new ArrayList<>();
    for (int i = 0; i < shipCount; i++) {
      int x = (int) (Math.random() * information.getBoard().getWidth());
      int y = (int) (Math.random() * information.getBoard().getHeight());
      Coord c = information.getCoord(x, y,
          information.getEnemyBoard());
      while (information.getTotalShots().contains(c)) {
        x = (int) (Math.random() * information.getBoard().getWidth());
        y = (int) (Math.random() * information.getBoard().getHeight());
        c = information.getCoord(x, y, information.getEnemyBoard());
      }
      information.getTotalShots().add(c);
      shots.add(c);
    }
    for (Coord c : shots) {
      if (information.getCoord(c.getCoordX(), c.getCoordY(),
          information.getEnemyBoard()).getStatus() == Status.OCCUPIED) {
        Coord a = information.getCoord(c.getCoordX(), c.getCoordY(), information.getEnemyBoard());
        a.setStatus(Status.HIT);
      } else if (information.getCoord(c.getCoordX(), c.getCoordY(),
          information.getEnemyBoard()).getStatus() == Status.EMPTY) {
        Coord b = information.getCoord(c.getCoordX(), c.getCoordY(), information.getEnemyBoard());
        b.setStatus(Status.MISSED);
      }
    }
    return shots;
  }

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's information.getBoard()
   * @return a filtered list of the given shots that contain all locations of shots that hit a
   *     ship on this board
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    List<Coord> damage = new ArrayList<>();
    for (Coord opponentShot : opponentShotsOnBoard) {
      Coord hit = information.getCoord(opponentShot.getCoordX(),
          opponentShot.getCoordY(), information.getBoard());
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
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    for (Coord coord : shotsThatHitOpponentShips) {
      Coord cord = information.getEnemyBoard().getCoord(coord.getCoordX(), coord.getCoordY());
      cord.setStatus(Status.HIT);
    }
  }

  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public boolean endGame(GameResult result, String reason) {
    if (result != null) {
      return false;
    }
    System.out.print(reason);
    return true;
  }

}
