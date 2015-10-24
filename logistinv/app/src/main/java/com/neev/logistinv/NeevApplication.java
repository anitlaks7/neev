package com.neev.logistinv;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseObject;

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
            // ParseCrashReporting.enable(this);
            Parse.enableLocalDatastore(this);
            Parse.initialize(this,"GimrU2G4Qn3g9Yci2taHbaiyfYf60oBc8XF9vern","6fkbLFklMV5IRIfWHadzy8pwjPb7GX1fw6tNAovN");
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
