package cs3500.pa01.files;

import cs3500.pa01.spacedrep.Question;
import java.util.ArrayList;

/**
 * A File that can be used to generate questions
 */
public interface QuestionFile {

  /**
   * Get the questions this file contains
   *
   * @return Returns an ArrayList of the questions in this file
   */
  ArrayList<Question> getQuestions();
}
