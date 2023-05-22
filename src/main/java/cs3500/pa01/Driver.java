package cs3500.pa01;

import cs3500.pa01.files.FileIo;
import cs3500.pa01.files.MkCollectorFileVisitor;
import cs3500.pa01.files.SummarizableFile;
import cs3500.pa01.spacedrep.Session;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Collectors;

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

      // TODO fix this please
      ArrayList<SummarizableFile> files = visitor.getFiles().stream()
          .map((a) -> (SummarizableFile) a).collect(
            Collectors.toCollection(ArrayList::new));

      // generate a summary from this list & writes it to a file
      SummaryGenerator sg = new SummaryGenerator(files, args[1]);
      FileIo.writeFile(args[2] + ".md", sg.generate());

      SrFileGenerator sr = new SrFileGenerator(visitor.getFiles());
      FileIo.writeFile(args[2] + ".sr", sr.generate());

    } else if (args.length == 0) {
      Session session = new Session();
      session.run();
    } else {
      throw new RuntimeException("Too few arguments");
    }
  }
}