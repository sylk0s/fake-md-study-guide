package cs3500.pa01.spacedrep;

import cs3500.pa01.cli.UiController;
import cs3500.pa01.files.FileIo;
import cs3500.pa01.files.QuestionFile;
import cs3500.pa01.parsing.SrParser;
import java.io.IOException;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;

public class Session {
  private final UiController ui;
  private final QuestionBank qb;
  private int qAnswered;
  private int hToE;
  private int eToH;
  private final int hard;
  private final int easy;

  // TODO clean the redundant constructors
  public Session(QuestionBank qb) {
    this.ui = new UiController();
    this.qb = qb;
    this.qAnswered=0;
    this.hToE=0;
    this.eToH=0;
    this.hard = qb.numOfType(QuestionType.HARD);
    this.easy = qb.numOfType(QuestionType.EASY);
  }

  public Session(ArrayList<QuestionFile> files) {
    this.ui = new UiController();
    this.qb = new QuestionBank(files, this.ui.getMax());
    this.qAnswered=0;
    this.hToE=0;
    this.eToH=0;
    this.hard = qb.numOfType(QuestionType.HARD);
    this.easy = qb.numOfType(QuestionType.EASY);
  }

  public Session() throws IOException {
    this.ui = new UiController();
    // Get the path to the SR file to read
    String path = this.ui.getPath();

    // Read in the SR file

    QuestionFile sr = new SrParser(FileIo.readFile(path))
        .parse(path, FileTime.fromMillis(0), FileTime.fromMillis(0));

    // Turn the SR file into a question bank
    ArrayList<QuestionFile> files = new ArrayList<>();
    files.add(sr);
    this.qb = new QuestionBank(files, this.ui.getMax());
    this.qAnswered = 0;
    this.hToE = 0;
    this.eToH = 0;
    this.easy = this.qb.numOfType(QuestionType.EASY);
    this.hard = this.qb.numOfType(QuestionType.HARD);
  }

  public String summary() {
    return "You answered " + qAnswered + " questions.\n"
        + hToE + " questions flipped from hard to easy.\n"
        + eToH + " questions flipped from easy to hard.\n"
        + "Currently there are " + hard + " hard questions.\n"
        + "Currently there are " + easy + " easy questions.";
  }

  public void run() {
    while (this.qb.hasNext()) {
      Question q = this.qb.next();
      this.ui.showQuestion(q);
      this.ui.updateQuestion(q, this);
      qAnswered += 1;
    }

    this.ui.showSummary(this);

    // Write updates SR file using question bank's questions
    SrFile sr = new SrFile(this.qb.getFinalQuestions());

    // Write file
  }

  public void questionToHard(Question q) {
    if (q.getType() != QuestionType.HARD) {
      q.changeType(QuestionType.HARD);
      this.eToH += 1;
    }

  }

  public void questionToEasy(Question q) {
    if (q.getType() != QuestionType.EASY) {
      q.changeType(QuestionType.EASY);
      this.hToE += 1;
    }
  }
}
