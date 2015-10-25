package com.neev.logistinv;

import android.os.AsyncTask;

/**
 * Created by anita.lakshmanan on 10/25/2015.
 */
public class NeevDataUpdateAsyncTask extends AsyncTask <String, String, String> {

    @Override
    protected String doInBackground(String... params) {
        NeevDataLayer data = new NeevDataLayer();
        data.initialize();
        return null;
    }
}
