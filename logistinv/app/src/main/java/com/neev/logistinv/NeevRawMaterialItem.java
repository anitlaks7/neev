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

    public String getUnitPrice(){ return getString("UnitPrice");}
    public void setUnitPrice(String value) {
        put("Unit", value);
    }

    public Number getQty(){ return getNumber("Quantity");}
    public void setQty(Number value) {
        put("Unit", value);
    }

    public Date getDate(){ return getDate("CreationDate");}
    public void setDate(Date value) {
        put("CreationDate", value);
    }

    public NeevRawMaterialItem()
    {

    }


}


