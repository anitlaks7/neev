package com.neev.logistinv;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;

/**
 * Created by chethana.savalgi on 11-10-2015.
 */
public class NeevApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        try {

            // ParseCrashReporting.enable(this);
            Parse.enableLocalDatastore(this);
            Parse.initialize(this, "TnftdYLSYkSJHmlNmgm1Sa5bVrVEEPCo1g48vjOD", "I7aaYGQkikHBIjbkoqcQh5HalZXWAyiVGKZbzuBw");
            NeevDataLayer data = new NeevDataLayer();
            data.initialize();
            Log.i("Info","Parse Session Established");
           // Intent mainIntent = new Intent(this,MainActivity.class);
           // mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           // startActivity(mainIntent);
        }
        catch(Exception e)
        {
            Log.e("Error",e.toString());
        }
    }

}
