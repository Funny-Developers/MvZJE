package org.funnydevelopers.mvzje.client;

import static org.lwjgl.opengl.GL11.GL_NEAREST;

/**
 * @author squid233
 * @since 0.1.0
 */
public class Textures
    extends org.overrun.glutils.Textures {
    private static final ClassLoader cl =
        Textures.class.getClassLoader();
    public static final int TEXTURE_PROGRESS_BAR = load(cl, "progress_bar.png");
    public static final int TEXTURE_DAISY = load(cl, "daisy.png");

    public static int load(ClassLoader loader,
                           String filename)
        throws RuntimeException {
        try {
            return loadAWT(loader, filename, GL_NEAREST);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
