package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

/**
 * Tests the FileIo class for interacting with (reading & writing to) files in the filesystem
 */
class FileIoTest {

  /**
   * Tests reading in a file preloaded with some content
   */
  @Test
  public void testReadFile() {
    ArrayList<String> result = new ArrayList<>();
    result.add("aaa");
    result.add("bbb");
    result.add("cdefg");
    result.add("587");

    try {
      assertEquals(FileIo.readFile("sampleData/io-testing/data.txt"), result);
    } catch (IOException e) {
      fail();
    }

    assertThrows(IOException.class, () -> FileIo.readFile("some/fake/path"));
  }

  /**
   * Tests writing to a new file
   */
  @Test
  public void testWriteFile() {
    String s = "abc\ndef";

    FileIo.writeFile("sampleData/io-testing/written.txt", s);

    ArrayList<String> result = new ArrayList<>();
    result.add("abc");
    result.add("def");

    try {
      assertEquals(FileIo.readFile("sampleData/io-testing/written.txt"), result);
    } catch (IOException e) {
      fail();
    }

    // I think this will only work on *nix systems
    try {
      // cant.txt is a file that can't be written to
      // (using chmod -w sampleData/io-testing/cant.txt on a linux system,
      // probably also works on macOS, I doubt it does on windows)
      Runtime.getRuntime().exec(
          new String[] {"sh", "-c", "chmod -w sampleData/io-testing/cant.txt"}).onExit()
          .thenRun(() ->
              assertThrows(RuntimeException.class, () ->
                  FileIo.writeFile("sampleData/io-testing/cant.txt", "aaaaa")));

    } catch (IOException e) {
      e.printStackTrace();
      System.err.println("Failed to run the command and so the test will not work");
    }
  }
}