package com.neev.example;

import java.util.Calendar;
import java.util.Locale;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;


import com.neev.logistinv.DashboardItemDetailFragment;
import com.neev.logistinv.DashboardItemListFragment;
import com.neev.logistinv.ListViewMultipleSelectionActivity;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener,DashboardItemListFragment.Callbacks{

    static int fromYear;
    static int fromMonth;
    static int fromDay;
    static int toYear;
    static int toMonth;
    static int toDay;
    private static String ARG_FROM_TO = "From";


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        fromYear = c.get(Calendar.YEAR);
        fromMonth = c.get(Calendar.MONTH);
        fromDay = c.get(Calendar.DAY_OF_MONTH);
        toYear = c.get(Calendar.YEAR);
        toMonth = c.get(Calendar.MONTH);
        toDay = c.get(Calendar.DAY_OF_MONTH);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        // Show the Up button in the action bar.
        actionBar.setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
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
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
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
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(DashboardItemDetailFragment.ARG_ITEM_ID, id);
            DashboardItemDetailFragment fragment = new DashboardItemDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.dashboarditem_detail_container, fragment)
                    .commit();

          /*  FragmentManager fragMgr = this.getFragmentManager().;
            FragmentTransaction fragTrans = fragMgr.beginTransaction();

            fragTrans.replace(android.R.id.content, fragment);
            fragTrans.addToBackStack(null);
            fragTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragTrans.commit();*/

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID. This original code started a new intent and another
            // activity. Retained here if in case needed for future use.
            //Intent detailIntent = new Intent(this, DashboardItemDetailActivity.class);
            //detailIntent.putExtra(DashboardItemDetailFragment.ARG_ITEM_ID, id);
            //startActivity(detailIntent);


            //DashboardItemListFragment dashitemlistFrag= (DashboardItemListFragment) getSupportFragmentManager()
            //        .findFragmentById(R.id.dashboarditem_list);
            //FragmentTransaction ft = getFragmentManager().beginTransaction();
            // if(dashitemlistFrag.isAdded()){
            //   ft.hide(dashitemlistFrag);
            //};

            // Fragment dashitemlist =  getSupportFragmentManager().findFragmentById(R.id.dashboarditem_list);
            //FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //if (dashitemlist.isAdded()) { ft.hide(dashitemlist);};
            //android.support.v4.app.FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
            //List<android.support.v4.app.Fragment> fragments = fragmentManager.getFragments();
            //Fragment currentFragment;
            //for(android.support.v4.app.Fragment fragment : fragments)
            //   if (fragment != null && fragment.isVisible())
            //        currentFragment = fragment;
            //Fragment curFragment = mSectionsPagerAdapter.getItem(0);
            //DashboardItemListFragment dashItemListFrag = (DashboardItemListFragment) curFragment.getFragmentManager().findFragmentById(R.id.dashboarditem_list);
           // Fragment g = getFragmentManager().findFragmentByTag(
              //      "android:switcher:" + mViewPager.getId() + ":"
               //             + mSectionsPagerAdapter.getItemId(0));
            //Fragment fg = getFragmentManager().findFragmentById(mViewPager.getId());
            //FragmentManager fragMan = g.getChildFragmentManager();

            //DashboardItemListFragment dashboardItemListFragment = (DashboardItemListFragment) fragMan.findFragmentById(R.id.dashboarditem_list);
            //long v = dashboardItemListFragment.getView().getId();
            //FragmentTransaction ft = getFragmentManager().beginTransaction();
//            if (dashboardItemListFragment.isAdded()) {
//                ft.hide(dashboardItemListFragment);
//                ft.commit();
//            }

/********************************************************************************GOOD CODE BELOW*/
            Bundle arguments = new Bundle();
            arguments.putString(DashboardItemDetailFragment.ARG_ITEM_ID, id);
            DashboardItemDetailFragment fragment = new DashboardItemDetailFragment();
            fragment.setArguments(arguments);
            FragmentTransaction ft1 = getFragmentManager().beginTransaction();
           // removes the existing fragment calling onDestroy
           // ft1.replace(R.id.dashboarditem_list, fragment);
//            ft1.addToBackStack("DashboardItemDetailFragment");
            //ft1.commit();

            if (fragment.isAdded()) { // if the fragment is already in container
                ft1.show(fragment);
            } else { // fragment needs to be added to frame container
               // ft1.add(R.id.dashboarditem_detail, fragment, "DashboardItemDetailFragment");
                ft1.replace(R.id.dashboarditem_list1,fragment);
                ft1.show(fragment);
            }
            ft1.addToBackStack("DashboardItemDetailFragment");
            ft1.commit();
        }

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

                DashboardItemListFragment fragment = new DashboardItemListFragment();
                //fragment.setArguments(arguments);
                FragmentTransaction ft1 = getFragmentManager().beginTransaction();
                // removes the existing fragment calling onDestroy
                ft1.replace(R.id.dashboarditem_list1, fragment);
                //ft1.addToBackStack("DashboardItemListFragment");
                //ft1.show(fragment);
                ft1.commit();

            }
            else {
                rootView = inflater.inflate(R.layout.fragment_custom, container, false);
                EditText editText = (EditText) rootView.findViewById(R.id.editText2);
                editText.setText("From: "+fromDay +"/"+ fromMonth +"/"+ fromYear);
                EditText editTextTo = (EditText) rootView.findViewById(R.id.editText3);
                editTextTo.setText("To: " + toDay + "/" + toMonth + "/" + toYear);


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
                }

                return rootView;
        }
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
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            //bundle = savedInstanceState.getBundle(SAVED_BUNDLE_TAG);
            bundle = getArguments();
            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }


        public void onDateSet(DatePicker view, int year, int month, int day) {

            Log.w("DatePicker", "Date = " + year);

            if (bundle.getString(ARG_FROM_TO) == "From") {
                fromDay = day;
                fromMonth = month;
                fromYear = year;
                ((EditText) getActivity().findViewById(R.id.editText2)).setText("From: "+ fromDay + "/" + fromMonth + "/" + fromYear);
            } else {
                toDay = day;
                toMonth = month;
                toYear = year;
                ((EditText) getActivity().findViewById(R.id.editText3)).setText("To: "+ toDay + "/" + toMonth + "/" + toYear);
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


