package cs3500.pa01.spacedrep;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import cs3500.pa01.cli.UiController;
import cs3500.pa01.files.FileIo;
import cs3500.pa01.files.QuestionFile;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SessionTest {
  Question q1;
  Question q2;
  QuestionBank qb;
  Session session;

  // TODO add more here

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
  public void testRun1() {
    try {

      // Reset the contents of the file
      FileIo.writeFile("sampleData/srgeneration/example.sr",
          """
              E Q2Q
              Q2A
                            
              H Q1Q
              Q1A
                            
              E Doesn't get called
              Nonexistent
              
              """);

      // This mimics the user's input
      // Here, the user specifies example.sr
      // 2 questions max
      // for the first question, mark it as easy
      // for the second question, show the answer
      // then press enter to show the summary since there are no more questions
      Readable input = new StringReader("""
          sampleData/srgeneration/example.sr
          2
          1
          3
          4
          
          """);
      Appendable output = new StringBuilder();
      UiController controller = new UiController(input, output);
      Session session1 = new Session(controller);
      session1.run();

      assertEquals(output.toString(), """
          Input the path to the SR file:
          How many questions do you want in this study session:
          Question: Q1Q
          Options:
            1) Mark easy
            2) Mark hard
            3) Show answer
            4) Continue
            5) Exit
          Question: Q2Q
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
          You answered 2 questions.
          1 questions flipped from hard to easy.
          0 questions flipped from easy to hard.
          Currently there are 0 hard questions.
          Currently there are 3 easy questions.
          """);
    } catch (IOException e) {
      fail();
    }

  }

  @Test
  public void testCorrectCounts() {
    assertEquals(this.session.summary(), """
        You answered 0 questions.
        0 questions flipped from hard to easy.
        0 questions flipped from easy to hard.
        Currently there are 1 hard questions.
        Currently there are 2 easy questions.""");
  }

  @Test
  public void testToHard() {
    this.session.questionToHard(this.q2);
    assertEquals(this.session.summary(), """
        You answered 0 questions.
        0 questions flipped from hard to easy.
        0 questions flipped from easy to hard.
        Currently there are 1 hard questions.
        Currently there are 2 easy questions.""");

    this.session.questionToHard(this.q1);
    assertEquals(this.session.summary(), """
        You answered 0 questions.
        0 questions flipped from hard to easy.
        1 questions flipped from easy to hard.
        Currently there are 2 hard questions.
        Currently there are 1 easy questions.""");
  }

  @Test
  public void testToEasy() {
    // Does nothing, since this question doesn't change state
    this.session.questionToHard(this.q2);
    assertEquals(this.session.summary(), """
        You answered 0 questions.
        0 questions flipped from hard to easy.
        0 questions flipped from easy to hard.
        Currently there are 1 hard questions.
        Currently there are 2 easy questions.""");

    this.session.questionToEasy(this.q2);
    assertEquals(this.session.summary(), """
        You answered 0 questions.
        1 questions flipped from hard to easy.
        0 questions flipped from easy to hard.
        Currently there are 0 hard questions.
        Currently there are 3 easy questions.""");
  }
}