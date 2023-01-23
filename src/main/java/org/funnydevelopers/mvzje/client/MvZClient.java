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

package org.funnydevelopers.mvzje.client;

import org.funnydevelopers.mvzje.client.screen.LoadingScreen;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;
import union.xenfork.fe2d.Fe2D;
import union.xenfork.fe2d.Game;
import union.xenfork.fe2d.Input;
import union.xenfork.fe2d.graphics.GLStateManager;
import union.xenfork.fe2d.graphics.batch.SpriteBatch;
import union.xenfork.fe2d.graphics.font.Font;
import union.xenfork.fe2d.graphics.font.TextRenderer;
import union.xenfork.fe2d.gui.layout.Alignment;

import static org.lwjgl.opengl.GL11.*;
import static union.xenfork.fe2d.gui.screen.ScreenUtil.*;

/**
 * @author crazy-piggy, squid233
 * @since 0.1.0
 */
public final class MvZClient extends Game {
    private static final MvZClient INSTANCE = new MvZClient();
    public static final String VERSION = "0.1.0";
    public static final String TITLE = "Minecraft vs. Zombies: Java Edition " + VERSION;
    public static final String NAMESPACE = "mvzje";
    public SpriteBatch spriteBatch;
    private boolean debugHudVisible = false;

    public static MvZClient getInstance() {
        return INSTANCE;
    }

    @Override
    public void start() {
        super.start();
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
    }

    @Override
    public void init() {
        super.init();
        glClearColor(0.4f, 0.6f, 0.9f, 1.0f);
        GLStateManager.enableBlend();
        GLStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        Textures.init();

        spriteBatch = new SpriteBatch();
        Fe2D.setSpriteRenderer(spriteBatch);

        openScreen(new LoadingScreen(this));
    }

    @Override
    public void onKey(int key, int scancode, Input.@NotNull Action action, int mods) {
        super.onKey(key, scancode, action, mods);
        if (key == Input.KEY_F3 && action == Input.Action.PRESS) {
            debugHudVisible = !debugHudVisible;
        }
    }

    public void render(double delta) {
        clear(COLOR_BUFFER_BIT | DEPTH_BUFFER_BIT);
        super.render(delta);
        if (debugHudVisible) {
            renderDebugHud();
        }
    }

    private void renderDebugHud() {
        TextRenderer textRenderer = Fe2D.textRenderer();
        Font font = Fe2D.defaultFont();
        int height = Fe2D.graphics.height();

        textRenderer.begin();
        textRenderer.draw(font, TITLE, 0, height - font.getTextHeight(TITLE), Alignment.V.LEFT, 16);
//        textRenderer.draw(font, frames + " fps", 0, height - 16, Alignment.V.LEFT, 16);

        textRenderer.draw(font, "Cursor pos: (" + Fe2D.input.cursorX() + ", " + Fe2D.input.cursorY() + ")",
            0, height - 3 * 16, Alignment.V.LEFT, 16);
        textRenderer.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        Textures.dispose();
        dispose(spriteBatch);
    }
}
