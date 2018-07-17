package io.ginius.cp.kt.lostfound;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        ImageView icon =  convertView.findViewById(R.id.icon);


        final HashMap<String, String> map = list.get(position);

        docType.setText("Type: "+map.get("DocName"));
        docNames.setText("Names: "+map.get("doc_fname") +" "+ map.get("doc_lname"));
        if(map.get("DocType").equalsIgnoreCase("NATIONAL_ID")){
            bg.setBackgroundResource(R.drawable.search_item_id_bg);
            docId.setText("ID No: "+map.get("DocId"));
        }
        else if(map.get("DocType").equalsIgnoreCase("PASSPORT")) {
            docId.setText("Passport No: "+map.get("DocId"));
            icon.setBackgroundResource(R.drawable.ic_pasport_white);
            docId.setTextColor(activity.getResources().getColor(R.color.colorWhite));
            docNames.setTextColor(activity.getResources().getColor(R.color.colorWhite));
            docType.setTextColor(activity.getResources().getColor(R.color.colorWhite));
            bg.setBackgroundResource(R.drawable.search_item_passport_bg);
        } else if(map.get("DocType").equalsIgnoreCase("DRIVING_LICENSE")) {
            docId.setText("License No: "+map.get("DocId"));
            icon.setBackgroundResource(R.drawable.ic_pasport_white);
            docId.setTextColor(activity.getResources().getColor(R.color.colorWhite));
            docNames.setTextColor(activity.getResources().getColor(R.color.colorWhite));
            docType.setTextColor(activity.getResources().getColor(R.color.colorWhite));
            bg.setBackgroundResource(R.drawable.seearch_item_dl_bg);
        } else {
            docId.setText("ID: "+map.get("DocId"));
            bg.setBackgroundResource(R.drawable.other_docs_bg);
            icon.setBackgroundResource(R.drawable.ic_id_white);
            docId.setTextColor(activity.getResources().getColor(R.color.colorWhite));
            docNames.setTextColor(activity.getResources().getColor(R.color.colorWhite));
            docType.setTextColor(activity.getResources().getColor(R.color.colorWhite));
        }
        bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (customListner != null) {
                    customListner.onButtonClickListner(position, map.get("DocId"), map.get("DocName"),  map.get("doc_fname"),
                            map.get("doc_lname"),"", map.get("DocImage"));}
            }
        });
        return convertView;
    }

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }

    public interface customButtonListener {
        void onButtonClickListner(int position, String value, String iDType, String firstName, String secondName, String doB, String image);
    }
}