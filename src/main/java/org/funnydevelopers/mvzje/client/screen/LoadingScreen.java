/*
 * MIT License
 *
 * Copyright (c) 2021-2023 Funny Developers
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.funnydevelopers.mvzje.client.screen;

import org.funnydevelopers.mvzje.client.MvZClient;
import union.xenfork.fe2d.Fe2D;
import union.xenfork.fe2d.graphics.Color;
import union.xenfork.fe2d.graphics.batch.SpriteBatch;
import union.xenfork.fe2d.graphics.font.Font;
import union.xenfork.fe2d.graphics.font.TextRenderer;
import union.xenfork.fe2d.gui.screen.Screen;
import union.xenfork.fe2d.gui.widget.button.RectButton;

import static org.funnydevelopers.mvzje.client.Language.translate;
import static org.funnydevelopers.mvzje.client.Textures.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * The loading screen, player first see.
 *
 * @author crazy-piggy, squid233
 * @since 0.1.0
 */
public class LoadingScreen extends Screen {
    private final MvZClient client;
    private RectButton startButton;
    private int progress;
    private int daisy;

    public LoadingScreen(MvZClient client) {
        super(null);
        this.client = client;
    }

    @Override
    public void init() {
        super.init();
        progress = 0;
        startButton = addWidget(new RectButton(null,
            0,
            24,
            256,
            32,
            x -> client.openScreen(new MainScreen())) {
            @Override
            public void update() {
                super.update();
                if (progress < 100) {
                    setText(translate("main.loading"));
                } else {
                    setText(translate("main.start"));
                }
            }

            @Override
            public boolean isCursorHover(double cursorX, double cursorY) {
                return progress >= 100 && super.isCursorHover(cursorX, cursorY);
            }
        });
        startButton.setTextFont(Fe2D.defaultFont(), 16f);
        startButton.setColor(Color.CLEAR);
        startButton.setHoverColor(new Color(0x80ffffff));
    }

    @Override
    public void onResize(int width, int height) {
        super.onResize(width, height);
        startButton.setX(width * 0.5f - 128);
    }

    @Override
    public void fixedUpdate() {
        super.fixedUpdate();
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
    public void render(double delta, double cursorX, double cursorY) {
        SpriteBatch batch = Fe2D.spriteRenderer();
        batch.begin();
        batch.draw(LOADING_BG, 0, 0);

        batch.draw(PROGRESS_BAR, width * 0.5f - 128, 24, 256, 32, 0.0f, 0.5f, 8.0f, 1.0f);

        float unmlProgress = progress * 2.56f;
        float u = unmlProgress / 32.0f;
        batch.draw(PROGRESS_BAR, width * 0.5f - 128, 24, unmlProgress, 32, 0.0f, 0.0f, u, 0.5f);

        if (progress>=100) {
            batch.draw(DAISY, width * 0.5f - 128 + 256 - 16, 24+32);
        }
        batch.end();

        if (false){
        glPushMatrix();
        glTranslatef(width / 2f - 128, height - 24, 0);
        glColor3f(1, 1, 1);
        PROGRESS_BAR.bind();

        glBegin(GL_QUADS);

        glTexCoord2f(0, 0.5f);
        glVertex2f(0, -32);
        glTexCoord2f(0, 1);
        glVertex2f(0, 0);
        glTexCoord2f(8.0f, 1);
        glVertex2f(256, 0);
        glTexCoord2f(8.0f, 0.5f);
        glVertex2f(256, -32);

//        float unmlProgress = progress * 2.56f;
//        float u = unmlProgress / 32.0f;
        glTexCoord2f(0, 0);
        glVertex2f(0, -32);
        glTexCoord2f(0, 0.5f);
        glVertex2f(0, 0);
        glTexCoord2f(u, 0.5f);
        glVertex2f(unmlProgress, 0);
        glTexCoord2f(u, 0);
        glVertex2f(unmlProgress, -32);

        glEnd();

//        bindTexture2D(0);

        if (progress >= 100) {
            glPushMatrix();
            glTranslatef(256 - 16, -32, 0);
            DAISY.bind();
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
//            DAISY.unbind();
            glPopMatrix();
        }
//        disableTexture2D();

        glPopMatrix();

        var text = "v" + MvZClient.VERSION;
//        client.textRenderer.drawText(
//            width - UnifontTextBatch.getTextWidth(text),
//            height - UnifontTextBatch.CHAR_HEIGHT,
//            text);
        }

        String text = "v" + MvZClient.VERSION;
        TextRenderer textRenderer = Fe2D.textRenderer();
        Font font = Fe2D.defaultFont();
        textRenderer.begin();
        textRenderer.draw(font, text, width - font.getTextWidth(text), 0);
        textRenderer.end();

        super.render(delta, cursorX, cursorY);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}
