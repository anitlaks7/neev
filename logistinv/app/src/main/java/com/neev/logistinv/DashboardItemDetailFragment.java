package com.neev.logistinv;


import android.content.Intent;
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
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.neev.example.R;
import com.parse.ParseObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


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
    public static  String ARG_ITEM_ID = "item_id";
    public  String PANE = "ARG_PANE";
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

    public DashboardItemDetailFragment(){
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
        BarChart chart =  (BarChart)chartParent.findViewById(R.id.chart);

        mDetector = new GestureDetectorCompat(getActivity(),this);
        // Set the gesture detector as the double tap
        // listener.
        mDetector.setOnDoubleTapListener(this);
        String text = TextValue();
        ((TextView) rootView.findViewById(R.id.text1)).setText(text + " (Quantity)");
        ArrayList XAxisValues=getXAxisValues();
        ArrayList DataSetForToday= getDataSetForToday();
        BarData data=null;
        if(XAxisValues.size()!=0 && DataSetForToday.size()!=0) {
             data = new BarData(XAxisValues, DataSetForToday);

        chart.setData(data);
        chart.setDescription("");
        chart.animateXY(2000, 2000);
        chart.invalidate();
        chart.getLegend().setEnabled(false);
        chart.getXAxis().setTextSize(12.0f);
        chart.getBarData().setValueTextSize(10.0f);
       // chart.setDrawValueAboveBar(false);
        }
        else
        {
            Toast toast = Toast.makeText(getActivity(), "Please be online for the databases to sync for the first time.", Toast.LENGTH_LONG);
            toast.show();
        }

        final Button button = (Button)rootView.findViewById(R.id.Next);
        final Button button1 = (Button) rootView.findViewById(R.id.button);
        button1.setVisibility(View.INVISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ViewGroup chartParent = (ViewGroup) rootView.findViewById(R.id.chartParent);
                HorizontalBarChart chart1 = (HorizontalBarChart) chartParent.findViewById(R.id.chart);
                ArrayList XAxisValues=getXAxisValues();
                ArrayList DataSetForToday= getDataSetMoney();
                BarData data=null;
                if(XAxisValues.size()!=0 && DataSetForToday.size()!=0) {
                    data = new BarData(XAxisValues, DataSetForToday);

                    chart1.setData(data);
                    chart1.setDescription("");
                    chart1.animateXY(2000, 2000);
                    chart1.invalidate();
                    chart1.getLegend().setEnabled(false);
                    chart1.getXAxis().setTextSize(12.0f);
                    chart1.getBarData().setValueTextSize(10.0f);
                    chart1.setDrawValueAboveBar(false);
                }
                else
                {
                    Toast toast = Toast.makeText(getActivity(), "Please be online for the databases to sync for the first time.", Toast.LENGTH_LONG);
                    toast.show();
                }
                button.setVisibility(View.INVISIBLE);
                button1.setVisibility(View.VISIBLE);
                String text1 = TextValue();
                ((TextView) rootView.findViewById(R.id.text1)).setText(text1 + " (in Rupees)");


            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ViewGroup chartParent = (ViewGroup) rootView.findViewById(R.id.chartParent);
                HorizontalBarChart chart1 = (HorizontalBarChart) chartParent.findViewById(R.id.chart);
                BarData data = new BarData(getXAxisValues(), getDataSetForToday());
                chart1.setData(data);
                chart1.setDescription("");
                chart1.animateXY(2000, 2000);
                chart1.invalidate();
                chart1.getLegend().setEnabled(false);
                chart1.getXAxis().setTextSize(12.0f);
                chart1.getBarData().setValueTextSize(10.0f);
                chart1.setDrawValueAboveBar(false);
                button.setVisibility(View.VISIBLE);
                button1.setVisibility(View.INVISIBLE);
                String text2 = TextValue();
                ((TextView) rootView.findViewById(R.id.text1)).setText(text2 + " (Quantity)");

            }
        });

        chart.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                // ... Respond to touch events
                return mDetector.onTouchEvent(event);

            }
        });

        return rootView;
    }

