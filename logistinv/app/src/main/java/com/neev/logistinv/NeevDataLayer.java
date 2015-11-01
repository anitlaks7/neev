package com.neev.logistinv;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.parse.DeleteCallback;
import com.parse.ParseCloud;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by chethana.savalgi on 11-10-2015.
 */
public class NeevDataLayer {
    private int count;
    private Context ctx;
    NeevDataLayer() {
        count = 0;

    }
    NeevDataLayer(Context c) {
        count = 0;
        ctx = c;

    }

    public void initialize()
    {
        try {
            ParseQuery<ParseObject> query1 = ParseQuery.getQuery("RawMaterialMaster");
            //query1.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
            Log.d("RMMASTER", "RawMaterialMaster");
            query1.findInBackground(new FindCallback<ParseObject>() {

                public void done(List<ParseObject> objects, ParseException e) {
                    final List<ParseObject> localobjects = objects;
                    if (e == null) {
                        ParseObject.unpinAllInBackground("RawMaterialMaster", new DeleteCallback(){
                                public void done(ParseException e){

                                    ParseObject.pinAllInBackground("RawMaterialMaster",localobjects,new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            count ++;
                                            if(count == 4)
                                                new NeevDataRecalculateAsyncTask(ctx).execute();
                                        }
                                    });
                                }
                                });
                        }
                     else {
                        Log.e("ERROR","Parse data retrieval failed");
                        //objectRetrievalFailed();
                    }
                }
            });

