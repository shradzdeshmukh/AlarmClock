package com.cyno.alarm.models;

import android.graphics.Color;
import android.util.Log;

import com.cyno.alarm.color_picker.Palette;
import com.mattyork.colours.Colour;

/**
 * Created by hp on 24-07-2016.
 */
public class ThemeModel {
    private int backgroundColor ;
    private int digitsgroundColor ;

    public ThemeModel(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        this.digitsgroundColor = getContrast(backgroundColor);

    }

    private int getContrast(int backgroundColor) {
        if(Color.red(backgroundColor) + Color.green(backgroundColor)+Color.blue(backgroundColor) > 500)
            return Colour.BLACK;
        return Color.WHITE;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getDigitsgroundColor() {
        return digitsgroundColor;
    }

    public void setDigitsgroundColor(int digitsgroundColor) {
        this.digitsgroundColor = digitsgroundColor;
    }
}