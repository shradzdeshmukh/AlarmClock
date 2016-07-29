package com.cyno.alarm.ui;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.cyno.alarm.adapters.ThemesAdapter;
import com.cyno.alarm.models.ThemeModel;
import com.cyno.alarmclock.R;

import java.util.ArrayList;

/**
 * Created by hp on 20-07-2016.
 */
public class FragmentDefinedThemes extends Fragment implements AdapterView.OnItemClickListener {


    private ThemesAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_defined_themes , null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GridView gvThemes = (GridView) view.findViewById(R.id.gv_themes);
        gvThemes.setAdapter(getGridAdapter());
        gvThemes.setOnItemClickListener(this);
    }

    public BaseAdapter getGridAdapter() {
        adapter = new ThemesAdapter(getActivity(), getThemesList());
        return adapter;
    }

    public ArrayList<ThemeModel> getThemesList() {
        ArrayList<ThemeModel> list = new ArrayList<>();
        int[] colors = getResources().getIntArray(R.array.theme_colors);
        for (int color : colors){
            list.add(new ThemeModel(color));
        }
        return list;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("red" , Color.red(adapter.getItem(position).getBackgroundColor()) + "");
        PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().
                putInt(SettingsActivity.PREF_CLOCK_BACKGROUND_COLOR ,
                        adapter.getItem(position).getBackgroundColor())
                .putInt(SettingsActivity.PREF_CLOCK_DIGIT_COLOR ,
                        adapter.getItem(position).getDigitsgroundColor())
                .commit();
        getActivity().finish();
    }
}
