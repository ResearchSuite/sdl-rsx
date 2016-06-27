package edu.cornell.tech.foundry.sdlrsx_example;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Set;

/**
 * Created by jk on 6/27/16.
 */
public class AppPrefs {

    public static final String YADL_ACTIVITIES      = "YADL_ACTIVITIES";
    public static final String MEDL_ITEMS      = "MEDL_ITEMS";

    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // Statics
    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private static AppPrefs instance;

    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // Field Vars
    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private final SharedPreferences prefs;

    AppPrefs(Context context)
    {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static synchronized AppPrefs getInstance(Context context)
    {
        if(instance == null)
        {
            instance = new AppPrefs(context);
        }
        return instance;
    }

    public Set<String> getYADLActivities()
    {
        return prefs.getStringSet(YADL_ACTIVITIES, null);
    }

    public void setYADLActivities(Set<String> activities) {
        prefs.edit().putStringSet(YADL_ACTIVITIES, activities).apply();
    }

    public Set<String> getMEDLItems()
    {
        return prefs.getStringSet(MEDL_ITEMS, null);
    }

    public void setMEDLItems(Set<String> activities) {
        prefs.edit().putStringSet(MEDL_ITEMS, activities).apply();
    }
}
