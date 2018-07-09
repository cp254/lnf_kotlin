package io.ginius.cp.kt.lostfound;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ResultReceiver;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import io.ginius.cp.kt.lostfound.models.Result;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by cyprian on 7/4/18.
 */

public class CreateDocsTwo extends MainBaseActivity implements LocationResult {

    public int SUCCESS = 0;
    ArrayList<HashMap<String, String>> transfersList;
    Toolbar toolbar;
    Result docObjList[];
    ArrayList<Double> loc;
    private Context mContext;
    private Activity mActivity;
    InputMethodManager imm;
    Geocoder geocoder;
    String bestProvider;
    List<Address> user = null;
    double lat = 0;
    double lng = 0;
    EditText pickuplocation;
    Boolean gpsLocation = false;
    Button next, btn_gps;
    String userid, username, userphone;
    String fname, lname, docid, comments, doctype, mAddressOutput;
    private MyLocation myLocation = null;
    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private static final int INITIAL_REQUEST = 13;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_doc_two);
        toolbar = findViewById(R.id.toolbar);
        mContext = getApplicationContext();

        pickuplocation = findViewById(R.id.et_pickup);
        myLocation = new MyLocation();


        try {
            fname = getIntent().getStringExtra("f_name");
            lname = getIntent().getStringExtra("l_name");
            docid = getIntent().getStringExtra("doc_id");
            comments = getIntent().getStringExtra("comments");
            doctype = getIntent().getStringExtra("doc_type");
            userid = getIntent().getStringExtra("user_id");
            username = getIntent().getStringExtra("creator_name");
            userphone = getIntent().getStringExtra("user_phone");
        } catch (Exception e) {
            onBackPressed();
        }
        loc = new ArrayList<Double>();
        next = findViewById(R.id.btn_next);
        btn_gps = findViewById(R.id.btn_gps);
        mActivity = CreateDocsTwo.this;

        btn_gps.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                gpsLocation = true;
//                Intent intent = new Intent(mActivity, CreateDocsThree.class);
//                startActivity(intent);

                if (!canAccessLocation() || !canAccessCoreLocation()) {
                    requestPermissions(INITIAL_PERMS, INITIAL_REQUEST);

                } else {
                    boolean networkPresent = myLocation.getLocation(CreateDocsTwo.this, CreateDocsTwo.this);
                    if (!networkPresent) {
                        showSettings("Check your internet and gps settings");
                    }
                }




        }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    try {
                        webServiceRequest(POST, getString(R.string.service_url), createDoc(),
                                "create_document");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }




