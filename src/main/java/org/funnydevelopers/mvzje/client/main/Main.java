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

package org.funnydevelopers.mvzje.client.main;

import org.funnydevelopers.mvzje.client.MvZClient;
import union.xenfork.fe2d.ApplicationConfig;

/**
 * @author crazy-piggy, squid233
 * @since 0.1.0
 */
public final class Main {
    public static void main(String[] args) {
        ApplicationConfig config = new ApplicationConfig();
        config.applicationName = MvZClient.TITLE;
        config.windowWidth = 800;
        config.windowHeight = 600;
        MvZClient.getInstance().launch(config);
    }
}
