package org.funnydevelopers.mvzje.client.main;

import org.funnydevelopers.mvzje.client.MvZ;
import org.lwjgl.BufferUtils;
import org.overrun.glutils.wnd.GLFWindow;

import java.nio.DoubleBuffer;

/**
 * @author crazy-piggy, squid233
 * @since 0.1.0
 */
public class Main implements AutoCloseable {
    public static void main(String[] args) {
        try (Main main = new Main()) {
            main.start();
        }
    }

    public static double getCursorPosX(GLFWindow window) {
        DoubleBuffer posX = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(window, posX, null);
        return posX.get(0);
    }

    public static double getCursorPosY(GLFWindow window) {
        DoubleBuffer posY = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(window, posY, null);
        return posY.get(0);
    }

    private static void glfwGetCursorPos(GLFWindow window, DoubleBuffer posX, Object o) {
    }

    public void start() {
        MvZ.INSTANCE.init();
        MvZ.INSTANCE.run();
    }

    @Override
    public void close() {
        MvZ.INSTANCE.close();
    }
}
