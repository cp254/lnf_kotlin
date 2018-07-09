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
import android.view.MenuItem;
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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.ginius.cp.kt.lostfound.models.Result;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.android.volley.VolleyLog.TAG;

public class MainActivity extends MainBaseActivity implements DocSearchAdapter.customButtonListener {

    public int SUCCESS = 0;
    ArrayList<HashMap<String, String>> transfersList;
    ListView rv;
    TextView desc, tv;
    SearchView sv;
    Toolbar toolbar;
    Result docObjList[];
    private Context mContext;
    private Activity mActivity;
    ConstraintLayout header;
    String idQuery = "", contact = "", email = "", password = "", name = "", docNum = "", contct = "", pn = "";
    InputMethodManager imm;
    RelativeLayout button;
    LinearLayout no_result, cont;
    NestedScrollView nsv;
    Map<String, String> typeMap;
    CheckBox cbSms, cbEmail;
    ArrayList<String> notificationType;
    Boolean foundID = false, reg = false, log = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rv);
        toolbar = findViewById(R.id.toolbar);
        desc = findViewById(R.id.tv_desc);
        sv = findViewById(R.id.searchView);
        header = findViewById(R.id.header);
        button = findViewById(R.id.button);
        cont = findViewById(R.id.cont);
        cbSms = findViewById(R.id.cb_sms);
        cbEmail = findViewById(R.id.cb_email);
        nsv = findViewById(R.id.nsv);
        mContext = getApplicationContext();
        tv = findViewById(R.id.tv_id);
        no_result = findViewById(R.id.not_found);
        mActivity = MainActivity.this;
        cont.setVisibility(View.GONE);
        notificationType = new ArrayList<String>();
        sv.setIconified(false);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                header.setVisibility(View.GONE);
                idQuery = query;
                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(sv.getApplicationWindowToken(), 0);
                try {
                    webServiceRequest(POST, getString(R.string.service_url), searchId(query), "search_document");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        cbSms.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                notificationType.clear();
                if (cbSms.isChecked()) {
                    notificationType.add("SMS");

                } else {
                    notificationType.clear();

                }

                if (cbEmail.isChecked() || cbSms.isChecked()) {
                    cont.setVisibility(View.VISIBLE);
                } else {
                    cont.setVisibility(View.GONE);
                }
            }
        });

        cbEmail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                notificationType.clear();
                if (cbEmail.isChecked()) {
                    notificationType.add("EMAIL");
                } else {
                    notificationType.clear();
                }
                if (cbEmail.isChecked() || cbSms.isChecked()) {
                    cont.setVisibility(View.VISIBLE);
                } else {
                    cont.setVisibility(View.GONE);
                }
            }
        });


        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.register);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCanceledOnTouchOutside(false);
                RelativeLayout bg = dialog.findViewById(R.id.image);
                final EditText ETphone = dialog.findViewById(R.id.et_contact);
                final EditText ETemail = dialog.findViewById(R.id.et_email);
                final EditText ETpassword = dialog.findViewById(R.id.et_password);
                TextView login = dialog.findViewById(R.id.tvlogin);
                Button submit = dialog.findViewById(R.id.btn_submit);


                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        if(email != "" && password != "" && contact != "") {
                        if (TextUtils.isEmpty(ETemail.getText().toString()))
                            ETemail.setError("Please enter an email address");

                        else
                            email = ETemail.getText().toString().trim();
                        if (TextUtils.isEmpty(ETphone.getText().toString()))
                            ETphone.setError("Please enter an email address");
                        else{
                            contact = ETphone.getText().toString().trim();
                            pn = ETphone.getText().toString().trim();
                        }
                        if (TextUtils.isEmpty(ETpassword.getText().toString()))
                            ETpassword.setError("Please enter an email address");
                        else
                            password = ETpassword.getText().toString().trim();
                        reg = true;
                        try {
                            webServiceRequest(POST, getString(R.string.service_url), register(), "register");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        } else
                        dialog.dismiss();

                    }
                });

                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        log = true;
                        loginDialog();


                    }
                });

                dialog.show();


            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foundID = true;
