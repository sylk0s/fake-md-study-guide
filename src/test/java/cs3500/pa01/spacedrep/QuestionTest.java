package cs3500.pa01.spacedrep;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuestionTest {
  private Question q1;
  private Question q2;

  @BeforeEach
  public void setup() {
    this.q1 = new Question(QuestionType.HARD, "This is a hard question",
        "This is a hard answer");
    this.q2 = new Question(QuestionType.EASY, "This is an easy question",
        "This is a easy answer");
  }

  @Test
  public void testToString() {
    assertEquals(this.q1.toString(), "H This is a hard question\nThis is a hard answer");
    assertEquals(this.q2.toString(), "E This is an easy question\nThis is a easy answer");
  }

  @Test
  public void testGetQa() {
    assertEquals(this.q1.getQuestion(), "This is a hard question");
    assertEquals(this.q1.getAnswer(), "This is a hard answer");
  }

  @Test
  public void testGetSetType() {
    assertEquals(this.q1.getType(), QuestionType.HARD);
    this.q1.flipType();
    assertEquals(this.q1.getType(), QuestionType.EASY);
  }
}