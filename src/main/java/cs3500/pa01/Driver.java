package cs3500.pa01;

import cs3500.pa01.files.FileIo;
import cs3500.pa01.files.MkCollectorFileVisitor;
import cs3500.pa01.spacedrep.Session;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This is the main driver of this project.
 */
public class Driver {
  // TODO make sure command line args are correct

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
      // Reads in a list of MarkdownFiles
      MkCollectorFileVisitor visitor = new MkCollectorFileVisitor();
      Files.walkFileTree(Path.of(args[0]), visitor);

      // generate a summary from this list & writes it to a file
      SummaryGenerator sg = new SummaryGenerator(visitor.getFiles(), args[1]);
      FileIo.writeFile(args[2], sg.generate());

      // Generate a sr files from this list and write it to a file
      SrFileGenerator sr = new SrFileGenerator(visitor.getFiles());
      FileIo.writeFile(args[2].substring(0, args[2].length() - 3) + ".sr", sr.generate());

    } else if (args.length == 0) {

      // Create a new session
      Session session = new Session();
      session.run();
    } else {
      throw new RuntimeException("Too few arguments");
    }
  }
}