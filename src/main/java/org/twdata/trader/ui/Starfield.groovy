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
import org.newdawn.slick.geom.Vector2f
import org.newdawn.slick.geom.Circle
import org.newdawn.slick.geom.Shape

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

    public void warp(Closure onEnd) {
        warp = new GraphicsAnimation(
                frames : 60 * 3,
                totalTime: 2 * 1000,
                onNewFrame: {int lastFrame, int frame ->
                    float advance = (float)((float)frame/10f * (float)frame/10f * (1f / 180f)) as float;
                    stars.each {Star s ->
                        s.updateTrail(advance);
                    }
                },
                onRender: {Graphics g, int frame ->
                    stars.each {Star s ->
                        s.render(g);
                    }
                },
                onEnd : { warp = null; onEnd() }
        );
        warp.start();
    }

    public boolean inWarp() {
        return warp != null;
    }

    public void draw(Graphics g) {
        if (!warp) {
            if (rnd.nextInt(3) == 1) {
                [1..30].each {
                    Star s = stars.get(rnd.nextInt(stars.size()));
                    def mod = (rnd.nextBoolean() ? s.size + 1 : s.size - 1);
                    mod = Math.max(0, Math.min(5, mod));
                    s.size = mod;
                }
            }
            stars.each {Star s ->
                s.render(g);
            };
        } else {
            warp.render(g);
        }
    }
}

class Star {
    final Rectangle2D bounds;
    final float x;
    final float y;
    int size;
    final Color color;
    final Point2D warpPoint;
    final Polygon trail;
    final float slope;
    final Line line;
    Shape warpTrail;


    public Star(Map args=[:]) {

        args.each { key, value -> this.@"$key" = value }
        //println("x:${x} y:${y}");
        line = new Line((float)bounds.centerX, (float)bounds.centerY, x, y);
        slope = (line.DX == 0f ? Float.MIN_VALUE : line.DY/line.DX);
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
    }

    public Star advance(float percent) {

        Vector2f start = new Vector2f(x, y);
        Vector2f end = new Vector2f((float)warpPoint.x, (float)warpPoint.y);
        Vector2f mid = end.sub(start);
        Vector2f pt = start.add(new Vector2f(mid.x * percent as float, mid.y * percent as float));
        return new Star(
                bounds: bounds,
                x: pt.x,
                y: pt.y,
                size: size,
                color: color
                );
    }

    public void render(Graphics g) {
        g.color = color;
        if (warpTrail == null) {
            float rad = size / 2;
            g.fillOval((float)(x - rad), (float)(y - rad), size, size);
        } else {
            Line top = new Line(warpTrail.points[0], warpTrail.points[1], warpTrail.points[2], warpTrail.points[3]);
            Line bottom = new Line(warpTrail.points[4], warpTrail.points[5], warpTrail.points[6], warpTrail.points[7]);
            float endx = warpTrail.centerX - bottom.centerX;
            float endy = warpTrail.centerY - bottom.centerY;
            float startx = warpTrail.centerX - top.centerX;
            float starty = warpTrail.centerY - top.centerY;
            def fill = new GradientFill(startx, starty, color, endx, endy, Color.black);
            g.fill(warpTrail, fill);
            float rad = size / 2;
            g.fillOval((float)(bottom.centerX - rad), (float)(bottom.centerY - rad), size, size);
        }
        

    }

    public void updateTrail(float percent)  {

        Vector2f start = new Vector2f(x, y);
        Vector2f end = new Vector2f((float)warpPoint.x, (float)warpPoint.y);
        Vector2f mid = end.sub(start);
        Vector2f pt = start.add(new Vector2f(mid.x * percent as float, mid.y * percent as float));

        float warpAngle = Math.atan(slope);
        if (line.DX < 0) {
            warpAngle += Math.PI;
        }
        float len = new Line(x, y, (float)pt.x, (float)pt.y).length() + size;
        Polygon rect = new Polygon([x, y-size/2, x, y+size/2, x + len, y + size/2, x + len, y - size/2] as float[]);
        warpTrail =  rect.transform(Transform.createRotateTransform(warpAngle, x, y));
    }

}