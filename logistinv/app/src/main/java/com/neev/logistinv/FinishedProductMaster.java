package com.neev.logistinv;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by sandhya.r.sharma on 10/26/2015.
 */
@ParseClassName("FinishedProductMaster")
public class FinishedProductMaster extends ParseObject {

    public String getName(){ return getString("Name");}
    public void setName(String name) {
        put("Name", name);
    }

    public int getID(){ return getInt("ID");}
    public void setID(int val) {
        put("ID", val);
    }

    public FinishedProductMaster()
    {
        super();
    }

}
