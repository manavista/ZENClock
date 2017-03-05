/*
 * Copyright (c) 2017. manavista, atsushi takahagi
 */

package jp.manavista.zenclock.flagment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.util.List;
import java.util.ArrayList;

import jp.manavista.zenclock.R;

/**
 *
 * ColorDialogFragment
 *
 * Created by atsushi on 2017/02/09.
 */

public class ColorDialogFragment extends DialogFragment {

    /**
     *
     * On Changed Color Listener
     *
     * <p>
     *     interface for Changed Listener.
     * </p>
     */
    public interface OnChangedColorListener {
        void onChangedColor();
    }

    /** Listener Object */
    private OnChangedColorListener listener;

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        try {
            listener = (OnChangedColorListener) context;
        } catch (ClassCastException e) {
            Log.e("ColorDialogFragment ","onAttach " ,e);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final String[] colors = {
                getResources().getString(R.string.dialog_color_black),
                getResources().getString(R.string.dialog_color_green),
                getResources().getString(R.string.dialog_color_blue)
        };

        final List<Integer> checkedItems = new ArrayList<>();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        int clockColorId = preferences.getInt(getResources().getString(R.string.key_clock_color), 0);

        checkedItems.add(clockColorId);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(getResources().getString(R.string.dialog_color_title))
                .setSingleChoiceItems(colors, clockColorId, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkedItems.clear();
                        checkedItems.add(which);
                    }
                })

                .setPositiveButton(getResources().getString(R.string.dialog_apply), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt(getResources().getString(R.string.key_clock_color), checkedItems.get(0));
                        editor.apply();

                        /*
                         * Set Changed Color
                         *
                         * change arc item color in activity.
                         * see ClockActivity#onChangedColor
                         */
                        listener.onChangedColor();

                    }
                })
                .setNegativeButton(getResources().getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /* no description */
                    }
                });

        return builder.create();
    }
}
