package cs3500.pa01.markdown;

import cs3500.pa01.files.FileContents;
import cs3500.pa01.files.QuestionFile;
import cs3500.pa01.files.SummarizableFile;
import cs3500.pa01.spacedrep.Question;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;

/**
 * Represents a simplified markdown file that can be summarized
 */
public class MarkdownFile implements SummarizableFile, QuestionFile {

  /**
   * The contents of this file
   */
  private final FileContents contents;

  /**
   * The time this file was created
   */
  private final FileTime created;

  /**
   * The time this file was last modified
   */
  private final FileTime modified;

  /**
   * The name of this file
   */
  private final String name;

  /**
   * The questions in this file
   */
  private final ArrayList<Question> questions;

  /**
   * Creates a Markdown file given it's contents
   * The contents are generated by the parser
   *
   * @param contents  The contents of this file
   * @param created   The time when this file was created
   * @param modified  The time when this file was last modified
   * @param name      The name of this file
   * @param questions The questions from this markdown file
   */
  public MarkdownFile(FileContents contents, FileTime created, FileTime modified, String name,
                      ArrayList<Question> questions) {
    this.contents = contents;
    this.created = created;
    this.modified = modified;
    this.name = name;
    this.questions = questions;
  }

  /**
   * Creates a Markdown file given it's contents
   * The contents are generated by the parser
   *
   * @param contents  The contents of this file
   * @param created   The time when this file was created
   * @param modified  The time when this file was last modified
   * @param name      The name of this file
   */
  public MarkdownFile(FileContents contents, FileTime created, FileTime modified, String name) {
    this(contents, created, modified, name, new ArrayList<>());
  }

  /**
   * Creates a Markdown file given a single header
   * This is mostly for testing and provides an easy method to create simple files
   *
   * @param header    The header this file contains
   * @param created   The time when this file was created
   * @param modified  The time when this file was last modified
   * @param name      The name of this file
   */
  public MarkdownFile(Header header, FileTime created, FileTime modified, String name) {
    this.questions = new ArrayList<>();
    ArrayList<Header> h = new ArrayList<>();
    h.add(header);

    this.contents = new MarkdownContents(h);
    this.created = created;
    this.modified = modified;
    this.name = name;
  }

  /**
   * Summarizes this markdown file
   *
   * @return A String representing the summarized version of this file
   */
  @Override
  public String summarize() {
    return contents.summarize();
  }

  /**
   * Get the time this file was created
   *
   * @return A FileTime representing when this file was created
   */
  @Override
  public FileTime getCreated() {
    return this.created;
  }

  /**
   * Get the time this file was last modified
   *
   * @return A FileTime representing when this file was last modified
   */
  @Override
  public FileTime getModified() {
    return this.modified;
  }

  /**
   * Get the name of this file
   *
   * @return A String representing the name of this file
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * Get the questions this file contains
   *
   * @return Returns an ArrayList of the questions in this file
   */
  @Override
  public ArrayList<Question> getQuestions() {
    return this.questions;
  }
}