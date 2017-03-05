/*
 * Copyright (c) 2017. manavista, atsushi takahagi
 */

package jp.manavista.zenclock.vo;

import android.graphics.RectF;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import jp.manavista.zenclock.view.ZENClockView;

/**
 *
 * ArcItem
 *
 * Created by atsushi on 2017/01/29.
 */
public class ArcItem extends Animation {

    /** start angle of circle */
    private float startAngle = 270;
    /** sweep angle of circle */
    private float sweepAngle = 0;
    /** sweep start angle of circle */
    private float sweepStartAngle = 0;

    /** is draw reversal flag */
    private boolean is_reversal = false;

    /** circle color */
    private int color;
    /** a ratio if arc */
    private float ratio;

    /** circle position and radius */
    private ArcPosition position;

    /** ZEN Clock View For animation */
    private ZENClockView view;

    /**
     *
     * constructor
     *
     * @param color color of circle
     * @param ratio ratio of arc
     * @param position position of circle
     */
    public ArcItem(ZENClockView view, int color, float ratio, ArcPosition position) {
        this.view = view;
        this.color = color;
        this.ratio = ratio;
        this.position = position;
    }

    /**
     *
     * Build Rect Object
     *
     * <p>
     *     summary<br>
     *     return of Rect Object for Arc setting.
     * </p>
     * @return Rect Object
     */
    public RectF buildRectF() {
        float x = this.position.getCenter().x;
        float y = this.position.getCenter().y;
        float radius = this.position.getRadius() * ratio;
        return new RectF(x - radius, y - radius, x + radius, y + radius);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {

        float angle = this.sweepStartAngle + (6 * interpolatedTime);
        if( angle > 360.0f ){
            angle = 360.0f;
        }

        if( !this.is_reversal() ) {
            this.setStartAngle(270);
            this.setSweepAngle(angle);
        } else {
            this.setStartAngle(270 + angle);
            this.setSweepAngle(360 - angle);
        }

        if( !this.is_reversal() && this.getSweepAngle() == 360.0f  ) {
            this.setIs_reversal(true);
            this.setStartAngle(270);
            this.setSweepAngle(360);

            cancel();
            start();
            return;

        } else if( this.is_reversal() && this.getSweepAngle() == 0.0f ) {
            this.setIs_reversal(false);
            this.setStartAngle(270);
            this.setSweepAngle(0);

            cancel();
            start();
            return;
        }

        this.view.requestLayout();
    }

    public float getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
    }

    public float getSweepAngle() {
        return sweepAngle;
    }

    public void setSweepAngle(float sweepAngle) {
        this.sweepAngle = sweepAngle;
    }

    public void setSweepStartAngle(float sweepStartAngle) {
        this.sweepStartAngle = sweepStartAngle;
    }

    public boolean is_reversal() {
        return is_reversal;
    }

    public void setIs_reversal(boolean is_reversal) {
        this.is_reversal = is_reversal;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

}