//                Intent intent = new Intent(mActivity, DocUpload.class);
//                startActivity(intent);
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.register);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCanceledOnTouchOutside(false);
                RelativeLayout bg = dialog.findViewById(R.id.image);
                final EditText ETphone = dialog.findViewById(R.id.et_contact);
                final EditText ETnames = dialog.findViewById(R.id.et_names);
                final EditText ETemail = dialog.findViewById(R.id.et_email);
                final EditText ETpassword = dialog.findViewById(R.id.et_password);
                TextView login = dialog.findViewById(R.id.tvlogin);
                Button submit = dialog.findViewById(R.id.btn_submit);


                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        if(email != "" && password != "" && contact != "") {
                        if (TextUtils.isEmpty(ETemail.getText().toString()))
                            ETemail.setError("Please enter an email address");

                        else
                            email = ETemail.getText().toString().trim();
                        if (TextUtils.isEmpty(ETphone.getText().toString()))
                            ETphone.setError("Please enter an email address");
                        else
                            contact = ETphone.getText().toString().trim();
                        if (TextUtils.isEmpty(ETpassword.getText().toString()))
                            ETpassword.setError("Please enter an email address");
                        else
                            password = ETpassword.getText().toString().trim();
                        if (TextUtils.isEmpty(ETnames.getText().toString()))
                            ETnames.setError("Please enter your name");
                        else
                            name = ETnames.getText().toString().trim();

                        try {
                            webServiceRequest(POST, getString(R.string.service_url), register(), "register");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        } else
                        dialog.dismiss();

                    }
                });

                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        log = true;
                        foundID = true;
                        loginDialog();


                    }
                });

                dialog.show();


            }
        });

        //

    }

    void loginDialog() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.login);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        final EditText ETphone = dialog.findViewById(R.id.et_contact);
        //EditText email =  dialog.findViewById(R.id.et_email);
        final EditText ETpassword = dialog.findViewById(R.id.et_password);
        //TextView login =  dialog.findViewById(R.id.tvlogin);
        Button submit = dialog.findViewById(R.id.btn_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(ETphone.getText().toString()))
                    ETphone.setError("Please enter phone number");
                else {
                    contact = ETphone.getText().toString().trim();
                    pn = ETphone.getText().toString().trim();
                }
                if (TextUtils.isEmpty(ETpassword.getText().toString()))
                    ETpassword.setError("Please enter password");
                else
                    password = ETpassword.getText().toString().trim();


                try {
                    webServiceRequest(POST, getString(R.string.service_url), login(), "register");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                        } else
                dialog.dismiss();


            }
        });

        dialog.show();
    }

    boolean validate() {
        boolean valid;
        if (contact == null || password == null || email == null) {
            valid = false;
        } else
            valid = true;


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

    public JSONObject subscribeT0(String acct, String docType) throws JSONException {
        JSONObject dataW = new JSONObject();
        JSONObject dataItem = new JSONObject();
        dataItem.put(getString(R.string.doc_ref), acct);
        dataItem.put("doc_type", docType);
        dataItem.put("user_ref", USERID);
        JSONArray list = new JSONArray(notificationType);
        dataItem.put("notification_type", list);
        dataW.put(getString(R.string.data), dataItem);
        dataW.put(getString(R.string.command), "subscribe");
        Log.e("test", dataW.toString(4));
        return dataW;
    }

    public JSONObject register() throws JSONException {
        JSONObject dataW = new JSONObject();
        JSONObject dataItem = new JSONObject();
        dataItem.put(getString(R.string.contact), contact);
        dataItem.put(getString(R.string.email), email);
        dataItem.put("password", password);
        dataItem.put("reg_by", getString(R.string.self));
        dataW.put(getString(R.string.data), dataItem);
        dataW.put(getString(R.string.command), getString(R.string.register_user));
        Log.e("test", dataW.toString(4));
        return dataW;
    }

    public JSONObject login() throws JSONException {
        JSONObject dataW = new JSONObject();
        JSONObject dataItem = new JSONObject();
        dataItem.put(getString(R.string.contact), contact);
        dataItem.put("password", password);
        dataW.put(getString(R.string.data), dataItem);
        dataW.put(getString(R.string.command), "mobile_login");
        Log.e("test", dataW.toString(4));
        return dataW;
    }

//    	"doc_ref":"2" ,
//                "amount":"10" ,
//                "contact":"254725596227"

    private String modifyNumber(String num) {
        String formatted = "";
        Log.e(TAG + "aaaaaaaaa", num);
        if(num.startsWith("0")) {
            formatted = num.replace("0", "254");
            Log.e(TAG + "bbbbbbbbbb", formatted);
        }
        return formatted;
    }

    public JSONObject payment() throws JSONException {
        JSONObject dataW = new JSONObject();
        JSONObject dataItem = new JSONObject();
        Log.e(TAG + "aaaaaaaaa", contct);
        String formatted = null;
        if (contct.startsWith("0")) {
            formatted = contct.replace("0", "254");
            Log.e(TAG + "bbbbbbbbbb", formatted);
        }
        dataItem.put("contact", formatted);
        dataItem.put("doc_ref", docNum);
        dataItem.put("amount", "10");
        dataW.put(getString(R.string.data), dataItem);
        dataW.put(getString(R.string.command), "make_mpesa_payment");
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
                            else if (reqIdentifier.equalsIgnoreCase("register"))
                                registerResponse(response);
                            else if (reqIdentifier.equalsIgnoreCase("subscribe"))
                                subscribeResponse(response);
                            else if (reqIdentifier.equalsIgnoreCase("payment"))
                                paymentResponse(response);


                        } catch (Exception ex) {
                            Log.e(TAG + " error", ex.getMessage());
                            ex.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                createErrorDetailDialog(MainActivity.this, error, "Error").show();
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
            Log.e(TAG, jsonObject.toString());
            if (jsonObject.getInt(getString(R.string.statuscode)) == SUCCESS) {
                JSONArray documentList = jsonObject.getJSONArray(getString(R.string.result));

                initTransfersMenu(documentList);

                if (documentList.length() == 0) {
                    nsv.setVisibility(View.VISIBLE);
                    header.setVisibility(View.INVISIBLE);
                    desc.setVisibility(View.INVISIBLE);
                    button.setVisibility(View.GONE);
                    Window window = MainActivity.this.getWindow();
                    if (Build.VERSION.SDK_INT >= 21)
                        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.yellow));
                    toolbar.setBackgroundResource(R.color.yellow);
                    //desc.setText(getString(R.string.no_results) +" "+idQuery);
                    tv.setText("\"" + idQuery + "\"");
                    rv.setVisibility(View.GONE);
                } else {
                    Window window = MainActivity.this.getWindow();
                    if (Build.VERSION.SDK_INT >= 21)
                        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.skyblue));
                    toolbar.setBackgroundResource(R.color.skyblue);
                    desc.setVisibility(View.VISIBLE);
                    nsv.setVisibility(View.GONE);
                    desc.setText("Showing " + String.valueOf(documentList.length()) + " result(s) for " + "\"" + idQuery + "\"");
                    rv.setVisibility(View.VISIBLE);
                    button.setVisibility(View.VISIBLE);
                }
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

    public void registerResponse(JSONObject jsonObject) {
        pd.dismiss();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        try {
            Log.e("t", jsonObject.toString());
            if (jsonObject.getInt(getString(R.string.statuscode)) == SUCCESS) {
                JSONObject result = jsonObject.getJSONObject(getString(R.string.result));
                JSONObject user = result.getJSONObject(getString(R.string.user));
                Log.e("t", user.getString("user_id"));
                USERID = user.getString("user_id");
                //Toast.makeText(this, jsonObject.getString("statusname"), Toast.LENGTH_SHORT).show();
                final Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.success_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCanceledOnTouchOutside(false);
                TextView text = dialog.findViewById(R.id.text);
                 contct = user.getString("contact");
                 pn = user.getString("contact");
                Log.e("&&&&&&", user.getString("contact") +" "+pn);
                if (reg)
                    text.setText("Register successful");
                else if (log) {
                    text.setText("Login successful");
                    name = user.getString("names");
                    Log.e("++++", contct);
                    //Toast.makeText(mActivity, name, Toast.LENGTH_SHORT).show();
                }
                log = false;
                reg = false;
                Button submit = dialog.findViewById(R.id.btn_next);

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        if (foundID) {
                            Intent intent = new Intent(mActivity, CreateDocsOne.class);
                            intent.putExtra("user_id", USERID);
                            intent.putExtra("creator_name", name);
                            intent.putExtra("user_contact", contct);
                            startActivity(intent);
                        } else {
                            subCont();
                        }
                    }
                });

                dialog.show();

            } else if (jsonObject.getInt(getString(R.string.statuscode)) == 1) {
                Utils.dialogConfig(this, jsonObject.getString("error_message"));

            }

        } catch (Exception e) {

        }

    }

    void subCont() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_enter_id_type);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        final Spinner docTypeSpinner = dialog.findViewById(R.id.spinner_doc_type);
        Button submit = dialog.findViewById(R.id.btn_next);
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
        ArrayAdapter<String> arrayAdapterLeaves = new ArrayAdapter<>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, TypeList);
        docTypeSpinner.setAdapter(arrayAdapterLeaves);
        docTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    DOCTYPE = typeMap.get(docTypeSpinner.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                try {
                    webServiceRequest(POST, getString(R.string.service_url), subscribeT0(idQuery, DOCTYPE), "subscribe");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

        dialog.show();
    }

    public void subscribeResponse(JSONObject jsonObject) {
        pd.dismiss();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        try {
            Log.e(TAG, jsonObject.toString());
            if (jsonObject.getInt(getString(R.string.statuscode)) == SUCCESS) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.success_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCanceledOnTouchOutside(false);
                TextView text = dialog.findViewById(R.id.text);
                text.setText(jsonObject.getString(getString(R.string.statusname)));
                Button submit = dialog.findViewById(R.id.btn_next);

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Window window = MainActivity.this.getWindow();
                        if (Build.VERSION.SDK_INT >= 21)
                            window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.skyblue));
                        toolbar.setBackgroundResource(R.color.skyblue);
                        desc.setVisibility(View.VISIBLE);
                        nsv.setVisibility(View.GONE);
                        rv.setVisibility(View.GONE);
                        button.setVisibility(View.VISIBLE);


                    }
                });

                dialog.show();
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

    public void paymentResponse(JSONObject jsonObject) {
        pd.dismiss();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        try {
            Log.e(TAG, jsonObject.toString(4));
            if (jsonObject.getInt(getString(R.string.statuscode)) == SUCCESS) {

            } else {
                String respMsg = jsonObject.getString("error_message");
                Utils.dialogConfig(this,respMsg);

            }
        } catch (Exception ex) {
            Log.e(TAG + " error", ex.toString());
            Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
            ex.printStackTrace();
//            resetLayout();
        }
    }

    public void initTransfersMenu(JSONArray productdetails) throws JSONException {
        transfersList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < productdetails.length(); i++) {
            Log.e(TAG, productdetails.toString(4));
            HashMap<String, String> temp = new HashMap<String, String>();
            temp.put("DocId", productdetails.getJSONObject(i).getString("_id"));
            temp.put("DocName", productdetails.getJSONObject(i).getString("doc_name"));
            temp.put("doc_fname", productdetails.getJSONObject(i).getString("doc_fname"));
            temp.put("doc_lname", productdetails.getJSONObject(i).getString("doc_lname"));
            temp.put("DocType", productdetails.getJSONObject(i).getString("doc_type"));
            temp.put("DocNum", productdetails.getJSONObject(i).getString("doc_num"));
            temp.put("DocDetails", productdetails.getJSONObject(i).getString("doc_details"));
            docNum = productdetails.getJSONObject(i).getString("doc_num");
            transfersList.add(temp);


        }
        DocSearchAdapter adapter = new DocSearchAdapter(this, transfersList);
        adapter.setCustomButtonListner(this);
        rv.setAdapter(adapter);
        setListViewHeightBasedOnChildren(rv);
        rv.deferNotifyDataSetChanged();
    }

    private void setListViewHeightBasedOnChildren(ListView listView) {
        Log.e("Listview Size ", "" + listView.getCount());
        DocSearchAdapter listAdapter = (DocSearchAdapter) listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    @Override
    public void onButtonClickListner(int position, String value, String iDType, String firstName,
                                     String secondName, String doB, String image) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.doc_preview_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //dialog.getWindow().setBackgroundDrawableResource(Color.TRANSPARENT);
        // dialog.setCancelable(false);
        RelativeLayout bg = dialog.findViewById(R.id.image);
        com.jcminarro.roundkornerlayout.RoundKornerRelativeLayout bg1 = dialog.findViewById(R.id.bg);
        TextView textIdType = dialog.findViewById(R.id.doc_type);
        TextView textDocNo = dialog.findViewById(R.id.doc_id);
        TextView textDocNames = dialog.findViewById(R.id.doc_names);
        ImageView expand = dialog.findViewById(R.id.expand);
        textIdType.setText(iDType);
        textDocNo.setText("No: " + value);
        textDocNames.setText("Names: " + firstName + "  " + secondName);
        //bg.setBackgroundResource(R.drawable.);
        byte[] decodedString = Base64.decode(image, Base64.NO_WRAP);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        BitmapDrawable background = new BitmapDrawable(decodedByte);
        bg.setBackgroundDrawable(background);
        GradientDrawable shape = new GradientDrawable();
        shape.setCornerRadius(16);
        bg1.setBackground(shape);

        Button btn = dialog.findViewById(R.id.button_pay);
        ImageView docIcon = dialog.findViewById(R.id.doc_icon);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                try {
                    webServiceRequest(POST, getString(R.string.service_url), payment(), "payment");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
        docIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();


            }
        });

        dialog.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        foundID = false;
        log = false;
        reg = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        cbEmail.setChecked(false);
        cbSms.setChecked(false);
        foundID = false;
        log = false;
        reg = false;
    }
}
