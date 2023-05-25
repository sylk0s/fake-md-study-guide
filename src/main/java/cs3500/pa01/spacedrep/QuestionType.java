package cs3500.pa01.spacedrep;

/**
 * Represent the possible difficulty of a question
 */
public enum QuestionType {
  /**
   * An easy question
   */
  EASY("E"),
  /**
   * A hard question
   */
  HARD("H");

  /**
   * The string form of this difficulty
   */
  private final String stringForm;

  QuestionType(String stringForm) {
    this.stringForm = stringForm;
  }

  /**
   * Turns this difficulty into a string
   *
   * @return A String representing the difficulty
   */
  @Override
  public String toString() {
    return this.stringForm;
  }
}