public String TextValue()
{
    String text = null;
    if (mItem == MainActivity.RAW_MATERIAL) {
        text = "RAW MATERIAL";
    }
    else if (mItem == MainActivity.PRODUCT_INVENTORY)

    {
        text = "PRODUCT INVENTORY";
    }
    else if (mItem == MainActivity.RETURNED)

    {
        text = "RETURNED";
    }
    else if (mItem == MainActivity.IN_TRANSIT)

    {
        text = "IN TRANSIT";
    }
    else if (mItem == MainActivity.SALES)

    {
        text = "SALES";
    }
    return text;
}

    public ArrayList<BarDataSet> getDataSetMoney()
    {
        ArrayList<BarDataSet> dataSets = null;
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();

        NeevDataLayer dataLayer = new NeevDataLayer();
        List products = null;
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

        if(mItem == MainActivity.RAW_MATERIAL) {
            products = dataLayer.retrieveAllRawMaterialFromLocalStore();
            if(products.size()!=0) {
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
            else
            {
                Toast toast = Toast.makeText(getActivity(), "Please be online for the databases to sync for the first time.", Toast.LENGTH_LONG);
                toast.show();
            }
        }
        else
        {
            products = dataLayer.retrieveAllFinishedProductFromLocalStore();
            if(products.size()!=0) {
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
            else
            {
                Toast toast = Toast.makeText(getActivity(), "Please be online for the databases to sync for the first time.", Toast.LENGTH_LONG);
                toast.show();
            }

        }

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "RAW MATERIALS");
        barDataSet1.setColors(ColorTemplate.VORDIPLOM_COLORS);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);

        return dataSets;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        if(btn != null) {
            final RelativeLayout  fCurrentView = (RelativeLayout) getView();
            fCurrentView.removeView(btn);
        }
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
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
                if(mItem == null){
                    tempString = getArguments().getString(ARG_ITEM_ID);
                }
                else
                    tempString = mItem;
                fl.removeView(btn);
                Intent myIntent = new Intent(view.getContext(),ManageDataActivity.class);
                myIntent.putExtra("item_type",tempString); //Optional parameters
                startActivity(myIntent);


            }
        });
        return false;
    }

    private ArrayList<BarDataSet> getDataSetForToday() {
        ArrayList<BarDataSet> dataSets = null;
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        NeevDataLayer dataLayer = new NeevDataLayer();
        List products = null;
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

        if(mItem == MainActivity.RAW_MATERIAL) {
            products = dataLayer.retrieveAllRawMaterialFromLocalStore();

            if(products.size()!=0) {

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
            }
            else
            {
                Toast toast = Toast.makeText(getActivity(), "Please be online for the databases to sync for the first time.", Toast.LENGTH_LONG);
                toast.show();
            }
        }
        else
        {
            products = dataLayer.retrieveAllFinishedProductFromLocalStore();
            if(products.size()!=0) {
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
            }
            else
            {
                Toast toast = Toast.makeText(getActivity(), "Please be online for the databases to sync for the first time.", Toast.LENGTH_LONG);
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

    private ArrayList<String> getXAxisValues() {

        ArrayList<String> xAxis = new ArrayList<>();
        NeevDataLayer dataLayer = new NeevDataLayer();
        List products = null;
        if(mItem == MainActivity.RAW_MATERIAL) {

            products = dataLayer.retrieveAllRawMaterialFromLocalStore();
            if (products.size()!=0) {
                for (int i = 0; i < products.size(); i++) {
                    ParseObject po = (ParseObject) products.get(i);

                    xAxis.add((String) po.get("Name"));
                }
            }
            else
            {
                Toast toast = Toast.makeText(getActivity(), "Please be online for the databases to sync for the first time.", Toast.LENGTH_LONG);
                toast.show();
            }
        }

        else  if(mItem == MainActivity.SALES || mItem == MainActivity.RETURNED  || mItem == MainActivity.IN_TRANSIT || mItem == MainActivity.PRODUCT_INVENTORY)
        {//TODO : check the list category and add the types.
            products = dataLayer.retrieveAllFinishedProductFromLocalStore();
            if(products.size()!=0) {
                for (int i = 0; i < products.size(); i++) {
                    ParseObject po = (ParseObject) products.get(i);

                    xAxis.add((String) po.get("Name"));
                }
            }
            else
            {
                Toast toast = Toast.makeText(getActivity(), "Please be online for the databases to sync for the first time.", Toast.LENGTH_LONG);
                toast.show();
            }
        }
        return xAxis;
    }

}
