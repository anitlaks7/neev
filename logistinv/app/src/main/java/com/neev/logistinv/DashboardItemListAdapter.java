package com.neev.logistinv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TwoLineListItem;


import com.neev.example.R;
import com.neev.logistinv.dashboarditemlistcontent.DashboardItemListContent;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by anita.lakshmanan on 10/19/2015.
 */
public class DashboardItemListAdapter extends ArrayAdapter<DashboardItemListContent.DashboardListItem>{
    private final Context context;
    private ArrayList<DashboardItemListContent.DashboardListItem> values;
    private int resource;
    private int Quantity;
    private float Total;
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

        TextView row1 = (TextView) rowView.findViewById(R.id.rtext1);
        TextView row2 = (TextView) rowView.findViewById(R.id.rtext2);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        TextView textViewQty = (TextView) rowView.findViewById(R.id.rowtxtcol2);

        row1.setText(values.get(position).content);

        //getDataSetForToday(convertView, values.get(position).content);

        if(MainActivity.isTabToday) {
            if (values.get(position).todayTotalQty != 0)
                row2.setText("Rs." + String.format("%.2f", values.get(position).todayTotalValueRs));
            else
                row2.setText("");
            textViewQty.setText(String.valueOf(values.get(position).todayTotalQty));
        }
        else {
            if (values.get(position).customTotalQty != 0)
                row2.setText("Rs." + String.format("%.2f", values.get(position).customTotalValueRs));
            else
                row2.setText("");
            textViewQty.setText(String.valueOf(values.get(position).customTotalQty));
        }


        String s = values.get(position).content;



        // Change icon based on name

        if (s.equals(MainActivity.RAW_MATERIAL)) {
            imageView.setImageResource(R.drawable.rm_icon);
        } else if (s.equals(MainActivity.PRODUCT_INVENTORY)) {
            imageView.setImageResource(R.drawable.prodinv);
        } else if (s.equals(MainActivity.SALES)) {
            imageView.setImageResource(R.drawable.sale_icon);
        } else if (s.equals(MainActivity.IN_TRANSIT)) {
            imageView.setImageResource(R.drawable.line_chart_icon);
        } else if (s.equals(MainActivity.RETURNED)) {
            imageView.setImageResource(R.drawable.return_icon);
        }

        return rowView;
    }

    private void getDataSetForToday(View convertView, String type) {

        NeevDataLayer dataLayer = new NeevDataLayer();
        List products = null;
        String startdate;
        String enddate;
        Quantity = 0;
        Total = 0;

        if (type == MainActivity.RAW_MATERIAL)
            products = dataLayer.retrieveAllRawMaterialFromLocalStore();
        else
            products = dataLayer.retrieveAllRawMaterialFromLocalStore();
        if (MainActivity.isTabToday) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH) + 1;
            int day = c.get(Calendar.DAY_OF_MONTH);
            startdate = enddate = day + "/" + month + "/" + year;
        } else {
            startdate = MainActivity.fromDay + "/" + MainActivity.fromMonth + "/" + MainActivity.fromYear;
            enddate = MainActivity.toDay + "/" + MainActivity.toMonth + "/" + MainActivity.toYear;
        }

        for (int i = 0; i < products.size(); i++) {
            ParseObject po = (ParseObject) products.get(i);
            String name = (String) po.get("Name");
            List list = dataLayer.retrieveDetailData(type, null, startdate, enddate);
            float quantity = 0;
            if (list != null) {
                for (int j = 0; j < list.size(); j++) {
                    ParseObject po2 = (ParseObject) list.get(j);
                    int q = (int) po2.get("Quantity");
                    Quantity = Quantity + q;
                    float totalprice = Float.parseFloat(po2.get("Total").toString());
                    Total = Total + totalprice;
                }

            } else {

            }
            }
        }
    }
   

