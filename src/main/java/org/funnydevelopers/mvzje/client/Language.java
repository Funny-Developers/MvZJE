package org.funnydevelopers.mvzje.client;

import java.util.ResourceBundle;

/**
 * @author squid233
 * @since 0.1.0
 */
public class Language {
    private static final ResourceBundle BUNDLE =
        ResourceBundle.getBundle("translat");

    public static String translate(String key) {
        return String.valueOf(BUNDLE.getObject(key));
    }
}
