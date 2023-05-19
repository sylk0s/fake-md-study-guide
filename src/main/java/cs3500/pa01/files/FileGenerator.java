package cs3500.pa01.files;

/**
 * Some object that can generate the String contents of a file
 */
public interface FileGenerator {

  /**
   * Generate the contents of a file
   *
   * @return A String representing the contents of a file to be written
   */
  String generate();
}
