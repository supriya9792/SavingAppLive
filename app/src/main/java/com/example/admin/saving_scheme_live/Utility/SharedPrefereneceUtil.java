package com.example.admin.saving_scheme_live.Utility;

/**
 * Created by admin on 7/11/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


public class SharedPrefereneceUtil {

    public static SharedPreferences pref;
    public static SharedPreferences.Editor editor;














    public static void setemail(Activity activity, String userId) {
        pref =activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("email", userId);
        editor.commit();

        Log.v("SharedPreferenece:: ","Saved ::");

    }



    public static void setSavingSchemeId(Activity activity, String userId) {
        pref =activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("userId", userId);
        editor.commit();

        Log.v("SharedPreferenece:: ","Saved ::");

    }
    public static String getSavingSchemeId(Activity activity) {
        pref =activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String id = pref.getString("userId", null);
        Log.v("SharedPreferenece:: ","Stored ::");
        return id;
    }

    public static void setSavingSchemename(Activity activity, String userId) {
        pref =activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("name", userId);
        editor.commit();

        Log.v("SharedPreferenece:: ","Saved ::");

    }
    public static String getSavingSchemename(Activity activity) {
        pref =activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String id = pref.getString("name", null);
        Log.v("SharedPreferenece:: ","Stored ::");
        return id;
    }

    public static String getEmail(Activity activity) {
        pref =activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String id = pref.getString("email", null);
        Log.v("SharedPreferenece:: ","Stored ::");
        return id;
    }



    public static void setEmail(Activity activity, String userId) {
        pref =activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("email", userId);
        editor.commit();

        Log.v("SharedPreferenece:: ","Saved ::");

    }



    public static void setRsvpId(Activity activity, String clubdid) {
        pref =activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("rsvp_id", clubdid);
        editor.commit();

        Log.v("SharedPreferenece:: ","Saved ::");

    }
    public static String getRsvpId(Activity activity) {
        pref =activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String id = pref.getString("rsvp_id", null);
        Log.v("SharedPreferenece:: ","Stored ::");
        return id;
    }


    public static void setTokan(Activity activity, String userId) {
        pref =activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("name", userId);
        editor.commit();

        Log.v("SharedPreferenece:: ","Saved ::");

    }
    public static void LogOut(Activity activity){

        pref =activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.remove("rsvp_id");
        editor.remove("emp_id");
        editor.remove("club_id");
        editor.remove("id");
        editor.remove("imageurl");
        editor.clear();
        editor.commit();

        Log.v("SharedPreferenece:: ", "LogoutFragment ::");
    }
}
