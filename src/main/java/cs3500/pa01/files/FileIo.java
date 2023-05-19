package cs3500.pa01.files;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a class to manage various interactions with the file system
 */
public class FileIo {

  /**
   * Convenience method to convert a String to a Path
   *
   * @param path The path as a string
   * @return The path as a Path object
   */
  private static Path getPath(String path) {
    return FileSystems.getDefault().getPath(path);
  }

  /**
   * Reads the content of the file at a path
   *
   * @param path A String representing the path
   *
   * @return An ArrayList representing the lines of the file
   */
  public static ArrayList<String> readFile(String path) throws IOException {
    Path p = getPath(path);
    ArrayList<String> lines = new ArrayList<>();

    Scanner sc = new Scanner(p);

    while (sc.hasNextLine()) {
      lines.add(sc.nextLine());
    }

    return lines;
  }

  /**
   * Writes the contents of a string to a file
   *
   * @param pathStr A String representing the path
   * @param content The content to write to the file
   */
  public static void writeFile(String pathStr, String content) {
    Path path = getPath(pathStr);
    byte[] data = content.getBytes();

    try {
      Files.write(path, data);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
