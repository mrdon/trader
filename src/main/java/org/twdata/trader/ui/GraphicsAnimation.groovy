package org.twdata.trader.ui

import org.newdawn.slick.Graphics

/**
 * 
 */

public class GraphicsAnimation {

    Closure drawClosure;
    Closure endClosure;
    int frames;
    long totalTime;
    boolean loop;
    boolean active;

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
                    //println("Drawing frame ${x} with ${(frameUpdates[x]-now)/1000000}ms to go");
                    drawClosure(g, Math.max(0, x-1));
                    drawn = true;
                }
            }
            if (!drawn) {
                active = false;
                endClosure();
            }
        }
    }

}