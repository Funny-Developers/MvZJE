package org.funnydevelopers.mvzje.client.screen;

import org.funnydevelopers.mvzje.client.MvZ;
import org.funnydevelopers.mvzje.client.render.Drawable;
import org.funnydevelopers.mvzje.client.screen.widget.Button;
import org.funnydevelopers.mvzje.client.screen.widget.Widget;

import java.util.ArrayList;
import java.util.List;

/**
 * @author squid233
 * @since 0.1.0
 */
public class Screen implements Drawable, Widget {
    protected final List<Widget> widgets = new ArrayList<>();
    protected final List<Button> buttons = new ArrayList<>();
    protected MvZ client;
    protected int width;
    protected int height;

    protected void init(int width,
                        int height) {
        this.width = width;
        this.height = height;
    }

    public void init() {
        client = MvZ.getInstance();
        init(client.getWindow().getWidth(),
            client.getWindow().getHeight());
    }

    public <T extends Widget> T addWidget(T widget) {
        widgets.add(widget);
        return widget;
    }

    public <T extends Button> T addButton(T button) {
        buttons.add(button);
        return addWidget(button);
    }

    public void mousePress(int mouseX,
                           int mouseY,
                           int button) {
        for (var b : buttons) {
            if (b.isHovered(mouseX, mouseY)) {
                b.onPress();
                break;
            }
        }
    }

    public void tick() {
    }

    @Override
    public void render(double delta) {
        for (var widget : widgets) {
            if (widget instanceof Drawable) {
                ((Drawable) widget).render(delta);
            }
        }
        for (var b : buttons) {
            if (b.isHovered) {
                b.hover(delta);
            }
        }
    }
}
