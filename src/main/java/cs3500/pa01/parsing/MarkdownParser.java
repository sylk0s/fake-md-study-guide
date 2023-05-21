package cs3500.pa01.parsing;

import static java.util.stream.Collectors.toCollection;

import cs3500.pa01.markdown.Header;
import cs3500.pa01.markdown.MarkdownContents;
import cs3500.pa01.markdown.MarkdownFile;
import cs3500.pa01.spacedrep.Question;
import cs3500.pa01.spacedrep.QuestionType;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * Represents a Parser that can parse a markdown file into an object
 */
public class MarkdownParser {

  /**
   * A list of the lines of the file
   */
  private final ArrayList<String> lines;

  /**
   * A Stack for intermediate storage of headers (before nesting)
   */
  private final Stack<Header> headers;

  /**
   * The questions from this file
   */
  private final ArrayList<Question> questions;

  /**
   * Creates a new markdown parser given the lines to parse
   *
   * @param lines the content of the markdown file to parse
   */
  public MarkdownParser(ArrayList<String> lines) {
    this.lines = lines;
    this.headers = new Stack<>();
    this.questions = new ArrayList<>();
  }

  /**
   * Parse the contents of this file into an object
   *
   * @param name The name of the file being parsed
   * @param created The FileTime that the file was created
   * @param modified The FileTime that the file was last modified
   *
   * @return An object representing the parsed markdown file
   */
  public MarkdownFile parse(String name, FileTime created, FileTime modified) {
    this.readIntoHeaderStack();
    return new MarkdownFile(new MarkdownContents(this.nestHeaders()),
        created, modified, name, this.questions);
  }

  /**
   * Reads the headers into the headerStack and nests the important phrases under them
   * this essentially handles creating the objects but doesn't
   * put them into their proper tree structure
   */
  private void readIntoHeaderStack() {
    // Handles putting important phrases under the lines
    StringBuilder headerBlock = new StringBuilder();
    for (String line : lines) {
      if (isHeader(line)) {
        if (!this.headers.empty()) {
          // Adds all important phrases in the block to the most recent header
          this.addPhrases(headerBlock);
          headerBlock = new StringBuilder();
        }

        // Add a new header
        this.headers.push(new Header(headerName(line), depthOf(line)));
      } else {
        // This removes the "- " from the start of the line if needed
        // Also handles the logic for wraparound of [[ ]]
        // Then it appends it to the header block for processing later
        headerBlock.append(" ");
        headerBlock.append(line.substring(line.indexOf("- ") + 1).trim());
      }
    }

    // Catches the stuff at the end of the file
    addPhrases(headerBlock);
  }

  /**
   * Adds the important phrases in this block to the most recent header
   *
   * @param headerBlock A string containing all of the text under the header
   */
  private void addPhrases(StringBuilder headerBlock) {
    Header current = this.headers.pop();
    // process all the phrases from the previous header
    for (String phrase : getPhrases(headerBlock.toString())) {
      if (isQuestion(phrase)) {
        this.questions.add(parseQuestion(phrase));
      } else {
        current.addPhrase(phrase);
      }
    }
    this.headers.push(current);
  }

  /**
   * Determines if a phrase is a question
   *
   * @param phrase the phrase to analyze
   * @return A boolean, true if it is a question
   */
  private boolean isQuestion(String phrase) {
    return phrase.contains(":::");
  }

  /**
   * Parses a String into a question
   *
   * @param question The question as a string
   * @return The question as a Question obj
   */
  private Question parseQuestion(String question) {
    String[] split = question.split(":::");
    return new Question(QuestionType.HARD, split[0], split[1]);
  }

  /**
   * Nests the headers into the proper tree form
   *
   * @return An ArrayList of all the top level headers
   */
  private ArrayList<Header> nestHeaders() {
    // Cleans up and nests headers properly
    ArrayList<Header> below = new ArrayList<>();
    while (!this.headers.empty()) {
      Header h = this.headers.pop();

      // Moves anything less than this header to it's subheaders
      below = below.stream().filter((sub) -> {
        if (sub.deeperThan(h)) {
          h.addSubHeader(sub);
          return false;
        }
        return true;
      }).collect(toCollection(ArrayList::new));

      // Adds this header to the list (since it now contains anything less than it)
      // Puts it at the start so it doesn't get reversed
      below.add(0, h);
    }
    return below;
  }

  /**
   * Checks if a line is a header
   *
   * @param line The line to check
   * @return a boolean representing if this line is a header
   */
  private static boolean isHeader(String line) {
    return line.startsWith("#");
  }

  /**
   * The name of this header (Assumes this line is a header)
   *
   * @param line The line to extract the name from
   * @return The name of the header
   */
  private static String headerName(String line) {
    return line.substring(depthOf(line) + 1);
  }

  /**
   * Basically gets the number of "#" before the header
   *
   * @param line The line to extract from
   * @return The number of "#" before the header
   */
  private static int depthOf(String line) {
    return line.split(" ")[0].length();
  }

  /**
   * Takes a single string containing all the text under a header and turns it
   * into a list of important phrases
   *
   * @param contents The contents of all the phrases under a header
   * @return A list of all the important phrases
   */
  private static ArrayList<String> getPhrases(String contents) {
    // Adds "a" to the start here so we know that the first split will NEVER start with
    // the beginning of an important phrase,
    // all the others will always start with an important phrase
    String[] aaa = ("a" + contents).split("\\[\\[");

    // Here we skip the first one (see previous comment)
    // Then we split each string on the end of an important phrase and pick out the start of that
    // (the first important phrase). Then we return an arraylist of these
    return Arrays.stream(aaa).skip(1).map((chunk) -> chunk.split("]]")[0])
        .collect(toCollection(ArrayList::new));
  }
}
