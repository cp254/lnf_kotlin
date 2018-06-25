package io.ginius.cp.kt.lostfound;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class DocSearchAdapter extends BaseAdapter {

    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    customButtonListener customListner;

    public DocSearchAdapter(Activity activity, ArrayList<HashMap<String, String>> list) {
        super();
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.search_item, null);
        }

        TextView docId = convertView.findViewById(R.id.doc_id);
        TextView docType = convertView.findViewById(R.id.doc_type);
        TextView docNames = convertView.findViewById(R.id.doc_name);
        LinearLayout bg = convertView.findViewById(R.id.background);


        HashMap<String, String> map = list.get(position);
        docId.setText(map.get("DocId"));
        docType.setText(map.get("DocType"));
        docNames.setText(map.get("DocDetails"));
        if(map.get("DocType").equalsIgnoreCase("NATIONAL_ID"))
            bg.setBackgroundResource(R.drawable.search_item_id_bg);
        else
            bg.setBackgroundResource(R.drawable.search_item_passport_bg);
        bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (customListner != null) {
                    customListner.onButtonClickListner(position, "", view);
                }
            }
        });
        return convertView;
    }

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }

    public interface customButtonListener {
        void onButtonClickListner(int position, String value, View view);
    }
}