//                    Intent intent = new Intent(mActivity, CreateDocsThree.class);
//                    intent.putExtra("pick_up", pickuplocation.getText().toString().trim());
//                    intent.putExtra("lat", lat);
//                    intent.putExtra("lng", lng);
//                    intent.putExtra("doc_type", DOCTYPE);
//                    intent.putExtra("f_name", fname);
//                    intent.putExtra("l_name", lname);
//                    intent.putExtra("doc_id", docid);
//                    intent.putExtra("comments", comments);
//                    startActivity(intent);
                }
            }
        });
    }
    public JSONObject createDoc() throws JSONException {
        JSONObject dataW = new JSONObject();
        JSONObject dataItem = new JSONObject();
        dataItem.put("doc_type", doctype);
        dataItem.put("doc_unique_id", docid);
        dataItem.put("doc_name", doctype);
        dataItem.put("doc_details", comments);
        JSONArray list = new JSONArray(loc);
        dataItem.put("coordinates", list);
        dataItem.put("created_by", username);
        dataItem.put("pick_up_location", pickuplocation.getText().toString());
        dataItem.put("foundby_contact", userphone);
        dataItem.put("doc_fname", fname);
        dataItem.put("doc_lname", lname);
        dataW.put(getString(R.string.data), dataItem);
        dataW.put(getString(R.string.command), "create_document");
        return dataW;
    }

    public void webServiceRequest(final int reqMethod, final String url, JSONObject jsonReq, final String reqIdentifier) {
        if (jsonReq != null) {
            pd.show();
            try {
                Log.e(TAG + "WebService Request ", jsonReq.toString(4));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else
            Log.i(TAG + "WebService Request ", url);

        JsonObjectRequest jor = new JsonObjectRequest(reqMethod, url, jsonReq,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Log.i(TAG + " WebService response", response.toString());
                            if (reqIdentifier.equalsIgnoreCase("create_document"))
                                createDocResponse(response);


                        } catch (Exception ex) {
                            Log.e(TAG + " error", ex.getMessage());
                            ex.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                createErrorDetailDialog(CreateDocsTwo.this, error, "Error").show();
                Log.e(TAG, error.toString());
            }
        });
//        SingletonRequestQueue.getInstance().addToRequestQueue(jor,"search_doc");
        SingletonRequestQueue.getInstance(mContext).addToRequestQueue(jor);
    }

    public void createDocResponse(JSONObject jsonObject) {
        pd.dismiss();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        try {
            Log.e("cdd", jsonObject.toString());
            if (jsonObject.getInt(getString(R.string.statuscode)) == SUCCESS) {
                final String result = jsonObject.getString(getString(R.string.result));
                final Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.success_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCanceledOnTouchOutside(false);
                TextView message = dialog.findViewById(R.id.text);
                message.setText("Document uploaded successfully");
                dialog.findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Intent intent = new Intent(mActivity, DocUpload.class);
                        intent.putExtra("doc_res", result);
                        intent.putExtra("user_id", userid);
                        intent.putExtra("image_name", fname+"_"+lname+"_"+docid);
                        startActivity(intent);
//
                    }
                });
                dialog.show();



            } else {
                String respMsg = jsonObject.getString("error_message");
                ;
                Utils.dialogConfig(this, respMsg);




            }
        } catch (Exception ex) {
            Log.e(TAG + " error", ex.toString());
            Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
            ex.printStackTrace();
//            resetLayout();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case INITIAL_REQUEST:
                if (canAccessLocation() && canAccessCoreLocation()) {
                    boolean networkPresent = myLocation.getLocation(CreateDocsTwo.this, this);
                    if (!networkPresent) {
                        showSettings("Enable GPS in settings.");
                    }
                }

                break;


        }
    }

    public void showSettings(String msg) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.success_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        TextView message = dialog.findViewById(R.id.text);
        if(msg != null)
            message.setText(msg);
        dialog.findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        CreateDocsTwo.this.startActivity(intent);
            }
        });
        dialog.show();
    }

    @Override
    public void gotLocation(Location location) {
        pd.show();
        final double latitude = location.getLatitude();
        final double longitude = location.getLongitude();
        final String result = "Latitude: " + location.getLatitude() +
                " Longitude: " + location.getLongitude();

        loc.add(latitude);
        loc.add(longitude);
        geocoder = new Geocoder(this);
        try {
            pd.dismiss();
            user = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            lat=(double)user.get(0).getLatitude();
            lng=(double)user.get(0).getLongitude();
            String city = user.get(0).getLocality();
            String area = user.get(0).getFeatureName();
            String pickupLoc = city+" "+area;
            System.out.println(" DDD lat: " +lat+",  longitude: "+lng);
            pickuplocation.setText(pickupLoc);

        }catch (Exception e) {
            pd.dismiss();
            e.printStackTrace();
        }


        CreateDocsTwo.this.runOnUiThread(new Runnable() {
            public void run() {
                //pickuplocation.setText(result);
                LocationAddress locationAddress = new LocationAddress();
                locationAddress.getAddressFromLocation(latitude, longitude,
                        getApplicationContext(), new GeocoderHandler());
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            //pickuplocation.setText(locationAddress);
        }
    }





    boolean validate() {
        boolean valid = true;
        if (!(gpsLocation)) {
            if (TextUtils.isEmpty(pickuplocation.getText().toString())) {
                pickuplocation.setError("Please enter the pickup location.");
                valid = false;
            }
        } else {
            if(lat == 0 || lng == 0){
                Utils.dialogConfig(this, "Unable to retrieve your location. Enter the pickup location.");
                valid = false;
            }
        }
        return valid;
    }

    private boolean canAccessLocation() {
        return (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }
    private boolean canAccessCoreLocation() {
        return (hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION));
    }
    private boolean hasPermission(String perm) {
        return (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(CreateDocsTwo.this, perm));
    }



    @Override
    protected void onPause() {
        super.onPause();
    }


}
