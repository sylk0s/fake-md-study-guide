package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa01.files.SummarizableFile;
import cs3500.pa01.markdown.Header;
import cs3500.pa01.markdown.MarkdownFile;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the summary generator class
 */
class SummaryGeneratorTest {

  /**
   * A SummaryGenerator used by most tests
   */
  private SummaryGenerator sg;

  /**
   * Setup function called before each function
   * Creates the summary generator object from some file objects with set times and names
   */
  @BeforeEach
  public void setup() {
    Header h1 = new Header("Header 1", new ArrayList<>(), new ArrayList<>(), 1);
    Header h2 = new Header("Header 2", new ArrayList<>(), new ArrayList<>(), 1);
    Header h3 = new Header("Header 3", new ArrayList<>(), new ArrayList<>(), 1);

    // Artificially gives the files "created" and "modified" times for testing
    // This way we don't even need to artificially create files
    MarkdownFile f1 = new MarkdownFile(h1, FileTime.fromMillis(0),
        FileTime.fromMillis(2), "bbb");
    MarkdownFile f2 = new MarkdownFile(h2, FileTime.fromMillis(1),
        FileTime.fromMillis(1), "ccc");
    MarkdownFile f3 = new MarkdownFile(h3, FileTime.fromMillis(2),
        FileTime.fromMillis(0), "aaa");

    ArrayList<SummarizableFile> files = new ArrayList<>();
    files.add(f1);
    files.add(f2);
    files.add(f3);

    this.sg = new SummaryGenerator(files);
  }

  /**
   * Confirms that ordering by modified time works
   * oldest should be first
   */
  @Test
  public void testModifiedOrdering() {
    assertEquals(this.sg.generate("modified"), "# Header 1\n\n# Header 2\n\n# Header 3\n\n");
  }

  /**
   * Confirms that ordering by created date works
   * oldest should be first
   */
  @Test
  public void testCreatedOrdering() {
    assertEquals(this.sg.generate("created"), "# Header 3\n\n# Header 2\n\n# Header 1\n\n");
  }

  /**
   * Confirms that ordering by name works
   */
  @Test
  public void testNameOrdering() {
    assertEquals(this.sg.generate("filename"), "# Header 3\n\n# Header 1\n\n# Header 2\n\n");
  }

  /**
   * Confirms that the contents of the files are combined as expected
   * Uses a more complex example with sub headers and phrases
   */
  @Test
  public void testProperConcat() {
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
        FileTime.fromMillis(0), FileTime.fromMillis(0), "filename");

    // This file should get put after the other one, so it shows up afterward
    ArrayList<String> important2 = new ArrayList<>();
    important2.add("IMPORTANT!!!");
    Header h = new Header("Some random other header",
        important2, new ArrayList<>(), 1);
    MarkdownFile file2 = new MarkdownFile(h,
        FileTime.fromMillis(0), FileTime.fromMillis(0), "zzz");

    ArrayList<SummarizableFile> files = new ArrayList<>();
    files.add(file2);
    files.add(file);

    SummaryGenerator sumGen = new SummaryGenerator(files);

    assertEquals(sumGen.generate("filename"),
        """
            # Header 1
            - another important phrase

            ## Header 2

            ## Header 3
            - some phrase

            # Some random other header
            - IMPORTANT!!!

            """
    );
  }

  /**
   * Confirms that an invalid sorting type will throw an error
   */
  @Test
  public void testThrows() {
    assertThrows(IllegalArgumentException.class, () -> sg.generate("AAA"));
  }
}