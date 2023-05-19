package cs3500.pa01;

import cs3500.pa01.files.FileGenerator;
import cs3500.pa01.files.SummarizableFile;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Represents a class to generate a summary of a list of markdown files
 */
public class SummaryGenerator implements FileGenerator {

  Comparator<SummarizableFile> sorter;
  ArrayList<SummarizableFile> files;

  SummaryGenerator(ArrayList<SummarizableFile> files, Comparator<SummarizableFile> sorter) {
    this.files = files;
    this.sorter = sorter;
  }

  /**
   * Creates a new summary generator object
   *
   * @param files The files used to generate this summary
   * @param type The method used to generate the summary
   */
  public SummaryGenerator(ArrayList<SummarizableFile> files, String type) {
    this.files = files;
    this.sorter = switch (type) {
      case "filename" -> Comparator.comparing(SummarizableFile::getName);
      case "created" -> (a, b) -> -a.getCreated().compareTo(b.getCreated());
      case "modified" -> (a, b) -> -a.getModified().compareTo(b.getModified());
      default -> throw new IllegalArgumentException("Invalid sorting type");
    };
  }

  /**
   * Generates a summary of the files given a method of sorting
   *
   * @return A String representing the summarized version of these files
   */
  @Override
  public String generate() {
    // Why oh why is it that java sorting *ALWAYS* wants to be the opposite of what I want
    return files.stream().sorted((a, b) -> this.sorter.compare(b, a))
        .reduce("", (acc, file) -> file.summarize() + acc, String::concat);
  }
}
