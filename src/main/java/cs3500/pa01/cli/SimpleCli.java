package cs3500.pa01.cli;

import java.io.IOException;
import java.util.Scanner;

/**
 * Represents a super simple implementation of a CLI based UI
 */
public class SimpleCli implements Ui {

  private final Readable input;
  private final Appendable output;

  public SimpleCli(Readable input, Appendable output) {
    this.input = input;
    this.output = output;
  }

  /**
   * Displays a string to the user
   *
   * @param s The string to display
   */
  @Override
  public void displayString(String s) throws IOException {
    this.output.append(s).append("\n");
  }

  /**
   * Get string input from the user
   *
   * @param prompt The prompt to display the user
   * @return The input from the user
   */
  @Override
  public String getInput(String prompt) throws IOException {
    this.output.append(prompt).append("\n");
    Scanner sc = new Scanner(this.input);
    return sc.nextLine();
  }

  /**
   * Get int input from the user
   *
   * @param prompt The prompt to display the user
   * @return The user's input
   */
  @Override
  public int getInt(String prompt) throws IOException {
    this.output.append(prompt).append("\n");
    Scanner sc = new Scanner(this.input);
    return sc.nextInt();
  }
}
