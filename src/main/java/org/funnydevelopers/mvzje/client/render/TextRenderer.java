package org.funnydevelopers.mvzje.client.render;

import org.funnydevelopers.mvzje.client.MvZClient;
import org.overrun.swgl.core.gl.GLStateMgr;

import static org.lwjgl.opengl.GL11.*;

/**
 * @author squid233
 * @since 0.1.0
 */
public class TextRenderer {
    private final MvZClient client;

    public TextRenderer(MvZClient client) {
        this.client = client;
    }

    public void drawText(float x, float y,
                         String text) {
        var textBatch = client.textBatch;
        textBatch.bindTexture();
        GLStateMgr.enableTexture2D();
        glColor3f(1, 1, 1);
        glBegin(GL_QUADS);
        textBatch.drawText(text, x, y, false, (x1, y1, z, r, g, b, a, s, t, p, nx, ny, nz, color, tex, normal, i) -> {
            glTexCoord2f(s, t);
            glVertex2f(x1, y1);
        });
        glEnd();
        GLStateMgr.disableTexture2D();
        textBatch.unbindTexture();
    }
}
