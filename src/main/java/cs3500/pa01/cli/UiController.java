package cs3500.pa01.cli;

import cs3500.pa01.spacedrep.Question;
import cs3500.pa01.spacedrep.Session;

public class UiController {
  private final Ui ui;


  public UiController() {
    this.ui = new SimpleCli();
  }

  public void showQuestion(Question q) {
    this.ui.displayString(q.getQuestion());
  }

  public void showAnswer(Question q) {
    this.ui.displayString(q.getAnswer());
  }

  public void updateQuestion(Question q, Session session) {
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
      default -> {}
    }
  }

  public void showSummary(Session s) {
    this.ui.displayString(s.summary());
  }

  public int getMax() {
    return this.ui.getInt("How many questions do you want in this study session?");
  }

  public String getPath() {
    return this.ui.getInput("Input the path to the SR file:");
  }
}
