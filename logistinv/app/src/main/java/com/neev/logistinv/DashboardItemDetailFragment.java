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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.neev.example.R;
import com.neev.logistinv.dashboarditemlistcontent.DashboardItemListContent;

import java.util.ArrayList;

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
    public static final String ARG_ITEM_ID = "item_id";
    public  String ARG_PANE = "today";
    private GestureDetectorCompat mDetector;

    /**
     * The dummy content this fragment is presenting.
     */
    private DashboardItemListContent.DashboardListItem mItem;
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
            mItem = DashboardItemListContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
        if (getArguments().containsKey(ARG_PANE)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            /*
      The pane in which this fragment is presenting.
     */
            String mPane = getArguments().getString(ARG_PANE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDetector = new GestureDetectorCompat(getActivity(), this);
        // Set the gesture detector as the double tap
        // listener.
        mDetector.setOnDoubleTapListener(this);
        // Detect touched area
        View rootView = inflater.inflate(R.layout.fragment_dashboarditem_detail, container, false);
        BarChart chart = (BarChart) rootView.findViewById(R.id.chartMain);
        BarData data = new BarData(getXAxisValues(), getDataSetForToday());
        chart.setData(data);
        chart.setDescription("");
        chart.animateXY(2000, 2000);
        chart.invalidate();
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        // chart.setMaxVisibleValueCount(60);
        //Tilts the chart
        //chart.setRotationX(View.TEXT_ALIGNMENT_VIEW_START);
        //chart.setLabelFor(SCROLLBAR_POSITION_LEFT);
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //RelativeLayout rl = (RelativeLayout) findViewById(R.id.relativeMain);
       /* rootView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent();
                                            intent.setClass(getActivity().mContext, QuantityActivity.class);
                                            startActivity(intent);
                                        }
                                    });*/
        // Show the dummy content as text in a TextView.
        /*if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.dashboarditem_detail)).setText(mItem.content);
        }
        else
            ((TextView) rootView.findViewById(R.id.dashboarditem_detail)).setText(getArguments().getString(ARG_ITEM_ID));*/
        rootView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                  // ... Respond to touch events
                return mDetector.onTouchEvent(event);

            }
        });

        return rootView;
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
        //params.setMargins(2,4,2,4);
        final Button btn = new Button(getActivity());
        btn.setText("MANAGE DATA");
        //btn.setBackgroundColor(Color.RED);
        //btn.setBackground(getResources().getDrawable(R.drawable.mybutton));

        final FrameLayout fl = (FrameLayout) getView().getParent();
        fl.addView(btn, params);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String tempString = "Inventory";
                if(mItem == null){
                    tempString = getArguments().getString(ARG_ITEM_ID);
                }
                else
                    tempString = mItem.content;
               /*Toast.makeText(view.getContext(),
                        "Button clicked index = " + tempString, Toast.LENGTH_SHORT)
                        .show();*/
                final FrameLayout fCurrentView = (FrameLayout) view.getParent();
                fCurrentView.removeView(btn);
                Intent myIntent = new Intent(view.getContext(),ManageDataActivity.class);
                myIntent.putExtra("item_type", tempString); //Optional parameters
                startActivity(myIntent);
               // return true;

            }
        });
        return false;
    }
    private ArrayList<BarDataSet> getDataSetForToday() {
        ArrayList<BarDataSet> dataSets = null;
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();

        BarEntry v1e1 = new BarEntry(2500.000f, 0); // HANDMADE PAPER BAGS
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(5000.000f, 1); // DIARIES
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(110000.000f, 2); // TABLE CLOTHS AND NAPKINS
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(10000.000f, 3); // CUSHION COVERS
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(85000.000f, 4); // TABLE MATS
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(10000.000f, 5); // HANDMADE PAPER CARDS
        valueSet1.add(v1e6);
        BarEntry v1e7 = new BarEntry(8000.000f, 6); // SHAGUN ENVELOPES
        valueSet1.add(v1e7);
        BarEntry v1e8 = new BarEntry(8500.000f, 7); // BED COVERS
        valueSet1.add(v1e8);


        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "SALES OF PRODUCTS");
        barDataSet1.setColors(ColorTemplate.JOYFUL_COLORS);


        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);


        return dataSets;

    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("HANDMADE PAPER BAGS");
        xAxis.add("DIARIES");
        xAxis.add("TABLE CLOTHS AND NAPKINS");
        xAxis.add("CUSHION COVERS");
        xAxis.add("TABLE MATS");
        xAxis.add("HANDMADE PAPER CARDS");
        xAxis.add("SHAGUN ENVELOPES");
        xAxis.add("BED COVERS");
        return xAxis;
    }

}
