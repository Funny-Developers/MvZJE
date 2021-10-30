package org.funnydevelopers.mvzje.client.main;

import org.funnydevelopers.mvzje.client.MvZ;

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

    public void start() {
        MvZ.INSTANCE.init();
        MvZ.INSTANCE.run();
    }

    @Override
    public void close() {
        MvZ.INSTANCE.close();
    }
}
