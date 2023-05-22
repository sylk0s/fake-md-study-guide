package cs3500.pa01.cli;

/**
 * A UI to interact with the user
 */
public interface Ui {

  /**
   * Display a string to the user
   *
   * @param s The string to display
   */
  void displayString(String s);

  /**
   * Get a string input from the user
   *
   * @param prompt The prompt to display the user
   * @return The value input
   */
  String getInput(String prompt);

  /**
   * Get an int input from the user
   *
   * @param prompt The prompt to display the user
   * @return The value input
   */
  int getInt(String prompt);
}
