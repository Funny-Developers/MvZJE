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

import org.funnydevelopers.mvzje.client.Textures;
import union.xenfork.fe2d.Fe2D;
import union.xenfork.fe2d.graphics.batch.SpriteBatch;
import union.xenfork.fe2d.gui.screen.Screen;

/**
 * The main screen, selecting things.
 *
 * @author crazy-piggy, squid233
 * @since 0.1.0
 */
public class MainScreen extends Screen {
    public MainScreen() {
        super(null);
    }

    public void renderBackground() {
        SpriteBatch batch = Fe2D.spriteRenderer();
        batch.begin();
        batch.draw(Textures.MAIN_BG, 0, 0);
        batch.end();
    }

    @Override
    public void render(double delta, double cursorX, double cursorY) {
        renderBackground();
        super.render(delta, cursorX, cursorY);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}
