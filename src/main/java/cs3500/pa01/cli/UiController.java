package cs3500.pa01.cli;

import cs3500.pa01.spacedrep.Question;
import cs3500.pa01.spacedrep.Session;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * A Controller class for the UI
 */
public class UiController {

  /**
   * The UI this controller wil control
   */
  private final Ui ui;

  /**
   * Constructor
   */
  public UiController() {
    this(new InputStreamReader(System.in), new PrintStream(System.out));
  }

  public UiController(Readable input, Appendable output) {
    this.ui = new SimpleCli(input, output);
  }

  public UiController(Ui ui) {
    this.ui = ui;
  }

  /**
   * Displays the following question on the screen
   *
   * @param q The question to display
   *
   * @throws IOException on failing to append to the output stream
   */
  public void showQuestion(Question q) throws IOException {
    this.ui.displayString("Question: " + q.getQuestion());
  }

  /**
   * Display the answer to a question on the screen
   *
   * @param q The question of whose answer should be displayed
   *
   * @throws IOException on failing to read from the input
   */
  public void showAnswer(Question q) throws IOException {
    this.ui.displayString("Answer: " + q.getAnswer());
  }

  /**
   * Update a question after it has been displayed
   *
   * @param q The question that could be updated
   * @param session The current session
   *
   * @throws IOException on failing to read or write
   */
  public void updateQuestion(Question q, Session session) throws IOException {
    String s = this.ui.getInput("""
        Options:
          1) Mark easy
          2) Mark hard
          3) Show answer
          4) Continue
          5) Exit""");
    switch (s) {
      case "1" -> session.questionToEasy(q);
      case "2" -> session.questionToHard(q);
      case "3" -> {
        this.showAnswer(q);
        // This will show the options AGAIN so the user can choose to re-mark it
        // after viewing the answer
        this.updateQuestion(q, session);
      }
      case "5" -> session.end();
      default -> {
      }
    }
  }

  /**
   * Display the summary of this session
   *
   * @param s The current session
   *
   * @throws IOException on failing to write to output
   */
  public void showSummary(Session s) throws IOException {
    this.ui.displayString(s.summary());
  }

  /**
   * Get the max number of questions in this study session
   *
   * @return A number representing the maximum number of questions in this study section
   *
   * @throws IOException on failing to read from the input
   */
  public int getMax() throws IOException {
    return this.ui.getInt("How many questions do you want in this study session:");
  }

  /**
   * Get the path to the SR file for this session
   *
   * @return A string representing a path to a sr file
   *
   * @throws IOException on failing to read from the input
   */
  public String getPath() throws IOException {
    return this.ui.getInput("Input the path to the SR file:");
  }
}
