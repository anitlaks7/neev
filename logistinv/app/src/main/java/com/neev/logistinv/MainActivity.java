package com.neev.logistinv;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.neev.example.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener,DashboardItemListFragment.Callbacks{

    public static int fromYear;
    public static int fromMonth;
    public static int fromDay;
    public static int toYear;
    public static int toMonth;
    public static int toDay;
    private static final String ARG_FROM_TO = "From";
    public static final String RAW_MATERIAL = "Raw Material";
    public static final String PRODUCT_INVENTORY = "Product Inventory";
    public static final String IN_TRANSIT = "In Transit";
    public static final String SALES = "Sales";
    public static final String RETURNED = "Returned";
    public static boolean isTabToday = true;
    public static String defaultPrevFromDate= null ;
    public static String defaultPrevToDate= null ;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    /**
     * String array to store the selected items string.
     */
    private ArrayList<String> mSelectedItems;

    public static DashboardItemDetailFragment mDashboardItemDetailFragmentToday;
    public static DashboardItemListFragment mDashboardItemListFragmentCustom;
    public static DashboardItemListFragment mDashboardItemListFragmentToday;
    public static DashboardItemDetailFragment mDashboardItemDetailFragmentCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        fromYear = c.get(Calendar.YEAR);
        fromMonth = c.get(Calendar.MONTH) +1;
        fromDay = c.get(Calendar.DAY_OF_MONTH);
        toYear = c.get(Calendar.YEAR);
        toMonth = c.get(Calendar.MONTH) +1;
        toDay = c.get(Calendar.DAY_OF_MONTH);
        defaultPrevFromDate = fromDay + "/" + fromMonth + "/" + fromYear;
        defaultPrevToDate = toDay+ "/" + toMonth + "/" + toYear;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDashboardItemDetailFragmentToday = new DashboardItemDetailFragment();
        mDashboardItemListFragmentCustom = new DashboardItemListFragment();
        mDashboardItemListFragmentToday = new DashboardItemListFragment();
        mDashboardItemDetailFragmentCustom = new DashboardItemDetailFragment();
        Bundle args = new Bundle();
        args.putString("ARG_PANE","custom");
        mDashboardItemListFragmentCustom.setArguments(args);
        mDashboardItemDetailFragmentCustom.setArguments(args);
        args.putString("ARG_PANE","today");
        mDashboardItemListFragmentToday.setArguments(args);
        mDashboardItemDetailFragmentToday.setArguments(args);

        if (findViewById(R.id.dashboarditem_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((DashboardItemListFragment) getFragmentManager()
                    .findFragmentById(R.id.dashboarditem_list))
                    .setActivateOnItemClick(true);
        }

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        assert actionBar != null;
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        // Show the Up button in the action bar.
        actionBar.setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        /*
      The {@link android.support.v4.view.PagerAdapter} that will provide
      fragments for each of the sections. We use a
      {@link FragmentPagerAdapter} derivative, which will keep every
      loaded fragment in memory. If this becomes too memory intensive, it
      may be best to switch to a
      {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        setContentView(R.layout.activity_main);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }

        //  Parse.enableLocalDatastore(this);
        //  Parse.initialize(this, "TnftdYLSYkSJHmlNmgm1Sa5bVrVEEPCo1g48vjOD", "I7aaYGQkikHBIjbkoqcQh5HalZXWAyiVGKZbzuBw");
        //  NeevDataLayer data = new NeevDataLayer();
        //  data.initialize();

       /* ParseObject rawMaterialList = new ParseObject("RawMaterialList");
        rawMaterialList.put("item1", "Cotton");
        rawMaterialList.put("item2", "Ink");
        rawMaterialList.saveInBackground();*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.plus_button) {
            Intent intent = new Intent(getApplicationContext(),
                    ListViewMultipleSelectionActivity.class);
            startActivity(intent);

            return true;
        }

        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            if (getFragmentManager().getBackStackEntryCount() == 0) {
                this.onBackPressed();
            } else {
                getFragmentManager().popBackStack();
            }
            return true;
        }

        if (id == R.id.action_managedata)
        {
            Intent myIntent = new Intent(this,ManageDataActivity.class);
            myIntent.putExtra("item_type", MainActivity.RAW_MATERIAL); //Optional parameters
            startActivity(myIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        if(tab.getPosition()==0)
            isTabToday = true;
        else
            isTabToday = false;

        //DashboardItemListFragment.adapter = new DashboardItemListAdapter(this, R.layout.dashboard_list_item_layout, DashboardItemListContent.returnSelectedItems());
        //DashboardItemListFragment.adapter.registerDataSetObserver(MainActivity.mDashboardItemListFragmentCustom.mObserver);
        //DashboardItemListFragment.adapter.registerDataSetObserver(MainActivity.mDashboardItemListFragmentToday.mObserver);
        //if(DashboardItemListFragment.adapter!=null) DashboardItemListFragment.adapter.notifyDataSetChanged();
        new NeevDataRecalculateAsyncTask(this).execute();
        mViewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
       /* ActionBar bar = getActionBar();
        ActionBar.Tab tab = bar.getTabAt(tab.getPosition());
        View view = getActionBar().getChildAt(tab).setBackgroundColor(Color.CYAN);
        TextView v = new TextView(getBaseContext());
        v.setBackground()
                tab.getPosition().setBackgroundColor()

        tab.setCustomView(v);*/
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * Callback method from {@link DashboardItemListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id, int containerView) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(DashboardItemDetailFragment.ARG_ITEM_ID, id);
            mDashboardItemDetailFragmentToday.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.dashboarditem_detail_container, mDashboardItemDetailFragmentToday)
                    .commit();
            isTabToday = true;

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID. This original code started a new intent and another
            // activity. Retained here if in case needed for future use.
            //Intent detailIntent = new Intent(this, DashboardItemDetailActivity.class);
            //detailIntent.putExtra(DashboardItemDetailFragment.ARG_ITEM_ID, id);
            //startActivity(detailIntent);

            Bundle arguments = new Bundle();
            arguments.putString("item_id", id);
            FragmentTransaction ft1 = getFragmentManager().beginTransaction();
            if (containerView == R.id.dashboarditem_list1) {
                arguments.putString("ARG_PANE", "today");
                mDashboardItemDetailFragmentToday.setArguments(arguments);
                if (mDashboardItemDetailFragmentToday.isAdded()) { // if the fragment is already in container
                    ft1.show(mDashboardItemDetailFragmentToday);
                } else { // fragment needs to replace on the frame container
                    ft1.replace(containerView, mDashboardItemDetailFragmentToday);
                    ft1.show(mDashboardItemDetailFragmentToday);
                }
                isTabToday = true;
            }
            else {

                arguments.putString("ARG_PANE", "custom");
                mDashboardItemDetailFragmentCustom.setArguments(arguments);
                if (mDashboardItemDetailFragmentCustom.isAdded()) { // if the fragment is already in container
                    ft1.show(mDashboardItemDetailFragmentCustom);
                } else { // fragment needs to replace on the frame container
                    ft1.replace(containerView, mDashboardItemDetailFragmentCustom);
                    ft1.show(mDashboardItemDetailFragmentCustom);
                }
                isTabToday = false;
            }

            ft1.addToBackStack("DashboardItemDetailFragment");
            ft1.commit();
        }
    }

    @Override
    public void onMultipleItemsSelected(ArrayList<String> selectedItems, int containerView) {
        mSelectedItems = selectedItems;
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView;
            rootView = getView();
            if(this.getArguments().getInt(ARG_SECTION_NUMBER) == 1 ) {
                rootView = inflater.inflate(R.layout.fragment_dashboarditem_list, container, false);
                //DashboardItemListFragment fragment = new DashboardItemListFragment();
                Bundle args = new Bundle();
                args.putString(DashboardItemListFragment.ARG_PANE, "today");
                mDashboardItemListFragmentToday.setArguments(args);
                FragmentTransaction ft1 = getFragmentManager().beginTransaction();
                // removes the existing fragment calling onDestroy
                ft1.replace(R.id.dashboarditem_list1, mDashboardItemListFragmentToday);
                ft1.commit();
            }
            else {
                rootView = inflater.inflate(R.layout.fragment_custom, container, false);
                EditText editText = (EditText) rootView.findViewById(R.id.editText2);
                editText.setText("From: " + "\n" + fromDay + "/" + fromMonth + "/" + fromYear);
                EditText editTextTo = (EditText) rootView.findViewById(R.id.editText3);
                editTextTo.setText("To: " + "\n" + toDay + "/" + toMonth + "/" + toYear);

                editText.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        DialogFragment newFragment = new DatePickerFragment();
                        Bundle args = new Bundle();
                        args.putString(ARG_FROM_TO, "From");
                        newFragment.setArguments(args);
                        newFragment.show(getFragmentManager(), "datePicker");
                    }
                });
                editTextTo.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        DialogFragment newFragment = new DatePickerFragment();
                        Bundle args = new Bundle();
                        args.putString(ARG_FROM_TO, "To");
                        newFragment.setArguments(args);
                        newFragment.show(getFragmentManager(), "datePicker");
                    }
                });
                //DashboardItemListFragment fragment1 = new DashboardItemListFragment();
                //fragment.setArguments(arguments);
                FragmentTransaction ft2 = getFragmentManager().beginTransaction();
                // removes the existing fragment calling onDestroy
                ft2.replace(R.id.dashboarditem_list_custom, mDashboardItemListFragmentCustom);
                ft2.commit();
            }

            return rootView;
        }
    }
    public static boolean ValidateDateOlder(String fromDate, String toDate){
        SimpleDateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy");
        boolean isOlder = false;

        try{

            if(dfDate.parse(toDate).before(dfDate.parse(fromDate))){
                isOlder = true;
            }
            else {
                isOlder = false;
            }
        }catch (Exception e){
            // e.printStackTrace();
        }
        return isOlder;
    }
    public static boolean ValidateMaxMinDate(String fromDate, String toDate){
        SimpleDateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy");
        boolean isInvalid = false;

        try{

            final Calendar c1 = Calendar.getInstance();
            String currentDate  = dfDate.format(c1.getTime());
            c1.add(Calendar.MONTH,-3);
            String PastDate  = dfDate.format(c1.getTime());
            if((dfDate.parse(fromDate).before(dfDate.parse(PastDate))) || (dfDate.parse(toDate).after(dfDate.parse(currentDate)))) {
                isInvalid = true;
            }
            else {
                isInvalid = false;
            }
        }catch (Exception e){
            // e.printStackTrace();
        }
        return isInvalid;
    }
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        private static final String SAVED_BUNDLE_TAG = "saved_bundle";
        static Bundle bundle;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH) ;
            int day = c.get(Calendar.DAY_OF_MONTH);

            bundle = getArguments();
            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month  , day);

        }


        public void onDateSet(DatePicker view, int year, int month, int day) {

            Log.w("DatePicker", "Date = " + year);
            Boolean IsInvalidDate =false;
            if (Objects.equals(bundle.getString(ARG_FROM_TO), "From")) {
                fromDay = day;
                fromMonth = month +1;
                fromYear = year;
                ((EditText) getActivity().findViewById(R.id.editText2)).setText("From: " + "\n"  + fromDay + "/" + (fromMonth ) + "/" + fromYear);
            } else {
                toDay = day;
                toMonth = month +1;
                toYear = year;
                ((EditText) getActivity().findViewById(R.id.editText3)).setText("To: " + "\n" + toDay + "/" + (toMonth ) + "/" + toYear);
            }
            if ((ValidateMaxMinDate(fromDay + "/" + fromMonth + "/" + fromYear, toDay + "/" + toMonth + "/" + toYear))) {
                IsInvalidDate=true;
                Toast toast = Toast.makeText(getActivity(), "Pick dates within the last 3 months", Toast.LENGTH_LONG);
                toast.show();
            } else if ((ValidateDateOlder(fromDay + "/" + fromMonth + "/" + fromYear, toDay + "/" + toMonth + "/" + toYear))) {
                IsInvalidDate=true;
                Toast toast = Toast.makeText(getActivity(), "\"From\" date cannot be after \"To\" date", Toast.LENGTH_LONG);
                toast.show();
            }
            else {
                new NeevDataRecalculateAsyncTask(getActivity()).execute();
                defaultPrevFromDate = fromDay + "/" + fromMonth + "/" + fromYear;
                defaultPrevToDate = toDay + "/" + toMonth + "/" + toYear;
                /* redraw the graph data as well */
                try {
                    mDashboardItemDetailFragmentCustom.onSingleTapConfirmed(null);
                }
                catch (Exception e){
                    /* do nothing */
                }

            }
            if (IsInvalidDate) {
                ((EditText) getActivity().findViewById(R.id.editText2)).setText("From: " + "\n" + defaultPrevFromDate);
                ((EditText) getActivity().findViewById(R.id.editText3)).setText("To: " + "\n" + defaultPrevToDate);

            }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }
}


