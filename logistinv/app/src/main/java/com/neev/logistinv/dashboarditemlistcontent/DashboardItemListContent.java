package com.neev.logistinv.dashboarditemlistcontent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.neev.example.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardItemListContent {

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
        addItem(new DashboardListItem("1", "Raw Material", true));
        addItem(new DashboardListItem("2", "Product Inventory", true));
        addItem(new DashboardListItem("3", "In Transit", true));
        addItem(new DashboardListItem("4", "Sales", true));
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


    /**
     * A dummy item representing a piece of content.
     */
    public static class DashboardListItem {
        public final String id;
        public final String content;
        public boolean isSelected = false;
        public int todayTotalQty;
        public float todayTotalValueRs;
        public int customTotalQty;
        public float customTotalValueRs;

        public DashboardListItem(String id, String content, boolean isSel) {
            this.id = id;
            this.content = content;
            this.isSelected = isSel;
            this.todayTotalQty = 0;
            this.todayTotalValueRs = 0;
            this.customTotalQty = 0;
            this.customTotalValueRs = 0;

        }

        @Override
        public String toString() {
            return content;
        }
    }
        /*
        Return a string of selected items
         */
        public static ArrayList<DashboardItemListContent.DashboardListItem> returnSelectedItems(){
            ArrayList<DashboardItemListContent.DashboardListItem> selectedItems = new ArrayList<DashboardItemListContent.DashboardListItem>(ITEMS.size());
            for(int i = 0; i < ITEMS.size(); i++){
                if (ITEMS.get(i).isSelected){
                    selectedItems.add(ITEMS.get(i));
                }
            }
            return selectedItems;
        }
    }

