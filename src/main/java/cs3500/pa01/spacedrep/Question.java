package cs3500.pa01.spacedrep;

/**
 * A Type representing a question
 */
public class Question {

  /**
   * The difficulty of this question
   */
  private QuestionType type;

  /**
   * The question
   */
  private final String question;

  /**
   * The answer to this question
   */
  private final String answer;

  /**
   * If this question's type has been flipped
   */
  private boolean flipped;

  /**
   * Constructor
   *
   * @param type the difficulty of this question
   * @param question the question
   * @param answer the answer to this question
   */
  public Question(QuestionType type, String question, String answer) {
    this.type = type;
    this.question = question;
    this.answer = answer;
    this.flipped = false;
  }

  /**
   * Gets the question
   *
   * @return A String representing the question
   */
  public String getQuestion() {
    return question;
  }

  /**
   * Gets the answer
   *
   * @return A String representing the answer
   */
  public String getAnswer() {
    return answer;
  }

  /**
   * Gets the type of question
   *
   * @return A QuestionType representing the type of this question
   */
  public QuestionType getType() {
    return type;
  }

  /**
   * Changes the type of this question to the other type
   */
  public void flipType() {
    if (this.type.equals(QuestionType.HARD)) {
      this.type = QuestionType.EASY;
    } else {
      this.type = QuestionType.HARD;
    }
    this.flipped = !this.flipped;
  }

  /**
   * Check is a question has been flipped
   *
   * @return A boolean representing if a value has been flipped or not
   */
  public boolean hasBeenFlipped() {
    return this.flipped;
  }

  /**
   * The string version of this question
   * As it appears in the SR file
   *
   * @return A String representing this question
   */
  @Override
  public String toString() {
    return this.type.toString() + " " + this.question + "\n" + this.answer;
  }
}
