package cs3500.pa01;

import java.nio.file.attribute.FileTime;

/**
 * Represents a parser for a file that can be summarized
 */
public interface Parser {

  /**
   * Parses some information about a file into a SummarizableFile
   *
   * @param name The name of the file being parsed
   * @param created The FileTime that the file was created
   * @param modified The FileTime that the file was last modified
   *
   * @return A file object that can be used to generate a summary of the files
   */
  SummarizableFile parse(String name, FileTime created, FileTime modified);
}
