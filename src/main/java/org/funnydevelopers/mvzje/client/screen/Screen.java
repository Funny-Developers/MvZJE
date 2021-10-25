package org.funnydevelopers.mvzje.client.screen;

import org.funnydevelopers.mvzje.client.MvZ;
import org.funnydevelopers.mvzje.client.render.Drawable;

/**
 * @author squid233
 * @since 0.1.0
 */
public class Screen implements Drawable {
    protected MvZ client;
    protected int width;
    protected int height;

    protected void init(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void init() {
        client = MvZ.INSTANCE;
        init(client.window.getWidth(),
                client.window.getHeight());
    }

    public void tick() {
    }

    @Override
    public void render(double delta) {
    }
}
