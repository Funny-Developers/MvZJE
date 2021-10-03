package org.funnydevelopers.mvzje;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.overrun.glutils.GLProgram;
import org.overrun.glutils.ShaderReader;
import org.overrun.glutils.wnd.Framebuffer;
import org.overrun.glutils.wnd.GLFWindow;

import java.nio.DoubleBuffer;

import static java.awt.Font.MONOSPACED;
import static java.awt.Font.PLAIN;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13C.GL_CLAMP_TO_BORDER;

public class Main implements Runnable, AutoCloseable {
    public GLFWindow window;

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
    public static double getCursorPosY(GLFWindow window){
        DoubleBuffer posY = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(window, posY, null);
        return posY.get(0);
    }

    private static void glfwGetCursorPos(GLFWindow window, DoubleBuffer posX, Object o) {
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
        window = new GLFWindow(800, 600, "Minecraft vs Zombies: Java Edition");
        window.keyCb((hWnd, key, scancode, action, mods) -> {
            if (action == GLFW_PRESS) {
                if (key == GLFW_KEY_ESCAPE) {
                    glfwSetWindowShouldClose(hWnd, true);
                }
            }
        });
        Framebuffer framebuffer = new Framebuffer();
        framebuffer.cb = (hWnd, width, height) -> resize(width, height);
        framebuffer.init(window);
        window.makeCurr();
        GL.createCapabilities();
        glClearColor(0, 0, 0, 1);
        resize(800, 600);
        glfwSwapInterval(1);
        run();
        ClassLoader cl = Main.class.getClassLoader();
        GLProgram prg = new GLProgram();
        try {
            prg.createVsh(ShaderReader.lines(cl, "shaders/scene.vsh"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            prg.createFsh(ShaderReader.lines(cl, "shaders/scene.fsh"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        prg.link();
        render();
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
