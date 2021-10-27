package org.funnydevelopers.mvzje.client;

import org.lwjgl.system.MemoryStack;

import java.io.IOException;

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
    public static final int TEXTURE_LOADING_BG = load(cl, "loading_bg.png");

    public static int load(ClassLoader loader,
                           String filename)
        throws RuntimeException {
        try (var stack = MemoryStack.stackPush();
             var is = loader.getResourceAsStream(filename)) {
            assert is != null;
            var bytes = is.readAllBytes();
            var bb = stack.malloc(bytes.length);
            return load(filename, bb.put(bytes).flip(), GL_NEAREST);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
