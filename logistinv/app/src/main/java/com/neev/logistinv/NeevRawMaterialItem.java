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

    public String getCost() {
        return getString("Cost");
    }

    public void setCost(String value) {
        put("Cost", value);
    }

    public int getQuantity() {
        return getInt("Quantity");
    }

    public void setQuantity(int value) {
        put("Quantity", value);
    }

    public String getUnit() {
        return getString("Unit");
    }

    public void setUnit(String value) {
        put("Unit", value);
    }
}
