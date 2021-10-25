package org.funnydevelopers.mvzje.client.screen;

import static org.lwjgl.opengl.GL11.*;

/**
 * @author crazy-piggy, squid233
 * @since 0.1.0
 */
public class EnterAnimationScreen extends Screen {
    //开场动画,类似Minecraft启动时的Mojang Studio标志

    private int progressBar;

    @Override
    public void init() {
        super.init();
        progressBar = 0;
    }

    @Override
    public void tick() {
        if (progressBar < 100) {
            ++progressBar;
        }
    }

    @Override
    public void render(double delta) {
        glPushMatrix();
        glTranslatef(width / 2f - 100, height / 2f - 10, 0);
        glColor3f(1, 1, 1);
        glBegin(GL_QUADS);
        float x = progressBar * 2;
        glVertex3f(0, 0, 0);
        glVertex3f(0, 20, 0);
        glVertex3f(x, 20, 0);
        glVertex3f(x, 0, 0);
        glEnd();
        glPopMatrix();
    }
}
