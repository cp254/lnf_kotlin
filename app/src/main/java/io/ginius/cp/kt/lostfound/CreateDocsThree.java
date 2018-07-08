package io.ginius.cp.kt.lostfound;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.HashMap;

import io.ginius.cp.kt.lostfound.models.Result;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by cyprian on 7/4/18.
 */

public class CreateDocsThree extends MainBaseActivity{

    public int SUCCESS = 0;
    ArrayList<HashMap<String, String>> transfersList;
    Toolbar toolbar;
    Result docObjList[];
    private Context mContext;
    private Activity mActivity;
    InputMethodManager imm;
    String fname, lname, docid, comments, doctype, pickup;
    Double lat = 0.0, lng = 0.0;
    EditText names, phone;
    ArrayList<Double> location;
    Button next;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_doc_three);
        toolbar = findViewById(R.id.toolbar);
        mContext = getApplicationContext();
        location = new ArrayList<Double>();
        phone = findViewById(R.id.et_contact);
        names = findViewById(R.id.et_fname);
        next = findViewById(R.id.btn_next);
        mActivity = CreateDocsThree.this;

        try {
            fname = getIntent().getStringExtra("f_name");
            lname = getIntent().getStringExtra("l_name");
            docid = getIntent().getStringExtra("doc_id");
            comments = getIntent().getStringExtra("comments");
            doctype = getIntent().getStringExtra("doc_type");
            lat = getIntent().getDoubleExtra("lat", 0.0);
            lng = getIntent().getDoubleExtra("lng", 0.0);
            pickup = getIntent().getStringExtra("pick_up");
        } catch (Exception e) {
            onBackPressed();
        }
        location.add(lat);
        location.add(lng);





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

//
                }

            }
        });


    }
    public JSONObject createDoc() throws JSONException {
        JSONObject dataW = new JSONObject();
        JSONObject dataItem = new JSONObject();
        dataItem.put("doc_type", doctype);
        dataItem.put("doc_unique_id", doctype);
        dataItem.put("doc_name", docid);
        dataItem.put("doc_details", comments);
        JSONArray list = new JSONArray(location);
        dataItem.put("coordinates", list);
        dataItem.put("created_by", names.getText().toString());
        dataItem.put("pick_up_location", pickup);
        dataItem.put("foundby_contact", phone.getText().toString());
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
                createErrorDetailDialog(CreateDocsThree.this, error, "Error").show();
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
                        intent.putExtra("image_name", fname+"_"+lname+"_"+docid);
                        startActivity(intent);
//
                    }
                });
                dialog.show();



            } else {
                String respMsg = jsonObject.getString(getString(R.string.statusname));
                Toast.makeText(this, respMsg, Toast.LENGTH_LONG).show();
                Utils.dialogConfig(this, respMsg);

//                Window window = CreateDocsThree.this.getWindow();
//                if (Build.VERSION.SDK_INT >= 21)
//                    window.setStatusBarColor(ContextCompat.getColor( CreateDocsThree.this, R.color.yellow));
//                toolbar.setBackgroundResource(R.color.yellow);



            }
        } catch (Exception ex) {
            Log.e(TAG + " error", ex.toString());
            Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
            ex.printStackTrace();
//            resetLayout();
        }
    }

    boolean validate() {
        boolean valid = true;
            if (TextUtils.isEmpty(names.getText().toString())) {
                names.setError("Please enter your name.");
                valid = false;
            }
            if(TextUtils.isEmpty(phone.getText().toString())){
                phone.setError("Please your phone number.");
                valid = false;
            }

        return valid;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
