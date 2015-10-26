package com.neev.logistinv;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.neev.example.R;
import com.neev.logistinv.dashboarditemlistcontent.DashboardItemListContent;
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

        BarData data = new BarData(getXAxisValues(), getDataSetForToday());
        chart.setData(data);
        chart.setDescription("");
        chart.animateXY(2000, 2000);
        chart.invalidate();
        chart.getLegend().setEnabled(false);

        final Button button = (Button)rootView.findViewById(R.id.Next);
        final Button button1 = (Button) rootView.findViewById(R.id.button);
        button1.setVisibility(View.INVISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ViewGroup chartParent = (ViewGroup) rootView.findViewById(R.id.chartParent);
                HorizontalBarChart chart1 = (HorizontalBarChart) chartParent.findViewById(R.id.chart);
                BarData data = new BarData(getXAxisValues(), getDataSetMoney());
                chart1.setData(data);
                chart1.setDescription("");
                chart1.animateXY(2000, 2000);
                chart1.invalidate();
                chart1.getLegend().setEnabled(false);
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

        if(mItem == MainActivity.RAW_MATERIAL) {
            products = dataLayer.retrieveAllRawMaterialFromLocalStore();
            for (int i = 0; i < products.size(); i++) {
                ParseObject po = (ParseObject) products.get(i);
                String name = (String) po.get("Name");
                List list = dataLayer.retrieveDetailData(mItem,name,"22/10/2015","22/10/2015" );
                float quantity = 0;
                if(list !=null) {
                    for (int j = 0; j < list.size(); j++) {
                        ParseObject po2 = (ParseObject) list.get(j);
                        int q = (int) po2.get("Total");
                        quantity = quantity + q;
                    }
                    BarEntry barEntry = new BarEntry(quantity, i);
                    valueSet1.add(barEntry);
                }
                else {
                    BarEntry barEntry = new BarEntry(000.00f, i);
                    valueSet1.add(barEntry);
                }
            }
        }
        else {

            /*BarEntry v1e1 = new BarEntry(10.000f, 0); // Jan
            valueSet1.add(v1e1);
            BarEntry v1e2 = new BarEntry(110.000f, 1); // Feb
            valueSet1.add(v1e2);
            BarEntry v1e3 = new BarEntry(80.000f, 2); // Mar
            valueSet1.add(v1e3);
            BarEntry v1e4 = new BarEntry(40.000f, 3); // Apr
            valueSet1.add(v1e4);
            BarEntry v1e5 = new BarEntry(80.000f, 4); // May
            valueSet1.add(v1e5);
            BarEntry v1e6 = new BarEntry(20.000f, 5); // Jun
            valueSet1.add(v1e6);*/
            products = dataLayer.retrieveAllFinishedProductFromLocalStore();
            for (int i = 0; i < products.size(); i++) {
                ParseObject po = (ParseObject) products.get(i);
                String name = (String) po.get("Name");
                List list = dataLayer.retrieveDetailData(mItem,name,"22/10/2015","22/10/2015" );
                float quantity = 0;
                if(list !=null) {
                    for (int j = 0; j < list.size(); j++) {
                        ParseObject po2 = (ParseObject) list.get(j);
                        int q = (int) po2.get("Total");
                        quantity = quantity + q;
                    }
                    BarEntry barEntry = new BarEntry(quantity, i);
                    valueSet1.add(barEntry);
                }
                else {
                    BarEntry barEntry = new BarEntry(000.00f, i);
                    valueSet1.add(barEntry);
                }
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
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM|Gravity.CENTER;

        final Button btn = new Button(getActivity());
        btn.setText("MANAGE DATA");



        final FrameLayout fl = (FrameLayout) getView().getParent();

        fl.addView(btn, params);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String tempString = "Inventory";
                if(mItem == null){
                    tempString = getArguments().getString(ARG_ITEM_ID);
                }
                else
                    tempString = mItem;

                final FrameLayout fCurrentView = (FrameLayout) view.getParent();
                fCurrentView.removeView(btn);
                Intent myIntent = new Intent(view.getContext(),ManageDataActivity.class);
                myIntent.putExtra("item_type", "inventory"); //Optional parameters
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

        if(mItem == MainActivity.RAW_MATERIAL) {
            products = dataLayer.retrieveAllRawMaterialFromLocalStore();
            for (int i = 0; i < products.size(); i++) {
                ParseObject po = (ParseObject) products.get(i);
                String name = (String) po.get("Name");
                List list = dataLayer.retrieveDetailData(mItem,name,"22/10/2015","22/10/2015" );
                float quantity = 0;
                if(list !=null) {
                    for (int j = 0; j < list.size(); j++) {
                        ParseObject po2 = (ParseObject) list.get(j);
                        int q = (int) po2.get("Quantity");
                        quantity = quantity + q;
                    }
                    BarEntry barEntry = new BarEntry(quantity, i);
                    valueSet1.add(barEntry);
                }
                else {
                    BarEntry barEntry = new BarEntry(000.00f, i);
                    valueSet1.add(barEntry);
                }
            }
        }
        else
        {
            /*BarEntry v1e1 = new BarEntry(500.000f, 0); // HANDMADE PAPER BAGS
            valueSet1.add(v1e1);
            BarEntry v1e2 = new BarEntry(500.000f, 1); // DIARIES
            valueSet1.add(v1e2);
            BarEntry v1e3 = new BarEntry(500.000f, 2); // TABLE CLOTHS AND NAPKINS
            valueSet1.add(v1e3);
            BarEntry v1e4 = new BarEntry(500.000f, 3); // CUSHION COVERS
            valueSet1.add(v1e4);
            BarEntry v1e5 = new BarEntry(500.000f, 4); // TABLE MATS
            valueSet1.add(v1e5);
            BarEntry v1e6 = new BarEntry(500.000f, 5); // HANDMADE PAPER CARDS
            valueSet1.add(v1e6);
            BarEntry v1e7 = new BarEntry(500.000f, 6); // SHAGUN ENVELOPES
            valueSet1.add(v1e7);
            BarEntry v1e8 = new BarEntry(500.000f, 7); // BED COVERS
            valueSet1.add(v1e8) ;*/

            products = dataLayer.retrieveAllFinishedProductFromLocalStore();
            for (int i = 0; i < products.size(); i++) {
                ParseObject po = (ParseObject) products.get(i);
                String name = (String) po.get("Name");
                List list = dataLayer.retrieveDetailData(mItem,name,"22/10/2015","22/10/2015" );
                float quantity = 0;
                if(list !=null) {
                    for (int j = 0; j < list.size(); j++) {
                        ParseObject po2 = (ParseObject) list.get(j);
                        int q = (int) po2.get("Quantity");
                        quantity = quantity + q;
                    }
                    BarEntry barEntry = new BarEntry(quantity, i);
                    valueSet1.add(barEntry);
                }
                else {
                    BarEntry barEntry = new BarEntry(000.00f, i);
                    valueSet1.add(barEntry);
                }
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


        /*BarEntry v1e1 = new BarEntry(500.000f, 0); // HANDMADE PAPER BAGS
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(500.000f, 1); // DIARIES
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(500.000f, 2); // TABLE CLOTHS AND NAPKINS
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(500.000f, 3); // CUSHION COVERS
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(500.000f, 4); // TABLE MATS
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(500.000f, 5); // HANDMADE PAPER CARDS
        valueSet1.add(v1e6);
        BarEntry v1e7 = new BarEntry(500.000f, 6); // SHAGUN ENVELOPES
        valueSet1.add(v1e7);
        BarEntry v1e8 = new BarEntry(500.000f, 7); // BED COVERS
        valueSet1.add(v1e8);*/


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
            for (int i = 0; i < products.size(); i++) {
                ParseObject po = (ParseObject) products.get(i);

                xAxis.add((String) po.get("Name"));
            }
        }

        else  if(mItem == MainActivity.SALES || mItem == MainActivity.RETURNED  || mItem == MainActivity.IN_TRANSIT || mItem == MainActivity.PRODUCT_INVENTORY)
        {//TODO : check the list category and add the types.
            products = dataLayer.retrieveAllFinishedProductFromLocalStore();
            for (int i = 0; i < products.size(); i++) {
                ParseObject po = (ParseObject) products.get(i);

                xAxis.add((String) po.get("Name"));
            }
        }
        return xAxis;
    }

}
