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

    public String getPPrice(){ return getString("UnitPrice");}
    public void setPPrice(String value) {
        put("Unit", value);
    }

    public Number getPQty(){ return getNumber("Quantity");}
    public void setPQty(Number value) {
        put("Unit", value);
    }

    public String getPType(){ return getString("Type");}
    public void setPType(String value) {
        put("Type", value);
    }

    public Date getPDate(){ return getDate("CreationDate");}
    public void setPDate(Date value) {
        put("CreationDate", value);
    }

    public NeevProductItem()
    {

    }


}
