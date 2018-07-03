package io.ginius.cp.kt.lostfound;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import io.ginius.cp.kt.lostfound.models.Data;
import io.ginius.cp.kt.lostfound.models.DocSearch;
import io.ginius.cp.kt.lostfound.models.DocSearchReq;
import io.ginius.cp.kt.lostfound.models.Result;

import static com.android.volley.Request.Method.POST;
import static com.android.volley.VolleyLog.TAG;

public class MainActivity extends MainBaseActivity implements DocSearchAdapter.customButtonListener{

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
    String idQuery = "";
    InputMethodManager imm;
    RelativeLayout button;
    LinearLayout no_result, cont;
    NestedScrollView nsv;
    CheckBox cbSms, cbEmail;
    String[] both = new String[2];
    String[] email = new String[1];
    String[] sms = new String[1];
    ArrayList<String> notificationType;




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
                cbSms.setChecked(true);
                if(cbSms.isChecked()){
                    Toast.makeText(MainActivity.this, "checked", Toast.LENGTH_SHORT).show();
                    System.out.println("Checked");
                }else{
                    Toast.makeText(MainActivity.this, "Unchecked", Toast.LENGTH_SHORT).show();
                    System.out.println("Un-Checked");
                }
            }
        });

        cbEmail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(cbEmail.isChecked()){
                    cont.setEnabled(true);
                }else{
                    cont.setEnabled(false);
                }
//                if(cbEmail.isChecked()){
//                    Toast.makeText(MainActivity.this, "checked", Toast.LENGTH_SHORT).show();
//                    System.out.println("Checked");
//                }else{
//                    Toast.makeText(MainActivity.this, "Unchecked", Toast.LENGTH_SHORT).show();
//                    System.out.println("Un-Checked");
//                }
            }
        });


//        if(cbSms.isChecked() || cbEmail.isChecked())
//            cont.setEnabled(true);
//        else
//            cont.setEnabled(false);

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    webServiceRequest(POST, getString(R.string.service_url), subscribeT0(idQuery), "subscribe");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

            //

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

        if(cbSms.isChecked()){
            notificationType.add("EMAIL");
            email = {"EMAIL"};
        }
        if(cbSms.isChecked()){
            notificationType.add("SMS");
        }
        if(cbSms.isChecked() && cbEmail.isChecked()) {
            notificationType.add("EMAIL");
            notificationType.addAll()

        }
        JSONArray list = new JSONArray(notificationType);
        dataItem.put("notification_type", list);
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
            Log.i(TAG, jsonObject.toString());
            if (jsonObject.getInt(getString(R.string.statuscode)) == SUCCESS) {
                JSONArray documentList = jsonObject.getJSONArray(getString(R.string.result));

                initTransfersMenu(documentList);

                if(documentList.length()==0){
                    nsv.setVisibility(View.VISIBLE);
                    header.setVisibility(View.INVISIBLE);
                    desc.setVisibility(View.INVISIBLE);
                    button.setVisibility(View.GONE);
                    Window window = MainActivity.this.getWindow();
                    if (Build.VERSION.SDK_INT >= 21)
                        window.setStatusBarColor(ContextCompat.getColor( MainActivity.this,R.color.yellow));
                    toolbar.setBackgroundResource(R.color.yellow);
                    desc.setText(getString(R.string.no_results) +" "+idQuery);
                    tv.setText("\""+idQuery+"\"");
                    rv.setVisibility(View.GONE);
                }else {
                    Window window = MainActivity.this.getWindow();
                    if (Build.VERSION.SDK_INT >= 21)
                        window.setStatusBarColor(ContextCompat.getColor( MainActivity.this,R.color.skyblue));
                    toolbar.setBackgroundResource(R.color.skyblue);
                    desc.setVisibility(View.VISIBLE);
                    nsv.setVisibility(View.GONE);
                    desc.setText("Showing "+String.valueOf(documentList.length())+ " result(s) for "+"\""+idQuery+"\"");
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

    public void initTransfersMenu(JSONArray productdetails) throws JSONException {
        transfersList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < productdetails.length(); i++) {
            Log.e(TAG, productdetails.toString(4));
            HashMap<String, String> temp = new HashMap<String, String>();
                    temp.put("DocId", productdetails.getJSONObject(i).getString("_id"));
                    temp.put("DocName", productdetails.getJSONObject(i).getString("doc_name"));
                    temp.put("DocType", productdetails.getJSONObject(i).getString("doc_type"));
                    temp.put("DocNum", productdetails.getJSONObject(i).getString("doc_num"));
                    temp.put("DocDetails", productdetails.getJSONObject(i).getString("doc_details"));
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
        RelativeLayout bg =  dialog.findViewById(R.id.image);
        com.jcminarro.roundkornerlayout.RoundKornerRelativeLayout bg1 =  dialog.findViewById(R.id.bg);
        TextView textIdType =  dialog.findViewById(R.id.doc_type);
        TextView textDocNo =  dialog.findViewById(R.id.doc_id);
        TextView textDocNames =  dialog.findViewById(R.id.doc_names);
        ImageView expand = dialog.findViewById(R.id.expand);
        textIdType.setText(iDType);
        textDocNo.setText("No: "+value);
        textDocNames.setText("Names: "+firstName+"  "+secondName);
        //bg.setBackgroundResource(R.drawable.);
        byte[] decodedString = Base64.decode(image, Base64.NO_WRAP);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        BitmapDrawable background = new BitmapDrawable(decodedByte);
        bg.setBackgroundDrawable(background);
        GradientDrawable shape =  new GradientDrawable();
        shape.setCornerRadius( 16 );
        bg1.setBackground(shape);

        Button btn =  dialog.findViewById(R.id.button_pay);
        ImageView docIcon =  dialog.findViewById(R.id.doc_icon);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();


            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();


            }
        });

        dialog.show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_history:
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        cbEmail.setChecked(false);
        cbSms.setChecked(false);
    }
}
