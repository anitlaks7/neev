package com.neev.logistinv;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;

/**
 * Created by chethana.savalgi on 23-10-2015.
 */

@ParseClassName("NeevRawMaterialMaster")
public class NeevRawMaterialMaster extends ParseObject{

    public String getName(){ return getString("Name");}
    public void setName(String name) {
        put("Name", name);
    }

    public int getID(){ return getInt("ID");}
    public void setID(int val) {
        put("ID", val);
    }

    public String getUnit(){ return getString("Unit");}
    public void setUnit(String unit) {
        put("Unit", unit);
    }

    public NeevRawMaterialMaster()
    {
        super();
    }

}











