package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa01.files.QuestionFile;
import cs3500.pa01.markdown.MarkdownContents;
import cs3500.pa01.markdown.MarkdownFile;
import cs3500.pa01.spacedrep.Question;
import cs3500.pa01.spacedrep.QuestionType;
import cs3500.pa01.spacedrep.SrFile;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SrFileGeneratorTest {

  ArrayList<Question> questions;

  @BeforeEach
  public void setup() {
    this.questions = new ArrayList<>();
    Question q1 = new Question(QuestionType.EASY, "Q1Q", "Q1A");
    Question q2 = new Question(QuestionType.HARD, "Q2Q", "Q2A");
    this.questions.add(q1);
    this.questions.add(q2);
  }

  @Test
  public void testMkSrGenerator() {
    MarkdownFile file = new MarkdownFile(new MarkdownContents(new ArrayList<>()),
        FileTime.fromMillis(0), FileTime.fromMillis(0), "name", this.questions);
    ArrayList<MarkdownFile> files = new ArrayList<>();
    files.add(file);

    assertEquals(new SrFileGenerator(files).generate(),
        """
            E Q1Q
            Q1A
            
            H Q2Q
            Q2A
            
            """);
  }

  @Test
  public void testSrSrGenerator() {
    ArrayList<SrFile> files = new ArrayList<>();
    SrFile file = new SrFile(this.questions);
    files.add(file);

    assertEquals(new SrFileGenerator(files).generate(),
        """
            E Q1Q
            Q1A
            
            H Q2Q
            Q2A
            
            """);
  }

  @Test
  public void testMixedSrGenerator() {
    MarkdownFile file = new MarkdownFile(new MarkdownContents(new ArrayList<>()),
        FileTime.fromMillis(0), FileTime.fromMillis(0), "name", this.questions);
    SrFile file2 = new SrFile(this.questions);
    ArrayList<QuestionFile> files = new ArrayList<>();
    files.add(file);
    files.add(file2);

    assertEquals(new SrFileGenerator(files).generate(),
        """
            E Q1Q
            Q1A
            
            H Q2Q
            Q2A
            
            E Q1Q
            Q1A
            
            H Q2Q
            Q2A
            
            """);
  }
}