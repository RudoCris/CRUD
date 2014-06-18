package pro.rudo.crud.app;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;

import com.parse.Parse;

/**
 * Created by rudolf on 11.05.14.
 */
public class SpeleoTools extends Application {
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "jG4p9JnJXW1f8jaYvynOTsB9z1BjZpFThx6MnLBc", "F8JQZoPtkUqYWTptr99vAvcdQnbjd9zh6LCXSTBW");



        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if(pref.getString("author", "") == "") {
            Intent intent = new Intent(getBaseContext(), EditPrefencesActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
