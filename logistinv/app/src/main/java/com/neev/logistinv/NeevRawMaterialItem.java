package com.neev.logistinv;

import com.parse.ParseObject;

/**
 * Created by chethana.savalgi on 11-10-2015.
 */
public class NeevRawMaterialItem extends ParseObject {
    public String getName() {
        return getString("Name");
    }

    public void setName(String value) {
        put("Name", value);
    }

    public String getUnit() {
        return getString("Unit");
    }

    public void setUnit(String value) {
        put("Unit", value);
    }
}
