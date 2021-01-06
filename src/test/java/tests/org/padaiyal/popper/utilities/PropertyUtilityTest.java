package tests.org.padaiyal.popper.utilities;

import java.io.IOException;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.padaiyal.popper.utilities.PropertyUtility;

class PropertyUtilityTest {

  /**
   * Loads the necessary property file for testing.
   *
   * @throws IOException When there is an issue loading the property file.
   */
  @BeforeAll
  static void setup() throws IOException {
    PropertyUtility.addPropertyFile(PropertyUtilityTest.class, "test.properties");
  }

  /**
   * Test retrieving the value for a valid property key.
   */
  @Test
  void testGetStringWithValidProperty() {
    Assertions.assertEquals(
        "TEST1", PropertyUtility.getProperty("test1")
    );
  }

  /**
   * Test retrieving the value for an invalid property key.
   */
  @Test
  void testGetStringWithInvalidProperty() {
    Assertions.assertThrows(
        NoSuchElementException.class, () -> PropertyUtility.getProperty("test_invalid")
    );
    // Null input
    Assertions.assertThrows(
        NullPointerException.class, () -> PropertyUtility.getProperty(null)
    );
  }

  /**
   * Test adding a valid properties file.
   *
   * @throws IOException When there is an issue loading the property file.
   */
  @Test
  void testAddPropertyFileWithValidInputs() throws IOException {
    // Add property file again.
    PropertyUtility.addPropertyFile(PropertyUtilityTest.class, "test.properties");

    String value = PropertyUtility.getProperty("test1");
    Assertions.assertEquals("TEST1", value);
  }

  /**
   * Test adding a non existent or a null properties file.
   */
  @Test
  void testAddPropertyFileWithInvalidInputs() {
    // Non existent properties file
    Assertions.assertThrows(
        NullPointerException.class,
        () ->
            PropertyUtility.addPropertyFile(PropertyUtilityTest.class, "test_invalid.properties")
    );
    // Null inputs
    //noinspection ConstantConditions
    Assertions.assertThrows(
        NullPointerException.class,
        () ->
            PropertyUtility.addPropertyFile(null, "test.properties")
    );
    Assertions.assertThrows(
        NullPointerException.class,
        () ->
            PropertyUtility.addPropertyFile(PropertyUtilityTest.class, null)
    );
  }

  /**
   * Test removing a previously added properties file.
   *
   * @throws IOException When there is an issue loading the properties file.
   */
  @Test
  void testRemovePropertyFileWithValidInputs() throws IOException {
    PropertyUtility.addPropertyFile(PropertyUtilityTest.class, "test_remove.properties");
    Assertions.assertEquals("TEST3", PropertyUtility.getProperty("test3"));
    // Attempting to remove again shouldn't throw an exception.
    Assertions.assertDoesNotThrow(
        () -> PropertyUtility.removePropertyFile(
            PropertyUtilityTest.class,
            "test_remove.properties"
        )
    );
    // Attempting to retrieve a property from the removed properties should throw an exception.
    Assertions.assertThrows(
        NoSuchElementException.class, () -> PropertyUtility.getProperty("test3")
    );
  }

  /**
   * Test removing a properties file with null inputs, invalid class or a properties file that
   * hasn't been added.
   */
  @Test
  void testRemovePropertyFileForInvalidInputs() {
    Class<String> someRandomClass = String.class;
    // Invalid class
    PropertyUtility.removePropertyFile(someRandomClass, "test_remove.properties");

    // Null inputs
    Assertions.assertThrows(
        NullPointerException.class,
        () -> PropertyUtility.removePropertyFile(null, null)
    );
    Assertions.assertThrows(
        NullPointerException.class,
        () -> PropertyUtility.removePropertyFile(PropertyUtilityTest.class, null)
    );
    Assertions.assertThrows(
        NullPointerException.class,
        () -> PropertyUtility.removePropertyFile(null, "test.properties")
    );

    // Remove properties file that hasn't been added
    Assertions.assertDoesNotThrow(
        () -> PropertyUtility.removePropertyFile(
            PropertyUtilityTest.class,
            "test_invalid.properties"
        )
    );
  }

  /**
   * Test retrieving property values as a desired type.
   */
  @Test
  void testGetTypedPropertyWithValidInputs() {
    Assertions.assertEquals(
        PropertyUtility.getTypedProperty(Byte.class, "testByte"),
        (byte) 5
    );
    Assertions.assertEquals(
        PropertyUtility.getTypedProperty(Short.class, "testShort"),
        (short) 543
    );
    Assertions.assertEquals(
        PropertyUtility.getTypedProperty(Integer.class, "testInteger"),
        54321
    );
    Assertions.assertEquals(
        PropertyUtility.getTypedProperty(Long.class, "testLong"),
        987654321L
    );
    Assertions.assertEquals(
        PropertyUtility.getTypedProperty(Boolean.class, "testBooleanFalse"),
        false
    );
    Assertions.assertEquals(
        PropertyUtility.getTypedProperty(Boolean.class, "testBooleanTrue"),
        true
    );
    Assertions.assertEquals(
        PropertyUtility.getTypedProperty(Float.class, "testFloat"),
        (float) -90.234
    );
    Assertions.assertEquals(
        PropertyUtility.getTypedProperty(Double.class, "testDouble"),
        -90.234234
    );
  }

  /**
   * Test retrieving property values as a desired type with null inputs or unsupported type classes.
   */
  @Test
  void testGetTypedPropertyWithInvalidInputs() {
    // Unsupported type class
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> PropertyUtility.getTypedProperty(String.class, "test1")
    );

    // Null inputs
    Assertions.assertThrows(
        NullPointerException.class,
        () -> PropertyUtility.getTypedProperty(null, null)
    );
    Assertions.assertThrows(
        NullPointerException.class,
        () -> PropertyUtility.getTypedProperty(null, "test1")
    );
    Assertions.assertThrows(
        NullPointerException.class,
        () -> PropertyUtility.getTypedProperty(Integer.class, null)
    );
  }
}
