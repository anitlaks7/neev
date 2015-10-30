package com.neev.logistinv;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.neev.logistinv.dashboarditemlistcontent.DashboardItemListContent;
import com.parse.ParseObject;

import java.util.Calendar;
import java.util.List;

/**
 * Created by anita.lakshmanan on 10/25/2015.
 */
public class NeevDataRecalculateAsyncTask extends AsyncTask<String, String, String > {
    Context context;
    public ProgressDialog dialog;

    public NeevDataRecalculateAsyncTask (Context ctx){
        context = ctx;
    }
    /** progress dialog to show user that the backup is processing. */
    /** application context. */
    @Override
    protected void onPreExecute() {
        //dialog = new ProgressDialog(context);
        //this.dialog.setMessage("Please wait");
        //this.dialog.show();
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        for (int i = 0; i <5; i++){

        DashboardItemListContent.ITEMS.get(i).customTotalValueRs = customTotalRsArray[i];
        DashboardItemListContent.ITEMS.get(i).todayTotalValueRs = todayTotalRsArray[i];
        DashboardItemListContent.ITEMS.get(i).customTotalQty = customTotalQtyArray[i];
        DashboardItemListContent.ITEMS.get(i).todayTotalQty = todayTotalQtyArray[i];
        }
        if (DashboardItemListFragment.adapter!=null) {DashboardItemListFragment.adapter.notifyDataSetChanged();}
        if(dialog != null) dialog.dismiss();
        Log.d("DATARECALC", "DataRecalculateDone");
    }

    public int Quantity = 0;
    public float Total = 0;
    public int[] todayTotalQtyArray = new int[5];
    public int[] customTotalQtyArray = new int[5];
    public float[] todayTotalRsArray = new float[5];
    public float[] customTotalRsArray = new float[5];


    @Override
    protected String doInBackground(String... params) {
        //NeevDataLayer data = new NeevDataLayer();
        //data.initialize();
        for(int i =0; i < DashboardItemListContent.ITEMS.size(); i++) {
            getDataSet(true, DashboardItemListContent.ITEMS.get(i).toString());
            todayTotalQtyArray[i] = Quantity;
            todayTotalRsArray[i] = Total;
            getDataSet(false, DashboardItemListContent.ITEMS.get(i).toString());
            customTotalQtyArray[i] = Quantity;
            customTotalRsArray[i] = Total;
        }

        return null;
    }


    private void getDataSet(boolean today, String type) {

        NeevDataLayer dataLayer = new NeevDataLayer();
        List products = null;
        String startdate;
        String enddate;
        Quantity = 0;
        Total = 0;


        if (today) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH) + 1;
            int day = c.get(Calendar.DAY_OF_MONTH);
            startdate = enddate = day + "/" + month + "/" + year;
        } else {
            startdate = MainActivity.fromDay + "/" + MainActivity.fromMonth + "/" + MainActivity.fromYear;
            enddate = MainActivity.toDay + "/" + MainActivity.toMonth + "/" + MainActivity.toYear;
        }


        List list = dataLayer.retrieveDetailData(type, null, startdate, enddate);
        float quantity = 0;
        if (list != null) {
            for (int j = 0; j < list.size(); j++) {
                ParseObject po2 = (ParseObject) list.get(j);
                int q = (int) po2.get("Quantity");
                Quantity = Quantity + q;

                float totalprice = Float.parseFloat(po2.get("Total").toString());
                Total = Total + totalprice;
            }

        } else {

        }

    }
}
