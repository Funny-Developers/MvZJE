package org.funnydevelopers.mvzje.client.screen;

import org.funnydevelopers.mvzje.client.MvZ;
import org.funnydevelopers.mvzje.client.render.TextRenderer;
import org.funnydevelopers.mvzje.client.screen.widget.Button;

import static org.funnydevelopers.mvzje.client.Language.translate;
import static org.funnydevelopers.mvzje.client.Textures.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * 开场动画,类似Minecraft启动时的Mojang Studio标志
 *
 * @author crazy-piggy, squid233
 * @since 0.1.0
 */
public class LoadingScreen extends Screen {
    private int progress;
    private int daisy;

    @Override
    public void init() {
        super.init();
        progress = 0;
        addButton(new Button(width / 2 - 128,
            24 + 32,
            256,
            32,
            System.out::println) {
            @Override
            public void render(double delta) {
                super.render(delta);
                String str;
                float fy =
                    y - (height + TextRenderer.getHeight()) / 2f;
                if (progress < 100) {
                    str = translate("main.loading");
                } else {
                    str = translate("main.start");
                }
                TextRenderer.drawText(
                    x + (width - TextRenderer.getWidth(str)) / 2f,
                    fy,
                    str);
            }

            @Override
            public void hover(double delta) {
                if (progress >= 100) {
                    glColor4f(1, 1, 1, 0.5f);
                    glBegin(GL_QUADS);
                    glVertex3f(x, y, 0);
                    glVertex3f(x, y - height, 0);
                    glVertex3f(x + width, y - height, 0);
                    glVertex3f(x + width, y, 0);
                    glEnd();
                }
            }
        });
    }

    @Override
    public void mousePress(int mouseX, int mouseY, int button) {
        super.mousePress(mouseX, mouseY, button);
    }

    @Override
    public void tick() {
        if (progress < 100) {
            progress += 2;
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
        glBindTexture(GL_TEXTURE_2D, TEXTURE_LOADING_BG);
        glColor3f(1, 1, 1);
        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex3f(0, 600, 0);
        glTexCoord2f(0, 1);
        glVertex3f(0, 0, 0);
        glTexCoord2f(1, 1);
        glVertex3f(800, 0, 0);
        glTexCoord2f(1, 0);
        glVertex3f(800, 600, 0);
        glEnd();

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

        float unmlProgress = progress * 2.56f;
        float u = unmlProgress / 256.0f;
        glTexCoord2f(0, 0);
        glVertex3f(0, 32, 0);
        glTexCoord2f(0, 0.5f);
        glVertex3f(0, 0, 0);
        glTexCoord2f(u, 0.5f);
        glVertex3f(unmlProgress, 0, 0);
        glTexCoord2f(u, 0);
        glVertex3f(unmlProgress, 32, 0);

        glEnd();

        glBindTexture(GL_TEXTURE_2D, 0);

        if (progress >= 100) {
            glPushMatrix();
            glTranslatef(256 - 16, 32, 0);
            glBindTexture(GL_TEXTURE_2D, TEXTURE_DAISY);
            var v = 1 - daisy / 16.0f;
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

        glPushMatrix();
        var text = "v" + MvZ.VERSION;
        glTranslatef(800 - TextRenderer.getWidth(text), 0, 0);
        TextRenderer.drawText(0, 0, text);
        glPopMatrix();

        super.render(delta);
    }
}
