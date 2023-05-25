package cs3500.pa01.spacedrep;

import cs3500.pa01.cli.UiController;
import cs3500.pa01.files.FileIo;
import cs3500.pa01.files.QuestionFile;
import cs3500.pa01.parsing.SrParser;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents a spaced repetition session
 */
public class Session {

  /**
   * The UI controller to display this session to the user
   */
  private final UiController ui;

  /**
   * The bank of questions this user will draw from
   */
  private final QuestionBank qb;

  /**
   * The number of questions answers so far
   */
  private int questionsAnswered;

  private final String path;

  private boolean run;

  /**
   * Constructor
   *
   * @param qb The question bank this session will use
   */
  public Session(QuestionBank qb) {
    this.ui = new UiController();
    this.qb = qb;
    this.questionsAnswered = 0;
    this.path = "";
    this.run = true;
  }

  /**
   * Constructor for a session
   *
   * @throws IOException Error from reading SR file
   */
  public Session() throws IOException {
    this(new UiController());
  }

  /**
   * Creates a new default session from user generated inputs
   *
   * @param ui The UI controller this session uses to interact with the UI
   * @throws IOException Error from reading the file
   */
  public Session(UiController ui) throws IOException {
    this.ui = ui;
    // Get the path to the SR file to read
    this.path = this.ui.getPath();

    // Read in the SR file

    QuestionFile sr = new SrParser(FileIo.readFile(path))
        .parse();

    // Turn the SR file into a question bank
    ArrayList<QuestionFile> files = new ArrayList<>();
    files.add(sr);
    this.qb = new QuestionBank(files, this.ui.getMax());
    this.questionsAnswered = 0;
    this.run = true;
  }

  /**
   * Get a summary of this session
   *
   * @return A summary of this session
   */
  public String summary() {
    return "You answered " + questionsAnswered + " questions.\n"
        + this.qb.getFlippedTo(QuestionType.EASY) + " questions flipped from hard to easy.\n"
        + this.qb.getFlippedTo(QuestionType.HARD) + " questions flipped from easy to hard.\n"
        + "Currently there are " + this.qb.numOfType(QuestionType.HARD) + " hard questions.\n"
        + "Currently there are " + this.qb.numOfType(QuestionType.EASY) + " easy questions.";
  }

  /**
   * Run this session
   *
   * @throws IOException On an error reading from the input stream
   */
  public void run() throws IOException {
    while (this.qb.hasNext() && this.run) {
      Question q = this.qb.next();
      this.ui.showQuestion(q);
      this.ui.updateQuestion(q, this);
      questionsAnswered += 1;

      // Now updates files as it's going rather than at the end!

      // Write updates SR file using question bank's questions
      SrFile sr = new SrFile(this.qb.getFinalQuestions());

      // Write file
      FileIo.writeFile(this.path, sr.toString());
    }

    this.ui.showSummary(this);
  }

  /**
   * Switch the type of the question to type
   *
   * @param q The question to switch types on
   * @param type The type to switch to
   */
  public void switchTypeTo(Question q, QuestionType type) {
    if (!q.getType().equals(type)) {
      q.flipType();
    }
  }

  /**
   * Stops the running session
   */
  public void end() {
    this.run = false;
  }
}
