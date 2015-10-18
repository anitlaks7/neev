package com.neev.logistinv.dashboarditemlistcontent;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardItemListContent extends BaseAdapter {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DashboardListItem> ITEMS = new ArrayList<DashboardListItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DashboardListItem> ITEM_MAP = new HashMap<String, DashboardListItem>();


    static {
        // Add 5 sample items.
        addItem(new DashboardListItem("1", "Inventory", true));
        addItem(new DashboardListItem("2", "Sales", true));
        addItem(new DashboardListItem("3", "Personnel", true));
        addItem(new DashboardListItem("4", "In Transit", true));
        addItem(new DashboardListItem("5", "Returned", true));
    }

    private static void addItem(DashboardListItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }


    public static void setSelectionSate(String s, boolean b) {
        for (int i = 0; i < ITEMS.size(); i++) {
            if (ITEMS.get(i).content.equals(s))
                ITEMS.get(i).isSelected = b;
        }
    }
    /* Note - this function only works if the list has no repetitive items */
    public static boolean getSelectionSate(String s) {
        for(int i = 0; i < ITEMS.size(); i++){
            if(ITEMS.get(i).content.equals(s))
                return(ITEMS.get(i).isSelected);
        }
        return false;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }


    /**
     * A dummy item representing a piece of content.
     */
    public static class DashboardListItem {
        public final String id;
        public final String content;
        public boolean isSelected = false;

        public DashboardListItem(String id, String content, boolean isSel) {
            this.id = id;
            this.content = content;
            this.isSelected = isSel;
        }

        @Override
        public String toString() {
            return content;
        }
    }
        /*
        Return a string of selected items
         */
        public static ArrayList<String> returnSelectedItems(){
            ArrayList<String> selectedItems = new ArrayList<String>(ITEMS.size());
            for(int i = 0; i < ITEMS.size(); i++){
                if (ITEMS.get(i).isSelected){
                    selectedItems.add(ITEMS.get(i).content);
                }
            }
            return selectedItems;
        }


    }

