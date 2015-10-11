package com.neev.example;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by chethana.savalgi on 11-10-2015.
 */
public class DataLayer {

    public void initialize()
    {
        try {
            ParseQuery query = new ParseQuery("RawMaterialTable");
            List rmList = query.find();
            for(int i=0;i< rmList.size();i++)
            {
                ParseObject po = (ParseObject)rmList.get(i);
                String name = (String) po.get("Name");
                po.pinInBackground();
            }
        }
        catch (Exception e)
        {

        }

    }

    public List retrieveAllRawMaterialFromLocalStore()
    {
        List rmList = null;
        try {
            ParseQuery query = new ParseQuery("RawMaterialTable");
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

        }
        return rmList;
    }
}
