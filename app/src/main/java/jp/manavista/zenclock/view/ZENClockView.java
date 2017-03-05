/*
 * Copyright (c) 2017. manavista, atsushi takahagi
 */

package jp.manavista.zenclock.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import java.util.Calendar;

import jp.manavista.zenclock.R;
import jp.manavista.zenclock.vo.ArcItem;
import jp.manavista.zenclock.vo.ArcPosition;

/**
 *
 * ZEN Clock View
 *
 * Created by atsushi on 2017/01/26.
 */
public class ZENClockView extends View {

    /** Animation interval for clock (ms)  */
    private static final long CLOCK_INTERVAL = 1000;
    /** Radius on center */
    private final int CENTER_RADIUS = 5;

    /** Position Y for center */
    private int centerY = 0;
    /** Position X for center */
    private int centerX = 0;

    /** Arc position (x, y, radius) */
    private ArcPosition position;

    /** Arc Item for second */
    private ArcItem arcSec;
    /** Arc Item for minute */
    private ArcItem arcMin;
    /** Arc Item for hour */
    private ArcItem arcHour;

    /** Paint object for drawArc */
    private Paint paint;
    /** RectF Object for clock center */
    private RectF center;

    /**
     *
     * Constructor
     *
     * @param context Context
     */
    public ZENClockView(Context context) {

        super(context);

        position = new ArcPosition(0, 0, 0);

        final int color = ContextCompat.getColor(context, R.color.colorBlack_trans);
        arcSec = new ArcItem(this, color, 1, position);
        arcMin = new ArcItem(this, color, 0.7f, position);
        arcHour = new ArcItem(this, color, 0.4f, position);

        arcSec.setDuration(CLOCK_INTERVAL);
        arcSec.setInterpolator(new LinearInterpolator());
        arcSec.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                calc();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                calc();
                startAnimation(arcSec);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                /* no description */
            }
        });

        this.paint = new Paint();
        paint.setAntiAlias(true);

        this.center = new RectF(
                centerX - CENTER_RADIUS,
                centerY - CENTER_RADIUS,
                centerX + CENTER_RADIUS,
                centerY + CENTER_RADIUS
        );

    }

    /**
     *
     * Set Arc Items Color
     *
     *
     * @param color Draw arc color code
     */
    public void setArcItemsColor(int color) {
        arcSec.setColor(color);
        arcMin.setColor(color);
        arcHour.setColor(color);
    }

    /**
     *
     * Get Arc Second
     *
     * <p>
     *     get arc second object.
     * </p>
     *
     * @return second arc item object
     */
    public ArcItem getArcSec() {
        return arcSec;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        paint.setColor(arcSec.getColor());
        canvas.drawArc(arcSec.buildRectF(), arcSec.getStartAngle(), arcSec.getSweepAngle(), true, paint);

        paint.setColor(arcMin.getColor());
        canvas.drawArc(arcMin.buildRectF(), arcMin.getStartAngle(), arcMin.getSweepAngle(), true, paint);

        paint.setColor(arcHour.getColor());
        canvas.drawArc(arcHour.buildRectF(), arcHour.getStartAngle(), arcHour.getSweepAngle(), true, paint);

        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
        canvas.drawArc(this.center, 270, 360, true, paint);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        super.onSizeChanged(w, h, oldw, oldh);

        centerX = w / 2;
        centerY = h / 2;
        final int handLength  = Math.min(centerX, centerY);

        this.position.getCenter().set(centerX, centerY);
        this.position.setRadius(handLength);

        this.center.set(
                centerX - CENTER_RADIUS,
                centerY - CENTER_RADIUS,
                centerX + CENTER_RADIUS,
                centerY + CENTER_RADIUS
        );

    }

    /**
     *
     * Calculation
     *
     * <p>
     *     Calculation to sweep angle for now second, minute, hour.
     * </p>
     *
     */
    private void calc() {

        final Calendar calendar = Calendar.getInstance();

        // for test code
        // calendar.set(2017,1,29,13,50,35);
        //

        final float milliSecond = calendar.get(Calendar.MILLISECOND);
        final float sec = calendar.get(Calendar.SECOND);
        final float min = calendar.get(Calendar.MINUTE);
        final float hour = calendar.get(Calendar.HOUR);
        final int am_or_pm = calendar.get(Calendar.AM_PM);

        final float secAngle = (sec + (milliSecond / 1000)) * 6;

        arcSec.setSweepStartAngle(secAngle);

        if( !arcMin.is_reversal() ) {
            arcMin.setStartAngle(270);
            arcMin.setSweepAngle(( min + sec / 60 ) * 6);
        } else {
            arcMin.setStartAngle(270 + ( min + sec / 60 ) * 6);
            arcMin.setSweepAngle(360 - ( min + sec / 60 ) * 6);
        }

        if ( !arcMin.is_reversal() && arcMin.getSweepAngle() == 0 ) {
            arcMin.setSweepAngle(360);
            arcMin.setIs_reversal(true);
        } else if ( arcMin.is_reversal() && arcMin.getSweepAngle() == 360 ) {
            arcMin.setSweepAngle(0);
            arcMin.setIs_reversal(false);
        }

        if( am_or_pm == Calendar.AM ) {
            arcHour.setStartAngle(270);
            arcHour.setSweepAngle(( hour + min / 60 ) * 30);
        } else {
            arcHour.setStartAngle(270 + ( hour + min / 60 ) * 30);
            arcHour.setSweepAngle(360 - ( hour + min / 60 ) * 30);
        }

    }

}
