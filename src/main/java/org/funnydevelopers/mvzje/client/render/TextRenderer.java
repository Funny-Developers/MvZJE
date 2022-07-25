package org.funnydevelopers.mvzje.client.render;

import org.overrun.swgl.core.gl.GLStateMgr;
import org.overrun.swgl.core.gui.font.SwglEasyFont;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;

/**
 * @author squid233
 * @since 0.1.0
 */
public class TextRenderer {
    private static final Map<String, Integer> textLists = new HashMap<>();

    private static void genTextNoList(String text) {
        var desc = SwglEasyFont.createTextDesc(0, 0, text);
        float invTexSz = 1.0f / SwglEasyFont.getTextureSize();
        glBegin(GL_QUADS);
        glColor3f(1, 1, 1);
        for (int i = 0, c = desc.getLength(); i < c; i++) {
            var xy0 = desc.getXY0(i);
            var xy1 = desc.getXY1(i);
            var glyph = desc.getGlyph(i);
            glTexCoord2f(glyph.u0() * invTexSz, glyph.v0() * invTexSz);
            glVertex2f(xy0.x, xy0.y);
            glTexCoord2f(glyph.u0() * invTexSz, glyph.v1() * invTexSz);
            glVertex2f(xy0.x, xy1.y);
            glTexCoord2f(glyph.u1() * invTexSz, glyph.v1() * invTexSz);
            glVertex2f(xy1.x, xy1.y);
            glTexCoord2f(glyph.u1() * invTexSz, glyph.v0() * invTexSz);
            glVertex2f(xy1.x, xy0.y);
        }
        glEnd();
    }

    public static void genText(String text) {
        if (!textLists.containsKey(text)) {
            int lst = glGenLists(1);
            glNewList(lst, GL_COMPILE);
            genTextNoList(text);
            glEndList();
            textLists.put(text, lst);
        }
    }

    public static void drawTextNoList(float x, float y, String text) {
        SwglEasyFont.bindTexture();
        GLStateMgr.enableTexture2D();
        glPushMatrix();
        glTranslatef(x, y, 0);
        genTextNoList(text);
        GLStateMgr.disableTexture2D();
        SwglEasyFont.unbindTexture();
        glPopMatrix();
    }

    public static void drawText(float x, float y, String text) {
        SwglEasyFont.bindTexture();
        GLStateMgr.enableTexture2D();
        glPushMatrix();
        glTranslatef(x, y, 0);
        glCallList(textLists.get(text));
        GLStateMgr.disableTexture2D();
        SwglEasyFont.unbindTexture();
        glPopMatrix();
    }

    public static void free() {
        for (int lst : textLists.values()) {
            glDeleteLists(lst, 1);
        }
    }
}
