package com.sismics.books;

import android.app.Application;

import com.androidquery.callback.BitmapAjaxCallback;
import com.sismics.books.model.ApplicationContext;
import com.sismics.books.util.PreferenceUtil;

import org.json.JSONObject;

/**
 * Main application.
 * 
 * @author bgamard
 */
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        // Fetching /user from cache
        JSONObject json = PreferenceUtil.getCachedJson(getApplicationContext(), PreferenceUtil.Pref.CACHED_USER_INFO_JSON);
        ApplicationContext.getInstance().setUserInfo(getApplicationContext(), json);

        super.onCreate();
    }
    
    @Override
    public void onLowMemory() {
        BitmapAjaxCallback.clearCache();
    }
}
