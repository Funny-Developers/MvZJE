package org.funnydevelopers.mvzje.client;

import org.funnydevelopers.mvzje.client.screen.LoadingScreen;
import org.funnydevelopers.mvzje.client.screen.Screen;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.overrun.swgl.core.GlfwApplication;
import org.overrun.swgl.core.cfg.GlobalConfig;
import org.overrun.swgl.core.gui.font.SwglEasyFont;
import org.overrun.swgl.core.io.Mouse;
import org.overrun.swgl.core.io.Window;

import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.*;
import static org.overrun.swgl.core.gl.GLClear.*;
import static org.overrun.swgl.core.gl.GLStateMgr.*;
import static org.overrun.swgl.core.gl.ims.GLImmeMode.*;

/**
 * @author crazy-piggy, squid233
 * @since 0.1.0
 */
public class MvZ extends GlfwApplication {
    private static final MvZ INSTANCE = new MvZ();
    public static final String VERSION = "0.1.0";
    public Screen screen;

    public static MvZ getInstance() {
        return INSTANCE;
    }

    public Window getWindow() {
        return window;
    }

    public Mouse getMouse() {
        return mouse;
    }

    @Override
    public void onResize(int width, int height) {
        glViewport(0, 0, width, height);
        lglGetMatrix(MatrixMode.PROJECTION).setOrtho(0, width, height, 0, -100, 100);
        lglMatrixMode(MatrixMode.MODELVIEW);
        lglLoadIdentity();
        if (screen != null) {
            screen.init();
        }
    }

    public void openScreen(Screen screen) {
        this.screen = screen;
        if (screen != null) {
            screen.init();
        }
    }

    @Override
    public void prepare() {
        GLFWErrorCallback.createPrint(System.err).set();
        GlobalConfig.initialTitle = "Minecraft vs. Zombies: Java Edition";
        GlobalConfig.initialSwapInterval = 1;
    }

    @Override
    public void preStart() {
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
    }

    @Override
    public void start() {
        lglRequestContext();
        onResize(window.getWidth(), window.getHeight());
        var vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        if (vidMode != null) {
            window.moveToCenter(vidMode.width(), vidMode.height());
        }
        clearColor(0.4f, 0.6f, 0.9f, 1.0f);
        enableTexture2D();
        enableBlend();
        blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        SwglEasyFont.initialize();
        Textures.init();
    }

    @Override
    public void onKeyPress(int key, int scancode, int mods) {
        if (key == GLFW_KEY_ESCAPE) {
            glfwSetWindowShouldClose(window.getHandle(), true);
        }
    }

    @Override
    public void onMouseBtnPress(int btn, int mods) {
        if (screen != null) {
            screen.mousePress(mouse.getIntLastX(), mouse.getIntLastY(), btn);
        }
    }

    @Override
    public void postStart() {
        openScreen(new LoadingScreen());
    }

    @Override
    public void run() {
        render(timer.deltaTime);
    }

    @Override
    public void tick() {
        if (screen != null) {
            screen.tick();
        }
    }

    public void render(double delta) {
        clear(COLOR_BUFFER_BIT | DEPTH_BUFFER_BIT);
        if (screen != null) {
            screen.render(delta);
        }
    }

    @Override
    public void close() {
        lglDestroyContext();
        SwglEasyFont.destroy();
        Textures.close();
    }

    @Override
    public void postClose() {
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }
}
