package cs3500.pa01;

import cs3500.pa01.files.FileGenerator;
import cs3500.pa01.files.QuestionFile;
import cs3500.pa01.spacedrep.Question;
import java.util.ArrayList;

/**
 * Generates the String form of an SR file given a file that contains questions
 */
public class SrFileGenerator implements FileGenerator {

  /**
   * The files to extract data from
   */
  private final ArrayList<QuestionFile> files;

  /**
   * Constructor
   *
   * @param files The files used to source the questions
   */
  SrFileGenerator(ArrayList<QuestionFile> files) {
    this.files = files;
  }

  SrFileGenerator(QuestionFile file) {
    this.files = new ArrayList<>();
    this.files.add(file);
  }

  /**
   * Generate the contents of a file
   *
   * @return A String representing the contents of a file to be written
   */
  @Override
  public String generate() {
    StringBuilder sb = new StringBuilder();
    for (Question q : this.allQuestions()) {
      sb.append(q);
      sb.append("\n\n");
    }
    return sb.toString();
  }

  /**
   * Gets all the questions contained in a list of files
   *
   * @return An ArrayList of all questions in the files
   */
  private ArrayList<Question> allQuestions() {
    ArrayList<Question> questions = new ArrayList<>();
    for (QuestionFile f : this.files) {
      questions.addAll(f.getQuestions());
    }
    return questions;
  }
}
