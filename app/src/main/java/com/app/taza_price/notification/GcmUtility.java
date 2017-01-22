package com.app.taza_price.notification;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import com.app.taza_price.HomeActivity;

import java.util.ArrayList;

import static com.app.taza_price.utility.AppConstants.NOTIFICATION;

/**
 * Created by Rakshith on 8/12/2016.
 */
public class GcmUtility {
    public static final  String SENDER_ID = "1093882550075";

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 900;

    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final String GCM_ID_SENT = "gcm_id_sent";
    static final String TAG = "GCM";

    public static void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }





    //get gcm id from shred preference
    public static String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }

        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    private static SharedPreferences getGCMPreferences(Context context) {

        return context.getSharedPreferences(HomeActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }


    //check app version
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    public static boolean checkPlayServices(Activity activity) {
		  /* int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
		    if (resultCode != ConnectionResult.SUCCESS) {
		        if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
		            //GooglePlayServicesUtil.getErrorDialog(resultCode, activity,
		              //      PLAY_SERVICES_RESOLUTION_REQUEST).show();
		        } else {
		            Log.i(TAG, "This device is not supported.");

		        }
		        return false;
		    }*/
        return true;
    }



    public static void setGcmIdSent(Context context,boolean value) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(GCM_ID_SENT, value);

        editor.commit();
    }





    //get gcm id from shred preference
    public static boolean isGcmIdSent(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
       return prefs.getBoolean(GCM_ID_SENT, false);

    }


    static  String DELIMITER =" --- ";
    public static void addNewNotification(Context context, String notification) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String oldNotifications=getNotificationText(context);
        if(!TextUtils.isEmpty(oldNotifications)){

            String array[]=oldNotifications.split(DELIMITER);
            if(array.length>3){

                array=removeItem(array,3)     ;
                oldNotifications=TextUtils.join(DELIMITER, array);
                oldNotifications=notification+DELIMITER+oldNotifications;

            }else{
                oldNotifications=notification+DELIMITER+oldNotifications;
            }
        }else{
            oldNotifications=notification;
        }


        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(NOTIFICATION, oldNotifications);

        editor.commit();
    }





    //get gcm id from shred preference
    public static String getNotificationText(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String notification= prefs.getString(NOTIFICATION, "");
        notification=notification.replaceAll(DELIMITER," --- ");
        if(TextUtils.isEmpty(notification))
            notification="Welcome to TaazaPrice";
        return  notification;

    }

    private static String[] removeItem(String[] names,
                                       int position) {

        ArrayList<String> al_temp=new ArrayList<String>();// temporary ArrayList

        for(int i=0;i<names.length;i++)
        {
            al_temp.add(names[i]);
        }

        al_temp.remove(position);
        names= new String[al_temp.size()];//array cleared with new size



        for(int i=0;i<al_temp.size();i++)
        {
            names[i]=al_temp.get(i);
        }


        return names;
    }
}
