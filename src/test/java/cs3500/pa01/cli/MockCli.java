package cs3500.pa01.cli;

import java.io.IOException;

/**
 * A Mock Object that will always throw an IOException
 */
public class MockCli implements Ui {
  /**
   * Display a string to the user
   *
   * @param s The string to display
   */
  @Override
  public void displayString(String s) throws IOException {
    throw new IOException();
  }

  /**
   * Get a string input from the user
   *
   * @param prompt The prompt to display the user
   * @return The value input
   */
  @Override
  public String getInput(String prompt) throws IOException {
    throw new IOException();
  }

  /**
   * Get an int input from the user
   *
   * @param prompt The prompt to display the user
   * @return The value input
   */
  @Override
  public int getInt(String prompt) throws IOException {
    throw new IOException();
  }
}
