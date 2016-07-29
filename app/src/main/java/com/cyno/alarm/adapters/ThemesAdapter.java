package com.cyno.alarm.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cyno.alarm.models.ThemeModel;
import com.cyno.alarm.ui.SettingsActivity;
import com.cyno.alarmclock.R;

import java.util.ArrayList;

/**
 * Created by hp on 24-07-2016.
 */
public class ThemesAdapter extends BaseAdapter {

    private final Context context;
    private final ArrayList<ThemeModel> items;

    public ThemesAdapter(Context context , ArrayList<ThemeModel> list){
        this.context = context;
        this.items = list;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public ThemeModel getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = View.inflate(context , R.layout.grid_item_themes , null);
            holder.background = convertView.findViewById(R.id.background);
            convertView.setTag(R.string.view_holder , holder);
        }else{
            holder = (ViewHolder) convertView.getTag(R.string.view_holder);
        }
        setBackgroundColor(holder.background ,getItem(position).getBackgroundColor());
        return convertView;
    }


    private static class ViewHolder{
        private View background;
    }


    private void setBackgroundColor(View view , final int color){
        ShapeDrawable.ShaderFactory sf = new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {
                LinearGradient lg = new LinearGradient(0, 0, width, height,
                        new int[]{Color.BLACK,color, color, color, Color.BLACK, },
//                        new float[]{0,0.5f,.55f,1}, Shader.TileMode.REPEAT);
                        new float[]{0.03f,0.4798f,0.48f,0.5f,0.99f}, Shader.TileMode.REPEAT);
                return lg;
            }
        };

        PaintDrawable p=new PaintDrawable();
        p.setShape(new RectShape());
        p.setShaderFactory(sf);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(p);
        }
    }
}
