package cs3500.pa01.spacedrep;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa01.files.QuestionFile;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuestionBankTest {
  Question q1;
  Question q2;

  ArrayList<Question> questions;

  ArrayList<QuestionFile> files;

  SrFile sr;

  @BeforeEach
  public void setup() {
    this.q1 = new Question(QuestionType.HARD, "Some question", "An answer");
    this.q2 = new Question(QuestionType.EASY, "Some question 2", "An answer 2");
    this.questions = new ArrayList<>();
    this.files = new ArrayList<>();
    this.sr = new SrFile(this.questions);
    files.add(this.sr);
  }

  @Test
  public void testHasNext() {
    questions.add(q1);
    questions.add(q2);

    QuestionBank qb = new QuestionBank(files, 100);

    assertTrue(qb.hasNext());
    qb.next();
    qb.next();
    assertTrue(qb.hasNext());
    assertDoesNotThrow(qb::next);


    assertThrows(IllegalArgumentException.class,
        () -> new QuestionBank(new ArrayList<>(), 10));
  }

  @Test
  public void testMaxCount() {
    questions.add(q1);
    questions.add(q2);

    QuestionBank qb = new QuestionBank(files, 0);

    assertFalse(qb.hasNext());

    QuestionBank qb2 = new QuestionBank(files, 1);
    assertTrue(qb2.hasNext());
    qb2.next();
    assertFalse(qb2.hasNext());
  }

  @Test
  public void testThrows() {
    questions.add(q1);
    questions.add(q2);
    QuestionBank qb = new QuestionBank(files, 0);

    assertThrows(NoSuchElementException.class, qb::next);
  }

  @Test
  public void testSorting() {
    questions.add(q2);
    questions.add(q1);

    QuestionBank qb = new QuestionBank(files, 100);

    assertEquals(qb.next(), q1);
  }

  @Test
  public void testGetQuestions() {
    questions.add(q1);
    questions.add(q2);

    QuestionBank qb = new QuestionBank(files, 100);

    assertEquals(qb.getFinalQuestions(), questions);
  }
}