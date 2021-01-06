package org.padaiyal.popper.wordspackage;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.constraints.NotNull;

/**
 * Provides functionality to count words in a string.
 */
public final class WordCounter {

  /**
   * Utility class which doesn't need to be instantiated, hence the constructor is private.
   */
  private WordCounter() {}

  /**
   * Returns the number of words in a given string.
   *
   * @param str String to compute word count.
   * @return The number of words in the input string
   */
  public static long getWordCount(@NotNull final String str) {
    Objects.requireNonNull(str);

    long wordCount = 0;
    if (str.length() != 0) {
      wordCount = 1;
      String wordSeparatorRegex = "\\s+";
      Matcher matcher = Pattern.compile(wordSeparatorRegex).matcher(str.trim());
      while (matcher.find()) {
        wordCount++;
      }
    }

    return wordCount;
  }
}
