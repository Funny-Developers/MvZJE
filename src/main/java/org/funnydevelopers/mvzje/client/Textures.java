package org.funnydevelopers.mvzje.client;

import org.overrun.swgl.core.asset.tex.ITextureParam;
import org.overrun.swgl.core.asset.tex.Texture2D;
import org.overrun.swgl.core.io.IFileProvider;

import static org.lwjgl.opengl.GL13C.*;

/**
 * @author squid233
 * @since 0.1.0
 */
public class Textures {
    private static final IFileProvider FILE_PROVIDER = IFileProvider.ofCaller();
    public static Texture2D TEXTURE_PROGRESS_BAR;
    public static Texture2D TEXTURE_DAISY;
    public static Texture2D TEXTURE_LOADING_BG;

    public static void init() {
        ITextureParam param = target -> {
            glTexParameteri(target, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(target, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        };
        TEXTURE_PROGRESS_BAR = new Texture2D();
        TEXTURE_PROGRESS_BAR.setParam(target -> {
            param.set(target);
            glTexParameteri(target, GL_TEXTURE_WRAP_S, GL_REPEAT);
        });
        TEXTURE_PROGRESS_BAR.reload("progress_bar.png", FILE_PROVIDER);
        TEXTURE_DAISY = new Texture2D();
        TEXTURE_DAISY.setParam(param);
        TEXTURE_DAISY.reload("daisy.png", FILE_PROVIDER);
        TEXTURE_LOADING_BG = new Texture2D();
        TEXTURE_LOADING_BG.setParam(param);
        TEXTURE_LOADING_BG.reload("loading_bg.png", FILE_PROVIDER);
    }

    public static void close() {
        TEXTURE_PROGRESS_BAR.close();
        TEXTURE_DAISY.close();
        TEXTURE_LOADING_BG.close();
    }
}
