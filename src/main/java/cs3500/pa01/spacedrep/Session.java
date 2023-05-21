package cs3500.pa01.spacedrep;

import cs3500.pa01.cli.UiController;
import cs3500.pa01.files.QuestionFile;
import java.util.ArrayList;

public class Session {
  private final UiController ui;
  private final QuestionBank qb;
  private int qAnswered;
  private int hToE;
  private int eToH;
  private int hard;
  private int easy;

  public Session(QuestionBank qb) {
    this.ui = new UiController();
    this.qb = qb;
    this.qAnswered=0;
    this.hToE=0;
    this.eToH=0;
  }

  public Session(ArrayList<QuestionFile> files) {
    this.ui = new UiController();
    this.qb = new QuestionBank(files, this.ui.getMax());
    this.qAnswered=0;
    this.hToE=0;
    this.eToH=0;
  }

  public String summary() {
    return "You answered " + qAnswered + " questions.\n"
        + hToE + " questions flipped from hard to easy.\n"
        + eToH + " questions flipped from easy to hard.\n"
        + "Currently there are " + hard + " hard questions.\n"
        + "Currently there are " + easy + " easy questions.";
  }
}
