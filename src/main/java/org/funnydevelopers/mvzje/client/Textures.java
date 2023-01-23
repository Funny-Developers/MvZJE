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

import union.xenfork.fe2d.Fe2D;
import union.xenfork.fe2d.graphics.texture.Texture;
import union.xenfork.fe2d.util.ResourcePath;

/**
 * @author squid233
 * @since 0.1.0
 */
public final class Textures {
    public static Texture PROGRESS_BAR;
    public static Texture DAISY;
    public static Texture LOADING_BG;
    public static Texture MAIN_BG;

    public static void init() {
        PROGRESS_BAR = Texture.ofFile(Fe2D.files.internal(ResourcePath.assets(MvZClient.NAMESPACE, "texture/sprite/progress_bar.png")));
        DAISY = Texture.ofFile(Fe2D.files.internal(ResourcePath.assets(MvZClient.NAMESPACE, "texture/sprite/daisy.png")));
        LOADING_BG = Texture.ofFile(Fe2D.files.internal(ResourcePath.assets(MvZClient.NAMESPACE, "texture/background/loading_bg.png")));
        MAIN_BG = Texture.ofFile(Fe2D.files.internal(ResourcePath.assets(MvZClient.NAMESPACE, "texture/background/main_bg.png")));
    }

    public static void dispose() {
        PROGRESS_BAR.dispose();
        DAISY.dispose();
        LOADING_BG.dispose();
        MAIN_BG.dispose();
    }
}
