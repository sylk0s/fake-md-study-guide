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

  /**
   * Displays the following question on the screen
   *
   * @param q The question to display
   */
  public void showQuestion(Question q) {
    try {
      this.ui.displayString(q.getQuestion());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Display the answer to a question on the screen
   *
   * @param q The question of whose answer should be displayed
   */
  public void showAnswer(Question q) {
    try {
      this.ui.displayString(q.getAnswer());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Update a question after it has been displayed
   *
   * @param q The question that could be updated
   * @param session The current session
   */
  public void updateQuestion(Question q, Session session) {
    try {
      String s = this.ui.getInput("""
          Question options:
            1) Mark easy
            2) Mark hard
            3) Show answer
            4) Continue
          """);
      switch (s) {
        case "1" -> session.questionToEasy(q);
        case "2" -> session.questionToHard(q);
        case "3" -> {
          this.showAnswer(q);
          this.ui.getInput("Press enter to show the next question...");
        }
        default -> {
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Display the summary of this session
   *
   * @param s The current session
   */
  public void showSummary(Session s) {
    try {
      this.ui.displayString(s.summary());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Get the max number of questions in this study session
   *
   * @return A number representing the maximum number of questions in this study section
   */
  public int getMax() {
    try {
      return this.ui.getInt("How many questions do you want in this study session:");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return 0;
  }

  /**
   * Get the path to the SR file for this session
   *
   * @return A string representing a path to a sr file
   */
  public String getPath() {
    try {
      return this.ui.getInput("Input the path to the SR file:");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "";
  }
}
