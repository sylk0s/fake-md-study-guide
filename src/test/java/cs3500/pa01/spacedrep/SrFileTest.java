package cs3500.pa01.spacedrep;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SrFileTest {
  ArrayList<Question> questions;
  SrFile sr;

  @BeforeEach
  public void setup() {
    this.questions = new ArrayList<>();
    Question q1 = new Question(QuestionType.EASY, "Q1Q", "Q1A");
    Question q2 = new Question(QuestionType.HARD, "Q2Q", "Q2A");
    this.questions.add(q1);
    this.questions.add(q2);
    this.sr = new SrFile(this.questions);
  }

  @Test
  public void testGetQuestions() {
    assertEquals(this.sr.getQuestions(), questions);
  }

  @Test
  public void testToString() {
    assertEquals(this.sr.toString(), """
        E Q1Q
        Q1A
            
        H Q2Q
        Q2A
            
        """);
  }
}