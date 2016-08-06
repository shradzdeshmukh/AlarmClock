package com.cyno.alarm.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cyno.alarm.UtilsAndConstants.GAConstants;
import com.cyno.alarm.UtilsAndConstants.Utils;
import com.cyno.alarm.color_picker.ColorPickerClickListener;
import com.cyno.alarm.color_picker.ColorPickerDialogBuilder;
import com.cyno.alarm.color_picker.ColorPickerView;
import com.cyno.alarm.color_picker.OnColorSelectedListener;
import com.cyno.alarmclockpro.R;

/**
 * Created by hp on 24-01-2016.
 */
public class ThemesActivity extends AppCompatActivity implements View.OnClickListener {

    private FrameLayout container;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themes);

        container = (FrameLayout) findViewById(R.id.container);
        findViewById(R.id.bt_choose_theme).setOnClickListener(this);
        setPredefinedFragment();

    }


    @Override
    public void onClick(View v) {
        if(v.getTag() == null){
            v.setTag(true);
            setManualFragment();
        }else if((Boolean) v.getTag()){
            v.setTag(false);
            setPredefinedFragment();
        }else{
            v.setTag(true);
            setManualFragment();
        }
    }

    private void setPredefinedFragment(){
        Utils.trackEvent(this , GAConstants.CATEGORY_THEMES, GAConstants.ACTION_BACKGROUND_DEFINED_THEME_CLICK, "");
        ((Button)findViewById(R.id.bt_choose_theme)).setText(getString(R.string.manual_theme));
        getFragmentManager().beginTransaction().
                replace(R.id.container , new FragmentDefinedThemes()).commit();
    }

    private void setManualFragment(){
        Utils.trackEvent(this , GAConstants.CATEGORY_THEMES, GAConstants.ACTION_BACKGROUND_CUSTOM_THEME_CLICK, "");

        ((Button)findViewById(R.id.bt_choose_theme)).setText(getString(R.string.defied_theme));
        getFragmentManager().beginTransaction().
                replace(R.id.container , new FragmentManualThemes()).commit();
    }
}
