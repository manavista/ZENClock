/*
 * Copyright (c) 2017. manavista, atsushi takahagi
 */

package jp.manavista.zenclock.constants;

import jp.manavista.zenclock.R;

/**
 *
 * Clock Color
 *
 * Created by atsushi on 2017/02/09.
 */
public enum ClockColor {

    /** BLACK */
    BLACK(0, R.color.colorBlack_trans),
    /** GREEN */
    GREEN(1, R.color.colorGreen_trans),
    /** BLUE */
    BLUE(2, R.color.colorBlue_trans);

    /** color Id */
    private int colorId;
    /** color Resource */
    private int colorResource;

    private ClockColor(int colorId, int colorResource) {
        this.colorId = colorId;
        this.colorResource = colorResource;
    }

    public int getColorId() {
        return colorId;
    }
    public int getColorResource() {
        return colorResource;
    }

    /**
     *
     * getClockColor
     *
     * @param colorId colorId
     * @return target ClockColor
     */
    public static ClockColor getClockColor(int colorId) {

        for( ClockColor clockColor : ClockColor.values() ){
            if( clockColor.getColorId() == colorId ) {
                return clockColor;
            }
        }

        /*
         * if not match colorId, default ClockColor
         */
        return BLACK;
    }
}
