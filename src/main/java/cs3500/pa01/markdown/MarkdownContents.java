package cs3500.pa01.markdown;

import cs3500.pa01.files.FileContents;
import java.util.ArrayList;

/**
 * Represents the contents of a markdown file
 */
public class MarkdownContents implements FileContents {

  /**
   * The headers in this markdown file
   */
  private final ArrayList<Header> headers;

  /**
   * Creates a markdown contents from a list of headers
   *
   * @param headers The headers contained in this file
   */
  public MarkdownContents(ArrayList<Header> headers) {
    this.headers = headers;
  }

  /**
   * Summarize the contents of this file into a string
   *
   * @return A String representing the summarized contents of this file
   */
  @Override
  public String summarize() {
    StringBuilder sb = new StringBuilder();
    for (Header h : headers) {
      sb.append(h.summarize());
    }
    return sb.toString();
  }
}
