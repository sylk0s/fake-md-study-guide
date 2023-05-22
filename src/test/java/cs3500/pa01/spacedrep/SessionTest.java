package cs3500.pa01.spacedrep;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa01.files.QuestionFile;
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