package cs3500.pa01.parsing;

import static org.junit.jupiter.api.Assertions.*;

import cs3500.pa01.spacedrep.Question;
import cs3500.pa01.spacedrep.QuestionType;
import cs3500.pa01.spacedrep.SrFile;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class SrParserTest {
  @Test
  public void testProperParsing() {
    ArrayList<String> lines = new ArrayList<>();
    lines.add("H Some question");
    lines.add("An answer");
    lines.add("");
    lines.add("E Some question 2");
    lines.add("An answer 2");
    lines.add("");

    Question q1 = new Question(QuestionType.HARD, "Some question", "An answer");
    Question q2 = new Question(QuestionType.EASY, "Some question 2", "An answer 2");
    ArrayList<Question> questions = new ArrayList<>();
    questions.add(q1);
    questions.add(q2);
    assertEquals(new SrParser(lines)
            .parse("", FileTime.fromMillis(0), FileTime.fromMillis(0))
            .toString(),
        new SrFile(questions).toString());
  }

  @Test
  public void testEmptyInput() {
    assertEquals(new SrParser(new ArrayList<>())
        .parse("", FileTime.fromMillis(0), FileTime.fromMillis(0))
            .toString(),
    new SrFile(new ArrayList<>()).toString());
  }

  @Test
  public void testStringIsOriginal() {
    ArrayList<String> lines = new ArrayList<>();
    lines.add("H Some question");
    lines.add("An answer");
    lines.add("");
    lines.add("E Some question 2");
    lines.add("An answer 2");
    lines.add("");

    StringBuilder sb = new StringBuilder();
    for (String line : lines) {
      sb.append(line);
      sb.append("\n");
    }
    String contents = sb.toString();

    assertEquals(new SrParser(lines)
            .parse("", FileTime.fromMillis(0), FileTime.fromMillis(0))
            .toString(),
        contents);
  }
}