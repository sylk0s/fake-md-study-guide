package cs3500.pa01;

import static java.nio.file.FileVisitResult.CONTINUE;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

/**
 * Represents a FileVisitor that collects all the markdown files in a list of SummarizableFiles
 */
public class MkCollectorFileVisitor implements FileVisitor<Path> {

  /**
   * A list of SummarizableFiles that has been collected so far by the FileVisitor
   */
  private final ArrayList<SummarizableFile> files = new ArrayList<>();

  /**
   * Logic for what to do when a file is visited
   *
   * @param file a reference to the readIntoHeaderStackfile
   * @param attr the file's basic attributes
   *
   * @return Will always return CONTINUE so that files continue to be visited
   */
  @Override
  public FileVisitResult visitFile(Path file, BasicFileAttributes attr) throws IOException {
    String fileName = file.getFileName().toString();
    // System.out.println("Visited " + fileName);
    if (fileName.endsWith(".md")) {
      files.add(new MarkdownParser(
          FileIo.readFile(file.toAbsolutePath().toString())).parse(fileName, attr.creationTime(),
          attr.lastModifiedTime()));
    }

    return CONTINUE;
  }

  /**
   * Called after finishing a directory
   *
   * @param dir a reference to the directory
   * @param exec
   *          {@code null} if the iteration of the directory completes without
   *          an error; otherwise the I/O exception that caused the iteration
   *          of the directory to complete prematurely
   *
   * @return No logic here, always returns CONTINUE
   */
  @Override
  public FileVisitResult postVisitDirectory(Path dir, IOException exec) {
    return CONTINUE;
  }

  /**
   * Called before visiting a directory
   *
   * @param dir a reference to the directory
   * @param attrs the directory's basic attributes
   *
   * @return No logic here, always returns CONTINUE
   */
  @Override
  public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
    return CONTINUE;
  }

  /**
   * Called on a failure to visit a file
   *
   * @param file
   *          a reference to the file
   *          * @param exc
   *          the I/O exception that prevented the file from being visited
   *
   * @return No logic here, always returns CONTINUE
   */
  @Override
  public FileVisitResult visitFileFailed(Path file, IOException exc) {
    return CONTINUE;
  }

  /**
   * Used to get the files collected by the FileVisitor
   *
   * @return Returns the parsed files in an ArrayList
   */
  public ArrayList<SummarizableFile> getFiles() {
    return files;
  }
}
