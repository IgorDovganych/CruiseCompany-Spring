package manager;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class Message {
    private static final HashMap<Locale, ResourceBundle> resources = new HashMap<Locale, ResourceBundle>();
    static final String BUNDLE_NAME = "localization.text";

    ResourceBundle getRes(Locale locale){
        ResourceBundle resource = resources.get(locale);
        if (resource == null) {
            resource = ResourceBundle.getBundle(BUNDLE_NAME, locale);
            resources.put(locale, resource);
        }
        return resource;
    }
}
