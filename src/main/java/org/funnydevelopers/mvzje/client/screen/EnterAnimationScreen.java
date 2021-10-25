package org.funnydevelopers.mvzje.client.screen;

import org.funnydevelopers.mvzje.client.render.TextRenderer;
import org.funnydevelopers.mvzje.client.screen.widget.Button;

import static org.funnydevelopers.mvzje.client.Textures.TEXTURE_DAISY;
import static org.funnydevelopers.mvzje.client.Textures.TEXTURE_PROGRESS_BAR;
import static org.lwjgl.opengl.GL11.*;

/**
 * 开场动画,类似Minecraft启动时的Mojang Studio标志
 *
 * @author crazy-piggy, squid233
 * @since 0.1.0
 */
public class EnterAnimationScreen extends Screen {
    private int overlay;
    private int daisy;

    @Override
    public void init() {
        super.init();
        overlay = 0;
        addButton(new Button(width / 2 - 128,
            24 + 32,
            256,
            32,
            System.out::println) {
            @Override
            public void render(double delta) {
                super.render(delta);
                // FIXME: 2021/10/25 can't render text
                TextRenderer.drawText(x, y, "Loading");
            }

            @Override
            public void hover(double delta) {
                // FIXME: 2021/10/25 can't hover
                glColor3f(1, 1, 1);
                glBegin(GL_QUADS);
                glVertex3f(x, y, 0);
                glVertex3f(x, y - height, 0);
                glVertex3f(x + width, y - height, 0);
                glVertex3f(x + width, y, 0);
                glEnd();
            }
        });
    }

    @Override
    public void mousePress(int mouseX, int mouseY, int button) {
        super.mousePress(mouseX, mouseY, button);
    }

    @Override
    public void tick() {
        if (overlay < 256) {
            overlay += 4;
        } else {
            if (daisy < 16) {
                daisy += 2;
            } else if (daisy > 16) {
                daisy = 16;
            }
        }
    }

    @Override
    public void render(double delta) {
        glPushMatrix();
        glTranslatef(width / 2f - 128, 24, 0);
        glColor3f(1, 1, 1);
        glBindTexture(GL_TEXTURE_2D, TEXTURE_PROGRESS_BAR);

        glBegin(GL_QUADS);

        glTexCoord2f(0, 0.5f);
        glVertex3f(0, 32, 0);
        glTexCoord2f(0, 1);
        glVertex3f(0, 0, 0);
        glTexCoord2f(1, 1);
        glVertex3f(256, 0, 0);
        glTexCoord2f(1, 0.5f);
        glVertex3f(256, 32, 0);

        float u = overlay / 256.0f;
        glTexCoord2f(0, 0);
        glVertex3f(0, 32, 0);
        glTexCoord2f(0, 0.5f);
        glVertex3f(0, 0, 0);
        glTexCoord2f(u, 0.5f);
        glVertex3f(overlay, 0, 0);
        glTexCoord2f(u, 0);
        glVertex3f(overlay, 32, 0);

        glEnd();

        glBindTexture(GL_TEXTURE_2D, 0);

        if (overlay >= 256) {
            glPushMatrix();
            glTranslatef(256 - 16, 32, 0);
            glBindTexture(GL_TEXTURE_2D, TEXTURE_DAISY);
            float v = 1 - daisy / 16.0f;
            glBegin(GL_QUADS);
            glTexCoord2f(0, v);
            glVertex3f(0, daisy, 0);
            glTexCoord2f(0, 1);
            glVertex3f(0, 0, 0);
            glTexCoord2f(1, 1);
            glVertex3f(16, 0, 0);
            glTexCoord2f(1, v);
            glVertex3f(16, daisy, 0);
            glEnd();
            glBindTexture(GL_TEXTURE_2D, 0);
            glPopMatrix();
        }

        glPopMatrix();

        super.render(delta);
    }
}