            ParseQuery<ParseObject> query2 = ParseQuery.getQuery("FinishedProductMaster");
            //query2.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
            Log.d("FPMASTER", "FinishedProdMaster");
            query2.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> objects, ParseException e) {
                    final List<ParseObject> localobjects = objects;
                    if (e == null) {
                        ParseObject.unpinAllInBackground("FinishedProductMaster", new DeleteCallback() {
                            public void done(ParseException e) {

                                ParseObject.pinAllInBackground("FinishedProductMaster", localobjects, new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        count++;
                                        if (count == 4)
                                            new NeevDataRecalculateAsyncTask(ctx).execute();
                                    }
                                });
                            }
                        });
                    }
                    else {
                        Log.e("ERROR","Parse data retrieval failed");
                        //objectRetrievalFailed();
                    }
                }
            });

            ParseQuery<ParseObject> query3 = ParseQuery.getQuery("NeevRawMaterialItem");
            //query3.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
            Log.d("RMITEM", "RMItem");
            query3.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> objects, ParseException e) {
                    final List<ParseObject> localobjects = objects;
                    if (e == null) {
                        ParseObject.unpinAllInBackground("NeevRawMaterialItem", new DeleteCallback() {
                            public void done(ParseException e) {

                                ParseObject.pinAllInBackground(localobjects, new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        count++;
                                        if(count == 4)
                                            new NeevDataRecalculateAsyncTask(ctx).execute();
                                    }
                                });
                            }
                        });
                    }
                    else {
                        Log.e("ERROR", "Parse data retrieval failed");
                        //objectRetrievalFailed();
                    }

                }
            });

            ParseQuery<ParseObject> query4 = ParseQuery.getQuery("NeevProductItem");
            //query4.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
            query4.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> objects, ParseException e) {
                    final List<ParseObject> localobjects = objects;
                    if (e == null) {
                        ParseObject.unpinAllInBackground("NeevProductItem", new DeleteCallback() {
                            public void done(ParseException e) {

                                ParseObject.pinAllInBackground("NeevProductItem",localobjects, new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        count++;
                                        if (count == 4)
                                            new NeevDataRecalculateAsyncTask(ctx).execute();
                                    }
                                });
                            }
                        });
                    }
                    else {
                        Log.e("ERROR","Parse data retrieval failed");
                        //objectRetrievalFailed();
                    }
                }
            });

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

    }

    public List retrieveAllRawMaterialFromLocalStore() {
        List rmList = null;
        try {
            ParseQuery query = new ParseQuery("RawMaterialMaster");
            //query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
            query.fromLocalDatastore();
            //query.fromPin("RawMaterialMaster");
            rmList = query.find();

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }
        return rmList;
    }

    public List retrieveAllFinishedProductFromLocalStore()
    {
        List rmList = null;
        try {
            ParseQuery query = new ParseQuery("FinishedProductMaster");
            //query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
            query.fromLocalDatastore();
            //query.fromPin("FinishedProductMaster");
            rmList = query.find();

        }
        catch (Exception e)
        {
            Log.e("ERROR",e.toString());
        }

        return rmList;
    }

    public boolean addToRMStore(NeevRawMaterialItem item)
    {
        try{
            item.pinInBackground("NeevRawMaterialItem");
            //item.saveInBackground();
            item.saveEventually();
        }
        catch(Exception e)
        {
            Log.e("ERROR",e.toString());
        }
        return true;
    }

    public boolean addToProdStore(NeevProductItem item)
    {
        try{
            item.pinInBackground("NeevProductItem");
            //item.saveInBackground();
            item.saveEventually();
        }
        catch(Exception e)
        {
            Log.e("ERROR", e.toString());

        }
        return true;
    }


    public List retrieveDetailData(String type,String name, String startDate, String endDate)
    {
        List rmList = null;

        try {


            String arr1[] = startDate.split("/");
            int d1 = Integer.parseInt(arr1[0]);
            int m1 = Integer.parseInt(arr1[1]);
            int y1 = Integer.parseInt(arr1[2]);

            Calendar calendar1 = Calendar.getInstance();
            calendar1.set(y1,m1-1,d1,0,0,0);
            Date createDate = calendar1.getTime();

            String arr2[] = endDate.split("/");
            int d2 = Integer.parseInt(arr2[0]);
            int m2 = Integer.parseInt(arr2[1]);
            int y2 = Integer.parseInt(arr2[2]);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(y2, m2 - 1, d2, 23, 59, 59);
            Date enddate= calendar2.getTime();

            if(type.equalsIgnoreCase(MainActivity.RAW_MATERIAL))
            {
                ParseQuery query = new ParseQuery("NeevRawMaterialItem");
                query.fromLocalDatastore();
                //query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
                query.whereGreaterThan("CreationDate",createDate);
                query.whereLessThan("CreationDate", enddate);
                if(name!=null)
                query.whereEqualTo("Name", name);
                rmList = query.find();
                //if(rmList !=null) Log.d("DEBUG","BAR graph count " + rmList.size());
               /* for(int i=0;i< rmList.size();i++)
                {
                    ParseObject po = (ParseObject)rmList.get(i);
                }*/

            }
            else
            {
                ParseQuery query = new ParseQuery("NeevProductItem");
                //query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
                query.fromLocalDatastore();
                if(name!=null)
                query.whereEqualTo("Name", name);
                query.whereEqualTo("Type", type);
                query.whereGreaterThan("CreationDate", createDate);
                query.whereLessThan("CreationDate", enddate);
                rmList = query.find();
                //if(rmList !=null) Log.d("DEBUG","BAR graph count " + rmList.size());
                /*for(int i=0;i< rmList.size();i++)
                {
                    ParseObject po = (ParseObject)rmList.get(i);
                }*/
            }

        }
        catch (Exception e)
        {
            Log.e("ERROR",e.toString());
        }

        return rmList;
    }

    public boolean checkNewRawMaterial(String name)
    {
        boolean result = true;
        List rmList = null;
        try {

            ParseQuery query = new ParseQuery("RawMaterialMaster");
            query.fromLocalDatastore();
            query.whereEqualTo("Name", name);
            rmList = query.find();
            if((rmList !=null) && (rmList.size()==1))
            {
                result = false;
            }
        }
        catch (Exception e)
        {
            Log.e("ERROR",e.toString());
        }

        return result;
    }

    public boolean checkNewProduct(String name)
    {
        boolean result = true;
        List rmList = null;
        try {

            ParseQuery query = new ParseQuery("FinishedProductMaster");
            query.fromLocalDatastore();
            query.whereEqualTo("Name", name);
            rmList = query.find();
            if((rmList !=null) && (rmList.size()==1))
            {
                result = false;
            }
        }
        catch (Exception e)
        {
            Log.e("ERROR",e.toString());
        }

        return result;
    }

    public boolean addToRawMaterialMaster(NeevRawMaterialMaster item)
    {
        try{
            item.pinInBackground("RawMaterialMaster");
            //item.saveInBackground();
            item.saveEventually();
        }
        catch(Exception e)
        {
            Log.e("ERROR", e.toString());

        }
        return true;
    }

    public boolean addToProductMaster(FinishedProductMaster item)
    {
        try{
            item.pinInBackground("FinishedProductMaster");
            //item.saveInBackground();
            item.saveEventually();
        }
        catch(Exception e)
        {
            Log.e("ERROR", e.toString());

        }
        return true;
    }


    public String summationInventory() {
        String result = null;
        Map param = new HashMap();
        try {
            Object ob = ParseCloud.callFunction("summationInventory", param);
            result = ob.toString();
        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        return result;
    }
    }
