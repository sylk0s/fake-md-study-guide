package cs3500.pa01.cli;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.StringReader;
import org.junit.jupiter.api.Test;

class SimpleCliTest {
  @Test
  public void testGetInput1() {
    // StringReader implements Readable
    Readable input = new StringReader("answer");

    // StringBuilder implements Appendable
    Appendable output = new StringBuilder();

    SimpleCli cli = new SimpleCli(input, output);

    try {
      assertEquals(cli.getInput("Question:"), "answer");
    } catch (IOException e) {
      fail();
    }

    assertEquals(output.toString(), "Question:");
  }

  @Test
  public void testGetInput2() {
    // StringReader implements Readable
    Readable input = new StringReader("answer\nsomething that shouldn't be included");

    // StringBuilder implements Appendable
    Appendable output = new StringBuilder();

    SimpleCli cli = new SimpleCli(input, output);

    try {
      assertEquals(cli.getInput("Question:"), "answer");
    } catch (IOException e) {
      fail();
    }

    assertEquals(output.toString(), "Question:");
  }

  @Test
  public void testGetInt() {
    // StringReader implements Readable
    Readable input = new StringReader("123");

    // StringBuilder implements Appendable
    Appendable output = new StringBuilder();

    SimpleCli cli = new SimpleCli(input, output);

    try {
      assertEquals(cli.getInt("Question:"), 123);
    } catch (IOException e) {
      fail();
    }

    assertEquals(output.toString(), "Question:");
  }

  @Test
  public void testDisplay() {
    // StringReader implements Readable
    Readable input = new StringReader("");

    // StringBuilder implements Appendable
    Appendable output = new StringBuilder();

    SimpleCli cli = new SimpleCli(input, output);

    try {
      cli.displayString("aaa");
    } catch (IOException e) {
      fail();
    }

    assertEquals(output.toString(), "aaa");

    try {
      cli.displayString("bbb");
    } catch (IOException e) {
      fail();
    }

    assertEquals(output.toString(), "aaabbb");
  }
}