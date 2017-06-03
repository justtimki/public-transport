package by.gstu.training.util;

import java.util.ResourceBundle;

public class ConfigurationUtility {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("resources.database");

    private ConfigurationUtility() {
    }

    public static String getValue(String key) {
        return bundle.getString(key);
    }
}
