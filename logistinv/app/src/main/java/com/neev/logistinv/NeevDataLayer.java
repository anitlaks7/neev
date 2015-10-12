package com.neev.logistinv;

import android.util.Log;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.FindCallback;
import com.parse.ParseException;

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
                            ParseObject po = (ParseObject)objects.get(i);
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
                            ParseObject po = (ParseObject)objects.get(i);
                            String name = (String) po.get("Name");
                            po.pinInBackground();
                        }
                    } else {
                        Log.e("ERROR","Parse data retrieval failed");
                        //objectRetrievalFailed();
                    }
                }
            });

           /* ParseQuery query = new ParseQuery("RawMaterialTable");
            List rmList = query.findInBackground();
            for(int i=0;i< rmList.size();i++)
            {
                ParseObject po = (ParseObject)rmList.get(i);
                String name = (String) po.get("Name");
                po.pinInBackground();
            }*/
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

    public boolean addToStore(NeevRawMaterialItem item)
    {
        try{
            item.saveEventually();
        }
        catch(Exception e)
        {

        }
        return true;
    }
}
