package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import cs3500.pa01.files.MkCollectorFileVisitor;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the markdown visitor class
 */
class MkCollectorFileVisitorTest {

  /**
   * The markdown file visitor used by most of the tests
   */
  private MkCollectorFileVisitor mkv;

  /**
   * A dummy attributes class to call the functions with
   */
  private BasicFileAttributes attrs;

  /**
   * The path to a dummy file to visit
   */
  private Path path;

  /**
   * Setup for the visiting functions
   *
   * @throws IOException On a failed read from the file (build.grade should always exist here)
   */
  @BeforeEach
  public void setup() throws IOException {
    this.path = Path.of("build.gradle");
    this.attrs = Files.readAttributes(this.path, BasicFileAttributes.class);
    this.mkv = new MkCollectorFileVisitor();
  }

  /**
   * Tests visiting a file, confirms that it returns CONTINUE
   * and that it only collects a file if it ends in ".md"
   */
  @Test
  public void testFileVisit() {
    try {
      assertEquals(this.mkv.visitFile(this.path, this.attrs),
          FileVisitResult.CONTINUE);
      assertEquals(this.mkv.getFiles(), new ArrayList<>());
    } catch (IOException e) {
      fail();
    }

    try {
      this.mkv.visitFile(Path.of("sampleData/tests2/", "file1.md"), this.attrs);
      assertEquals(this.mkv.getFiles().size(), 1);
    } catch (IOException e) {
      fail();
    }
  }

  /**
   * Tests failing to visit a file
   * Confirms it returns CONTINUE
   */
  @Test
  public void testFailVisit() {
    assertEquals(this.mkv.visitFileFailed(Path.of("/"), new IOException()),
        FileVisitResult.CONTINUE);
  }

  /**
   * Tests the pre-visit function
   * Confirms that it returns CONTINUE
   */
  @Test
  public void testPreVisit() {
    assertEquals(this.mkv.preVisitDirectory(this.path, this.attrs),
        FileVisitResult.CONTINUE);
  }

  /**
   * Tests the post-visit function
   * Confirms that it returns CONTINUE
   */
  @Test
  public void testPostVisit() {
    assertEquals(this.mkv.postVisitDirectory(this.path, new IOException()),
        FileVisitResult.CONTINUE);
  }
}