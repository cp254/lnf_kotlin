package io.ginius.cp.kt.lostfound;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

public class MainBaseActivity extends AppCompatActivity {

    public static final int SUCCESS = 0;
    public static final int POST = Request.Method.POST;
    public static final int GET = Request.Method.GET;

    private static final String TAG = MainBaseActivity.class.getSimpleName();
    private static final int PERMISSION_REQUEST_PHONE_STATE = 200;

    ProgressDialog pd;


//    Realm myRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressBar(this);
    }





    public void progressBar(Context c) {
        pd = new ProgressDialog(c);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage(getString(R.string.loading));
    }

    public boolean isConnected(Context c) {
        //Check Internet connection
        ConnectivityManager connMgr = (ConnectivityManager) c.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public AlertDialog NoInternet(Context ctx) {
        //Add No Internet
        return new AlertDialog.Builder(ctx)
                .setTitle(R.string.no_internet)
                .setMessage(
                        R.string.check_internet_connection)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        Integer.toString(which);
                    }
                }).create();
    }


    public AlertDialog createDetailDialog(Context ctx, String string,
                                          String title) {
        return new AlertDialog.Builder(ctx)
                .setTitle(title)
                .setMessage(string)
                .setPositiveButton(getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Integer.toString(which);
                            }
                        }).create();
    }

    public AlertDialog createErrorDetailDialog(Context ctx, VolleyError error,
                                               String title) {
        if (error instanceof TimeoutError)
            return createDetailDialog(ctx, "No Response.\nServer Timeout.", title);
        else if (error instanceof NoConnectionError)
            return createDetailDialog(ctx, "No Connection.\nNetwork Failure", title);
        else if (error instanceof AuthFailureError)
            return createDetailDialog(ctx, "Server Authentication Failure", title);
        else if (error instanceof ServerError)
            return createDetailDialog(ctx, "Internal Server Error", title);
        else if (error instanceof NetworkError)
            return createDetailDialog(ctx, "Network Failure", title);
        else if (error instanceof ParseError)
            return createDetailDialog(ctx, "Network Message Parse Error", title);
        else
            return createDetailDialog(ctx, error.getLocalizedMessage(), title);
    }

    public URL ConvertToUrl(String urlStr) {
        try {
            URL url = new URL(urlStr);
            URI uri = new URI(url.getProtocol(), url.getUserInfo(),
                    url.getHost(), url.getPort(), url.getPath(),
                    url.getQuery(), url.getRef());
            url = uri.toURL();
            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
