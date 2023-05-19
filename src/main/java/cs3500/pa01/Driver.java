package cs3500.pa01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This is the main driver of this project.
 */
public class Driver {
  /**
   * Project entry point
   *
   * @param args - command line arguments for this program
   *             1) Parent directory location
   *             2) sorting method: one of ["filename" "modified" "created"]
   *             3) output file path
   * @throws IOException thrown if walkFileTree somehow fails
   */
  public static void main(String[] args) throws IOException {
    if (args.length >= 3) {
      // Read files in and gets the list of SummarizableFiles
      MkCollectorFileVisitor visitor = new MkCollectorFileVisitor();
      Files.walkFileTree(Path.of(args[0]), visitor);

      // generate a summary from this list & writes it to a file
      SummaryGenerator sg = new SummaryGenerator(visitor.getFiles());
      FileIo.writeFile(args[2], sg.generate(args[1]));
    } else {
      throw new RuntimeException("Too few arguments");
    }
  }
}