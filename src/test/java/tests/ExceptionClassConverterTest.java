package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import parameterconverters.ExceptionClassConverter;

/**
 * Tests the functionality of ExceptionClassConverter.
 */
public class ExceptionClassConverterTest {

  /**
   * Tests parameterconverters.ExceptionClassConverter::convertExceptionNameToClass() with invalid inputs.
   */
  @Test
  public void testConvertExceptionNameToClassWithInvalidInputs() {
    // Null input
    Assertions.assertThrows(
        NullPointerException.class,
        () -> ExceptionClassConverter.convertExceptionNameToClass(null));

    // Unsupported/Unknown class
    Assertions.assertThrows(
        ArgumentConversionException.class,
        () -> ExceptionClassConverter.convertExceptionNameToClass(
            "UnknownException"
        )
    );
  }

  /**
   * Tests parameterconverters.ExceptionClassConverter::convertExceptionNameToClass() with valid inputs.
   */
  @Test
  public void testConvertExceptionNameToClassWithValidInputs() {
    Assertions.assertEquals(
        NullPointerException.class,
        ExceptionClassConverter.convertExceptionNameToClass("NullPointerException.class")
    );
    Assertions.assertEquals(
        IllegalArgumentException.class,
        ExceptionClassConverter.convertExceptionNameToClass("IllegalArgumentException.class")
    );
  }
}