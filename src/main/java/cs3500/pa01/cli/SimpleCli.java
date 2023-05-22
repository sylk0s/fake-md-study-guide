package cs3500.pa01.cli;

import java.util.Scanner;

/**
 * Represents a super simple implementation of a CLI based UI
 */
public class SimpleCli implements Ui {

  /**
   * Displays a string to the user
   *
   * @param s The string to display
   */
  @Override
  public void displayString(String s) {
    System.out.println(s);
  }

  /**
   * Get string input from the user
   *
   * @param prompt The prompt to display the user
   * @return The input from the user
   */
  @Override
  public String getInput(String prompt) {
    System.out.println(prompt);
    Scanner sc = new Scanner(System.in);
    return sc.nextLine();
  }

  /**
   * Get int input from the user
   *
   * @param prompt The prompt to display the user
   * @return The user's input
   */
  @Override
  public int getInt(String prompt) {
    System.out.println(prompt);
    Scanner sc = new Scanner(System.in);
    return sc.nextInt();
  }
}
