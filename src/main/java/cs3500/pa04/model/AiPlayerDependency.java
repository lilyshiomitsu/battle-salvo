package cs3500.pa04.model;

/**
 * represents the AIPlayerDependency class
 * - used for getters and setters of the AIPlayer class
 */
public class AiPlayerDependency {
  private AiPlayer aiPlayer;
  private DependencyInformation information;

  /**
   * Constructs an instance of AiPlayerDependency.
   * Initializes the DependencyInformation and AIPlayer objects.
   */
  public AiPlayerDependency() {
    this.information = new DependencyInformation();
    this.aiPlayer = new AiPlayer(this.information);
  }

  /**
   * Returns the DependencyInformation object.
   *
   * @return The DependencyInformation object.
   */
  public DependencyInformation getInformation() {
    return this.information;
  }

  /**
   * Returns the AIPlayer object.
   *
   * @return The AIPlayer object.
   */
  public AiPlayer getAiPlayer() {
    return this.aiPlayer;
  }
}
