package cs3500.pa02.fileformatters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Class for testing FormatFiles and its associated methods
 */
class StudyGuideTest extends FileFormattersTest {

  /**
   * Tests for the summarizeContent method
   */
  @Test
  public void testExtract() {
    StudyGuide arrays = new StudyGuide(arraysAndTest);
    String allCombined = """
        # Java Arrays
        - [[An **array** is a collection of variables of the same type]], referred to
          by a common name.
        - In Java, arrays are objects, and must be created dynamically (at runtime).

        ## Declaring an Array
        - [[General Form: type[] arrayName;]]
        - ex: int[] myData;

        - The above only creates a reference to an array object, [[but no array has
         actually been created yet.]]

        ## Creating an Array (Instantiation)
        - [[General form:  arrayName = new type[numberOfElements];]]
        - ex: myData = new int[100];

        - Data types of the reference and array need to match.
        - [[numberOfElements must be a positive Integer.]]
        - [[Gotcha: Array size is not modifiable once instantiated. ]]

        ... more brilliance captured...\s

        # This is a test file

        ## Heading
        - [[This is a test question:::this is the answer]]

        # Vectors
        - [[Vectors act like resizable arrays]].

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
    String arraysAndTestSummarized = """
        # This is a test file
              
        ## Heading
              
        # Java Arrays
        - An **array** is a collection of variables of the same type
              
        ## Declaring an Array
        - General Form: type[] arrayName;
              
        ## Creating an Array (Instantiation)
        - General form:  arrayName = new type[numberOfElements];
        - numberOfElements must be a positive Integer.
        - Gotcha: Array size is not modifiable once instantiated.\s
        -  T/F Testing with two colons ::
        """;
    assertEquals(arraysAndTestSummarized, arrays.extract());
    StudyGuide all = new StudyGuide(allCombined);
    String allCombinedSummarized = """
        # Java Arrays
        - An **array** is a collection of variables of the same type
              
        ## Declaring an Array
        - General Form: type[] arrayName;
        - but no array has actually been created yet.
              
        ## Creating an Array (Instantiation)
        - General form:  arrayName = new type[numberOfElements];
        - numberOfElements must be a positive Integer.
        - Gotcha: Array size is not modifiable once instantiated.\s
              
        # This is a test file
              
        ## Heading
              
        # Vectors
        - Vectors act like resizable arrays
              
        ## Declaring a vector
        - General Form: Vector<type> v = new Vector();
        - type needs to be a valid reference type
              
        ## Adding an element to a vector
        - v.add(object of type);
              
        # Java Basics
        -  Object-oriented\s
              
        ## History
        -  May 1995\s
              
        ### Similarities
        """;
    assertEquals(allCombinedSummarized, all.extract());
  }
}