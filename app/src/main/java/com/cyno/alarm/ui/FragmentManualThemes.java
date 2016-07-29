package com.cyno.alarm.ui;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyno.alarm.color_picker.ColorPickerClickListener;
import com.cyno.alarm.color_picker.ColorPickerDialogBuilder;
import com.cyno.alarm.color_picker.ColorPickerView;
import com.cyno.alarm.color_picker.OnColorSelectedListener;
import com.cyno.alarmclock.R;

/**
 * Created by hp on 20-07-2016.
 */
public class FragmentManualThemes extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_manual_themes, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ColorPickerDialogBuilder
                .with(getActivity())
                .lightnessSliderOnly()
                .setTitle(getString(R.string.choose_color))
                .initialColor(Color.WHITE)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                    }
                })
                .setPositiveButton(getString(android.R.string.ok), new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
                        editor.putInt(SettingsActivity.PREF_CLOCK_BACKGROUND_COLOR, selectedColor);
                        editor.commit();
                        getActivity().finish();


                    }
                })
                .setNegativeButton(getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }
}
