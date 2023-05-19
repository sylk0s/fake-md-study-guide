package cs3500.pa01;

import cs3500.pa01.files.SummarizableFile;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Represents a class to generate a summary of a list of markdown files
 */
public class SummaryGenerator {

  /**
   * The files this generator can create a summary of
   */
  private final ArrayList<SummarizableFile> files;

  /**
   * Constructs this SummaryGenerator from a list of files to construct a summary of
   *
   * @param files An ArrayList of files this generator will use to create a summary
   */
  SummaryGenerator(ArrayList<SummarizableFile> files) {
    this.files = files;
  }

  /**
   * Generates a summary of the files given a method of sorting
   *
   * @param sorter The method used to sort these files
   * @return A String representing the summarized version of these files
   */
  private String generate(Comparator<SummarizableFile> sorter) {
    // Why oh why is it that java sorting *ALWAYS* wants to be the opposite of what I want
    return files.stream().sorted((a, b) -> sorter.compare(b, a))
        .reduce("", (acc, file) -> file.summarize() + acc, String::concat);
  }

  // I tried implementing this with an enum but kept getting errors about it not being constant?
  // I think I just don't properly understand how java enums work compared to rust enums, which
  // would let me do something like this
  /**
   * Generates a summary of the files given a method of sorting
   *
   * @param type A String representing the method used to sort the files
   * @return A String representing the summarized version of these files
   */
  public String generate(String type) {
    return switch (type) {
      case "filename" -> generate(Comparator.comparing(SummarizableFile::getName));
      case "created" -> generate((a, b) -> -a.getCreated().compareTo(b.getCreated()));
      case "modified" -> generate((a, b) -> -a.getModified().compareTo(b.getModified()));
      default -> throw new IllegalArgumentException("Invalid sorting type");
    };
  }
}
