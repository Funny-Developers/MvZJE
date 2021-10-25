package org.funnydevelopers.mvzje.client.render;

import org.overrun.glutils.IVertexBuilder;

import static org.lwjgl.opengl.GL11.*;

/**
 * @author squid233
 * @since 0.1.0
 */
public class DisplayListBuilder
    implements IVertexBuilder, AutoCloseable {
    private final int list;

    public DisplayListBuilder() {
        list = glGenLists(1);
        if (!glIsList(list)) {
            throw new RuntimeException("Can't create GL list");
        }
    }

    public DisplayListBuilder newList() {
        glNewList(1, GL_COMPILE);
        return this;
    }

    public DisplayListBuilder begin(int mode) {
        glBegin(mode);
        return this;
    }

    public DisplayListBuilder end() {
        glEnd();
        return this;
    }

    public DisplayListBuilder endList() {
        glEndList();
        return this;
    }

    @Override
    public DisplayListBuilder vertex(float x, float y, float z) {
        glVertex3f(x, y, z);
        return this;
    }

    @Override
    public DisplayListBuilder color(float r, float g, float b, float a) {
        glColor4f(r, g, b, a);
        return this;
    }

    @Override
    public DisplayListBuilder texture(float x, float y) {
        glTexCoord2f(x, y);
        return this;
    }

    @Override
    public DisplayListBuilder normal(float x, float y, float z) {
        glNormal3f(x, y, z);
        return this;
    }

    @Override
    public void render() {
        glCallList(list);
    }

    @Override
    public void render(int mode) {
        glCallList(list);
    }

    @Override
    public void close() {
        glDeleteLists(list, 1);
    }
}
