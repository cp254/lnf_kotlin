package io.ginius.cp.kt.lostfound;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
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
import java.util.Map;

import io.ginius.cp.kt.lostfound.models.Result;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by cyprian on 7/4/18.
 */

public class CreateDocsOne extends MainBaseActivity{

    public int SUCCESS = 0;
    ArrayList<HashMap<String, String>> transfersList;
    Toolbar toolbar;
    Result docObjList[];
    private Context mContext;
    private Activity mActivity;
    InputMethodManager imm;
    Map<String, String> typeMap;
    String userid, username, userphone;
    EditText fname, lname, phone, details, pickuplocation, docid, createdby;
    Spinner docType;
    Button next;
    private io.ginius.cp.kt.lostfound.PreferenceManager prefManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_doc);
        toolbar = findViewById(R.id.toolbar);
        mContext = getApplicationContext();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fname = findViewById(R.id.et_fname);
        lname = findViewById(R.id.et_lnames);
        docid = findViewById(R.id.et_doc_id);
        details = findViewById(R.id.et_doc_details);
        next = findViewById(R.id.btn_next);
        docType = findViewById(R.id.spinner_doc_type);
        mActivity = CreateDocsOne.this;

        prefManager = new io.ginius.cp.kt.lostfound.PreferenceManager(this);

        try {
            userid = prefManager.getUserId();
            username = prefManager.getUserName();
            userphone = prefManager.getUserPhoneNumber();
        } catch (Exception e) {
            onBackPressed();
        }

        ArrayList<String> TypeList = new ArrayList<String>();
        TypeList.add("Tap to select type");
        TypeList.add(getString(R.string.national_id));
        TypeList.add(getString(R.string.passport));
        TypeList.add(getString(R.string.dl));
        TypeList.add(getString(R.string.others));
        typeMap = new HashMap<>();
        typeMap.put(getString(R.string.national_id), "NATIONAL_ID");
        typeMap.put(getString(R.string.passport), "PASSPORT");
        typeMap.put(getString(R.string.dl), "DRIVING_LICENSE");
        typeMap.put(getString(R.string.others), "OTHERS");
        ArrayAdapter<String> arrayAdapterLeaves = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, TypeList);
        docType.setAdapter(arrayAdapterLeaves);
        docType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    DOCTYPE = typeMap.get(docType.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(validate()){
                    Intent intent = new Intent(mActivity, CreateDocsTwo.class);
                    prefManager.setDocType(DOCTYPE);
                    prefManager.setDocFname(fname.getText().toString().trim());
                    prefManager.setDocLname(lname.getText().toString().trim());
                    prefManager.setDocName(DOCTYPE);//TODO add logic when other options are selected on the spinner, a field to enter doc name appears
                    prefManager.setDocId(docid.getText().toString().trim());
                    prefManager.setDocDetails(details.getText().toString().trim());
                    startActivity(intent);
                }


            }
        });


    }

    boolean validate(){
        boolean valid = true;
//        fname = findViewById(R.id.et_fname);
//        lname = findViewById(R.id.et_lnames);
//        docid = findViewById(R.id.et_doc_id);
//        details = findViewById(R.id.et_doc_details);
        if(TextUtils.isEmpty(fname.getText().toString())){
            fname.setError("Please enter first name");
            valid = false;
        }

        if(TextUtils.isEmpty(fname.getText().toString())){
            fname.setError("Please enter first name");
            valid = false;
        }

        if(TextUtils.isEmpty(lname.getText().toString())){
            lname.setError("Please enter first name");
            valid = false;
        }

        if(TextUtils.isEmpty(docid.getText().toString())){
            docid.setError("Please enter first name");
            valid = false;
        }
        
        if(docType.getSelectedItemPosition() == 0){
            Toast.makeText(mActivity, "Please select document type", Toast.LENGTH_SHORT).show();
            valid = false;
        }


        return valid;
    }
    public JSONObject searchId(String acct) throws JSONException {
        JSONObject dataW = new JSONObject();
        JSONObject dataItem = new JSONObject();
        dataItem.put(getString(R.string.doc_ref), acct);
        dataW.put(getString(R.string.data), dataItem);
        dataW.put(getString(R.string.command), getString(R.string.search_documents_by_unique_ref));
        return dataW;
    }

    public JSONObject subscribeT0(String acct) throws JSONException {
        JSONObject dataW = new JSONObject();
        JSONObject dataItem = new JSONObject();
        dataItem.put(getString(R.string.doc_ref), acct);
        dataItem.put("doc_type", "NATIONAL_ID");
        dataItem.put("user_ref", "5");
        dataW.put(getString(R.string.data), dataItem);
        dataW.put(getString(R.string.command), "subscribe");
        Log.e("test", dataW.toString(4));
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
                            if (reqIdentifier.equalsIgnoreCase("search_document"))
                                docSearchResponse(response);


                        } catch (Exception ex) {
                            Log.e(TAG + " error", ex.getMessage());
                            ex.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                pd.dismiss();
                createErrorDetailDialog(CreateDocsOne.this, error, "Error").show();
                Log.e(TAG, error.toString());
            }
        });
//        SingletonRequestQueue.getInstance().addToRequestQueue(jor,"search_doc");
        SingletonRequestQueue.getInstance(mContext).addToRequestQueue(jor);
    }

    public void docSearchResponse(JSONObject jsonObject) {
        pd.dismiss();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        try {
            Log.i(TAG, jsonObject.toString());
            if (jsonObject.getInt(getString(R.string.statuscode)) == SUCCESS) {
                JSONArray documentList = jsonObject.getJSONArray(getString(R.string.result));

            } else {
                String respMsg = jsonObject.getString(getString(R.string.statusname));
                Toast.makeText(this, respMsg, Toast.LENGTH_LONG).show();

            }
        } catch (Exception ex) {
            Log.e(TAG + " error", ex.toString());
            Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
            ex.printStackTrace();
//            resetLayout();
        }
    }







    @Override
    protected void onPause() {
        super.onPause();
    }
}