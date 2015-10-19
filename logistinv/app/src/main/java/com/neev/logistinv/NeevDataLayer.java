package com.neev.logistinv;

import android.util.Log;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by chethana.savalgi on 11-10-2015.
 */
public class NeevDataLayer {

    public void initialize()
    {
        try {

            ParseQuery<ParseObject> query1 = ParseQuery.getQuery("RawMaterialMaster");
            query1.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        //objectsWereRetrievedSuccessfully(objects);
                        for(int i=0;i< objects.size();i++)
                        {
                            ParseObject po = objects.get(i);
                            String name = (String) po.get("Name");
                            po.pinInBackground();
                        }
                    } else {
                        Log.e("ERROR","Parse data retrieval failed");
                        //objectRetrievalFailed();
                    }
                }
            });

            ParseQuery<ParseObject> query2 = ParseQuery.getQuery("FinishedProductMaster");
            query2.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        //objectsWereRetrievedSuccessfully(objects);
                        for(int i=0;i< objects.size();i++)
                        {
                            ParseObject po = objects.get(i);
                            String name = (String) po.get("Name");
                            po.pinInBackground();
                        }
                    } else {
                        Log.e("ERROR","Parse data retrieval failed");
                        //objectRetrievalFailed();
                    }
                }
            });

            ParseQuery<ParseObject> query3 = ParseQuery.getQuery("NeevRawMaterialItem");
            query3.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        //objectsWereRetrievedSuccessfully(objects);
                        for(int i=0;i< objects.size();i++)
                        {
                            ParseObject po = objects.get(i);
                            String name = (String) po.get("Name");
                            po.pinInBackground();
                        }
                    } else {
                        Log.e("ERROR","Parse data retrieval failed");
                        //objectRetrievalFailed();
                    }
                }
            });

            ParseQuery<ParseObject> query4 = ParseQuery.getQuery("NeevProductItem");
            query4.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        //objectsWereRetrievedSuccessfully(objects);
                        for(int i=0;i< objects.size();i++)
                        {
                            ParseObject po = objects.get(i);
                            String name = (String) po.get("Name");
                            po.pinInBackground();
                        }
                    } else {
                        Log.e("ERROR","Parse data retrieval failed");
                        //objectRetrievalFailed();
                    }
                }
            });


        }
        catch (Exception e)
        {
           // log.d()
        }

    }

    public List retrieveAllRawMaterialFromLocalStore()
    {
        List rmList = null;
        try {
            ParseQuery query = new ParseQuery("RawMaterialMaster");
            query.fromLocalDatastore();
            rmList = query.find();
            for(int i=0;i< rmList.size();i++)
            {
                ParseObject po = (ParseObject)rmList.get(i);
                String name = (String) po.get("Name");
            }
        }
        catch (Exception e)
        {
            Log.e("ERROR",e.toString());
        }
        return rmList;
    }

    public List retrieveAllFinishedProductFromLocalStore()
    {
        List rmList = null;
        try {
            ParseQuery query = new ParseQuery("FinishedProductMaster");
            query.fromLocalDatastore();
            rmList = query.find();
            for(int i=0;i< rmList.size();i++)
            {
                ParseObject po = (ParseObject)rmList.get(i);
                String name = (String) po.get("Name");
            }
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

    public int retrieveProductToday(String name)
    {
        List productList = null;
        int total = 0;
        try
        {
            ParseQuery query = new ParseQuery("NeevProductItem");
            query.whereMatches("Name",name);
            query.fromLocalDatastore();
            productList = query.find();
            for (int i = 0; i < productList.size(); i++) {
                ParseObject po = (ParseObject)productList.get(i);
                int count =  (int)po.get("Quantity");
                total = total + count;
            }
            /*query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        //objectsWereRetrievedSuccessfully(objects);
                        for (int i = 0; i < objects.size(); i++) {
                            ParseObject po = objects.get(i);
                            int count =  (int)po.get("Quantity");
                            total = total + count;
                        }
                    } else {
                        Log.e("ERROR", "Parse data retrieval failed");
                        //objectRetrievalFailed();
                    }
                }
            });*/

        }
        catch(Exception e)
        {
            Log.e("ERROR", e.toString());
        }

        return total;
    }

    public List retrieveTodayRawMaterial()
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String creationDate = format.format(c.getTime());
        List rmList = null;
        try {
            ParseQuery query = new ParseQuery("NeevRawMaterialItem");
            query.fromLocalDatastore();
            query.whereEqualTo("CreationDate", creationDate);
            rmList = query.find();
            for(int i=0;i< rmList.size();i++)
            {
                ParseObject po = (ParseObject)rmList.get(i);
            }
        }
        catch (Exception e)
        {
            Log.e("ERROR",e.toString());
        }

        return rmList;
    }

    public List retrieveTodayProduct()
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String creationDate = format.format(c.getTime());
        List rmList = null;
        try {
            ParseQuery query = new ParseQuery("NeevProductItem");
            query.fromLocalDatastore();
            query.whereEqualTo("CreationDate", creationDate);
            rmList = query.find();
            for(int i=0;i< rmList.size();i++)
            {
                ParseObject po = (ParseObject)rmList.get(i);
            }
        }
        catch (Exception e)
        {
            Log.e("ERROR",e.toString());
        }

        return rmList;
    }
}
