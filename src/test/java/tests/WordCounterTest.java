package tests;

import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;
import parameterconverters.ExceptionClassConverter;
import wordspackage.WordCounter;

/**
 * Tests the WordCounter class.
 */
public class WordCounterTest {

  /**
   * Tests wordspackage.WordCounter::getWordCount() with invalid string inputs.
   *
   * @param str Invalid string input.
   * @param expectedExceptionClass Exception expected to be thrown.
   */
  @ParameterizedTest
  @CsvSource({", NullPointerException.class"})
  public void testGetWordCountWithInvalidInput(
      String str,
      @ConvertWith(ExceptionClassConverter.class)
          Class<? extends Exception> expectedExceptionClass) {
    Assertions.assertThrows(expectedExceptionClass, () -> WordCounter.getWordCount(str));
  }

  /**
   * Tests wordspackage.WordCounter::getWordCount() with invalid string inputs.
   *
   * @param str Invalid string input.
   * @param expectedWordCount Expected word count.
   * @throws IOException When there is an issue loading the properties file.
   */
  @ParameterizedTest
  @CsvSource({
      "'', 0",
      "a, 1",
      "The little brown fox jumped over the lazy dog, 9",
      "'The little brown fox\tjumped over the lazy dog', 9",
      "'The little brown fox\rjumped over the lazy dog', 9",
      "'The little brown fox\njumped over the lazy dog', 9",
  })
  public void testGetWordCount(String str, long expectedWordCount) throws IOException {
    Assertions.assertEquals(expectedWordCount, WordCounter.getWordCount(str));
  }
}
