package io.ginius.cp.kt.lostfound;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "io.ginius.cp.kt.lostfound";
    private static final String IS_LOGGED_IN = "IsFirstTimeLaunch";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    private static final String USER_PHONE_NUMBER = "user_phone_number";
    private static final String USER_ID = "user_id";
    private static final String USER_NAME = "user_name";
    private static final String USER_EMAIL = "user_email";

    private static final String DOC_ID = "doc_id";
    private static final String DOC_FNAME = "doc_id";
    private static final String DOC_LNAME = "doc_lname";
    private static final String DOC_TYPE = "doc_type";
    private static final String DOC_NAME = "doc_name";
    private static final String DOC_REF = "doc_ref";
    private static final String DOC_DETAILS = "doc_details";
    private static final String PICKUPLOCATION = "pick_up_location";
    private static final String LAT = "latitude";
    private static final String LON = "longitude";
    private static final String DOC_BASE64 = "doc_photo";

    public PreferenceManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }
    public void setIsLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn);
        editor.commit();
    }

    public void setUserPhoneNumber(String data) {
        editor.putString(USER_PHONE_NUMBER, data);
        editor.commit();
    }

    public void setUserEmail(String data) {
        editor.putString(USER_EMAIL, data);
        editor.commit();
    }

    public void setUserName(String data) {
        editor.putString(USER_NAME, data);
        editor.commit();
    }

    public void setUserId(String userId) {
        editor.putString(USER_ID, userId);
        editor.commit();
    }

    public void setDocId(String data) {
        editor.putString(DOC_ID, data);
        editor.commit();
    }

    public void setDocFname(String data) {
        editor.putString(DOC_FNAME, data);
        editor.commit();
    }

    public void setDocLname(String data) {
        editor.putString(DOC_LNAME, data);
        editor.commit();
    }

    public void setDocType(String data) {
        editor.putString(DOC_TYPE, data);
        editor.commit();
    }

    public void setDocRef(String data) {
        editor.putString(DOC_REF, data);
        editor.commit();
    }

    public void setDocName(String data) {
        editor.putString(DOC_NAME, data);
        editor.commit();
    }

    public void setDocDetails(String data) {
        editor.putString(DOC_DETAILS, data);
        editor.commit();
    }

    public void setDocBase64(String data) {
        editor.putString(DOC_BASE64, data);
        editor.commit();
    }

    public void setLat(String data) {
        editor.putString(LAT, data);
        editor.commit();
    }

    public void setLon(String data) {
        editor.putString(LON, data);
        editor.commit();
    }

    public void setPickuplocation(String data) {
        editor.putString(PICKUPLOCATION, data);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }


    public String getDocId() {
        return pref.getString(DOC_ID, null);
    }

    public Boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGGED_IN, true);
    }


    public  String getUserPhoneNumber() {
        return pref.getString(DOC_ID, null);
    }

    public  String getUserId() {
        return pref.getString(DOC_ID, null);
    }

    public  String getUserName() {
        return pref.getString(USER_NAME, null);
    }

    public  String getUserEmail() {
        return pref.getString(USER_EMAIL, null);
    }

    public  String getDocFname() {
        return pref.getString(DOC_FNAME, null);
    }

    public  String getDocLname() {
        return pref.getString(DOC_LNAME, null);
    }

    public  String getDocType() {
        return pref.getString(DOC_TYPE, null);
    }

    public  String getDocName() {
        return pref.getString(DOC_NAME, null);
    }

    public  String getDocRef() {
        return pref.getString(DOC_REF, null);
    }

    public  String getDocDetails() {
        return pref.getString(DOC_DETAILS, null);
    }

    public  String getPICKUPLOCATION() {
        return pref.getString(PICKUPLOCATION, null);
    }

    public  String getLAT() {
        return pref.getString(LAT, null);
    }

    public  String getLON() {
        return pref.getString(LON, null);
    }

    public  String getDocBase64() {
        return pref.getString(DOC_BASE64, null);
    }
}
