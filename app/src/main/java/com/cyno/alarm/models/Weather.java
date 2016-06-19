package com.cyno.alarm.models;

import com.google.gson.Gson;

public class Weather{

    /**
     * last_updated_epoch : 1466209387
     * last_updated : 2016-06-18 00:23
     * temp_c : 24.1
     * temp_f : 75.4
     * is_day : 0
     * condition : {"text":"Partly Cloudy ","icon":"//cdn.apixu.com/weather/64x64/night/116.png","code":1003}
     * wind_mph : 13.9
     * wind_kph : 22.3
     * wind_degree : 277
     * wind_dir : W
     * pressure_mb : 1006.0
     * pressure_in : 30.2
     * precip_mm : 0.0
     * precip_in : 0.0
     * humidity : 83
     * cloud : 44
     * feelslike_c : 26.1
     * feelslike_f : 79.0
     */

    private CurrentModel current;

    public static Weather objectFromData(String str) {

        return new Gson().fromJson(str, Weather.class);
    }

    public CurrentModel getCurrent() {
        return current;
    }

    public void setCurrent(CurrentModel current) {
        this.current = current;
    }

    public static class CurrentModel {
        private double temp_c;
        private double temp_f;
        private int is_day;
        /**
         * text : Partly Cloudy
         * icon : //cdn.apixu.com/weather/64x64/night/116.png
         * code : 1003
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