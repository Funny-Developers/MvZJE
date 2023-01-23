package org.funnydevelopers.mvzje.util;

import org.lwjgl.glfw.GLFW;

/**
 * @author squid233
 * @since 0.1.0
 */
public class Timer {
    protected double lastTime;
    protected double passedTime;
    /**
     * The real delta time.
     * <p>
     * The value is {@code currentTime -} {@link #lastTime}
     * </p>
     */
    public double deltaFrameTime;
    /**
     * The delta time floating from 0.0 to 1.0 for interpolation.
     */
    public double partialTick;
    /**
     * The ticks per seconds.
     */
    public int tps;
    /**
     * The ticks should be ticked in one frame.
     */
    public int ticks;
    /**
     * The timescale. Set to 0 to pause.
     */
    public double timescale = 1;
    /**
     * The max ticks per seconds.
     */
    public int maxTicks = 100;

    /**
     * Create a new {@link Timer} with the specified ticks per seconds.
     *
     * @param tps The ticks per seconds.
     * @since 0.2.0
     */
    public Timer(int tps) {
        this.tps = tps;
    }

    /**
     * Create a new {@link Timer}. The default tps is 50.
     */
    public Timer() {
        this(50);
    }

    /**
     * The {@link GLFW#glfwGetTime()} function for users.
     *
     * @return Current time in seconds
     */
    public static double getTime() {
        return GLFW.glfwGetTime();
    }

    public void update() {
        var currentTime = getTime();
        var pt = currentTime - lastTime;
        deltaFrameTime = pt;
        lastTime = currentTime;
        if (pt < 0.0) pt = 0.0;
        if (pt > 1.0) pt = 1.0;
        passedTime += pt * timescale * tps;
        ticks = (int) passedTime;
        if (ticks < 0) ticks = 0;
        if (ticks > maxTicks) ticks = maxTicks;
        passedTime -= ticks;
        partialTick = passedTime;
    }
}