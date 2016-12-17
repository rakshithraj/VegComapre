package com.app.vegcomapre.utility;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.text.Html;


/**
 * Created by Rakshith on 11/29/2016.
 */

public class Utility {

    public static void alerDialog(Context context , String message) {


            AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(context);
            //myAlertDialog.setTitle("LMS");
            message = "<font color='#F0A134'>" + message + "</font>";
            myAlertDialog.setMessage(Html.fromHtml(message));

            myAlertDialog.setPositiveButton(Html.fromHtml("OK"), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.cancel();
                }
            });


            myAlertDialog.show();


    }

    /**
     * check network avilablity
     * @param context application context
     *  @param alertDialog if true displays alert dailog
     *  @return true if network avilable els efalse

     */
    public static boolean isNetworkAvailable(Context context, boolean alertDialog) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (null != networkInfo && networkInfo.isConnected()) {
            return true;
        }else{
            if(alertDialog){
                Utility.alerDialog(context,"Connect to Internet");
            }

        }
        return false;
    }

}
