package com.cyno.alarm.alarm_logic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cyno.alarm.models.Alarm;
import com.cyno.alarm.ui.MainActivity;

/**
 * Created by hp on 13-07-2016.
 */
public class SnoozeAlarmReceiver extends BroadcastReceiver {
    public static final String ALARM_ID = "alarmId";

    @Override
    public void onReceive(Context context, Intent intent) {

        int alarmId = intent.getIntExtra(ALARM_ID, -1);
        Alarm mAlarm = Alarm.getAlarm(alarmId, context);
        if (mAlarm == null) {
            WakeLocker.release();
            return;
        }

        Intent mIntent = new Intent(context, MainActivity.class);
        mIntent.setAction(MainActivity.ACTION_RING_ALARM);
        mIntent.putExtra(MainActivity.KEY_ALARM_ID, mAlarm.getId());
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mIntent);

    }
}
