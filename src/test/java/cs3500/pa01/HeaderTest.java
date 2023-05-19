package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

/**
 * Represents tests for the header class
 */
class HeaderTest {

  /**
   * This tests the simplest case confirming that a MK file that's just a header prints correctly
   */
  @Test
  public void testSummarizeSimpleHeader() {
    ArrayList<String> important = new ArrayList<>();
    ArrayList<Header> sub = new ArrayList<>();
    Header header = new Header(
        "Header 1",
        important,
        sub,
        1
    );

    assertEquals(header.summarize(),
        "# Header 1\n\n");
  }

  /**
   * Confirms that a header with just important phrases works as expected
   */
  @Test
  public void testSummarizeDepth1Header() {
    ArrayList<String> important = new ArrayList<>();
    important.add("Some important phrase");
    important.add("Another important phrase");
    ArrayList<Header> sub = new ArrayList<>();
    Header header = new Header(
        "Header 1",
        important,
        sub,
        1
    );

    assertEquals(header.summarize(),
        """
            # Header 1
            - Some important phrase
            - Another important phrase

            """
    );
  }

  /**
   * Confirms that all headers display, even ones without important phrases
   * Also tests a much more complex case
   */
  @Test
  public void testConfirmAllHeadersDisplay() {
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

    assertEquals(header1.summarize(),
        """
            # Header 1
            - another important phrase

            ## Header 2

            ## Header 3
            - some phrase

            """
    );
  }

  /**
   * Confirms that adding important phrases works for headers
   */
  @Test
  public void testAddPhrase() {
    Header header1 = new Header("aaa", 1);
    ArrayList<String> strings = new ArrayList<>();
    strings.add("Phrase");
    Header header2 = new Header("aaa", strings, new ArrayList<>(), 1);

    header1.addPhrase("Phrase");
    // Uses the summarize methods here because we don't have a notion of equality for headers
    assertEquals(header2.summarize(), header1.summarize());
  }

  /**
   * Confirms that adding sub headers works for headers
   */
  @Test
  public void testAddSub() {
    Header parent = new Header("bbb", 1);
    Header sub = new Header("aaa", 2);
    ArrayList<Header> headers = new ArrayList<>();
    headers.add(sub);
    Header parent2 = new Header("bbb", new ArrayList<>(), headers, 1);

    parent.addSubHeader(sub);
    // Uses the summarize methods here because we don't have a notion of equality for headers
    assertEquals(parent2.summarize(), parent.summarize());
  }

  /**
   * Confirms that the deeperThan function works properly for Headers
   */
  @Test
  public void testDeeperThan() {
    Header deeper = new Header("aaa", 2);
    Header lessDeep = new Header("bbb", 1);

    assertTrue(deeper.deeperThan(lessDeep));
    assertFalse(lessDeep.deeperThan(deeper));
  }
}