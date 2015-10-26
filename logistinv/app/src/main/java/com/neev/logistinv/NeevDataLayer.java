package com.neev.logistinv;

import android.util.Log;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by chethana.savalgi on 11-10-2015.
 */
public class NeevDataLayer {

    public void initialize()
    {
        try {
            ParseQuery<ParseObject> query1 = ParseQuery.getQuery("RawMaterialMaster");
            //query1.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
            query1.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {

                        ParseObject.unpinAllInBackground("RawMaterialMaster");
                        ParseObject.pinAllInBackground(objects);
                    } else {
                        Log.e("ERROR","Parse data retrieval failed");
                        //objectRetrievalFailed();
                    }
                }
            });

            ParseQuery<ParseObject> query2 = ParseQuery.getQuery("FinishedProductMaster");
            //query2.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
            query2.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {

                        ParseObject.unpinAllInBackground("FinishedProductMaster");
                        ParseObject.pinAllInBackground(objects);
                    } else {
                        Log.e("ERROR","Parse data retrieval failed");

                    }
                }
            });

            ParseQuery<ParseObject> query3 = ParseQuery.getQuery("NeevRawMaterialItem");
            //query3.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
            query3.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {

                        ParseObject.unpinAllInBackground("NeevRawMaterialItem");
                        ParseObject.pinAllInBackground(objects);
                    } else {
                        Log.e("ERROR","Parse data retrieval failed");

                    }
                }
            });

            ParseQuery<ParseObject> query4 = ParseQuery.getQuery("NeevProductItem");
            //query4.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
            query4.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        ParseObject.unpinAllInBackground("NeevProductItem");
                        ParseObject.pinAllInBackground(objects);
                    } else {
                        Log.e("ERROR","Parse data retrieval failed");

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
            item.saveInBackground();
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

            item.saveInBackground();

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
                query.whereMatches("Name", name);
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
                query.whereMatches("Name", name);
                query.whereMatches("Type", type);
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
            query.whereMatches("Name", name);
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
            item.saveInBackground();
            //item.saveEventually();
        }
        catch(Exception e)
        {
            Log.e("ERROR", e.toString());

        }
        return true;
    }
    }
