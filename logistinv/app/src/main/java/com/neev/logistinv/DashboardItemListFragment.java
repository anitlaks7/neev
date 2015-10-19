package com.neev.logistinv;

import android.app.Activity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.app.ListFragment;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.neev.example.R;
import com.neev.logistinv.dashboarditemlistcontent.DashboardItemListContent;

import java.util.ArrayList;

import static com.neev.example.R.layout.dashboard_list_item_layout_checked;

/**
 * A list fragment representing a list of DashboardItems. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link DashboardItemDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */

public class DashboardItemListFragment extends ListFragment {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    private static boolean listSelectionMode = false;
    public static String ARG_PANE = "today";
    public static DashboardItemListAdapter adapter;

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        void onItemSelected(String id, int containerView);
        /**
         * Callback for when multiple items have been selected.
         */
        void onMultipleItemsSelected(ArrayList<String> selectedItems, int containerView);
    }

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static final Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String id, int containerView) {
        }

        @Override
        public void onMultipleItemsSelected(ArrayList<String> selectedItems, int containerView) {

        }

    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DashboardItemListFragment() {
    }

    public final DataSetObserver mObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
           setListAdapter(adapter);
        }
    };


    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Bundle b = getActivity().getIntent().getExtras();
        if(adapter==null) {
            adapter = new DashboardItemListAdapter(getActivity(), R.layout.dashboard_list_item_layout, DashboardItemListContent.returnSelectedItems());
            adapter.registerDataSetObserver(MainActivity.mDashboardItemListFragmentCustom.mObserver);
            adapter.registerDataSetObserver(MainActivity.mDashboardItemListFragmentToday.mObserver);
            adapter.setNotifyOnChange(true);
            setListAdapter(adapter);
        }
        else
            setListAdapter(adapter);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        //if (listSelectionMode)
         //   getListView().setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
        getListView().setLongClickable(true);
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
            getListView().setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
            getListView().setDrawSelectorOnTop(true);
            listSelectionMode = true;
                adapter = new DashboardItemListAdapter(getActivity(), dashboard_list_item_layout_checked, DashboardItemListContent.returnSelectedItems());

            setListAdapter(adapter);

            //add the 2 buttons here dynamically -> save and cancel
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.BOTTOM|Gravity.LEFT;
            final Button btnDelete = new Button(getActivity());
            btnDelete.setText("DELETE");

            final FrameLayout fl = (FrameLayout) getView().getParent();
            fl.addView(btnDelete, params);
            getListView().setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {

                }

                @Override
                public void onItemCheckedStateChanged(ActionMode mode,
                                                      int position, long id, boolean checked) {
                    // Capture total checked items
                    // 				final int checkedCount = list.getCheckedItemCount();
                    // Set the CAB title according to total checked items
                    // mode.setTitle(checkedCount + " Selected");
                    // Calls toggleSelection method from ListViewAdapter Class
                    // listviewadapter.toggleSelection(position);			}
                }
            });
            btnDelete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    SparseBooleanArray checked = getListView().getCheckedItemPositions();

                    ArrayList<String> selectedItems = new ArrayList<String>();
                    for (int i = 0; i < checked.size(); i++) {
                        // Item position in adapter
                        int position = checked.keyAt(i);
                        // Add sport if it is checked i.e.) == TRUE!
                        if (checked.valueAt(i))
                            DashboardItemListContent.setSelectionSate(adapter.getItem(position).toString(), false);
                    }
                    listSelectionMode = false;
                    getListView().setChoiceMode(AbsListView.CHOICE_MODE_NONE);
                    adapter = new DashboardItemListAdapter(getActivity(), R.layout.dashboard_list_item_layout, DashboardItemListContent.returnSelectedItems());
                    adapter.registerDataSetObserver(MainActivity.mDashboardItemListFragmentCustom.mObserver);
                    adapter.registerDataSetObserver(MainActivity.mDashboardItemListFragmentToday.mObserver);
                    adapter.notifyDataSetChanged();

                    final FrameLayout fCurrentView = (FrameLayout) view.getParent();
                    fCurrentView.removeView(btnDelete);
                }
            });

        return true;
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        if(!listSelectionMode) {
            ArrayList<DashboardItemListContent.DashboardListItem> selectedItems = DashboardItemListContent.returnSelectedItems();
            mCallbacks.onItemSelected(selectedItems.get(position).toString(), ((ViewGroup) getView().getParent()).getId());
            }
        else { /*listSelectionMode DO NOTHING*/
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

    public void Callbacks(){
        /**
         * Callback for when an item has been selected.
         */
        mCallbacks.onItemSelected("anita", 1);
    }

}
