package org.funnydevelopers.mvzje.client.screen;

import org.funnydevelopers.mvzje.client.render.TextRenderer;
import org.overrun.swgl.core.io.Mouse;

/**
 * @author crazy-piggy
 * @since 0.1.0
 */
public class DebugHud {
    public static void render(Mouse mouse) {
        TextRenderer.drawTextNoList(0, 0, "Cursor pos: (" + mouse.getIntLastX() + ", " + mouse.getIntLastY() + ")");
    }
}
