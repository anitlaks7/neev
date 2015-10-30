package com.neev.logistinv;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by anita.lakshmanan on 10/25/2015.
 */
public class NeevDataUpdateAsyncTask extends AsyncTask <String, String, String> {
    private Context context;
    public ProgressDialog dialog;
    public NeevDataUpdateAsyncTask (Context ctx){
        context = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
       // dialog = new ProgressDialog(context);
        //this.dialog.setMessage("Please wait");
        //this.dialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        NeevDataLayer data = new NeevDataLayer(context);
        data.initialize();
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //new NeevDataRecalculateAsyncTask(context).execute();
    }
}
