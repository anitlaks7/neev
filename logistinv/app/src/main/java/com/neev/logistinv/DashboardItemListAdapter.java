package com.neev.logistinv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.neev.example.R;
import com.neev.logistinv.dashboarditemlistcontent.DashboardItemListContent;

import java.util.ArrayList;

/**
 * Created by anita.lakshmanan on 10/19/2015.
 */
public class DashboardItemListAdapter extends ArrayAdapter<DashboardItemListContent.DashboardListItem>{
    private final Context context;
    private ArrayList<DashboardItemListContent.DashboardListItem> values;
    private int resource;
    public DashboardItemListAdapter(Context context, int resource, ArrayList<DashboardItemListContent.DashboardListItem> objects) {
        super(context, resource, objects);
        this.context = context;
        this.values = objects;
        this.resource = resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(resource, parent, false);
        TextView textViewItem = (TextView) rowView.findViewById(R.id.rowtxtcol1);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        String s = values.get(position).content;
        //if(resource == R.layout.dashboard_list_item_layout) {
            TextView textViewQty = (TextView) rowView.findViewById(R.id.rowtxtcol2);
            textViewQty.setText(s);
        //}
        //else {
            /*
            final CheckedTextView ctv = (CheckedTextView) rowView.findViewById(android.R.id.text1);

            ctv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ctv.isChecked())
                        ctv.setChecked(false);
                    else
                        ctv.setChecked(true);

                }
            });
            */
      //  }

        textViewItem.setText(s);


        // Change icon based on name

        System.out.println(s);

        if (s.equals("Inventory")) {
            imageView.setImageResource(R.drawable.line_chart_icon);
        } else if (s.equals("Sales")) {
            imageView.setImageResource(R.drawable.line_chart_icon);
        } else if (s.equals("In Transit")) {
            imageView.setImageResource(R.drawable.line_chart_icon);
        } else {
            imageView.setImageResource(R.drawable.line_chart_icon);
        }

        return rowView;
    }


}
