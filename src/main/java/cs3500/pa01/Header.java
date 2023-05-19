package cs3500.pa01;

import java.util.ArrayList;

/**
 * Represents a header in a markdown file
 */
public class Header {

  /**
   * The name of this header
   */
  private final String name;

  /**
   * The important phrases contained directly under this header
   * (Not ones nested under subheaders)
   */
  private final ArrayList<String> important;

  /**
   * The subheaders under this header
   */
  private final ArrayList<Header> subHeader;

  /**
   * The number of # before this header (essentially represents the depth)
   */
  private final int numTags;

  /**
   * Constructs a header by providing data for each field
   *
   * @param name The name of this header
   * @param important The important phrases contained directly under this header
   * @param subHeader The subheaders under this header
   * @param numTags The number of # before this header
   */
  Header(String name, ArrayList<String> important, ArrayList<Header> subHeader, int numTags) {
    this.name = name;
    this.important = important;
    this.subHeader = subHeader;
    this.numTags = numTags;
  }

  /**
   * Generates an empty header
   *
   * @param name The name of this header
   * @param numTags The depth of this header
   */
  Header(String name, int numTags) {
    this(name, new ArrayList<>(), new ArrayList<>(), numTags);
  }

  /**
   * Summarizes the contents and subcontents of this header
   *
   * @return A String representing the summarized version of this header
   */
  public String summarize() {
    StringBuilder sb = new StringBuilder();

    // Appends the header string
    appendNtags(this.numTags, sb);
    sb.append(this.name);
    sb.append("\n");

    // Appends the important info
    for (String s : this.important) {
      sb.append("- ");
      sb.append(s);
      sb.append("\n");
    }

    sb.append("\n");

    // Append all subtags
    for (Header h : this.subHeader) {
      sb.append(h.summarize());
    }

    return sb.toString();
  }

  /**
   * Appends N number of "#" to a string builder
   *
   * @param n the number of "#" to add
   * @param sb the string builder
   */
  private void appendNtags(int n, StringBuilder sb) {
    sb.append("#".repeat(Math.max(0, n)));
    sb.append(" ");
  }

  /**
   * Adds a phrase immediately under this header
   *
   * @param phrase The content of the phrase
   */
  public void addPhrase(String phrase) {
    this.important.add(phrase);
  }

  /**
   * Adds a subheader to this header
   *
   * @param h The subheader to add
   */
  public void addSubHeader(Header h) {
    this.subHeader.add(h);
  }

  /**
   * Compares the depth of this header to another header
   *
   * @param that The other header
   * @return true if this header is deeper than the other header
   */
  public boolean deeperThan(Header that) {
    return that.numTags < this.numTags;
  }
}