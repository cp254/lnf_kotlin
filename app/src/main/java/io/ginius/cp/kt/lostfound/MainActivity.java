package io.ginius.cp.kt.lostfound;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import io.ginius.cp.kt.lostfound.models.DocSearch;
import io.ginius.cp.kt.lostfound.models.Result;

import static com.android.volley.Request.Method.POST;
import static com.android.volley.VolleyLog.TAG;

public class MainActivity extends MainBaseActivity implements DocSearchAdapter.customButtonListener{

    public int SUCCESS = 0;
    ArrayList<HashMap<String, String>> transfersList;
    ListView rv;
    TextView desc;
    SearchView sv;
    Result docObjList[];
    private Context mContext;
    private Activity mActivity;
    ConstraintLayout header;
    String idQuery = "";
    InputMethodManager imm;
    RelativeLayout button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rv);
        desc = findViewById(R.id.tv_desc);
        sv = findViewById(R.id.searchView);
        header = findViewById(R.id.header);
        button = findViewById(R.id.button);
        mContext = getApplicationContext();
        mActivity = MainActivity.this;

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



    }



    public JSONObject searchId(String acct) throws JSONException {
        JSONObject dataW = new JSONObject();
        JSONObject dataItem = new JSONObject();
        dataItem.put(getString(R.string.doc_ref), acct);
        dataW.put(getString(R.string.data), dataItem);
        dataW.put(getString(R.string.command), getString(R.string.search_documents_by_unique_ref));
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
                    header.setVisibility(View.INVISIBLE);
                    desc.setVisibility(View.VISIBLE);
                    desc.setText(getString(R.string.no_results) +idQuery);
                    rv.setVisibility(View.GONE);
                }else {
                    desc.setText("Showing "+String.valueOf(documentList.length())+ " result(s) for '"+idQuery+"'");
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
    public void onButtonClickListner(int position, String value, View view) {

    }
}
