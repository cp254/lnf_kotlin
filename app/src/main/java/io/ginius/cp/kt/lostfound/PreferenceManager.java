package io.ginius.cp.kt.lostfound;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;

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

    public static final String SEARCH_HISTORY = "sh";

    public static final String DOC_ID = "doc_id";
    public static final String DOC_FNAME = "doc_fname";
    public static final String DOC_LNAME = "doc_lname";
    public static final String DOC_TYPE = "doc_type";
    public static final String DOC_NAME = "doc_name";
    public static final String DOC_REF = "doc_ref";
    public static final String DOC_DETAILS = "doc_details";
    public static final String PICKUPLOCATION = "pick_up_location";
    public static final String PICKEDBY = "picked_by";
    public static final String PICKEDBYCONTACT = "picked_by_contact";
    public static final String LAT = "latitude";
    public static final String LON = "longitude";
    public static final String DOC_BASE64 = "doc_photo";
    public static final String DATEFOUND = "date_found";
    public static final String COSTSUB = "cost_sub";
    public static final String COSTNOT = "cost_not";








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
    public void setSearchHistrory(String searchHistory) {
        editor.putString(SEARCH_HISTORY, searchHistory);
        editor.commit();

//        SharedPreferences pref = this.getSharedPreferences(getString(R.string.eapps_pref), MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putString(getString(R.string.menudata), JmenuData.toString());
//        editor.putString(getString(R.string.widgetdata), JwidgetData.toString());
//        editor.putString(getString(R.string.favdata), JfavData.toString());
//        editor.putString(getString(R.string.acctdata), JacctData.toString());
//        editor.putString(getString(R.string.userdata), JuserData.toString());
//        editor.putString(getString(R.string.instdata), JinstData.toString());

//        SharedPreferences pref = this.getSharedPreferences(getString(R.string.eapps_pref), MODE_PRIVATE);
//        menuData = new JSONArray( pref.getString( getString(R.string.menudata), null));
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public Boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGGED_IN, true);
    }


}
