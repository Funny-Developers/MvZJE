package org.funnydevelopers.mvzje.client.screen;

import org.funnydevelopers.mvzje.client.Textures;
import org.overrun.swgl.core.gl.GLStateMgr;

import static org.lwjgl.opengl.GL11.*;

/**
 * The main screen, selecting things.
 *
 * @author crazy-piggy, squid233
 * @since 0.1.0
 */
public class MainScreen extends Screen {
    public void renderBackground() {
        Textures.TEXTURE_MAIN_BG.bind();
        GLStateMgr.enableTexture2D();
        glColor3f(1, 1, 1);
        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2f(0, 0);
        glTexCoord2f(0, 1);
        glVertex2f(0, height);
        glTexCoord2f(1, 1);
        glVertex2f(width, height);
        glTexCoord2f(1, 0);
        glVertex2f(width, 0);
        glEnd();
        GLStateMgr.disableTexture2D();
        Textures.TEXTURE_MAIN_BG.unbind();
    }

    @Override
    public void render(double delta) {
        renderBackground();
        super.render(delta);
    }
}
