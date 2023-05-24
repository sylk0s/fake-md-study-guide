package cs3500.pa01.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import cs3500.pa01.files.QuestionFile;
import cs3500.pa01.spacedrep.Question;
import cs3500.pa01.spacedrep.QuestionBank;
import cs3500.pa01.spacedrep.QuestionType;
import cs3500.pa01.spacedrep.Session;
import cs3500.pa01.spacedrep.SrFile;
import java.io.IOException;
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

    try {
      controller.showQuestion(q1);

      assertEquals(output.toString(), "Question: Q1Q\n");
    } catch (IOException e) {
      fail();
    }
  }

  @Test
  public void testShowAnswer() {
    // StringReader implements Readable
    Readable input = new StringReader("");

    // StringBuilder implements Appendable
    Appendable output = new StringBuilder();

    UiController controller = new UiController(input, output);

    try {
      controller.showAnswer(q1);

      assertEquals(output.toString(), "Answer: Q1A\n");
    } catch (IOException e) {
      fail();
    }
  }

  @Test
  public void testUpdateQuestion() {

    Readable input = new StringReader("1");
    Appendable output = new StringBuilder();
    UiController controller = new UiController(input, output);

    try {
      controller.updateQuestion(this.q2, this.session);
      assertEquals(this.q2.getType(), QuestionType.EASY);
    } catch (IOException e) {
      fail();
    }

    Readable input2 = new StringReader("2");
    UiController controller2 = new UiController(input2, output);

    try {
      controller2.updateQuestion(this.q1, this.session);
      assertEquals(this.q1.getType(), QuestionType.HARD);
    } catch (IOException e) {
      fail();
    }

    try {
      Readable input3 = new StringReader("""
          1""");
      Appendable output3 = new StringBuilder();
      UiController controller3 = new UiController(input3, output3);

      controller3.updateQuestion(this.q2, this.session);
      assertEquals(output3.toString(), """
          Options:
            1) Mark easy
            2) Mark hard
            3) Show answer
            4) Continue
            5) Exit
            """);
      assertEquals(this.q2.getType(), QuestionType.EASY);
    } catch (IOException e) {
      fail();
    }

    try {
      Readable input4 = new StringReader("""
          2""");
      Appendable output4 = new StringBuilder();
      UiController controller4 = new UiController(input4, output4);

      controller4.updateQuestion(this.q2, this.session);
      assertEquals(output4.toString(), """
          Options:
            1) Mark easy
            2) Mark hard
            3) Show answer
            4) Continue
            5) Exit
            """);
      assertEquals(this.q2.getType(), QuestionType.HARD);
    } catch (IOException e) {
      fail();
    }

    try {
      Readable input5 = new StringReader("3\n4\n");
      Appendable output5 = new StringBuilder();
      UiController controller5 = new UiController(input5, output5);

      controller5.updateQuestion(this.q2, this.session);
      assertEquals(output5.toString(), """
          Options:
            1) Mark easy
            2) Mark hard
            3) Show answer
            4) Continue
            5) Exit
          Answer: Q2A
          Options:
            1) Mark easy
            2) Mark hard
            3) Show answer
            4) Continue
            5) Exit
          """);
    } catch (IOException e) {
      fail();
    }
  }

  @Test
  public void testShowSummary() {
    // StringReader implements Readable
    Readable input = new StringReader("");

    // StringBuilder implements Appendable
    Appendable output = new StringBuilder();

    UiController controller = new UiController(input, output);

    try {
      controller.showSummary(this.session);
      assertEquals(output.toString(), """
          You answered 0 questions.
          0 questions flipped from hard to easy.
          0 questions flipped from easy to hard.
          Currently there are 1 hard questions.
          Currently there are 2 easy questions.
          """);
    } catch (IOException e) {
      fail();
    }
  }

  @Test
  public void testGetMax() {
    // StringReader implements Readable
    Readable input = new StringReader("123");

    // StringBuilder implements Appendable
    Appendable output = new StringBuilder();

    UiController controller = new UiController(input, output);

    try {
      assertEquals(controller.getMax(), 123);

      assertEquals(output.toString(), "How many questions do you want in this study session:\n");
    } catch (IOException e) {
      fail();
    }
  }

  @Test
  public void testGetPath() {
    // StringReader implements Readable
    Readable input = new StringReader("some/path/to/something");

    // StringBuilder implements Appendable
    Appendable output = new StringBuilder();

    UiController controller = new UiController(input, output);

    try {
      assertEquals(controller.getPath(), "some/path/to/something");

      assertEquals(output.toString(), "Input the path to the SR file:\n");
    } catch (IOException e) {
      fail();
    }
  }

  @Test
  public void testThrows() {
    UiController controller = new UiController(new MockCli());

    assertThrows(IOException.class, () -> controller.showSummary(this.session));
    assertThrows(IOException.class, () -> controller.showAnswer(this.q1));
    assertThrows(IOException.class, () -> controller.showQuestion(this.q1));
    assertThrows(IOException.class, () -> controller.updateQuestion(this.q1, this.session));
    assertThrows(IOException.class, controller::getPath);
    assertThrows(IOException.class, controller::getMax);

  }
}