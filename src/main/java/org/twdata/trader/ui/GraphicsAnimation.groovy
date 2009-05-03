package org.twdata.trader.ui

import org.newdawn.slick.Graphics

/**
 * 
 */

public class GraphicsAnimation {

    Closure onRender;
    Closure onNewFrame;
    Closure onEnd;
    int frames;
    long totalTime;
    boolean loop;
    boolean active;
    int lastFrame = -1;

    private final long[] frameUpdates;

    public GraphicsAnimation(Map args=[:]) {
        args.each { key, value -> this.@"$key" = value }
        frameUpdates = new long[frames];
    }

    public void start() {
        long now = System.nanoTime();
        long frameTime = (totalTime  * 1000000 )/ frames;
        for (x in 0..frames-1) {
            frameUpdates[x] = now + frameTime * x;
        }
        active = true;
    }

    public void render(Graphics g) {
        if (active) {
            long now = System.nanoTime();

            // Draw the last frame you can
            boolean drawn = false;
            for (x in 0..frames-1) {
                if (!drawn && frameUpdates[x] > now) {
                    int frame = Math.max(0, x-1);
                    if (lastFrame != frame) {
                        onNewFrame(lastFrame, frame);
                        lastFrame = frame;
                    }
                    //println("Drawing frame ${x} with ${(frameUpdates[x]-now)/1000000}ms to go");
                    if (onRender) {
                        onRender(g, frame);
                    }
                    drawn = true;
                }
            }
            if (!drawn) {
                active = false;
                onEnd();
            }
        }
    }

}