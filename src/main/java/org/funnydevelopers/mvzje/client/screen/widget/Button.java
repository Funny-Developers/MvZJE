package org.funnydevelopers.mvzje.client.screen.widget;

import org.funnydevelopers.mvzje.client.MvZ;
import org.funnydevelopers.mvzje.client.render.Drawable;

/**
 * @author squid233
 * @since 0.1.0
 */
public class Button implements Drawable, Widget {
    private static final PressAction DEFAULT_ACTION = button -> {
    };
    protected PressAction pressAction;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    public boolean isHovered;

    public interface PressAction {
        void press(Button button);
    }

    public Button(int x,
                  int y,
                  int width,
                  int height,
                  PressAction pressAction) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.pressAction = pressAction;
    }

    public Button(int x,
                  int y,
                  int width,
                  int height) {
        this(x, y, width, height, DEFAULT_ACTION);
    }

    public void onPress() {
        pressAction.press(this);
    }

    public boolean isHovered(int mouseX,
                             int mouseY) {
        return mouseX >= x
            && mouseX < x + width
            && mouseY >= y
            && mouseY < y + height;
    }

    public void hover(double delta) {
    }

    @Override
    public void render(double delta) {
        var client = MvZ.getInstance();
        var mouse = client.getMouse();
        isHovered = isHovered(mouse.getIntLastX(),
            mouse.getIntLastY());
    }
}
