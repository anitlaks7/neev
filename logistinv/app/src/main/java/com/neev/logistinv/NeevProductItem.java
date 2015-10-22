package com.neev.logistinv;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;

/**
 * Created by chethana.savalgi on 11-10-2015.
 */

@ParseClassName("NeevProductItem")
public class NeevProductItem extends ParseObject {
    public String getPName(){ return getString("Name");}
    public void setPName(String name) {
        put("Name", name);
    }

    public double getPPrice(){ return getDouble("UnitPrice");}
    public void setPPrice(double value) {
        put("UnitPrice", value);
    }

    public int getPQty(){ return getInt("Quantity");}
    public void setPQty(int value) {
        put("Quantity", value);
    }

    public String getPType(){ return getString("Type");}
    public void setPType(String value) {
        put("Type", value);
    }

    public Date getPDate(){ return getDate("CreationDate");}
    public void setPDate(Date value) {
        put("CreationDate", value);
    }

    public double getPTotal(){ return getDouble("Total");}
    public void setPTotal(double value) {
        put("Total", value);
    }


    public NeevProductItem()
    {

    }

}
