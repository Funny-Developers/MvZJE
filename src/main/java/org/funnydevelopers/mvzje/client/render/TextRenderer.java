package org.funnydevelopers.mvzje.client.render;

import org.funnydevelopers.mvzje.client.FontType;
import org.lwjgl.stb.STBTTAlignedQuad;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBTruetype.stbtt_GetBakedQuad;

/**
 * @author squid233
 * @since 0.1.0
 */
public class TextRenderer {
    private static FontType UNIFONT;

    public static void init() {
        UNIFONT = new FontType();
    }

    public static void drawText(float x, float y, String text) {
        glBindTexture(GL_TEXTURE_2D, UNIFONT.id);
        glBegin(GL_QUADS);
        var cs = text.toCharArray();
        try (var stack = MemoryStack.stackPush();
             var q = STBTTAlignedQuad.malloc()) {
            var xb = stack.mallocFloat(1);
            var yb = stack.mallocFloat(1);
            xb.put(0, x);
            yb.put(0, y);
            for (var c : cs) {
                stbtt_GetBakedQuad(UNIFONT.charData,
                    2048,
                    2048,
                    c,
                    xb,
                    yb,
                    q,
                    true);
                glTexCoord2f(q.s0(), q.t1());
                glVertex2f(q.x0(), q.y0());
                glTexCoord2f(q.s1(), q.t1());
                glVertex2f(q.x1(), q.y0());
                glTexCoord2f(q.s1(), q.t0());
                glVertex2f(q.x1(), q.y1());
                glTexCoord2f(q.s0(), q.t0());
                glVertex2f(q.x0(), q.y1());
            }
        }
        glEnd();
    }
}
