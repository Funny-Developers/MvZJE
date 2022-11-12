package org.funnydevelopers.mvzje.client;

import org.overrun.swgl.core.asset.tex.Texture2D;
import org.overrun.swgl.core.asset.tex.TextureParam;
import org.overrun.swgl.core.io.IFileProvider;

import static org.lwjgl.opengl.GL13C.GL_NEAREST;
import static org.lwjgl.opengl.GL13C.GL_REPEAT;

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
        var param = new TextureParam()
            .minFilter(GL_NEAREST)
            .magFilter(GL_NEAREST);
        TEXTURE_PROGRESS_BAR = new Texture2D("progress_bar.png", FILE_PROVIDER,
            (t, b) -> t.setParam(new TextureParam().fromOther(param).wrapS(GL_REPEAT)));
        TEXTURE_DAISY = new Texture2D("daisy.png", FILE_PROVIDER,
            (t, b) -> t.setParam(param));
        TEXTURE_LOADING_BG = new Texture2D("loading_bg.png", FILE_PROVIDER,
            (t, b) -> t.setParam(param));
    }

    public static void close() {
        TEXTURE_PROGRESS_BAR.close();
        TEXTURE_DAISY.close();
        TEXTURE_LOADING_BG.close();
    }
}
