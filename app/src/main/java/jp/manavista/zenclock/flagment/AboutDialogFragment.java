/*
 * Copyright (c) 2017. manavista, atsushi takahagi
 */

package jp.manavista.zenclock.flagment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.ViewGroup;

import jp.manavista.zenclock.BuildConfig;
import jp.manavista.zenclock.R;

/**
 *
 * AboutDialogFragment
 *
 * Created by atsushi on 2017/02/02.
 */
public class AboutDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final ViewGroup viewGroup = null;
        View aboutView = getActivity().getLayoutInflater().inflate(R.layout.dialog_about, viewGroup);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(getResources().getString(R.string.app_name))
                .setView(aboutView)
                .setMessage(getResources().getString(R.string.dialog_about_version) + getResources().getString(R.string.space_half) + BuildConfig.VERSION_NAME + "\n" + "copyright 2017, manavista.")
                .setPositiveButton(getResources().getString(R.string.dialog_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /* no description */
                    }
                });

        return builder.create();
    }
}
