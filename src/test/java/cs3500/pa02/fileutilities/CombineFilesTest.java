package cs3500.pa02.fileutilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for CombineFiles and its associated methods
 */
class CombineFilesTest extends FileUtilitiesTest {

  private ArrayList<File> twoFiles;
  private ArrayList<File> files;
  private ArrayList<File> withFake;
  private String arraysAndTest;
  private String allCombined;

  /**
   * Initializes the data for tests
   */
  @BeforeEach
  public void setup() {
    File fake = Path.of("src/tests/resources/notes-root/nonexistent.md").toFile();
    twoFiles = new ArrayList<>(Arrays.asList(test, arrays));
    files = new ArrayList<>(Arrays.asList(arrays, test, vectors, java));
    withFake = new ArrayList<>(Arrays.asList(test, fake));
    arraysAndTest = """ 
        # This is a test file
                
        ## Heading
        - [[This is a test question:::answer]]
                
        # Java Arrays
        - [[An **array** is a collection of variables of the same type]], referred to
          by a common name.
        - In Java, arrays are objects, and must be created dynamically (at runtime).
        - [[Where are arrays stored in memory?:::the heap]]
                
        ## Declaring an Array
        - [[General Form: type[] arrayName;]]
        - ex: int[] myData;
        - [[T/F Arrays can hold multiple data types:::false]]
                
        - The above only creates a reference to an array object, but no array has
          actually been created yet.
                
        ## Creating an Array (Instantiation)
        - [[General form:  arrayName = new type[numberOfElements];]]
        - ex: myData = new int[100];
                
        - Data types of the reference and array need to match.
        - [[numberOfElements must be a positive Integer.]]
        - [[Gotcha: Array size is not modifiable once instantiated. ]]
                
        ... more brilliance captured...\s
        
        """;
    allCombined = """
        # Java Arrays
        - [[An **array** is a collection of variables of the same type]], referred to
          by a common name.
        - In Java, arrays are objects, and must be created dynamically (at runtime).
        - [[Where are arrays stored in memory?:::the heap]]

        ## Declaring an Array
        - [[General Form: type[] arrayName;]]
        - ex: int[] myData;
        - [[T/F Arrays can hold multiple data types:::false]]

        - The above only creates a reference to an array object, but no array has
          actually been created yet.

        ## Creating an Array (Instantiation)
        - [[General form:  arrayName = new type[numberOfElements];]]
        - ex: myData = new int[100];

        - Data types of the reference and array need to match.
        - [[numberOfElements must be a positive Integer.]]
        - [[Gotcha: Array size is not modifiable once instantiated. ]]

        ... more brilliance captured...\s

        # This is a test file

        ## Heading
        - [[This is a test question:::answer]]

        # Vectors
        - [[Vectors act like resizable arrays]].
        - [[Which method can resize a vector?:::setSize(int size)]]

        ## Declaring a vector
        - [[General Form: Vector<type> v = new Vector();]]
        - Example: Vector<Integer> v = new Vector();

        - [[type needs to be a valid reference type]]

        ## Adding an element to a vector
        - [[v.add(object of type);]]

        - Reminder - go back and review clearing a vector!

        # Java Basics
        - High-level language
        - [[ Object-oriented ]]
        - Class based
        - Java applications are typically compiled to bytecode that can run on any JVM

        ## History
        - Originally developed by James Gosling at Sun Microsystems
        - Released in [[ May 1995 ]] as part of Sun Microsystems' Java platform

        ### Similarities
        - C/C++ style syntax
        
        """;
  }

  /**
   * Tests the getCombinedFiles method
   */
  @Test
  public void testGetCombinedFiles() {
    CombineFiles one = new CombineFiles(twoFiles);
    CombineFiles two = new CombineFiles(files);
    String twoCombined;
    String fourCombined;
    twoCombined = one.getCombinedFiles();
    fourCombined = two.getCombinedFiles();
    CombineFiles three = new CombineFiles(withFake);
    assertEquals(arraysAndTest, twoCombined);
    assertEquals(allCombined, fourCombined);
    assertThrows(RuntimeException.class, three::getCombinedFiles);
  }
}