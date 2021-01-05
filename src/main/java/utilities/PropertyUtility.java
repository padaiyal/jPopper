package utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Used to load property values from pre-specified files.
 */
public class PropertyUtility {
  /**
   * Note: Cannot internationalize PropertyUtility class because I18NUtility class depends on this.
   */
  private static final Logger logger = LogManager.getLogger(PropertyUtility.class);
  private static final Map<Class<?>, HashMap<String, Properties>> classToPropertyFilesMap =
      Collections.synchronizedMap(new HashMap<>());

  /**
   * Utility class which doesn't need to be instantiated, hence the constructor is private.
   */
  private PropertyUtility() {}

  /**
   * Retrieves the value corresponding to the property key specified. If multiple property files
   * contain the same key, the first property file (Decided by the order in which the property file
   * is added) in which the property is present is used.
   *
   * @param propertyKey The property key to retrieve the value for
   * @return The value corresponding to the property specified
   */
  public static synchronized String getProperty(String propertyKey) {
    // Input validation
    Objects.requireNonNull(propertyKey);

    Properties propertiesObj =
        classToPropertyFilesMap.keySet().stream()
            .map(
                cls -> {
                  HashMap<String, Properties> propertiesForSpecificClassMap =
                      classToPropertyFilesMap.getOrDefault(cls, null);
                  return propertiesForSpecificClassMap.values().parallelStream()
                      .filter(properties -> properties.containsKey(propertyKey))
                      .findFirst()
                      .orElse(null);
                })
            .filter(Objects::nonNull)
            .findFirst()
            .orElseThrow();
    return propertiesObj.getProperty(propertyKey);
  }

  /**
   * Retrieves the value corresponding to the property key specified in the desired type.
   * If multiple property files contain the same key, the first property file (Decided by
   * the order in which the property file is added) in which the property is present is used.
   *
   * @param typeClass The class object of the desired return type.
   * @param propertyKey The property key to retrieve the value for.
   * @param <T> The type used to cast the property value.
   * @return The value corresponding to the property specified
   */
  @SuppressWarnings("unchecked")
  public static synchronized <T> T getTypedProperty(Class<T> typeClass, String propertyKey) {
    // Input validation
    Objects.requireNonNull(typeClass);
    Objects.requireNonNull(propertyKey);

    String propertyValueString = getProperty(propertyKey);
    if (typeClass == Boolean.class) {
      return (T) Boolean.valueOf(propertyValueString);
    } else if (typeClass == Byte.class) {
      return (T) Byte.valueOf(propertyValueString);
    } else if (typeClass == Short.class) {
      return (T) Short.valueOf(propertyValueString);
    } else if (typeClass == Integer.class) {
      return (T) Integer.valueOf(propertyValueString);
    } else if (typeClass == Long.class) {
      return (T) Long.valueOf(propertyValueString);
    } else if (typeClass == Float.class) {
      return (T) Float.valueOf(propertyValueString);
    } else if (typeClass == Double.class) {
      return (T) Double.valueOf(propertyValueString);
    } else {
      throw new IllegalArgumentException("Unknown type class (" + typeClass + ")");
    }
  }

  /**
   * Add a specified property file.
   *
   * @param cls Class to use to load the property file.
   * @param propertyFileName Name of the property file to add.
   * @throws IOException When there is an issue loading the properties file.
   */
  public static synchronized void addPropertyFile(Class<?> cls, String propertyFileName)
      throws IOException {
    Properties properties;
    try (InputStream inputStream = cls.getResourceAsStream(propertyFileName)) {
      properties = new Properties();
      properties.load(inputStream);
    }
    classToPropertyFilesMap.putIfAbsent(cls, new HashMap<>());
    HashMap<String, Properties> propertiesForSpecificClassMap = classToPropertyFilesMap.get(cls);
    if (propertiesForSpecificClassMap.containsKey(propertyFileName)) {
      logger.warn("Adding a property file ({}) again using class ({}})", propertyFileName, cls);
    }
    propertiesForSpecificClassMap.put(propertyFileName, properties);
  }

  /**
   * Remove a specified property file.
   *
   * @param cls Class whose property file needs to be removed.
   * @param propertyFileName name of the property file to remove.
   */
  public static synchronized void removePropertyFile(Class<?> cls, String propertyFileName) {
    // Input validation
    Objects.requireNonNull(cls);
    Objects.requireNonNull(propertyFileName);

    if (!classToPropertyFilesMap.containsKey(cls)) {
      logger.warn("No property files loaded for the specified class ({})", cls);
    } else {
      HashMap<String, Properties> propertiesForSpecificClassMap = classToPropertyFilesMap.get(cls);
      if (!propertiesForSpecificClassMap.containsKey(propertyFileName)) {
        String warningMessage = "Specified property file ({}) hasn't been loaded for the "
            + "specified class ({}). Hence it cannot be removed.";
        logger.warn(warningMessage, propertyFileName, cls);
      } else {
        propertiesForSpecificClassMap.remove(propertyFileName);
      }
    }
  }
}
