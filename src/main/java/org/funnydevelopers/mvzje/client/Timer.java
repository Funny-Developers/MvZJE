package org.funnydevelopers.mvzje.client;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

/**
 * @author squid233
 * @since 0.1.0
 */
public class Timer {
    public static final long NS_PER_SECOND = 1000000000L;
    public static final long MAX_NS_PER_UPDATE = 1000000000L;
    public static final int MAX_TICKS_PER_UPDATE = 100;
    private final double tps;
    private double lastTime;
    public int ticks = 0;
    public double delta = 0;
    public double fps = 0;
    public double passedTime = 0;

    public Timer(double tps) {
        this.tps = tps;
        lastTime = glfwGetTime() * NS_PER_SECOND;
    }

    public void advanceTime() {
        double now = glfwGetTime() * NS_PER_SECOND;
        double passedNs = now - lastTime;
        lastTime = now;
        if (passedNs < 0) {
            passedNs = 0;
        } else if (passedNs > MAX_NS_PER_UPDATE) {
            passedNs = MAX_NS_PER_UPDATE;
        }
        fps = NS_PER_SECOND / passedNs;
        passedTime += passedNs * tps / NS_PER_SECOND;
        ticks = (int) passedTime;
        if (ticks > MAX_TICKS_PER_UPDATE) {
            ticks = MAX_TICKS_PER_UPDATE;
        }
        passedTime -= ticks;
        delta = passedTime;
    }
}
