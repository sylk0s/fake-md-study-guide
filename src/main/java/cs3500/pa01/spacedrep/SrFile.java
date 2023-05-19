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

  /**
   * Get the questions from this file
   *
   * @return An ArrayList of the questions in this file
   */
  @Override
  public ArrayList<Question> getQuestions() {
    return this.questions;
  }
}
