package org.funnydevelopers.mvzje.client;

import org.lwjgl.stb.STBTTBakedChar;

import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBTruetype.stbtt_BakeFontBitmap;
import static org.lwjgl.system.MemoryUtil.memAlloc;
import static org.lwjgl.system.MemoryUtil.memFree;

/**
 * @author squid233
 * @since 0.1.0
 */
public class FontType {
    public final int id;
    public final STBTTBakedChar.Buffer charData;

    public FontType() {
        ByteBuffer bb = null;
        ByteBuffer pixelBuf = null;
        try (var is =
                 this.getClass()
                     .getClassLoader()
                     .getResourceAsStream("unifont.ttf")) {
            assert is != null;
            var bytes = is.readAllBytes();
            bb = memAlloc(bytes.length);
            pixelBuf = memAlloc(2048 * 2048);
            charData = STBTTBakedChar.malloc('\uffff');
            stbtt_BakeFontBitmap(bb,
                32,
                pixelBuf,
                2048,
                2048,
                0,
                charData);
            id = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, id);
            glTexImage2D(GL_TEXTURE_2D,
                0,
                GL_ALPHA,
                2048,
                2048,
                0,
                GL_ALPHA,
                GL_UNSIGNED_BYTE,
                pixelBuf);
            glTexParameteri(GL_TEXTURE_2D,
                GL_TEXTURE_MIN_FILTER,
                GL_LINEAR);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            memFree(bb);
            memFree(pixelBuf);
        }
    }
}
