package io.ginius.cp.kt.lostfound;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class PreferenceManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    public static final String PREF_NAME = "io.ginius.cp.kt.lostfound";
    public static final String IS_LOGGED_IN = "IsLoggedIn";
    public static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public static final String USER_PHONE_NUMBER = "user_phone_number";
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_EMAIL = "user_email";

    public static final String DOC_ID = "doc_id";
    public static final String DOC_FNAME = "doc_fname";
    public static final String DOC_LNAME = "doc_lname";
    public static final String DOC_TYPE = "doc_type";
    public static final String DOC_NAME = "doc_name";
    public static final String DOC_REF = "doc_ref";
    public static final String DOC_DETAILS = "doc_details";
    public static final String PICKUPLOCATION = "pick_up_location";
    public static final String LAT = "latitude";
    public static final String LON = "longitude";
    public static final String DOC_BASE64 = "doc_photo";







    public PreferenceManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    public void savePrefs(String key, String value){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    //get prefs
    public String loadPrefs(String key, String value){
        String data = pref.getString(key, value);
        return data;
    }

    public void saveBoolean(String key, Boolean value){
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    //get prefs
    public Boolean loadBoolean(String key, Boolean value){
        Boolean data = pref.getBoolean(key, value);
        return data;
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }
    public void setIsLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public Boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGGED_IN, true);
    }


}
