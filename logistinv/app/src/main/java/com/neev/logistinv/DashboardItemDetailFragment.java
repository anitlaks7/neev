package com.neev.logistinv;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.XAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.neev.example.R;
import com.parse.ParseObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;


/**
 * A fragment representing a single DashboardItem detail screen.
 * This fragment is either contained in a {@link DashboardItemListActivity}
 * in two-pane mode (on tablets) or a {@link DashboardItemDetailActivity}
 * on handsets.
 */

public class DashboardItemDetailFragment extends Fragment
        implements GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static String ARG_ITEM_ID = "item_id";
    public String PANE = "ARG_PANE";
    private GestureDetectorCompat mDetector;

    /**
     * The dummy content this fragment is presenting.
     */
    private String mItem;
    /**
     * The pane in which this fragment is presenting.
     */
    private String mPane;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    private Button btn;

    public DashboardItemDetailFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = getArguments().getString(ARG_ITEM_ID);
        }
        if (getArguments().containsKey(PANE)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            /*
      The pane in which this fragment is presenting.
     */
            String mPane = getArguments().getString(PANE);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        // Detect touched area

        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_dashboarditem_detail, container, false);
        final ViewGroup chartParent = (ViewGroup) rootView.findViewById(R.id.chartParent);
        BarChart chart = (BarChart) chartParent.findViewById(R.id.chart);

        mDetector = new GestureDetectorCompat(getActivity(), this);
        // Set the gesture detector as the double tap
        // listener.
        mDetector.setOnDoubleTapListener(this);
        Button btn = (Button) rootView.findViewById(R.id.btnMngData);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String tempString = MainActivity.RAW_MATERIAL;
                if (mItem == null) {
                    tempString = getArguments().getString(ARG_ITEM_ID);
                } else
                    tempString = mItem;

                Intent myIntent = new Intent(view.getContext(), ManageDataActivity.class);
                myIntent.putExtra("item_type", tempString); //Optional parameters
                startActivity(myIntent);
            }
        });

        String text = TextValue();
        ((TextView) rootView.findViewById(R.id.text1)).setText(text + " (Quantity)");
        ArrayList XAxisValues = getXAxisValues();
        ArrayList DataSetForToday = getDataSetForToday();
        BarData data;
        if (XAxisValues.size() != 0 && DataSetForToday.size() != 0) {
            data = new BarData(XAxisValues, DataSetForToday);

            chart.setData(data);
            chart.setDescription("");
            chart.animateXY(2000, 2000);
            chart.invalidate();
            chart.getLegend().setEnabled(false);
            chart.getXAxis().setTextSize(12.0f);
            chart.getBarData().setValueTextSize(10.0f);
            // chart.setDrawValueAboveBar(false);
        } else {
            Toast toast = Toast.makeText(getActivity(), "Please be online for the databases to sync for the first time.", Toast.LENGTH_SHORT);
            toast.show();
        }


        chart.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                // ... Respond to touch events
                return mDetector.onTouchEvent(event);

            }
        });

        return rootView;
    }

    public String TextValue() {
        String text = null;
        if (mItem.equals(MainActivity.RAW_MATERIAL)) {
            text = "RAW MATERIAL";
        } else if (mItem.equals(MainActivity.PRODUCT_INVENTORY))

        {
            text = "PRODUCT INVENTORY";
        } else if (mItem.equals(MainActivity.RETURNED))

        {
            text = "RETURNED";
        } else if (mItem.equals(MainActivity.IN_TRANSIT))

        {
            text = "IN TRANSIT";
        } else if (mItem.equals(MainActivity.SALES))

        {
            text = "SALES";
        }
        return text;
    }

    public ArrayList<BarDataSet> getDataSetMoney() {
        ArrayList<BarDataSet> dataSets;
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();

        NeevDataLayer dataLayer = new NeevDataLayer();
        List products;
        String startdate;
        String enddate;


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
        if (mItem.equals(MainActivity.RAW_MATERIAL)) {
            products = dataLayer.retrieveAllRawMaterialFromLocalStore();
            if (products.size() != 0) {
                for (int i = 0; i < products.size(); i++) {
                    ParseObject po = (ParseObject) products.get(i);
                    String name = (String) po.get("Name");
                    List list = dataLayer.retrieveDetailData(mItem, name, startdate, enddate);
                    float quantity = 0;
                    if (list != null) {
                        for (int j = 0; j < list.size(); j++) {
                            ParseObject po2 = (ParseObject) list.get(j);
                            float q = Float.parseFloat(po2.get("Total").toString());
                            quantity = quantity + q;
                        }
                        BarEntry barEntry = new BarEntry(quantity, i);
                        valueSet1.add(barEntry);
                    } else {
                        BarEntry barEntry = new BarEntry(000.00f, i);
                        valueSet1.add(barEntry);
                    }
                }
            } else {
                Toast toast = Toast.makeText(getActivity(), "Please be online for the databases to sync for the first time.", Toast.LENGTH_SHORT);
                toast.show();
            }
        } else {
            products = dataLayer.retrieveAllFinishedProductFromLocalStore();
            for (int i = 0; i < products.size(); i++) {
                ParseObject po = (ParseObject) products.get(i);
                String name = (String) po.get("Name");
                List list = dataLayer.retrieveDetailData(mItem, name, startdate, enddate);
                float quantity = 0;
                if (list != null) {
                    for (int j = 0; j < list.size(); j++) {
                        ParseObject po2 = (ParseObject) list.get(j);
                        float q = Float.parseFloat(po2.get("Total").toString());
                        quantity = quantity + q;
                    }
                    BarEntry barEntry = new BarEntry(quantity, i);
                    valueSet1.add(barEntry);
                } else {
                    BarEntry barEntry = new BarEntry(000.00f, i);
                    valueSet1.add(barEntry);
                }
            }

        }

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "RAW MATERIALS");
        barDataSet1.setColors(ColorTemplate.JOYFUL_COLORS);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);

        return dataSets;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        String text1 = TextValue();
        final ViewGroup chartParent = (ViewGroup) getView().findViewById(R.id.chartParent);
        HorizontalBarChart chart1 = (HorizontalBarChart) chartParent.findViewById(R.id.chart);
        final BarData data;
        //final PieData pdata;
        final String rupees;
        ArrayList XAxisValues = getXAxisValues();
        ArrayList DataSetForToday = getDataSetForToday();
        if (XAxisValues.size() != 0 && DataSetForToday.size() != 0) {
            if (((TextView) getView().findViewById(R.id.text1)).getText().toString().contains("Quantity")) {
                ((TextView) getView().findViewById(R.id.text1)).setText(text1 + " (in Rupees)");
                data = new BarData(getXAxisValues(), getDataSetMoney());
                rupees = "  Rs. ";

            }
        /* TODO placeholder for Pie Chart enhancement
        else if(((TextView) getView().findViewById(R.id.text1)).getText().toString().contains("in Rupees"){
            ((TextView) getView().findViewById(R.id.text1)).setText(text1 + " (PieChart)");

            pdata = new PieData(getXAxisValues(),(PieDataSet) getDataSetForToday());


        }*/
            else /*(((TextView) getView().findViewById(R.id.text1)).getText().toString().contains("Quantity"))*/ {
                ((TextView) getView().findViewById(R.id.text1)).setText(text1 + " (Quantity)");
                data = new BarData(getXAxisValues(), getDataSetForToday());
                rupees = "  ";
            }

            chart1.setData(data);
            chart1.setDescription("");
            chart1.animateXY(2000, 2000);
            chart1.invalidate();
            chart1.getLegend().setEnabled(false);
            chart1.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
            chart1.getXAxis().setTypeface(Typeface.MONOSPACE);
            /* don't draw the values at the top of the bar or within the bar*/
            data.getDataSetByIndex(0).setDrawValues(false);
            //chart1.getAxisLeft().setDrawLabels(false);
            //chart1.getAxisRight().setDrawLabels(false);
            //chart1.setViewPortOffsets(0f, 0f, 0f, 0f);
            XAxisValueFormatter xf = new XAxisValueFormatter() {
                @Override
                public String getXValue(String s, int i, ViewPortHandler viewPortHandler) {

                    float d = data.getDataSetByIndex(0).getYValForXIndex(i);
                    //String spacestring = new String();
                    int numspaces = 30 - s.length();
                    s = s + String.format("%" + numspaces + "s", " ");
                   // for (int cnt = 0; cnt < numspaces; cnt++)
                        //spacestring += "";
                    /*
                    String result = new String();
                    String[] token = new String[5];
                    char[] res = new char[30];
                    int indexintores = 0;
                    if(s.length()>14) {
                        token = s.split("\\s+");
                        for (int count = 0; count < token.length; count++) {
                            token[count].getChars(0, 3, res, indexintores);
                            indexintores += 4;
                            res[indexintores] = ' ';
                            indexintores++;
                        }
                        return new String(res) + " " + Float.toString(d);
                    }
                    */
                    //Log.d("Strings",s + rupees + Float.toString(d));
                    return s + rupees + Float.toString(d);
                }
            };
            chart1.getXAxis().setValueFormatter(xf);
            chart1.getBarData().setValueTextSize(10.0f);
        } else {
            Toast toast = Toast.makeText(getActivity(), "Please be online for the databases to sync for the first time.", Toast.LENGTH_SHORT);
            toast.show();
        }
        /* control if the quantity or rupees values should be above or below the bar*/
        //chart1.setDrawValueAboveBar(true);
        return false;
    }

        @Override
        public boolean onDoubleTap (MotionEvent e){
            return false;
        }

        @Override
        public boolean onDoubleTapEvent (MotionEvent e){
            return false;
        }

        @Override
        public boolean onDown (MotionEvent e){
       /* if(btn != null) {
            final RelativeLayout  fCurrentView = (RelativeLayout) getView();
            fCurrentView.removeView(btn);
        }*/
            return false;
        }

        @Override
        public void onShowPress (MotionEvent e){

        }

        @Override
        public boolean onSingleTapUp (MotionEvent e){
            return false;
        }

        @Override
        public boolean onScroll (MotionEvent e1, MotionEvent e2,float distanceX, float distanceY){
            return false;
        }

        @Override
        public void onLongPress (MotionEvent e){

        }

        @Override
        public boolean onFling (MotionEvent e1, MotionEvent e2,float velocityX, float velocityY){
            /*
            if (false) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

                btn = new Button(getActivity());
                btn.setText("MANAGE DATA");

                params.addRule(RelativeLayout.CENTER_IN_PARENT);
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

                final RelativeLayout fl = (RelativeLayout) getView();


                fl.addView(btn, params);


                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        String tempString = MainActivity.RAW_MATERIAL;
                        if (mItem == null) {
                            tempString = getArguments().getString(ARG_ITEM_ID);
                        } else
                            tempString = mItem;
                        fl.removeView(btn);
                        Intent myIntent = new Intent(view.getContext(), ManageDataActivity.class);
                        myIntent.putExtra("item_type", tempString); //Optional parameters
                        startActivity(myIntent);
                    }
                });
            }*/
            return false;
        }

        private ArrayList<BarDataSet> getDataSetForToday () {
            ArrayList<BarDataSet> dataSets;
            ArrayList<BarEntry> valueSet1 = new ArrayList<>();
            NeevDataLayer dataLayer = new NeevDataLayer();
            List products;
            String startdate;
            String enddate;

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

            if (mItem.equals(MainActivity.RAW_MATERIAL)) {
                products = dataLayer.retrieveAllRawMaterialFromLocalStore();

                if (products.size() != 0) {

                    for (int i = 0; i < products.size(); i++) {
                        ParseObject po = (ParseObject) products.get(i);
                        String name = (String) po.get("Name");
                        List list = dataLayer.retrieveDetailData(mItem, name, startdate, enddate);
                        float quantity = 0;
                        if (list != null) {
                            for (int j = 0; j < list.size(); j++) {
                                ParseObject po2 = (ParseObject) list.get(j);
                                int q = (int) po2.get("Quantity");
                                quantity = quantity + q;
                            }
                            BarEntry barEntry = new BarEntry(quantity, i);
                            valueSet1.add(barEntry);
                        } else {
                            BarEntry barEntry = new BarEntry(000.00f, i);
                            valueSet1.add(barEntry);
                        }
                    }
                } else {
                    Toast toast = Toast.makeText(getActivity(), "Please be online for the databases to sync for the first time.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            } else {
                products = dataLayer.retrieveAllFinishedProductFromLocalStore();
                if (products.size() != 0) {
                    for (int i = 0; i < products.size(); i++) {
                        ParseObject po = (ParseObject) products.get(i);
                        String name = (String) po.get("Name");
                        List list = dataLayer.retrieveDetailData(mItem, name, startdate, enddate);
                        float quantity = 0;
                        if (list != null) {
                            for (int j = 0; j < list.size(); j++) {
                                ParseObject po2 = (ParseObject) list.get(j);
                                int q = (int) po2.get("Quantity");
                                quantity = quantity + q;
                            }
                            BarEntry barEntry = new BarEntry(quantity, i);
                            valueSet1.add(barEntry);
                        } else {
                            BarEntry barEntry = new BarEntry(000.00f, i);
                            valueSet1.add(barEntry);
                        }
                    }
                } else {
                    Toast toast = Toast.makeText(getActivity(), "Please be online for the databases to sync for the first time.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        /*List items = null;

        if(mItem == "Inventory")
        {
            products = dataLayer.retrieveTodayRawMaterial();
            items = dataLayer.retrieveAllRawMaterialFromLocalStore();

            for(int i=0;i<products.size();i++)
            {
                ParseObject po = (ParseObject) products.get(i);
                String materialName = (String)po.get("Name");
                for(int j = 0; j < items.size();j++)
                {
                    ParseObject item = (ParseObject) items.get(j);
                    if(materialName == (String)item.get("Name"))
                    {
                        ParseObject val = (ParseObject) products.get(i);
                        valueSet1.add(new BarEntry(((float) val.get("Quantity")), j));
                    }
                }
            }
        }
        else  if(mItem == "Sales" || mItem == "Returned" || mItem =="In Transit")
        {
            products = dataLayer.retrieveTodayProduct();
            for(int i=0;i<products.size();i++)
            {
                ParseObject po = (ParseObject)products.get(i);
                valueSet1.add(new BarEntry(((float) po.get("Quantity")), i));
            }
        }*/

            BarDataSet barDataSet1 = new BarDataSet(valueSet1, "SALES OF PRODUCTS");
            barDataSet1.setColors(ColorTemplate.JOYFUL_COLORS);

            dataSets = new ArrayList<>();
            dataSets.add(barDataSet1);

            return dataSets;

        }

        private ArrayList<String> getXAxisValues () {

            ArrayList<String> xAxis = new ArrayList<>();
            NeevDataLayer dataLayer = new NeevDataLayer();
            List products;
            if (Objects.equals(mItem, MainActivity.RAW_MATERIAL)) {

                products = dataLayer.retrieveAllRawMaterialFromLocalStore();
                if (products.size() != 0) {
                    for (int i = 0; i < products.size(); i++) {
                        ParseObject po = (ParseObject) products.get(i);

                        xAxis.add((String) po.get("Name"));
                    }
                } else {
                    Toast toast = Toast.makeText(getActivity(), "Please be online for the databases to sync for the first time.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            } else if (mItem.equals(MainActivity.SALES) || mItem.equals(MainActivity.RETURNED) || mItem.equals(MainActivity.IN_TRANSIT) || mItem.equals(MainActivity.PRODUCT_INVENTORY)) {//TODO : check the list category and add the types.
                products = dataLayer.retrieveAllFinishedProductFromLocalStore();
                if (products.size() != 0) {
                    for (int i = 0; i < products.size(); i++) {
                        ParseObject po = (ParseObject) products.get(i);

                        xAxis.add((String) po.get("Name"));
                    }
                } else {
                    Toast toast = Toast.makeText(getActivity(), "Please be online for the databases to sync for the first time.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            return xAxis;
        }
    }
