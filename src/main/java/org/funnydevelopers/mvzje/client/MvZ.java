package org.funnydevelopers.mvzje.client;

import org.funnydevelopers.mvzje.client.render.TextRenderer;
import org.funnydevelopers.mvzje.client.screen.LoadingScreen;
import org.funnydevelopers.mvzje.client.screen.Screen;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.overrun.glutils.Textures;
import org.overrun.glutils.wnd.Framebuffer;
import org.overrun.glutils.wnd.GLFWindow;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * @author crazy-piggy, squid233
 * @since 0.1.0
 */
public enum MvZ implements AutoCloseable {
    INSTANCE;

    public static final String VERSION = "0.1.0";
    public GLFWindow window;
    public Framebuffer framebuffer;
    public Screen screen;
    private final Timer timer = new Timer(20);

    private void resize(int w, int h) {
        glViewport(0, 0, w, h);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, w, 0, h, -100, 100);
        glMatrixMode(GL_MODELVIEW);
    }

    public void openScreen(Screen screen) {
        this.screen = screen;
        if (screen != null) {
            screen.init();
        }
    }

    public void init() {
        GLFWErrorCallback.createPrint().set();
        glfwInit();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        window = new GLFWindow(800, 600, "Minecraft vs. Zombies: Java Edition");

        window.keyCb((hWnd, key, scancode, action, mods) -> {
            if (action == GLFW_PRESS) {
                if (key == GLFW_KEY_ESCAPE) {
                    glfwSetWindowShouldClose(hWnd, true);
                }
            }
        });
        window.cursorPosCb((hWnd, x, y) -> {
            window.mouseX = (int) x;
            window.mouseY = (int) y;
        });
        glfwSetMouseButtonCallback(window.hWnd, (hWnd, button, action, mods) -> {
            if (action == GLFW_PRESS) {
                if (screen != null) {
                    screen.mousePress(window.mouseX, window.mouseY, button);
                }
            }
        });

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        if (vidMode != null) {
            window.setPos((vidMode.width() - 800) / 2, (vidMode.height() - 600) / 2);
        }

        framebuffer = new Framebuffer();
        framebuffer.cb = (hWnd, width, height) -> resize(width, height);
        framebuffer.init(window);

        window.makeCurr();
        GL.createCapabilities();

        glClearColor(0, 0, 0, 1);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        TextRenderer.init();

        resize(800, 600);
        glfwSwapInterval(1);
        timer.advanceTime();
        window.show();
    }

    public void run() {
        double lastTime = glfwGetTime() * 1000;
        openScreen(new LoadingScreen());
        while (!window.shouldClose()) {
            timer.advanceTime();
            for (int i = 0; i < timer.ticks; i++) {
                tick();
            }
            render(timer.delta);
            glfwPollEvents();
            while (glfwGetTime() * 1000 >= lastTime + 1000) {
                lastTime += 1000;
            }
        }
    }

    public void tick() {
        if (screen != null) {
            screen.tick();
        }
    }

    public void render(double delta) {
        glClear(GL_COLOR_BUFFER_BIT);
        if (screen != null) {
            screen.render(delta);
        }
        window.swapBuffers();
    }

    @Override
    public void close() {
        Textures.close();
        window.free();
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}
