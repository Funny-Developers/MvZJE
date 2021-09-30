package org.funnydevelopers.mvzje;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.overrun.glutils.wnd.Framebuffer;
import org.overrun.glutils.wnd.GLFWindow;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Main implements Runnable, AutoCloseable {
    public GLFWindow window;

    public static void main(String[] args) {
        try (Main main = new Main()) {
            main.start();
        }
    }

    public void resize(int w, int h) {
        glViewport(0, 0, w, h);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, w, h, 0, -1, 1);
        glMatrixMode(GL_MODELVIEW);
    }

    public void start() {
        GLFWErrorCallback.createPrint().set();
        glfwInit();
        window = new GLFWindow(800, 600, "lwjgl-basic");
        window.keyCb((hWnd, key, scancode, action, mods) -> {
            if (action == GLFW_PRESS) {
                if (key == GLFW_KEY_ESCAPE) {
                    glfwSetWindowShouldClose(hWnd, true);
                }
            }
        });
        framebuffer = new Framebuffer();
        framebuffer.cb = (hWnd, width, height) -> resize(width, height);
        framebuffer.init(window);
        window.makeCurr();
        GL.createCapabilities();
        glClearColor(0, 0, 0, 1);
        resize(800, 600);
        glfwSwapInterval(1);
        run();
    }

    @Override
    public void run() {
        while (!window.shouldClose()) {
            render();
            glfwPollEvents();
        }
    }

    public void render() {
        glClear(GL_COLOR_BUFFER_BIT);
        window.swapBuffers();
    }

    @Override
    public void close() {
        window.free();
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}
