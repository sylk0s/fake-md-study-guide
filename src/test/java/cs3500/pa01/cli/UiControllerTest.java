package cs3500.pa01.cli;

import static org.junit.jupiter.api.Assertions.*;

import cs3500.pa01.files.QuestionFile;
import cs3500.pa01.spacedrep.Question;
import cs3500.pa01.spacedrep.QuestionBank;
import cs3500.pa01.spacedrep.QuestionType;
import cs3500.pa01.spacedrep.Session;
import cs3500.pa01.spacedrep.SrFile;
import java.io.StringReader;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UiControllerTest {

  Question q1;
  Question q2;
  QuestionBank qb;
  Session session;

  @BeforeEach
  public void setup() {
    this.q1 = new Question(QuestionType.EASY, "Q1Q", "Q1A");
    this.q2 = new Question(QuestionType.HARD, "Q2Q", "Q2A");
    Question q3 = new Question(QuestionType.EASY, "Q3Q", "Q3A");
    ArrayList<Question> questions = new ArrayList<>();
    questions.add(this.q1);
    questions.add(this.q2);
    questions.add(q3);
    SrFile sr = new SrFile(questions);
    ArrayList<QuestionFile> files = new ArrayList<>();
    files.add(sr);

    this.qb = new QuestionBank(files, 100);
    this.session = new Session(this.qb);
  }

  @Test
  public void testShowQuestion() {
    // StringReader implements Readable
    Readable input = new StringReader("");

    // StringBuilder implements Appendable
    Appendable output = new StringBuilder();

    UiController controller = new UiController(input, output);

    controller.showQuestion(q1);

    assertEquals(output.toString(), "Q1Q");
  }

  @Test
  public void testShowAnswer() {
    // StringReader implements Readable
    Readable input = new StringReader("");

    // StringBuilder implements Appendable
    Appendable output = new StringBuilder();

    UiController controller = new UiController(input, output);

    controller.showAnswer(q1);

    assertEquals(output.toString(), "Q1A");
  }

  @Test
  public void testUpdateQuestion() {

    Readable input = new StringReader("1");
    Appendable output = new StringBuilder();
    UiController controller = new UiController(input, output);

    controller.updateQuestion(this.q2, this.session);
    assertEquals(this.q2.getType(), QuestionType.EASY);

    Readable input2 = new StringReader("2");
    UiController controller2 = new UiController(input2, output);

    controller2.updateQuestion(this.q1, this.session);
    assertEquals(this.q1.getType(), QuestionType.HARD);

    // TODO fix this <3
    /*
    Readable input3 = new StringReader("3");
    UiController controller3 = new UiController(input3, output);

    controller3.updateQuestion(this.q2, this.session);
    assertEquals(output.toString(), "Q2A\nPress enter to show the next question...");
    */
  }

  @Test
  public void testShowSummary() {
    // StringReader implements Readable
    Readable input = new StringReader("");

    // StringBuilder implements Appendable
    Appendable output = new StringBuilder();

    UiController controller = new UiController(input, output);

    controller.showSummary(this.session);
    assertEquals(output.toString(), """
      You answered 0 questions.
      0 questions flipped from hard to easy.
      0 questions flipped from easy to hard.
      Currently there are 1 hard questions.
      Currently there are 2 easy questions.""");
  }

  @Test
  public void testGetMax() {
    // StringReader implements Readable
    Readable input = new StringReader("123");

    // StringBuilder implements Appendable
    Appendable output = new StringBuilder();

    UiController controller = new UiController(input, output);

    assertEquals(controller.getMax(), 123);

    assertEquals(output.toString(), "How many questions do you want in this study session:");
  }

  @Test
  public void testGetPath() {
    // StringReader implements Readable
    Readable input = new StringReader("some/path/to/something");

    // StringBuilder implements Appendable
    Appendable output = new StringBuilder();

    UiController controller = new UiController(input, output);

    assertEquals(controller.getPath(), "some/path/to/something");

    assertEquals(output.toString(), "Input the path to the SR file:");
  }
}