package cs3500.pa02.fileutilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class for testing MarkDownFile and its associated methods
 */
class MarkDownFileTest extends FileUtilitiesTest {

  private File invalidFormat;
  private File invalidPath;

  /**
   * Initializes the date for each test
   */
  @BeforeEach
  public void setup() {
    invalidFormat = Path.of("src/tests/resources/notes-root/invalid.pdf").toFile();
    invalidPath = Path.of("src/tests/resources/notes-root/invalid.md").toFile();
    arraysMd = new MarkDownFile(arrays);
    testMd = new MarkDownFile(test);
    vectorsMd = new MarkDownFile(vectors);
  }

  /**
   * Tests the constructor for exception
   */
  @Test
  public void testConstructor() {
    assertThrows(IllegalArgumentException.class, () -> new MarkDownFile(invalidFormat));
    assertThrows(IllegalArgumentException.class, () -> new MarkDownFile(invalidPath));
  }

  /**
   * Tests the getFilename method
   */
  @Test
  public void testGetFilename() {
    assertEquals("arrays.md", arraysMd.getFilename());
    assertEquals("test.md", testMd.getFilename());
    assertEquals("vectors.md", vectorsMd.getFilename());
  }

  /**
   * Tests the getDateCreated method
   */
  @Test
  public void testGetDateCreated() {
    FileTime arrayTime;
    FileTime testTime;
    FileTime vectorsTime;
    try {
      arrayTime = Files.readAttributes(arrays.toPath(),
          BasicFileAttributes.class).creationTime();
      testTime = Files.readAttributes(test.toPath(),
          BasicFileAttributes.class).creationTime();
      vectorsTime = Files.readAttributes(vectors.toPath(),
          BasicFileAttributes.class).creationTime();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertEquals(arrayTime, arraysMd.getDateCreated());
    assertEquals(testTime, testMd.getDateCreated());
    assertEquals(vectorsTime, vectorsMd.getDateCreated());
  }

  /**
   * Tests the getLastModified method
   *
   */
  @Test
  public void testGetLastModified() {
    FileTime arrayTime;
    FileTime testTime;
    FileTime vectorsTime;
    try {
      arrayTime = Files.readAttributes(arrays.toPath(),
          BasicFileAttributes.class).lastModifiedTime();
      testTime = Files.readAttributes(test.toPath(),
          BasicFileAttributes.class).lastModifiedTime();
      vectorsTime = Files.readAttributes(vectors.toPath(),
          BasicFileAttributes.class).lastModifiedTime();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertEquals(arrayTime, arraysMd.getLastModified());
    assertEquals(testTime, testMd.getLastModified());
    assertEquals(vectorsTime, vectorsMd.getLastModified());
  }

}