package org.funnydevelopers.mvzje.client;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author squid233
 * @since 0.1.0
 */
public class Language {
    private static final ResourceBundle BUNDLE =
        ResourceBundle.getBundle("l10n", Locale.getDefault());

    public static String translate(String key) {
        return BUNDLE.getString(key);
    }
}
