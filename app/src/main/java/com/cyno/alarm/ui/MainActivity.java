package com.cyno.alarm.ui;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.hardware.Camera;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cyno.alarm.UtilsAndConstants.Utils;
import com.cyno.alarm.alarm_logic.AlarmReceiver;
import com.cyno.alarm.alarm_logic.AlarmService;
import com.cyno.alarm.alarm_logic.SnoozeAlarmReceiver;
import com.cyno.alarm.alarm_logic.WakeLocker;
import com.cyno.alarm.database.PicCodesTable;
import com.cyno.alarm.database.SummaryCodesTable;
import com.cyno.alarm.in_app_utils.InAppListnerImpl;
import com.cyno.alarm.models.Alarm;
import com.cyno.alarm.models.Weather;
import com.cyno.alarm.models.WeatherCodes;
import com.cyno.alarm.networking.GetWeatherNetworking;
import com.cyno.alarm.sync.SyncUtils;
import com.cyno.alarmclock.R;
import com.squareup.seismic.ShakeDetector;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimerTask;


//TODO fix bug while alarm ringing press back... next time doesnt open app.. just rings alarm

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener, ShakeDetector.Listener{



    private static final long TIME_SHOW_BOTTOM_LAYOUT = 7000;
    public static final String ACTION_RING_ALARM = "ringalarm";
    public static final String KEY_ALARM_ID = "alarm_id";
    private static final long [] VIBRATOR_PATTERN = new long[]{0,2000,500,1000,500,1000,5000,100};
    private static final String STATE_MEDIA_PLAYING = "ringing";
    private static final String STATE_DURATION = "duration";
    private static final String STATE_ALARM_ID = "alarm_id";
    private static final String STATE_IS_ALARM_RINGING = "alarm_rining";
    public static final String FONT = "digital.ttf";
    private static final String ACTION_NULL = "NULL";
    private static final String FONT_WEATHER = "weather.ttf";

    public static final int WEATHER_UPDATED = 1000;
    private static final java.lang.String WEATHER_CODES_FILE = "weather_codes.json";
    private static final String KEY_ARE_WEATHER_CODES_DUMPED = "weather_codes_dumped";
    private static final String KEY_ARE_PIC_CODES_DUMPED = "key_codes_dumped";
    private static final int REQ_CODE_SNOOZE = 2133;


    final SimpleDateFormat HOUR_MIN_24_HOUR = new SimpleDateFormat("HH:mm", Locale.getDefault());
    final SimpleDateFormat HOUR_MIN_12_HOUR = new SimpleDateFormat("hh:mm", Locale.getDefault());
    final SimpleDateFormat AM_PM_FORMAT = new SimpleDateFormat("a", Locale.getDefault());
    final SimpleDateFormat SECONDS = new SimpleDateFormat("ss", Locale.getDefault());
    final SimpleDateFormat DATE = new SimpleDateFormat("dd MMM yy", Locale.getDefault());
    final SimpleDateFormat DAY = new SimpleDateFormat("EEE", Locale.getDefault());

    Calendar mCalendar = Calendar.getInstance();
    private TextView mTextViewTimeHourMin;
    private TextView mTextViewDay;
    private TextView mTextViewDate;
    private TextView mTextViewSeconds;
    private TextView mTextAmPm;

    private View bottomLayout;
    private static MediaPlayer mediaPlayer;
    private Camera camera;
    private Camera.Parameters params;
    private boolean isFlashOn;
    private View bottomLayoutAlarmRinging;
    private boolean isAlarmRinging;
    private Alarm mRingingAlarm;
    private static Vibrator mVibrator;
    private int originalVolume;
    private int currentAlarmId = -1;
    private int currentToneDuration = -1;
    private boolean isIntentionalBack;
    private static CountDownTimer count;
    private boolean hasFlash;
    private boolean hasAccelerometer;
    private boolean bSnoozed;
    private InAppListnerImpl inAppListner;
    private TextView mTextCityName;
    private TextView mTextCurrentTemp;
    private TextView mTextMinMax;
    private TextView mTextWeatherOverview;
    private TextView mTextWeatherIcon;
    private PendingIntent mSnoozePendingIntent;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case WEATHER_UPDATED:
                    updateWeather();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD  |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_main);

        SyncUtils.setSyncAccount(this);

        hasFlash = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);


        hasAccelerometer = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);


        bottomLayout = findViewById(R.id.bottom_layout);
        bottomLayoutAlarmRinging = findViewById(R.id.bottom_layout_alarm_ringing);
        mTextViewTimeHourMin = (TextView) findViewById(R.id.text1);
        mTextViewDay = (TextView) findViewById(R.id.tv_day);
        mTextViewDate = (TextView) findViewById(R.id.tv_date);
        mTextViewSeconds = (TextView) findViewById(R.id.tv_seconds);
        mTextAmPm= (TextView) findViewById(R.id.tv_am_pm);
        mTextCityName= (TextView) findViewById(R.id.tv_city_name);
        mTextCurrentTemp= (TextView) findViewById(R.id.tv_current_temp);
        mTextMinMax= (TextView) findViewById(R.id.tv_min_max);
        mTextWeatherOverview= (TextView) findViewById(R.id.tv_overview_weather);
        mTextWeatherIcon = (TextView) findViewById(R.id.tv_weather_icon);

        ImageView ivAddAlarm = (ImageView) findViewById(R.id.iv_add_alarm);
        ImageView ivSettings = (ImageView) findViewById(R.id.iv_settings);
        ImageView ivTorrch = (ImageView) findViewById(R.id.iv_torch);
        ImageView ivSnooze = (ImageView) findViewById(R.id.iv_snooze_alarm);
        ImageView ivStopAlarm = (ImageView) findViewById(R.id.iv_stop_alarm);

        ivAddAlarm.setOnClickListener(this);
        ivSettings.setOnClickListener(this);
        ivTorrch.setOnClickListener(this);
        ivStopAlarm.setOnClickListener(this);
        ivSnooze.setOnClickListener(this);

        Typeface tf = Typeface.createFromAsset(getAssets(),FONT);
        Typeface tfWeather = Typeface.createFromAsset(getAssets(),FONT_WEATHER);

        mTextViewTimeHourMin.setTypeface(tf);
        mTextViewDay.setTypeface(tf);
        mTextViewSeconds.setTypeface(tf);
        mTextViewDate.setTypeface(tf);
        mTextAmPm.setTypeface(tf);
        mTextAmPm.setTypeface(tf);
        mTextCurrentTemp.setTypeface(tf);
        mTextCityName.setTypeface(tf);
        mTextMinMax.setTypeface(tf);
        mTextWeatherOverview.setTypeface(tf);
        mTextWeatherIcon.setTypeface(tfWeather);


        restoreAlarmState();

        Intent mIntent = getIntent();

        Log.d("snooze" , "act created");
        if(mIntent != null)
            Log.d("snooze" , "action = "+mIntent.getAction());
        else
            Log.d("snooze" , "null intent");
        Log.d("snooze" , "is ringing = "+isAlarmRinging);
        Log.d("snooze" , "is snoozd= "+bSnoozed);


        if(mIntent != null && mIntent.getAction().equals(ACTION_RING_ALARM) ){
            Log.d("snooze" , "about to ring");
            ringAlarm(mIntent.getIntExtra(KEY_ALARM_ID, -1));
//            mIntent.removeExtra(KEY_ALARM_ID );
            mIntent.setAction(ACTION_NULL);
        }


        if(Build.VERSION.SDK_INT < 21)
            getCamera();

        updateWeather();
        GetWeatherNetworking networking = new GetWeatherNetworking(this , true , handler);
        networking.makeRequest(Weather.class);

        getWeatherCodes();
        getPicCodes();
    }



    @Override
    protected void onNewIntent(Intent mIntent) {
        super.onNewIntent(mIntent);
        if(mIntent != null && mIntent.getAction().equals(ACTION_RING_ALARM)&& (!isAlarmRinging || bSnoozed)){
            Log.d("alarm","new intent ");
            ringAlarm(mIntent.getIntExtra(KEY_ALARM_ID , -1));
        }

    }

    private void snoozeAlarm(){
        bSnoozed = true;
        try {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
        }catch(IllegalStateException ex){

        }
        if(mVibrator != null && mVibrator.hasVibrator())
            mVibrator.cancel();
     /*   Intent service = new Intent(this, AlarmService.class);
        service.setAction(AlarmService.ACTION_SNOOZE_ALARM);
        service.putExtra(AlarmService.KEY_ALARM_ID, mRingingAlarm.getId());
        startService(service);*/

        setAlarmSnoozeDuration(mRingingAlarm);
        setOriginalVolume();

        Toast.makeText(this , "snoozed for "+PreferenceManager.getDefaultSharedPreferences(this).
                getInt(SettingsActivity.PREF_SNOOZE_INTERVAL, 10)+" minutes" , Toast.LENGTH_LONG).show();
    }

    private void stopAlarm() {
        bSnoozed = false;
        try {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
        }catch(IllegalStateException ex){

        }
        bottomLayoutAlarmRinging.setVisibility(View.INVISIBLE);
        isAlarmRinging = false;
        Intent service = new Intent(this, AlarmService.class);
        service.setAction(AlarmService.ACTION_STOP_ALARM);
        service.putExtra(AlarmService.KEY_ALARM_ID, mRingingAlarm.getId());
        startService(service);
        if(mVibrator != null && mVibrator.hasVibrator())
            mVibrator.cancel();
        Alarm.storeLocally(mRingingAlarm, this);
        mRingingAlarm = null;
        currentToneDuration = -1;
        setOriginalVolume();
        WakeLocker.release();
        AlarmManager mManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        mManager.cancel(mSnoozePendingIntent);
    }


    private void ringAlarm(int id) {
        Log.d("alarm","id = "+id);
        bottomLayoutAlarmRinging.setVisibility(View.VISIBLE);
        mRingingAlarm = Alarm.getAlarm(id , this);
        if(mRingingAlarm.getRingtone() != null)
            playMediaSound(Uri.parse(mRingingAlarm.getRingtone()));
        if(hasAccelerometer) {
            SensorManager sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
            ShakeDetector sd = new ShakeDetector(this);
            sd.start(sensorMgr);
        }
        isAlarmRinging = true;

        if(mRingingAlarm.isVibrate()){
            if(mVibrator == null)
                mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

            mVibrator.vibrate(VIBRATOR_PATTERN , 0);
        }

        currentAlarmId = id;
    }

    private void playMediaSound(Uri uri) {
//        if (mediaPlayer.isPlaying()) {
//            mediaPlayer.stop();
//        }
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);

        setMaxVolume();


        try {
            mediaPlayer.setDataSource(getApplicationContext(), uri);
            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
            if(currentToneDuration != -1) {
                mediaPlayer.seekTo(currentToneDuration);
                currentToneDuration = -1;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "error ", Toast.LENGTH_LONG).show();
        }
        mediaPlayer.start();

        setTelephoneListner();
    }

    private void setTelephoneListner() {
        TelephonyManager telephonyManager = (TelephonyManager) this
                .getSystemService(Context.TELEPHONY_SERVICE);

        PhoneStateListener phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING:
                        Log.d(getClass().getSimpleName(), "Incoming call: "
                                + incomingNumber);
                        try {
                            if(mediaPlayer != null)
                                mediaPlayer.pause();
                        } catch (IllegalStateException e) {

                        }
                        break;
                    case TelephonyManager.CALL_STATE_IDLE:
                        Log.d(getClass().getSimpleName(), "Call State Idle");
                        try {
                            if(mediaPlayer != null && isAlarmRinging && !bSnoozed)
                                mediaPlayer.start();
                            else if(bSnoozed)
                                restoreAlarmState();
                        } catch (IllegalStateException e) {

                        }
                        break;
                }
                super.onCallStateChanged(state, incomingNumber);
            }
        };


        telephonyManager.listen(phoneStateListener,
                PhoneStateListener.LISTEN_CALL_STATE);

    }


    private void getCamera() {
        if (camera == null && hasFlash) {
            try {
                camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
                params = camera.getParameters();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    private void turnOnFlash() {
        if(!hasFlash){
            Toast.makeText(this , R.string.noflash, Toast.LENGTH_LONG).show();
            return;
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            turnOnFlashMarshmallow();
            return;
        }    if (!isFlashOn) {
            if (camera == null || params == null) {
                return;
            }
            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isFlashOn = true;
        }

    }

    private void turnOffFlash() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            turnOffFlashMarshmallow();
            return;
        }if (isFlashOn) {
            if (camera == null || params == null) {
                return;
            }
            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            isFlashOn = false;
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void turnOnFlashMarshmallow(){
        CameraManager mng = (CameraManager) getSystemService(CAMERA_SERVICE);
        try {
            mng.setTorchMode(mng.getCameraIdList()[0] , true);
            isFlashOn  = true;
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void turnOffFlashMarshmallow(){
        CameraManager mng = (CameraManager) getSystemService(CAMERA_SERVICE);
        try {
            mng.setTorchMode(mng.getCameraIdList()[0] , false);
            isFlashOn  = false;
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void setMaxVolume() {
//        AudioManager amanager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
//        originalVolume = amanager.getStreamVolume(AudioManager.STREAM_MUSIC);
//        int maxVolume = amanager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
//        amanager.setStreamVolume(AudioManager.STREAM_ALARM, maxVolume, 0);
        mediaPlayer.setVolume(1,1);

    }

    private void setOriginalVolume(){
//        final AudioManager mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
//        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, originalVolume, 0);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_layout:
                if(!isAlarmRinging) {
                    bottomLayout.setVisibility(View.VISIBLE);
                    bottomLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            bottomLayout.setVisibility(View.INVISIBLE);
                        }
                    }, TIME_SHOW_BOTTOM_LAYOUT);
                }
                break;
            case R.id.iv_add_alarm:
                startActivity(new Intent(this, AddAlarmActivity.class));
                break;
            case R.id.iv_torch:
                if(!isFlashOn)
                    turnOnFlash();
                else
                    turnOffFlash();
                break;
            case R.id.iv_snooze_alarm:
                snoozeAlarm();
                break;
            case R.id.iv_stop_alarm:
                stopAlarm();
                break;
            case R.id.iv_settings:
                startActivity(new Intent(this , SettingsActivity.class));
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mVibrator == null)
            mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        setClockBackGround();
        setDigitsColor();
        startClock();
    }

    private void startClock(){
        final boolean is24Hour = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(SettingsActivity.PREF_IS_24HOUR, true);
        if(is24Hour)
            mTextAmPm.setVisibility(View.INVISIBLE);
        else
            mTextAmPm.setVisibility(View.VISIBLE);

        count = new CountDownTimer(24*3600*1000, 1000) {
            public void onTick(long milliseconds) {
                mCalendar.setTimeInMillis(System.currentTimeMillis());
                if(is24Hour)
                    mTextViewTimeHourMin.setText(HOUR_MIN_24_HOUR.format(mCalendar.getTime()));
                else {
                    mTextViewTimeHourMin.setText(HOUR_MIN_12_HOUR.format(mCalendar.getTime()));
                    mTextAmPm.setText(AM_PM_FORMAT.format(mCalendar.getTime()));
                }
                mTextViewDate.setText(DATE.format(mCalendar.getTime()));
                mTextViewSeconds.setText(SECONDS.format(mCalendar.getTime()));
                mTextViewDay.setText(DAY.format(mCalendar.getTime()));
            }

            @Override
            public void onFinish() {

            }
        };
        count.start();

        if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean(SettingsActivity.PREF_KEEP_SCREEN_ON, true))
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        else
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

    private void setDigitsColor() {
        int color = PreferenceManager.getDefaultSharedPreferences(this).getInt(SettingsActivity.PREF_CLOCK_DIGIT_COLOR,
                Color.WHITE);
        mTextViewDate.setTextColor(color);
        mTextViewDay.setTextColor(color);
        mTextViewSeconds.setTextColor(color);
        mTextViewTimeHourMin.setTextColor(color);
        mTextAmPm.setTextColor(color);
        mTextCityName.setTextColor(color);
        mTextCurrentTemp.setTextColor(color);
        mTextWeatherOverview.setTextColor(color);
        mTextMinMax.setTextColor(color);
        mTextWeatherIcon.setTextColor(color);
    }

    private void setClockBackGround() {
        View rootView = findViewById(R.id.main_layout);
        ShapeDrawable.ShaderFactory sf = new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {
                int color = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).
                        getInt(SettingsActivity.PREF_CLOCK_BACKGROUND_COLOR, Color.BLUE);

                LinearGradient lg = new LinearGradient(0, 0, width, height,
                        new int[]{Color.BLACK,color, color, color, Color.BLACK, },
//                        new float[]{0,0.5f,.55f,1}, Shader.TileMode.REPEAT);
                        new float[]{0.1f,0.4798f,0.48f,0.5f,0.75f}, Shader.TileMode.REPEAT);
                return lg;
            }
        };

        PaintDrawable p=new PaintDrawable();
        p.setShape(new RectShape());
        p.setShaderFactory(sf);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            rootView.setBackground(p);
        }
        rootView.setOnClickListener(this);

    }

    @Override
    protected void onStop() {
        super.onStop();

        if (camera != null) {
            camera.release();
            camera = null;
        }

        count.cancel();

        if(isAlarmRinging)
            snoozeAlarm();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isAlarmRinging) {
            if (mediaPlayer != null) {
                if (!isAlarmRinging)
                    mediaPlayer.release();
                else {
                    if (!isIntentionalBack)
                        storeAlarmState();
                    else
                        snoozeAlarm();
                }
            } else if (isIntentionalBack)
                snoozeAlarm();
            else
                storeAlarmState();
        }
        setOriginalVolume();
        if(mVibrator != null && mVibrator.hasVibrator()){
            mVibrator.cancel();
        }


    }

    private void storeAlarmState(){
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(this).edit();
        try {
            if(mediaPlayer != null && mediaPlayer.isPlaying()) {
                edit.putBoolean(STATE_MEDIA_PLAYING, mediaPlayer.isPlaying());
                edit.putInt(STATE_DURATION, mediaPlayer.getCurrentPosition());
                mediaPlayer.release();
            }
            edit.putBoolean(STATE_IS_ALARM_RINGING, isAlarmRinging);
            edit.putInt(STATE_ALARM_ID, currentAlarmId);
            edit.commit();
        }catch (IllegalStateException ex){

        }
    }

    private void restoreAlarmState(){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        if(pref.getBoolean(STATE_IS_ALARM_RINGING,false)){
            isAlarmRinging = true;
            bottomLayoutAlarmRinging.setVisibility(View.VISIBLE);
            currentAlarmId = pref.getInt(STATE_ALARM_ID,-1);
            mRingingAlarm = Alarm.getAlarm(currentAlarmId, this);

            if(pref.getBoolean(STATE_MEDIA_PLAYING,false)) {
                currentToneDuration = pref.getInt(STATE_DURATION, -1);
                ringAlarm(currentAlarmId);
            }
            clearAlarmState();
        }
    }

    private void clearAlarmState(){
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(this).edit();
        edit.putBoolean(STATE_MEDIA_PLAYING, false);
        edit.putBoolean(STATE_IS_ALARM_RINGING, false);
        edit.putInt(STATE_DURATION, -1);
        edit.putInt(STATE_ALARM_ID, -1);
        edit.commit();
    }

    @Override
    public void hearShake() {
        if(isAlarmRinging)
            snoozeAlarm();
    }

    @Override
    public void onBackPressed() {
        isIntentionalBack = true;
        super.onBackPressed();
    }

    class SnoozeTimerTask extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(new Runnable(){
                @Override
                public void run() {
                    MainActivity.this.ringAlarm( mRingingAlarm.getId());
                }});
        }

    }

    private void updateWeather() {
        mTextCityName.setText(Utils.getCurrentLocation(this));
        mTextCurrentTemp.setText(String.valueOf(Utils.getCurrentTemperatureC(this)));
        mTextMinMax.setText(getString(R.string.avg)  +" "+
                String.valueOf(Utils.getTemperatureC(this)));
        mTextWeatherOverview.setText(Utils.getOverView(this , Utils.getWeatherCode(this) , Locale.getDefault().getLanguage()));
        mTextWeatherIcon.setText(Utils.getPic(this , Utils.getWeatherCode(this) , Utils.isDay(this)));
    }


    private void getWeatherCodes() {
        if(!PreferenceManager.getDefaultSharedPreferences(this).getBoolean(KEY_ARE_WEATHER_CODES_DUMPED, false)) {

            PreferenceManager.getDefaultSharedPreferences(this).
                    edit().putBoolean(KEY_ARE_WEATHER_CODES_DUMPED, true).commit();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.d("codes", "geting codes");
                    WeatherCodes[] codes = WeatherCodes.objectFromData(getJSONString());
                    Log.d("codes", "got codes");
                    for (WeatherCodes code : codes) {
                        ContentValues values = new ContentValues();
                        for (int index = 0; index < code.getLanguages().size(); index++) {
                            values.put(SummaryCodesTable.COL_UNIQUE_CODE, code.getCode());
                            WeatherCodes.LanguagesModel lang = code.getLanguages().get(index);
                            values.put(SummaryCodesTable.COL_LANGUAGE_CODE, lang.getLang_iso());
                            values.put(SummaryCodesTable.COL_DAY_SUMMARY, lang.getDay_text());
                            values.put(SummaryCodesTable.COL_NIGHT_SUMMARY, lang.getNight_text());
                            getContentResolver().insert(SummaryCodesTable.CONTENT_URI, values);
                            values.clear();
                        }
                        values.put(SummaryCodesTable.COL_UNIQUE_CODE, code.getCode());
                        values.put(SummaryCodesTable.COL_LANGUAGE_CODE, "en");
                        values.put(SummaryCodesTable.COL_DAY_SUMMARY, code.getDay());
                        values.put(SummaryCodesTable.COL_NIGHT_SUMMARY, code.getNight());
                        getContentResolver().insert(SummaryCodesTable.CONTENT_URI, values);
                    }
                    Log.d("codes", "got codes");
                }
            }).start();
        }
    }

    private String getJSONString() {
        String json = null;
        try {
            InputStream is = getAssets().open(WEATHER_CODES_FILE);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "unicode");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    private void getPicCodes(){
        if(!PreferenceManager.getDefaultSharedPreferences(this).getBoolean(KEY_ARE_PIC_CODES_DUMPED, false)) {

            PreferenceManager.getDefaultSharedPreferences(this).
                    edit().putBoolean(KEY_ARE_PIC_CODES_DUMPED , true).commit();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.d("codes", "geting codes");
                    WeatherCodes[] codes = WeatherCodes.objectFromData(getJSONString());
                    for(WeatherCodes code : codes){
                        ContentValues values = new ContentValues();
                        values.put(PicCodesTable.COL_UNIQUE_CODE , code.getCode());
                        values.put(PicCodesTable.COL_DAY_PIC , Utils.getDayPicCode(code.getCode()));
                        values.put(PicCodesTable.COL_NIGHT_PIC, Utils.getNightPicCode(code.getCode()));
                        getContentResolver().insert(PicCodesTable.CONTENT_URI,values);
                    }
                }
            }).start();
        }
    }

    private void setAlarmSnoozeDuration(Alarm mAlarm){
        Intent mIntent = new Intent(this,SnoozeAlarmReceiver.class);
        mIntent.putExtra(SnoozeAlarmReceiver.ALARM_ID , mAlarm.getId());
        mSnoozePendingIntent = PendingIntent.getBroadcast(this, REQ_CODE_SNOOZE ,
                mIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int lastTime = 1000 * 60* PreferenceManager.getDefaultSharedPreferences(this).
         getInt(SettingsActivity.PREF_SNOOZE_INTERVAL, 10);
        if(Build.VERSION.SDK_INT >= 23)
            mManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+lastTime,
                    mSnoozePendingIntent);
        else
            mManager.set(AlarmManager.RTC_WAKEUP, lastTime, mSnoozePendingIntent);

    }
}
