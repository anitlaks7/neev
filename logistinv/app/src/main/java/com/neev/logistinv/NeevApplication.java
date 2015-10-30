package com.neev.logistinv;

import android.app.Application;
import android.os.Handler;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseObject;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by chethana.savalgi on 11-10-2015.
 */
public class NeevApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        try {
            ParseObject.registerSubclass(NeevRawMaterialItem.class);
            ParseObject.registerSubclass(NeevProductItem.class);
            ParseObject.registerSubclass(NeevRawMaterialMaster.class);
            ParseObject.registerSubclass(FinishedProductMaster.class);
            // ParseCrashReporting.enable(this);
            Parse.enableLocalDatastore(this);
            Parse.initialize(this,"GimrU2G4Qn3g9Yci2taHbaiyfYf60oBc8XF9vern","6fkbLFklMV5IRIfWHadzy8pwjPb7GX1fw6tNAovN");
            NeevDataLayer data = new NeevDataLayer(getApplicationContext());
            data.initialize();
            final Handler handler = new Handler();
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        public void run() {
                            new NeevDataUpdateAsyncTask(getApplicationContext()).execute();
                        }
                    });
                }
            };
           timer.schedule(task, 0, 300000); //it executes this every 5min
           // Log.i("Info","Parse Session Established");
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
