package io.ginius.cp.kt.lostfound.kotlin

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager


@SuppressLint("StaticFieldLeak")
object Utils {
    internal lateinit var pd: ProgressDialog

    lateinit var pref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var _context: Context

    // shared pref mode
    var PRIVATE_MODE: Int = 0
    val PREF_NAME: String = "io.ginius.cp.kt.lostfound"
    val IS_LOGGED_IN: String = "IsLoggedIn"
    val IS_FIRST_TIME_LAUNCH: String = "IsFirstTimeLaunch"


    var s: String? = null

    var USER_PHONE_NUMBER: String = "user_phone_number"
    var USER_ID: String = "user_id"
    var USER_NAME: String = "user_name"
    var USER_EMAIL: String = "user_email"

    var SEARCH_HISTORY: String = "sh"

    var DOC_ID: String = "doc_id"
    var DOC_FNAME: String = "doc_fname"
    var DOC_LNAME: String = "doc_lname"
    var DOC_TYPE: String = "doc_type"
    var DOC_NAME: String = "doc_name"
    var DOC_REF: String = "doc_ref"
    var DOC_DETAILS: String = "doc_details"
    var PICKUPLOCATION: String = "pick_up_location"
    var PICKEDBY: String = "picked_by"
    var PICKEDBYCONTACT: String = "picked_by_contact"
    var LAT: String = "latitude"
    var LON: String = "longitude"
    var DOC_BASE64: String = "doc_photo"
    var DATEFOUND: String = "date_found"
    var COSTSUB: String = "cost_sub"
    var COSTNOT: String = "cost_not"

    fun PreferenceManager(context: Context) {
        this._context = context
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }

    fun savePrefs(key: String, value: String) {
        val editor = pref.edit()
        editor.putString(key, value)
        editor.commit()
    }

    //get prefs
    fun loadPrefs(key: String, value: String): String {
        return pref.getString(key, value)
    }

    fun saveBoolean(key: String, value: Boolean?) {
        val editor = pref.edit()
        editor.putBoolean(key, value!!)
        editor.commit()
    }

    //get prefs
    fun loadBoolean(key: String, value: Boolean?): Boolean? {
        return pref.getBoolean(key, value!!)
    }

    fun progressBar(c: Context) {
        pd = ProgressDialog(c)
        pd.setCancelable(false)
        pd.setMessage("Loading... \nPlease wait.")
    }

    fun isConnected(c: Context): Boolean {
        //Check Internet connection
        val connMgr = c.getSystemService(Activity.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }


}