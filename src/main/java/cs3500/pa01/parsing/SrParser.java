package cs3500.pa01.parsing;

import cs3500.pa01.files.QuestionFile;
import cs3500.pa01.spacedrep.Question;
import cs3500.pa01.spacedrep.QuestionType;
import cs3500.pa01.spacedrep.SrFile;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;

public class SrParser /*implements Parser*/ {
  private final ArrayList<String> lines;
  private final ArrayList<Question> questions;
  public SrParser(ArrayList<String> lines) {
    this.lines = lines;
    this.questions = new ArrayList<>();
  }

  /**
   * Parses some information about a file into a SummarizableFile
   *
   * @param name     The name of the file being parsed
   * @param created  The FileTime that the file was created
   * @param modified The FileTime that the file was last modified
   * @return A file object that can be used to generate a summary of the files
   */
  //@Override
  public QuestionFile parse(String name, FileTime created, FileTime modified) {

    if (this.lines.size() >= 2) {
      String question;
      String answer;
      QuestionType difficulty;
      for (int i = 0; i < this.lines.size(); i += 3) {
        String questionLine = this.lines.get(i);
        question = questionLine.substring(2);
        answer = this.lines.get(i + 1);
        difficulty = questionLine.charAt(0) == 'H' ? QuestionType.HARD : QuestionType.EASY;
        this.questions.add(new Question(difficulty, question, answer));
      }
    }

    return new SrFile(this.questions);
  }
}
