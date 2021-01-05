package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utilities.I18NUtility;
import utilities.PropertyUtility;

import java.io.IOException;
import java.util.IllegalFormatConversionException;
import java.util.MissingResourceException;

class I18nUtilityTest {

    /**
     * Loads the necessary property file for testing.
     *
     * @throws IOException When there is an issue loading the property file.
     */
    @BeforeAll
    static void setup() throws IOException {
//        I18NUtility.addResourceBundle("Test1_Resource_Bundle");
    }

    @Test
    void testGetNonFormattedStringWithValidKey() {
        Assertions.assertEquals("Hello All!!!", I18NUtility.getString("com.sample.message"));
    }

    @Test
    void testGetFormattedStringWithValidKeyAndValidValues() {
        Assertions.assertEquals("Hello value1!!!", I18NUtility.getFormattedString("com.sample.formattedmessage", "value1"));
    }

    @Test
    void testGetFormattedStringWithValidKeyAndInvalidValues() {
        boolean illegalFormatConversionExceptionThrown = false;
        try {
            I18NUtility.getFormattedString("com.sample.formattedmessage1", "value1");
        }
        catch(IllegalFormatConversionException e) {
            illegalFormatConversionExceptionThrown = true;
        }
        Assertions.assertTrue(illegalFormatConversionExceptionThrown);
    }

    @Test
    void testGetNonFormattedStringWithValidKeyAndValues() {
        Assertions.assertEquals("Hello All!!!", I18NUtility.getFormattedString("com.sample.message", "value1"));
    }

    @Test
    void testGetStringWithInvalidKey() {
        boolean missingResourceExceptionThrown = false;
        try {
            I18NUtility .getString("com.sample.message.invalid");
        }
        catch(MissingResourceException e) {
            missingResourceExceptionThrown = true;
        }
        finally {
            Assertions.assertTrue(missingResourceExceptionThrown);
        }
    }

    @Test
    void testGetStringWithInvalidKeyAndValues() {
        boolean missingResourceExceptionThrown = false;
        try {
            Assertions.assertEquals("Hello value1!!!", I18NUtility.getFormattedString("com.sample.formattedmessageinvalid", "value1"));
        }
        catch(MissingResourceException e) {
            missingResourceExceptionThrown = true;
        }
        finally {
            Assertions.assertTrue(missingResourceExceptionThrown);
        }
    }

    @Test
    void testAddValidResourceBundle() {
        I18NUtility.addResourceBundle("Test1_Resource_Bundle");
        String value = I18NUtility.getString("test1.message");
        Assertions.assertEquals("test message 1", value);
    }

    @Test
    void testAddMissingResourceBundle() {
        boolean missingResourceExceptionThrown = false;
        try {
            I18NUtility.addResourceBundle("Test1_Resource_Bundle_invalid");
        }
        catch (MissingResourceException e) {
            missingResourceExceptionThrown = true;
        }
        Assertions.assertTrue(missingResourceExceptionThrown);
    }

    @Test
    void testRemoveResourceBundle() {
        I18NUtility.addResourceBundle("Test2_Resource_Bundle");
        I18NUtility.getString("test2.message");
        I18NUtility.removeResourceBundle("Test2_Resource_Bundle");
        boolean missingResourceExceptionThrown = false;
        try {
            I18NUtility.getString("test2.message");
        }
        catch (MissingResourceException e) {
            missingResourceExceptionThrown = true;
        }
        Assertions.assertTrue(missingResourceExceptionThrown);
    }

    @Test
    void testRemoveMissingResourceBundle() {
        boolean missingResourceExceptionThrown = false;
        try {
            I18NUtility.removeResourceBundle("Test1_Resource_Bundle_invalid");
        }
        catch (MissingResourceException e) {
            missingResourceExceptionThrown = true;
        }
        Assertions.assertTrue(missingResourceExceptionThrown);
    }
}
