package com.cyno.alarm.models;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by hp on 02-07-2016.
 */
public class WeatherCodes {


    /**
     * code : 1000
     * day : Sunny
     * night : Clear
     * icon : 113
     * languages : [{"lang_name":"Arabic","lang_iso":"ar","day_text":"مشمس","night_text":"صافي"},{"lang_name":"Bengali","lang_iso":"bn","day_text":"সূর্যোজ্জ্বল","night_text":"পরিষ্কার"},{"lang_name":"Bulgarian","lang_iso":"bg","day_text":"Слънчево","night_text":"Ясно"},{"lang_name":"Chinese Simplified","lang_iso":"zh","day_text":"晴天","night_text":"晴朗"},{"lang_name":"Chinese Traditional","lang_iso":"zh_tw","day_text":"晴天","night_text":"晴朗"},{"lang_name":"Czech","lang_iso":"cs","day_text":"Slunečno","night_text":"Jasno"},{"lang_name":"Dutch","lang_iso":"nl","day_text":"Zonnig","night_text":"Helder"},{"lang_name":"Finnish","lang_iso":"fi","day_text":"Aurinkoinen","night_text":"Pilvetön"},{"lang_name":"French","lang_iso":"fr","day_text":"Ensoleillé","night_text":"Clair"},{"lang_name":"German","lang_iso":"de","day_text":"Sonnig","night_text":"Klar"},{"lang_name":"Greek","lang_iso":"el","day_text":"Ηλιόλουστη/ο","night_text":"Αίθριος"},{"lang_name":"Hindi","lang_iso":"hi","day_text":"धूप वाला","night_text":"साफ"},{"lang_name":"Hungarian","lang_iso":"hu","day_text":"Napos idő","night_text":"Felhőtlen"},{"lang_name":"Italian","lang_iso":"it","day_text":"Soleggiato","night_text":"Sereno"},{"lang_name":"Japanese","lang_iso":"ja","day_text":"晴れ","night_text":"快晴"},{"lang_name":"Javanese","lang_iso":"jv","day_text":"Srengenge Sumunar","night_text":"Resik"},{"lang_name":"Korean","lang_iso":"ko","day_text":"맑음","night_text":"화창함"},{"lang_name":"Mandarin","lang_iso":"zh_cmn","day_text":"晴天","night_text":"晴朗"},{"lang_name":"Marathi","lang_iso":"mr","day_text":"लख्ख","night_text":"स्वच्छ"},{"lang_name":"Polish","lang_iso":"pl","day_text":"Słonecznie","night_text":"Bezchmurnie"},{"lang_name":"Portuguese","lang_iso":"pt","day_text":"Sol","night_text":"Céu limpo"},{"lang_name":"Punjabi","lang_iso":"pa","day_text":"ਧੁੱਪਦਾਰ","night_text":"ਸਾਫ"},{"lang_name":"Romanian","lang_iso":"ro","day_text":"Soare","night_text":"Senin"},{"lang_name":"Russian","lang_iso":"ru","day_text":"Солнечно","night_text":"Ясно"},{"lang_name":"Serbian","lang_iso":"sr","day_text":"Sunčano","night_text":"Vedro"},{"lang_name":"Sinhalese","lang_iso":"si","day_text":"හිරු එළිය සහිත","night_text":"පැහැදිලි"},{"lang_name":"Slovak","lang_iso":"sk","day_text":"Slnečno","night_text":"Jasno"},{"lang_name":"Spanish","lang_iso":"es","day_text":"Soleado","night_text":"Despejado"},{"lang_name":"Swedish","lang_iso":"sv","day_text":"Soligt","night_text":"Klart"},{"lang_name":"Tamil","lang_iso":"ta","day_text":"வெயில்","night_text":"தெளிவு"},{"lang_name":"Telugu","lang_iso":"te","day_text":"ఎండకాయడం","night_text":"స్పష్టంగా ఉండటం"},{"lang_name":"Turkish","lang_iso":"tr","day_text":"Güneşli","night_text":"Açık"},{"lang_name":"Ukrainian","lang_iso":"uk","day_text":"Сонячно","night_text":"Ясно"},{"lang_name":"Urdu","lang_iso":"ur","day_text":"دھوپ والا","night_text":"شفاف"},{"lang_name":"Vietnamese","lang_iso":"vi","day_text":"Nhiều nắng","night_text":"Trời quang"},{"lang_name":"Wu (Shanghainese)","lang_iso":"zh_wuu","day_text":"贴金","night_text":"贴起好"},{"lang_name":"Xiang","lang_iso":"zh_hsn","day_text":"有阳光","night_text":"晴朗"},{"lang_name":"Yue (Cantonese)","lang_iso":"zh_yue","day_text":"晴天","night_text":"晴朗"},{"lang_name":"Zulu","lang_iso":"zu","day_text":"Kuyashisa","night_text":"Licwathile"}]
     */

    private int code;
    private String day;
    private String night;
    /**
     * lang_name : Arabic
     * lang_iso : ar
     * day_text : مشمس
     * night_text : صافي
     */

    private List<LanguagesModel> languages;

    public static WeatherCodes[] objectFromData(String str) {

        return new Gson().fromJson(str, WeatherCodes[].class);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getNight() {
        return night;
    }

    public void setNight(String night) {
        this.night = night;
    }

    public List<LanguagesModel> getLanguages() {
        return languages;
    }

    public void setLanguages(List<LanguagesModel> languages) {
        this.languages = languages;
    }

    public static class LanguagesModel {
        private String lang_iso;
        private String day_text;
        private String night_text;

        public static LanguagesModel objectFromData(String str) {

            return new Gson().fromJson(str, LanguagesModel.class);
        }

        public String getLang_iso() {
            return lang_iso;
        }

        public void setLang_iso(String lang_iso) {
            this.lang_iso = lang_iso;
        }

        public String getDay_text() {
            return day_text;
        }

        public void setDay_text(String day_text) {
            this.day_text = day_text;
        }

        public String getNight_text() {
            return night_text;
        }

        public void setNight_text(String night_text) {
            this.night_text = night_text;
        }
    }
}
