package org.funnydevelopers.mvzje.client.screen;

import org.funnydevelopers.mvzje.client.MvZClient;
import org.funnydevelopers.mvzje.client.screen.widget.Button;
import org.overrun.swgl.core.gui.font.UnifontTextBatch;

import static org.funnydevelopers.mvzje.client.Language.translate;
import static org.funnydevelopers.mvzje.client.Textures.*;
import static org.lwjgl.opengl.GL11.*;
import static org.overrun.swgl.core.gl.GLStateMgr.*;

/**
 * The loading screen, player first see.
 *
 * @author crazy-piggy, squid233
 * @since 0.1.0
 */
public class LoadingScreen extends Screen {
    private int progress;
    private int daisy;
    private boolean initialized;

    @Override
    public void init() {
        super.init();
        if (!initialized) {
            progress = 0;
            initialized = true;
        }
        buttons.clear();
        addButton(new Button(width / 2 - 128,
            height - (24 + 32),
            256,
            32,
            x -> client.openScreen(new MainScreen())) {
            @Override
            public void render(double delta) {
                super.render(delta);
                String str;
                if (progress < 100) {
                    str = translate("main.loading");
                } else {
                    str = translate("main.start");
                }
                client.textRenderer.drawText(
                    x + (width - UnifontTextBatch.getTextWidth(str)) * 0.5f,
                    y + (height - UnifontTextBatch.CHAR_HEIGHT) * 0.5f,
                    str);
            }

            @Override
            public void hover(double delta) {
                if (progress >= 100) {
                    glColor4f(1, 1, 1, 0.5f);
                    glBegin(GL_QUADS);
                    glVertex2f(x, y);
                    glVertex2f(x, y + height);
                    glVertex2f(x + width, y + height);
                    glVertex2f(x + width, y);
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
        TEXTURE_LOADING_BG.bind();
        glColor3f(1, 1, 1);
        enableTexture2D();
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

        glPushMatrix();
        glTranslatef(width / 2f - 128, height - 24, 0);
        glColor3f(1, 1, 1);
        TEXTURE_PROGRESS_BAR.bind();

        glBegin(GL_QUADS);

        glTexCoord2f(0, 0.5f);
        glVertex2f(0, -32);
        glTexCoord2f(0, 1);
        glVertex2f(0, 0);
        glTexCoord2f(8.0f, 1);
        glVertex2f(256, 0);
        glTexCoord2f(8.0f, 0.5f);
        glVertex2f(256, -32);

        float unmlProgress = progress * 2.56f;
        float u = unmlProgress / 32.0f;
        glTexCoord2f(0, 0);
        glVertex2f(0, -32);
        glTexCoord2f(0, 0.5f);
        glVertex2f(0, 0);
        glTexCoord2f(u, 0.5f);
        glVertex2f(unmlProgress, 0);
        glTexCoord2f(u, 0);
        glVertex2f(unmlProgress, -32);

        glEnd();

        bindTexture2D(0);

        if (progress >= 100) {
            glPushMatrix();
            glTranslatef(256 - 16, -32, 0);
            TEXTURE_DAISY.bind();
            float v = 1.0f - daisy / 16.0f;
            glBegin(GL_QUADS);
            glTexCoord2f(0, v);
            glVertex2f(0, -daisy);
            glTexCoord2f(0, 1);
            glVertex2f(0, 0);
            glTexCoord2f(1, 1);
            glVertex2f(16, 0);
            glTexCoord2f(1, v);
            glVertex2f(16, -daisy);
            glEnd();
            TEXTURE_DAISY.unbind();
            glPopMatrix();
        }
        disableTexture2D();

        glPopMatrix();

        var text = "v" + MvZClient.VERSION;
        client.textRenderer.drawText(
            width - UnifontTextBatch.getTextWidth(text),
            height - UnifontTextBatch.CHAR_HEIGHT,
            text);

        super.render(delta);
    }
}
