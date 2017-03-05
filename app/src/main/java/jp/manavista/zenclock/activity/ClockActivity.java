/*
 * Copyright (c) 2017. manavista, atsushi takahagi
 */

package jp.manavista.zenclock.activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import jp.manavista.zenclock.R;
import jp.manavista.zenclock.constants.ClockColor;
import jp.manavista.zenclock.flagment.AboutDialogFragment;
import jp.manavista.zenclock.flagment.ColorDialogFragment;
import jp.manavista.zenclock.view.ZENClockView;

/**
 *
 * Clock Activity
 *
 * <p>
 *      ZEN Clock main Activity.
 * </p>
 *
 * @author atsushi takahagi
 * @since 2012/02/21
 */
public class ClockActivity extends AppCompatActivity implements ColorDialogFragment.OnChangedColorListener {

    private ZENClockView view = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        view = new ZENClockView(getApplication());

        setContentView(view);
        setClockColor();

        view.setAnimation(view.getArcSec());

    }

    /**
     *
     * Create Options Menu
     *
     * @param menu Menu Object
     * @return You must return true for the menu to be displayed; if you return false it will not be shown.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch ( item.getItemId() ) {
            case R.id.menu_about_id:

                AboutDialogFragment dialog = new AboutDialogFragment();
                dialog.show(getSupportFragmentManager(),"dialog_about_app");
                return true;

            case R.id.menu_color_id:

                ColorDialogFragment colorDialogFragment = new ColorDialogFragment();
                colorDialogFragment.show(getSupportFragmentManager(),"dialog_color_app");
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onChangedColor() {
        setClockColor();
        view.invalidate();
    }

    /**
     *
     * Set Clock Color
     *
     * <p>
     *     Set clock color by saved sharedPreferences. <br>
     *     If can't resolve color data, set BLACK to color.
     * </p>
     *
     */
    private void setClockColor() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ClockActivity.this);
        final int clockColorId = preferences.getInt(getResources().getString(R.string.key_clock_color), ClockColor.BLACK.getColorId());
        final int clockColor = ClockColor.getClockColor(clockColorId).getColorResource();

        view.setArcItemsColor(ContextCompat.getColor(ClockActivity.this, clockColor));
    }


}
