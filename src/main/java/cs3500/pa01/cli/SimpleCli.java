package cs3500.pa01.cli;

import java.util.Scanner;

public class SimpleCli implements Ui {

  @Override
  public void displayString(String s) {
    System.out.println(s);
  }

  @Override
  public String getInput(String prompt) {
    System.out.println(prompt);
    Scanner sc = new Scanner(System.in);
    return sc.nextLine();
  }
}
