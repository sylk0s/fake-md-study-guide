package cs3500.pa01.spacedrep;

import cs3500.pa01.files.QuestionFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Basically wraps an iterator over the list of all questions in a check for if
 * the max number of questions has been hit yet
 */
public class QuestionBank implements Iterator<Question> {
  private final ArrayList<Question> questions;
  private Iterator<Question> questionsIter;
  private final int max;

  private int curr;

  /**
   * Constructor
   *
   * @param files The files to draw questions from
   * @param max The max number of questions in this bank
   */
  public QuestionBank(ArrayList<QuestionFile> files, int max) {
    this.max = max;
    this.curr = 0;
    this.questions = new ArrayList<>();
    if (files.size() == 0) {
      throw new IllegalArgumentException("No files to generation question from");
    }
    for (QuestionFile qf : files) {
      questions.addAll(qf.getQuestions());
    }

    // This sorting basically just puts all hard questions before all easy questions
    // No other promises are made about the order
    this.sortQuestions();
    this.questionsIter = questions.iterator();
  }

  /**
   * Sorts the questions by difficulty putting hard one first
   */
  private void sortQuestions() {
    this.questions.sort((a, b) -> {
      if (a.getType() == QuestionType.HARD) {
        return -1;
      } else {
        return 0;
      }
    });
  }

  /**
   * Returns {@code true} if the iteration has more elements.
   * (In other words, returns {@code true} if {@link #next} would
   * return an element rather than throwing an exception.)
   *
   * @return {@code true} if the iteration has more elements
   */
  @Override
  public boolean hasNext() {
    return this.max > this.curr;
  }

  /**
   * Returns the next element in the iteration.
   *
   * @return the next element in the iteration
   * @throws NoSuchElementException if the iteration has no more elements
   */
  @Override
  public Question next() {
    if (this.max <= this.curr) {
      throw new NoSuchElementException();
    }
    if (!this.questionsIter.hasNext()) {
      this.sortQuestions();
      this.questionsIter = this.questions.iterator();
    }
    this.curr += 1;
    return this.questionsIter.next();
  }

  /**
   * The number of questions with a certain type
   *
   * @param type The type of the question to count
   * @return The number of questions with type
   */
  public int numOfType(QuestionType type) {
    return this.allOfType(type).size();
  }

  /**
   * Get all items currently of type
   *
   * @param type The type of the item
   * @return An ArrayList of the items
   */
  private ArrayList<Question> allOfType(QuestionType type) {
    return this.questions.stream().filter((q) -> q.getType().equals(type))
        .collect(Collectors.toCollection(ArrayList::new));
  }

  /**
   * Get the number of questions that have been flipped to a type
   *
   * @param type The type that the items were flipped to
   * @return How many items were flipperd
   */
  public int getFlippedTo(QuestionType type) {
    return this.allOfType(type).stream().filter(Question::hasBeenFlipped)
        .collect(Collectors.toCollection(ArrayList::new)).size();
  }

  /**
   * Returns the list of questions this object contains
   *
   * @return An arraylist of the questions
   */
  public ArrayList<Question> getFinalQuestions() {
    return this.questions;
  }
}
