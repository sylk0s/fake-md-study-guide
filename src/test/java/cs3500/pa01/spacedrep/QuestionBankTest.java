package cs3500.pa01.spacedrep;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa01.files.QuestionFile;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

class QuestionBankTest {
  @Test
  public void testHasNext() {
    Question q1 = new Question(QuestionType.HARD, "Some question", "An answer");
    Question q2 = new Question(QuestionType.EASY, "Some question 2", "An answer 2");
    ArrayList<Question> questions = new ArrayList<>();
    questions.add(q1);
    questions.add(q2);
    SrFile sr = new SrFile(questions);
    ArrayList<QuestionFile> files = new ArrayList<>();
    files.add(sr);

    QuestionBank qb = new QuestionBank(files, 100);

    assertTrue(qb.hasNext());
    qb.next();
    qb.next();
    assertFalse(qb.hasNext());

    QuestionBank qb2 = new QuestionBank(new ArrayList<>(), 10);
    assertFalse(qb2.hasNext());
  }

  @Test
  public void testMaxCount() {
    Question q1 = new Question(QuestionType.HARD, "Some question", "An answer");
    Question q2 = new Question(QuestionType.EASY, "Some question 2", "An answer 2");
    ArrayList<Question> questions = new ArrayList<>();
    questions.add(q1);
    questions.add(q2);
    SrFile sr = new SrFile(questions);
    ArrayList<QuestionFile> files = new ArrayList<>();
    files.add(sr);

    QuestionBank qb = new QuestionBank(files, 0);

    assertFalse(qb.hasNext());

    QuestionBank qb2 = new QuestionBank(files, 1);
    assertTrue(qb2.hasNext());
    qb2.next();
    assertFalse(qb2.hasNext());
  }

  @Test
  public void testThrows() {
    QuestionBank qb = new QuestionBank(new ArrayList<>(), 0);

    assertThrows(NoSuchElementException.class, qb::next);
  }

  @Test
  public void testSorting() {
    Question q1 = new Question(QuestionType.HARD, "Some question", "An answer");
    Question q2 = new Question(QuestionType.EASY, "Some question 2", "An answer 2");
    ArrayList<Question> questions = new ArrayList<>();
    questions.add(q2);
    questions.add(q1);
    SrFile sr = new SrFile(questions);
    ArrayList<QuestionFile> files = new ArrayList<>();
    files.add(sr);

    QuestionBank qb = new QuestionBank(files, 100);

    assertEquals(qb.next(), q1);
  }
}