package com.neev.logistinv;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;

@ParseClassName("NeevRawMaterialItem")
public class NeevRawMaterialItem extends ParseObject {
    public String getName(){ return getString("Name");}
    public void setName(String name) {
        put("Name", name);
    }

    public double getUnitPrice(){ return getDouble("UnitPrice");}
    public void setUnitPrice(double value) {
        put("UnitPrice", value);
    }

    public int getQty(){ return getInt("Quantity");}
    public void setQty(int value) {
        put("Quantity", value);
    }

    public Date getDate(){ return getDate("CreationDate");}
    public void setDate(Date value) {
        put("CreationDate", value);
    }

    public double getTotal(){ return getDouble("Total");}
    public void setTotal(double value) {
        put("Total", value);
    }



    public NeevRawMaterialItem()
    {

    }


}

