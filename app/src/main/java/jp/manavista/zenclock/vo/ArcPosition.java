/*
 * Copyright (c) 2017. manavista, atsushi takahagi
 */

package jp.manavista.zenclock.vo;

import android.graphics.Point;

/**
 *
 * Arc Position
 *
 * Created by atsushi on 2017/01/29.
 */
public class ArcPosition {

    /** center point */
    private Point center;
    /** radius of circle  */
    private int radius;

    /**
     *
     * constructor
     *
     * @param x point x
     * @param y point y
     * @param radius radius
     */
    public ArcPosition(int x, int y, int radius) {
        this.center = new Point(x, y);
        this.radius = radius;
    }

    public Point getCenter() {
        return center;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
