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

/**
 * Created by cyprian on 7/17/18.
 */

public class SearchHistory extends BaseAdapter {

    public ArrayList<HashMap<String, String>> list;
    Activity activity;

    public SearchHistory(Activity activity, ArrayList<HashMap<String, String>> list) {
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
            convertView = inflater.inflate(R.layout.history_item, null);
        }

        TextView no = convertView.findViewById(R.id.doc_id);

        TextView date = convertView.findViewById(R.id.doc_type);


        final HashMap<String, String> map = list.get(position);

        no.setText("ID: "+map.get("number"));
        String full = map.get("date");
        String edited = full.substring(0, 10);
                date.setText("Date: "+edited);


        return convertView;
    }


}