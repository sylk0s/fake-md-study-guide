package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import cs3500.pa01.files.FileIo;
import java.io.File;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Driver Class
 */
class DriverTest {

  /**
   * Setup for the output directory
   */
  @BeforeAll
  public static void setup() {
    File out = new File("sampleData/out/");
    if (!out.exists()) {
      if (!out.mkdir()) {
        System.err.println("Failed to create output directory, stopping the test");
      } else {
        System.out.println("Created sampleData/out/");
      }
    }
  }

  /**
   * Tests for the case where a user doesn't provide enough arguments to the driver class
   */
  @Test
  public void testTooFewArgs() {
    assertThrows(RuntimeException.class, () -> Driver.main(new String[] { "a", "b" }));
  }

  /**
   * Tests that normal usage of the Driver class's main method
   * Doesn't test super complex parsing since that's confirmed to work by the parser tests
   */
  @Test
  public void testMainFunctionality() {
    try {
      Driver.main(new String[] {"sampleData/tests2", "filename", "sampleData/out/out2.md"});

      ArrayList<String> result = new ArrayList<>();
      result.add("# File 1 Header");
      result.add("- File 1 phrase");
      result.add("");
      result.add("# File 2 Header");
      result.add("- File 2 phrase");
      result.add("");

      assertEquals(FileIo.readFile("sampleData/out/out2.md"), result);
    } catch (Exception e) {
      fail();
    }
  }

  /**
   * This is a very complex case with multiple files nested in many directories
   * Also contains multiple cases of split line brackets, mixed file name, and other weirdness
   */
  @Test
  public void superComplexTest() {
    try {
      Driver.main(new String[] {"sampleData/tests1", "filename", "sampleData/out/out1.md"});

      assertEquals(FileIo.readFile("sampleData/out/out1.md").stream()
          .reduce("", (acc, x) -> acc + x + "\n", String::concat),
          """
              # Java Arrays
              - An **array** is a collection of variables of the same type
                            
              ## Declaring an Array
              - General Form: type[] arrayName;
              - only creates a reference
              - no array has actually been created yet
                            
              ## Creating an Array (Instantiation)
              - General form:  arrayName = new type[numberOfElements];
              - numberOfElements must be a positive Integer.
              - Gotcha: Array size is not modifiable once instantiated.
                            
              ## Random header from random file!
                            
              # Top Header
              - thing 1
              - 3
              - ing 4
                            
              ## Next Header
              - else
                            
              ## Another thing
                            
              ### Deepr header
              - important
                            
              # Another top level header
                            
              ## Another
                            
              # Vectors
              - Vectors act like resizable arrays
                            
              ## Declaring a vector
              - General Form: Vector<type> v = new Vector();
              - type needs to be a valid reference type
                            
              ## Adding an element to a vector
              - v.add(object of type);
                            
              """);
    } catch (Exception e) {
      fail();
    }
  }
}