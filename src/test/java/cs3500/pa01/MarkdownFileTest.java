package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

/**
 * Tests basic functions for MarkdownFile, most of the logic is tested for it's contents instead
 */
class MarkdownFileTest {

  /**
   * We have already confirmed that the header generates the summary correctly,
   * so we just confirm that these are the same
   */
  @Test
  public void testSameAsHeader() {
    Header header = new Header("Header 1", 1);

    MarkdownFile file = new MarkdownFile(header,
        FileTime.fromMillis(0), FileTime.fromMillis(0), "name");

    assertEquals(file.summarize(), header.summarize());
  }

  /**
   * Tests a more complex header example
   */
  @Test
  public void testComplexSummary() {
    Header header2 = new Header(
        "Header 2",
        new ArrayList<>(),
        new ArrayList<>(),
        2
    );

    ArrayList<String> important3 = new ArrayList<>();
    important3.add("some phrase");
    Header header3 = new Header(
        "Header 3",
        important3,
        new ArrayList<>(),
        2
    );

    ArrayList<String> important = new ArrayList<>();
    ArrayList<Header> sub = new ArrayList<>();
    important.add("another important phrase");
    Header header1 = new Header(
        "Header 1",
        important,
        sub,
        1
    );

    sub.add(header2);
    sub.add(header3);

    MarkdownFile file = new MarkdownFile(header1,
        FileTime.fromMillis(0), FileTime.fromMillis(0), "name");

    assertEquals(file.summarize(), header1.summarize());
  }

  /**
   * Confirms that having multiple top level headers works as expected
   */
  @Test
  public void testMultipleHeaders() {
    ArrayList<Header> headers = new ArrayList<>();
    headers.add(new Header("Header 1", 1));
    headers.add(new Header("Header 2", 1));

    MarkdownFile file = new MarkdownFile(new MarkdownContents(headers),
        FileTime.fromMillis(0), FileTime.fromMillis(0), "name");

    assertEquals(file.summarize(), "# Header 1\n\n# Header 2\n\n");
  }
}