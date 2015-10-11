package com.neev.example;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import com.parse.*;

import java.util.List;

/**
 * Created by chethana.savalgi on 10-10-2015.
 */



public class ManageDataActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.managedata);

        ListView mRawMaterialListView;
        ArrayAdapter<String> listAdapter ;
        listAdapter = new ArrayAdapter<String>(this,R.layout.simplerow);

        mRawMaterialListView = (ListView) findViewById(R.id.rawMateriaListView);
        DataLayer data = new DataLayer();
        try {
            //ParseQuery query = new ParseQuery("RawMaterialTable");
            //query.fromLocalDatastore();

            List rmList = data.retrieveAllRawMaterialFromLocalStore();
            for(int i=0;i< rmList.size();i++)
            {
                ParseObject po = (ParseObject)rmList.get(i);
                String name = (String) po.get("Name");
                listAdapter.add(name);
            }
            }
        catch (Exception e)
        {

        }
        mRawMaterialListView.setAdapter(listAdapter);
    }
}
