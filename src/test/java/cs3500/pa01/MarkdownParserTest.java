package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa01.files.SummarizableFile;
import cs3500.pa01.parsing.MarkdownParser;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

/**
 * Tests for the markdown file parser
 */
class MarkdownParserTest {

  /**
   * Tests the most basic parsable file with just a header
   */
  @Test
  public void testBasicHeaderParsing() {
    ArrayList<String> lines = new ArrayList<>();
    lines.add("# Header 1");
    MarkdownParser parser = new MarkdownParser(lines);
    SummarizableFile file = parser.parse("name",
        FileTime.fromMillis(0), FileTime.fromMillis(0));
    assertEquals(file.summarize(), "# Header 1\n\n");
  }

  /**
   * Tests a slightly more complex case with various forms of important phrases
   */
  @Test
  public void testSingleDepthHeader() {
    ArrayList<String> lines = new ArrayList<>();
    lines.add("# Header 1");
    lines.add("- [[important]]");
    lines.add("- nothing");
    lines.add("- aaa [[some");
    lines.add("- thing]]");
    MarkdownParser parser = new MarkdownParser(lines);
    SummarizableFile file = parser.parse("name",
        FileTime.fromMillis(0), FileTime.fromMillis(0));
    assertEquals(file.summarize(), "# Header 1\n- important\n- some thing\n\n");
  }

  /**
   * Tests an example that adds another subheader containing it's own phrase
   */
  @Test
  public void testSubheaderExample() {
    ArrayList<String> lines = new ArrayList<>();
    lines.add("# Header 1");
    lines.add("- [[important]]");
    lines.add("- nothing");
    lines.add("- aaa [[some");
    lines.add("- thing]]");
    lines.add("");
    lines.add("## Subheader");
    lines.add("- [[another important phrase]]");
    MarkdownParser parser = new MarkdownParser(lines);
    SummarizableFile file = parser.parse("name",
        FileTime.fromMillis(0), FileTime.fromMillis(0));
    assertEquals(file.summarize(),
        """
            # Header 1
            - important
            - some thing

            ## Subheader
            - another important phrase

            """
    );
  }

  /**
   * Tests a very complex example with many different depths of headers in varying orders
   */
  @Test
  public void testMultipleSameDepthHeaders() {
    ArrayList<String> lines = new ArrayList<>();
    lines.add("# Header 1");
    lines.add("- [[important]]");
    lines.add("- nothing");
    lines.add("- aaa [[some");
    lines.add("- thing]]");
    lines.add("");
    lines.add("## Subheader");
    lines.add("- [[another important phrase]]");

    // Tests wacky header nesting
    lines.add("# Header 2");
    lines.add("## Header 3");
    lines.add("### Header 4");
    lines.add("## Header 5");
    MarkdownParser parser = new MarkdownParser(lines);
    SummarizableFile file = parser.parse("name",
        FileTime.fromMillis(0), FileTime.fromMillis(0));
    assertEquals(file.summarize(),
        """
            # Header 1
            - important
            - some thing

            ## Subheader
            - another important phrase

            # Header 2

            ## Header 3

            ### Header 4

            ## Header 5

            """
    );
  }

  /**
   * Tests another example of multi line phrases where there isn't a "- " before the phrase
   */
  @Test
  public void confirmMultiLinePhrase() {
    ArrayList<String> lines = new ArrayList<>();
    lines.add("# Header 1");
    lines.add("- [[aaa");
    lines.add("bbb]]");
    MarkdownParser parser = new MarkdownParser(lines);
    SummarizableFile file = parser.parse("name",
        FileTime.fromMillis(0), FileTime.fromMillis(0));
    assertEquals(file.summarize(),
        """
            # Header 1
            - aaa bbb

            """
    );
  }

  /**
   * Tests having multiple important phrases on one line
   */
  @Test
  public void testMultiplePhrasesOnOneLine() {
    ArrayList<String> lines = new ArrayList<>();
    lines.add("# Header 1");
    lines.add("- [[aaa]] bbb [[ccc]]");
    MarkdownParser parser = new MarkdownParser(lines);
    SummarizableFile file = parser.parse("name",
        FileTime.fromMillis(0), FileTime.fromMillis(0));
    assertEquals(file.summarize(),
        """
            # Header 1
            - aaa
            - ccc

            """
    );
  }

  /**
   * Tests a ton of bracket configurations that should not be matched
   */
  @Test
  public void testBrackets() {
    ArrayList<String> lines = new ArrayList<>();
    lines.add("# Header 1");
    lines.add("- []");
    lines.add("- [ [ ] ]");
    lines.add("- int[]");
    lines.add("- [    [    [ [ [ [    [ ] [ ] [ [ ] ] ]    ]   ]  [   ]         [ ]");
    lines.add("- ");
    lines.add("");
    lines.add("\n");
    lines.add("- #");
    lines.add("- [ [ ### ] ]##");
    lines.add("[]");
    lines.add("- [a[a]a]");
    lines.add("- [][][][]");
    MarkdownParser parser = new MarkdownParser(lines);
    SummarizableFile file = parser.parse("name",
        FileTime.fromMillis(0), FileTime.fromMillis(0));

    // Asserts that none of the lines match
    assertEquals(file.summarize(), "# Header 1\n\n");
  }
}