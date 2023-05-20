package cs3500.pa01.spacedrep;

import cs3500.pa01.files.QuestionFile;
import java.util.ArrayList;

/**
 * A file containing questions for spaced repetition
 */
public class SrFile implements QuestionFile {

  /**
   * The questions in this file
   */
  ArrayList<Question> questions;

  public SrFile(ArrayList<Question> questions) {
    this.questions = questions;
  }

  /**
   * Get the questions from this file
   *
   * @return An ArrayList of the questions in this file
   */
  @Override
  public ArrayList<Question> getQuestions() {
    return this.questions;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Question q : this.questions) {
      sb.append(q.toString());
      sb.append("\n\n");
    }
    return sb.toString();
  }
}
