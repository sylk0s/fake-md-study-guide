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

  /**
   * The number of questions that have flipped from hard to easy
   */
  private int hardToEasy;

  /**
   * The number of questions that have flipped from easy to hard
   */
  private int easyToHard;

  /**
   * The number of unique hard questions in the session
   */
  private int hard;

  /**
   * The number of unique easy question in the session
   */
  private int easy;

  private final String path;

  private boolean run;

  // TODO clean the redundant constructors

  /**
   * Constructor
   *
   * @param qb The question bank this session will use
   */
  public Session(QuestionBank qb) {
    this.ui = new UiController();
    this.qb = qb;
    this.questionsAnswered = 0;
    this.hardToEasy = 0;
    this.easyToHard = 0;
    this.hard = qb.numOfType(QuestionType.HARD);
    this.easy = qb.numOfType(QuestionType.EASY);
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
    this.hardToEasy = 0;
    this.easyToHard = 0;
    this.easy = this.qb.numOfType(QuestionType.EASY);
    this.hard = this.qb.numOfType(QuestionType.HARD);
    this.run = true;
  }

  /**
   * Get a summary of this session
   *
   * @return A summary of this session
   */
  public String summary() {
    return "You answered " + questionsAnswered + " questions.\n"
        + hardToEasy + " questions flipped from hard to easy.\n"
        + easyToHard + " questions flipped from easy to hard.\n"
        + "Currently there are " + hard + " hard questions.\n"
        + "Currently there are " + easy + " easy questions.";
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
    }

    this.ui.showSummary(this);

    // Write updates SR file using question bank's questions
    SrFile sr = new SrFile(this.qb.getFinalQuestions());

    // Write file
    FileIo.writeFile(this.path, sr.toString());
  }

  /**
   * Change a question to hard
   *
   * @param q The question to change
   */
  public void questionToHard(Question q) {
    if (q.getType() != QuestionType.HARD) {
      q.changeType(QuestionType.HARD);
      this.easyToHard += 1;
      updateTypeCounts();
    }
  }

  /**
   * Change a question to easy
   *
   * @param q The question to change
   */
  public void questionToEasy(Question q) {
    if (q.getType() != QuestionType.EASY) {
      q.changeType(QuestionType.EASY);
      this.hardToEasy += 1;
      updateTypeCounts();
    }
  }

  /**
   * Update the counts of the easy and hard problems
   */
  private void updateTypeCounts() {
    this.hard = qb.numOfType(QuestionType.HARD);
    this.easy = qb.numOfType(QuestionType.EASY);
  }

  /**
   * Stops the running session
   */
  public void end() {
    this.run = false;
  }
}
