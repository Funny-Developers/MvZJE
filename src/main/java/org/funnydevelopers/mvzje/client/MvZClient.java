package org.funnydevelopers.mvzje.client;

import org.funnydevelopers.mvzje.client.render.TextRenderer;
import org.funnydevelopers.mvzje.client.screen.LoadingScreen;
import org.funnydevelopers.mvzje.client.screen.Screen;
import org.overrun.swgl.core.GlfwApplication;
import org.overrun.swgl.core.cfg.WindowConfig;
import org.overrun.swgl.core.gl.GLBlendFunc;
import org.overrun.swgl.core.gui.font.UnifontTextBatch;
import org.overrun.swgl.core.io.Mouse;
import org.overrun.swgl.core.io.Window;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.overrun.swgl.core.gl.GLClear.*;
import static org.overrun.swgl.core.gl.GLStateMgr.*;

/**
 * @author crazy-piggy, squid233
 * @since 0.1.0
 */
public class MvZClient extends GlfwApplication {
    private static final MvZClient INSTANCE = new MvZClient();
    public static final String VERSION = "0.1.0";
    public Screen screen;
    public UnifontTextBatch textBatch;
    public TextRenderer textRenderer;
    private boolean debugHudVisible = false;

    public static MvZClient getInstance() {
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
        super.onResize(width, height);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, width, height, 0, -100, 100);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        if (screen != null) {
            screen.init();
        }
    }

    public void openScreen(Screen screen) {
        if (this.screen != null) {
            this.screen.onClose();
        }
        this.screen = screen;
        if (screen != null) {
            screen.init();
        }
    }

    @Override
    public void prepare() {
        WindowConfig.initialTitle = "Minecraft vs. Zombies: Java Edition " + VERSION;
        WindowConfig.initialSwapInterval = 1;
        WindowConfig.visibleBeforeStart = false;
        WindowConfig.resizable = false;
        WindowConfig.setRequiredGlVer(1, 0);
        WindowConfig.coreProfile = false;
        WindowConfig.forwardCompatible = false;
    }

    @Override
    public void start() {
        var vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        if (vidMode != null) {
            window.moveToCenter(vidMode.width(), vidMode.height());
        }
        clearColor(0.4f, 0.6f, 0.9f, 1.0f);
        enableTexture2D();
        enableBlend();
        blendFunc(GLBlendFunc.SRC_ALPHA, GLBlendFunc.ONE_MINUS_SRC_ALPHA);
        textBatch = UnifontTextBatch.getInstance();
        textRenderer = new TextRenderer(this);
        Textures.init();
    }

    @Override
    public void onKeyPress(int key, int scancode, int mods) {
        if (key == GLFW_KEY_F3) {
            debugHudVisible = !debugHudVisible;
        }
    }

    @Override
    public void onCursorPos(double x, double y, double xd, double yd) {
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
        render(timer.partialTick);
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
        if (debugHudVisible) {
            renderDebugHud();
        }
    }

    private void renderDebugHud() {
        textRenderer.drawText(0, 0, WindowConfig.initialTitle);
        textRenderer.drawText(0, 16, frames + " fps");

        textRenderer.drawText(0, 3 * 16,
            "Cursor pos: (" + mouse.getIntLastX() + ", " + mouse.getIntLastY() + ")");
    }

    @Override
    public void close() {
        textBatch.dispose();
        Textures.dispose();
    }
}
