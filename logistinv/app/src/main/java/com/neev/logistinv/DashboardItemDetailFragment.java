package com.neev.logistinv;

import android.graphics.Color;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.neev.example.R;
import com.neev.logistinv.dummy.DummyContent;

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
    public static String ARG_PANE = "today";
    private GestureDetectorCompat mDetector;

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;
    /**
     * The pane in which this fragment is presenting.
     */
    private String mPane;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DashboardItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
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

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.dashboarditem_detail)).setText(mItem.content);
        }
        else
            ((TextView) rootView.findViewById(R.id.dashboarditem_detail)).setText(getArguments().getString(ARG_ITEM_ID));
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
        final Button btn = new Button(getActivity());
        btn.setText("MANAGE DATA");
        btn.setBackgroundColor(Color.RED);
        final FrameLayout fl = (FrameLayout) getView().getParent();
        fl.addView(btn, params);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(view.getContext(),
                        "Button clicked index = " + mItem.content, Toast.LENGTH_SHORT)
                        .show();
                fl.removeView(btn);
                //Start Manage Data activity here.
                //add code for finding which item is displaying currently
                //fix color and position of button
            }
        });
        return false;
    }
}
