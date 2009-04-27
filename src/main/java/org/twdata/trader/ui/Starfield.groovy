package org.twdata.trader.ui

import org.newdawn.slick.Color
import java.awt.Rectangle
import java.awt.geom.Rectangle2D
import org.newdawn.slick.Graphics
import java.awt.geom.Point2D
import org.newdawn.slick.geom.Line
import org.newdawn.slick.geom.Polygon
import org.newdawn.slick.fills.GradientFill
import org.newdawn.slick.geom.Transform

/**
 * 
 */

public class Starfield {
    private final List<Star> stars;
    private final List<Point2D> warpPoints;
    private final Rectangle2D bounds;
    private final Random rnd = new Random();
    private GraphicsAnimation warp;

    public Starfield(int fieldsize, Rectangle2D bounds) {
        this.bounds = bounds;
        this.stars = new ArrayList<Star>(fieldsize);
        for (int i=0; i<fieldsize; i++) {
            float posx=bounds.getMinX() + (rnd.nextFloat() * (bounds.getMaxX() - bounds.getMinX()));
            float posy=bounds.getMinY() + (rnd.nextFloat() * (bounds.getMaxY() - bounds.getMinY()));
            int size = rnd.nextInt(5);
            Star star = new Star(bounds: bounds, x:posx, y:posy, size:size, color:Color.gray)
            stars.add(star);
        }
    }

    public void warp() {
        warp = new GraphicsAnimation(
                frames : 60,
                totalTime: 3000,
                drawClosure: {Graphics g, int frame ->
                    stars.each {Star s ->
                        float endx = s.x - s.trail.centerX;
                        float endy = s.y - s.trail.centerY;
                        float startx = endx / frame;
                        float starty = endy / frame;
                        def fill = new GradientFill(startx, starty, s.color, (float)(endx/5), (float)(endy/5), Color.black);
                        g.fill(s.trail, fill);
                        //g.scale(1, 1);
                        /*g.color = Color.green;
                        g.fillOval((float)(s.trail.centerX + startx), (float)(s.trail.centerY + starty), 5f, 5f);
                        g.color = Color.red;
                        g.fillOval((float)(s.trail.centerX + endx), (float)(s.trail.centerY + endy), 5f, 5f);
                        */
                    }
                },
                endClosure : { warp = null; }
        );
        warp.start();
    }

    public boolean inWarp() {
        return warp != null;
    }

    public void draw(Graphics g) {
        if (rnd.nextInt(3) == 1) {
            [1..30].each {
                Star s = stars.get(rnd.nextInt(stars.size()));
                def mod = (rnd.nextBoolean() ? s.size + 1 : s.size - 1);
                mod = Math.max(0, Math.min(5, mod));
                s.size = mod;
            }
        }
        stars.each {Star s ->
            g.color = s.color;
            float rad = s.size / 2;
            g.fillOval((float)(s.x - rad), (float)(s.y - rad), s.size, s.size);
            if (warp != null) {
                warp.render(g); 
            }
        };
    }
}

private class Star {
    final Rectangle2D bounds;
    final float x;
    final float y;
    int size;
    final Color color;
    final Point2D warpPoint;
    final Polygon trail;

    public Star(Map args=[:]) {

        args.each { key, value -> this.@"$key" = value }
        //println("x:${x} y:${y}");
        Line line = new Line((float)bounds.centerX, (float)bounds.centerY, x, y);
        float slope = (line.DX == 0f ? Float.MIN_VALUE : line.DY/line.DX);
        float b = y - (slope * x);

        Float endx = null;
        Float endy = null;
        if (slope <= 1 && slope >= -1) {
            // east
            if (line.DX >= 0) {
                endx = bounds.getMaxX();
            // west
            } else {
                endx = bounds.getMinX();
            }
        } else {
            // south
            if (line.DY >= 0) {
                endy = bounds.getMaxY();
            // north
            } else {
                endy = bounds.getMinY();
            }
        }

        if (endx != null) {
            endy = (slope * endx) + b;
        } else {
            endx = ((endy - b)/ slope);
        }
        //println("cx:${bounds.centerX} cy:${bounds.centerY} x:${x} y:${y} sloap:${sloap} endx:${endx} endy:${endy}");
        warpPoint = new Point2D.Float(endx, endy);

        float warpAngle = Math.atan(slope);
        if (line.DX < 0) {
            warpAngle += Math.PI;
        }
        float len = new Line(x, y, (float)warpPoint.x, (float)warpPoint.y).length() + size;
        Polygon rect = new Polygon([x, y-size, x, y+size, x + len, y - (size*2), x + len, y + (size*2)] as float[]);
        trail = rect.transform(Transform.createRotateTransform(warpAngle, x, y));
    }

}