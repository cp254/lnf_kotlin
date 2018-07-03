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


        final HashMap<String, String> map = list.get(position);
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
                    customListner.onButtonClickListner(position, map.get("DocId"), map.get("DocType"), "Cyprian",
                            "Kabia","18-05-92","/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBw8QDxAPDw8QDw8PEA8NDw8PDw8NDw8NFREWFhURFRUYHSggGBolGxUVITEhJSkrLi4uFx8zODMtNyg5LisBCgoKBQUFDgUFDisZExkrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrK//AABEIAOEA4QMBIgACEQEDEQH/xAAbAAACAwEBAQAAAAAAAAAAAAABAgADBAUGB//EADQQAAIBAgQEBAQGAQUAAAAAAAABAgMRBBIhMQVBUWEGInGBEzKhwRQjQlKR8NEzNGJysf/EABUBAQEAAAAAAAAAAAAAAAAAAAAB/8QAFBEBAAAAAAAAAAAAAAAAAAAAAP/aAAwDAQACEQMRAD8A4PEsQoReur0R5itO7uacfinOTb9jnyZAJyK5MaRWwAwBAACEYEAQMKQWgFsCw1gAJlFaLQMBAqQJIVMDRTn3PQcK4pe0J+iZ5mMi+nOwHu4ssTOPwXG542k/MvqjrRYFlwXAmBsCMUJEAtRaCQjqW1NhYoCxFkStDwAvIEAHgqkimQ8hGArEY7FYCgYxAFsRIZIsjACuwGi5wK2gEYAsAAIQgCSELWUtAEeDK0goDo4Cu4TjJdT2NOV0eEpS1PXcKr5qUeqVmB0UwsVBAAUiEAlRaCxGqbCxAsHiINEDQAAQPASQkiyRWwAxRrAAFgpBCgDFFgqGTAVlckWyK5AVsUdigLYgzQAFkiqSLpFUgEkBEYVsBbA9B4eqfNH0Z5yLO54dl52v+P3A9MgioYAhQEFASpsJAepsJEBx4CIeIF5AXIB4JiyRZJCNAICw4LAKhkgpDKIASHCkGwFTEkWSEYFbAM0KwAyMjAAGVyLBWBUxRpAAMUdjgMvzUuqaORCR0+CP82PuvoB62I6EgWICBQBogCpsIh6uwiAcaIo0ALSECB4ditAUiNgCxLBuLOQDIa5nlUE+OBtRHYw/iQPFAa2yqTM/xx467gWiyQMwMwAZAsDAUAQXArmhCyYlgDBWOlwd/nQ9fsc1HX8P071b/tTYHqYDoVDoCDIAUAKmwkR6r0EQDjRFGiBYEBAPEKlcrqJrRjQqyj37MFfEqW6swKkwVGVuoVyqMAzuVMsVNvcsjQQGW4Mxu+FHoK4R6IDLGa5lqrLqOox6IfD4N1ZqFOEpy/bBOTt6ACM0I9zXVwtbDvzQlHk1ONuV7aroWYXH4eOk6NR9lU0+qAyJiyZ2cfh74edelgq6pxcYuvJN0ovS6bS31/8ADz8ZXTYFmYVzJCi2nKzyrd8hlYBHd8iZGXIgCQpM7/huhpNvTZHEz27nawHEowj8rbdm+SuB31R7sdUe7MGE4nCbs7xb2udOACqj3D8F9WWoiAzVKD3u2RLQ01FoUtABIeCFHiASEIB4eqnuZJm+qtGYM/zd9AEHoU7sRoswzs/UCyULBsXqFxJxAqY0aDYYO3I10asLau1wObCDbsbPD+NdDEKslfJfTVXvpZlNajr5WmvUmGhlbb/gDseI/EjxMcjpxh51JtXbdlY81M1V3e7MrW4F9XimIlT+FKrN073yZnlv6GWD0sKWxpsCKq0rXdt7cgZx1SGUEAimGMx8oIICyCLMzKc/ItjHTQCylVaf3PZ4OeaEX1ijxK6dz2HCpXpQ9LfUDeEVDICT2KWi+b0M7YBGiJcam9QCQhAPHYrSLfsc6EdTr8atFQprf5per2OdThYASp3KYSs/Q3U0ZMRC0uzA3q1hJEw7vFLoWuAGaSEaL5REAry9Awiy1WHikBmnDT3MtWKSZ02tDBjdF6gUYaOl+5oSJQhaKGYCsBGQAMMUQaHICzDUU5WJWjldj3vCPBqnw9YyNWlUzJupDMo1KSV7avS+m3c8fxHh86fmTz029JL79AMT2TPU8Elelbo2jy9tkeo4JC1K/wC5t+wHTiwiIZMAzehQ2XT2M7YDDQ3EuGG4FlwAIB5ytgrpVZu8ped9EuSMElqd3jVB0lGm90lm63OOogLFFNeF0apU30KagGWjWy6P2ZqhWTMtRJlKTWzA6FwNmJVpLlcb8V2YGuw0WY/xnYR4mb2QG+pUSRzpfmT7ImSUvmZpp08qAZorkPJlcmAhAgYEbEk9Uv5GY1OIHXhx2vRoyoUqso0quX4tP9M7bXRp4VjFUi4313cZfK10PP1WX4Wk3ZK9+3UDdVw6lXcae17Louv3PS0IZYqK2SsYOG4VU1d6ye76dkdBSQDoYRSQcyAaWxnbLpPQoYBGhuJcaD1AsIQgHO8RZnVbkt9UciJ6XFY6li6NmlCvTWz/AFLseWqStdAaXWWxlrQvsUzqhhiAKqlMosbJyuZmtQJFIsVNdBco8WBPhRCoRJcFwCK2RsVsCSZWxmIwBcgAgSxISaYURICzI24q3zPTuei4dg1TWusnu+hyuFeeorrSCbXrdWPQQAugiyNiuI6AeyDlQqGTAkkrMoZe9jOwCgoVBQFxAEA4GIw2RZm2unLU5s5N3OhxWvmlbkjmsBcl9xJ0egbjJgVKMupZCA40UAMouxayuQCtguRiXAZisjYjYEYGRkAAUAiAYaCu7dRQxbWq5Adjg1Kzm93pH/J2InH4M9LPnr7nYiBbEsRXEdAOgoUYCMoZezOwJcMQIKAuIAgHl8THVmSZ1sbh2m2vc5tSKAosQdvsCVmAsZ9R4zK3ERyaA0OQJMz52NGYBbBcjFuAQAZEBCMgGBCACAUPBiDRQHS4ZO9RJbJfU70DkcPwkoJTte7S0/SmdqKAaJYhEOgCggSGAhQy8ofMBQgiEC0gLkA5nFq0/iS2fW2iZxasux2MdB55Zt7u5zqsAMTkK2XuAVTXMDK5CymjRUiihwQFTkEeyFsAyAyIgEYLBIACEABCEHjEAJFkFqgWLaS1A9D4ZouTk3fLFJJcrs7NfDW2E8O0MtBPnJuX9/g6c4aAcmNN9A5TdKNvdhVNPdAYbENNbDW1WxnjqBLFMkdGOG031KKmEmr6X9AMiRGO4tboDQEANYgG/wAScNWZytZ9Vs0eRxFK19Uz6ZxqUXRjO11OKf8AKPnPEaSzNra4HOkVTmWTiVSgBXJlbLWhWgEFsOABbAsEgAIEjAFhQseEAJCA9hkhlEBEjZgcO5yjFbyaRTCB6bwxgbydVrSOkfXmB6DD0VGMYraKSXsWzWg8YkaAy1Y7DRiSprL0GSAWo9DBbzm6ZkoK8m/YDZSNLiUU0aaT0ApqUotaow1cF+1nRqRfIokrAYPwkiGy/qQDo4v/AGNH/qjw3EP1EIBy5iSIQCqZXIhAEAyEAUCIQAoEiEAEC6ISANAsRCAW09z2/Av9CH95kIB1IEfMJAMa+ZlpCAU1tmZ8Lz9SEA2QL6YSAB7lFUJAEIQgH//Z");}
            }
        });
        return convertView;
    }

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }

    public interface customButtonListener {
        void onButtonClickListner(int position, String value, String iDType, String firstName, String secondName, String doB, String image );
    }
}