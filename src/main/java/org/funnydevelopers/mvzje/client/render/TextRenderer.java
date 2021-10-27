package org.funnydevelopers.mvzje.client.render;

import org.overrun.glutils.FontTexture;
import org.overrun.glutils.FontTextures;

import java.awt.Font;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.lwjgl.opengl.GL11.*;

/**
 * @author squid233
 * @since 0.1.0
 */
public class TextRenderer
    extends org.overrun.glutils.ll.TextRenderer {
    private static FontTexture UNIFONT;

    public static void init() {
        var font = Font.decode("Unifont");
        UNIFONT = FontTextures.builder("Unifont-12-2")
            .font(font)
            .charset(UTF_8)
            .padding(2)
            .build();
    }

    public static int getWidth(String text) {
        return getWidth(text, UNIFONT);
    }

    public static int getHeight() {
        return getHeight(UNIFONT);
    }

    public static void drawText(float x, float y, String text) {
        glPushMatrix();
        glTranslatef(x, y, 0);
        drawText(
            text,
            UNIFONT,
            null,
            true
        );
        glPopMatrix();
    }
}
