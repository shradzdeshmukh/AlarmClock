package com.cyno.alarm.models;

import com.google.gson.Gson;

/**
 * Created by hp on 02-07-2016.
 */
public class Weather {

    /**
     * name : Poona
     * region : Maharashtra
     * country : India
     * lat : 18.52
     * lon : 73.86
     * tz_id : Asia/Kolkata
     * localtime_epoch : 1467493439
     * localtime : 2016-07-02 21:03
     */

    private LocationModel location;
    /**
     * last_updated_epoch : 1467492539
     * last_updated : 2016-07-02 20:48
     * temp_c : 24.2
     * temp_f : 75.6
     * is_day : 0
     * condition : {"text":"Light drizzle","icon":"//cdn.apixu.com/weather/64x64/night/266.png","code":1153}
     * wind_mph : 7.4
     * wind_kph : 11.9
     * wind_degree : 240
     * wind_dir : WSW
     * pressure_mb : 1004.0
     * pressure_in : 30.1
     * precip_mm : 0.4
     * precip_in : 0.02
     * humidity : 94
     * cloud : 89
     * feelslike_c : 26.8
     * feelslike_f : 80.2
     */

    private CurrentModel current;

    public static Weather objectFromData(String str) {

        return new Gson().fromJson(str, Weather.class);
    }

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(LocationModel location) {
        this.location = location;
    }

    public CurrentModel getCurrent() {
        return current;
    }

    public void setCurrent(CurrentModel current) {
        this.current = current;
    }

    public static class LocationModel {
        private String name;

        public static LocationModel objectFromData(String str) {

            return new Gson().fromJson(str, LocationModel.class);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class CurrentModel {
        private double temp_c;
        private double temp_f;
        private int is_day;
        /**
         * text : Light drizzle
         * icon : //cdn.apixu.com/weather/64x64/night/266.png
         * code : 1153
         */

        private ConditionModel condition;
        private double feelslike_c;
        private double feelslike_f;

        public static CurrentModel objectFromData(String str) {

            return new Gson().fromJson(str, CurrentModel.class);
        }

        public double getTemp_c() {
            return temp_c;
        }

        public void setTemp_c(double temp_c) {
            this.temp_c = temp_c;
        }

        public double getTemp_f() {
            return temp_f;
        }

        public void setTemp_f(double temp_f) {
            this.temp_f = temp_f;
        }

        public int getIs_day() {
            return is_day;
        }

        public void setIs_day(int is_day) {
            this.is_day = is_day;
        }

        public ConditionModel getCondition() {
            return condition;
        }

        public void setCondition(ConditionModel condition) {
            this.condition = condition;
        }

        public double getFeelslike_c() {
            return feelslike_c;
        }

        public void setFeelslike_c(double feelslike_c) {
            this.feelslike_c = feelslike_c;
        }

        public double getFeelslike_f() {
            return feelslike_f;
        }

        public void setFeelslike_f(double feelslike_f) {
            this.feelslike_f = feelslike_f;
        }

        public static class ConditionModel {
            private int code;

            public static ConditionModel objectFromData(String str) {

                return new Gson().fromJson(str, ConditionModel.class);
            }

            public int getCode() {
                return code;
            }

            public void setCode(int code) {
                this.code = code;
            }
        }
    }
}
