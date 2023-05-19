package cs3500.pa01.files;

import java.nio.file.attribute.FileTime;

/**
 * Represents a file that can be summarized
 */
public interface SummarizableFile {

  /**
   * Summarize the contents of this file
   *
   * @return A String representing the summary of this file
   */
  String summarize();

  /**
   * Gets the time this file was created
   *
   * @return A FileTime representing the time when this object was created
   */
  FileTime getCreated();

  /**
   * Gets the time this file was last modified
   *
   * @return A FileTime representing the time when this object was last modified
   */
  FileTime getModified();

  /**
   * Gets the name of this file
   *
   * @return A String representing the name of this file
   */
  String getName();
}