package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.*;

public class I18NUtility {

    private static final String language;
    private static final String region;
    private static final Locale locale;
    private static final Map<String, ResourceBundle> resourceBundles = Collections.synchronizedMap(new HashMap<>());
    private static final Logger logger = LogManager.getLogger(I18NUtility.class);

    static {
        try {
            PropertyUtility.addPropertyFile(I18NUtility.class, "jPopper.properties");
        } catch (IOException e) {
            logger.warn
        }
        language = PropertyUtility.getProperty("common.language");
        region = PropertyUtility.getProperty("common.region");
        locale = new Locale(language, region);
        String defaultResourceBundleName = PropertyUtility.getProperty("utilities.I18NUtility.resourcebundle.default");
        addResourceBundle(defaultResourceBundleName);

    }



    /**
     * Retrieves the message string corresponding to the key specified.
     * If multiple resource bundles contain the same key, the first resource
     * bundle (Decided by the order in which the resource bundle is added) in
     * which the key is present is used.
     * @param key The key to retrieve the value for
     * @return The value corresponding to the key specified
     */
    public static String getString(String key) {
        Optional<String> value = resourceBundles.values()
                .stream()
                .map(resourceBundle -> {
                    String tempValue;
                    try {
                        // Cannot internationalize as calls to getString()/getFormattedString() will cause an infinite recursion
                        logger.debug("Resource bundle " + resourceBundle.getBaseBundleName() + " contains property - " + key);
                        tempValue = resourceBundle.getString(key);
                    }
                    catch (MissingResourceException e) {
                        // Cannot internationalize as calls to getString()/getFormattedString() will cause an infinite recursion
                        logger.debug("Resource bundle " + resourceBundle.getBaseBundleName() + " does not contain property - " + key);
                        tempValue = null;
                    }
                    return tempValue;
                })
                .filter(Objects::nonNull)
                .findFirst();
        if(value.isPresent()) {
            return value.get();
        }
        else {
            throw new MissingResourceException(
                // Cannot internationalize as calls to getString()/getFormattedString() will cause an infinite recursion
                String.format("Can't find resource for bundle %s", key),
                I18NUtility.class.getName(),
                key
            );
        }
    }

    /**
     * Retrieves the message string corresponding to the key specified with the
     * provided values inserted.
     * If multiple resource bundles contain the same key, the first resource
     * bundle (Decided by the order in which the resource bundle is added) in
     * which the key is present is used.
     * @param key The key to retrieve the value for
     * @return The value corresponding to the key specified
     */
    public static String getFormattedString(String key, Object... values) {
        // Cannot internationalize as calls to getFormattedString() will cause an infinite recursion
        logger.debug("Retrieving property " + key + " with the following format values -  " + Arrays.toString(values));
        return String.format(
                getString(key),
                values
        );
    }

    /**
     * Add a specified resource bundle
     * @param resourceBundleName Resource bundle to add
     */
    public synchronized static void addResourceBundle(String resourceBundleName) {
        addResourceBundle(I18NUtility.class, resourceBundleName);
    }

    /**
     * Add a specified resource bundle
     * @param cls Class to retrieve the classpath from
     * @param resourceBundleName Resource bundle to add
     */
    public synchronized static void addResourceBundle(Class<?> cls, String resourceBundleName) {
        // Cannot internationalize as the required I18N string cannot be found without adding the relevant resource bundle
        logger.info("Adding resource bundle - " + resourceBundleName + " using the classpath " + cls.getPackageName());
        ResourceBundle resourceBundle = ResourceBundle.getBundle(cls.getPackageName() + "." + resourceBundleName, locale);
        resourceBundles.put(resourceBundleName, resourceBundle);
    }

    /**
     * Remove a specified resource bundle
     * @param resourceBundleName Resource bundle to remove
     */
    public synchronized static void removeResourceBundle(String resourceBundleName) {
        // Cannot internationalize as the reuqired I18N string cannot be found without adding the relevant resource bundle        logger.debug("Removing resource bundle - " + resourceBundleName);
        ResourceBundle removedResourceBundle = resourceBundles.remove(resourceBundleName);
        if(removedResourceBundle == null) {
            throw new MissingResourceException(
                    String.format("Can't find resource bundle %s", resourceBundleName),
                    I18NUtility.class.getName(),
                    resourceBundleName
            );
        }
    }
}
