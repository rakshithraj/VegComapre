package com.app.taza_price;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.app.taza_price.Webservice.ConnectWebService;
import com.app.taza_price.appInterface.ServerResponseInterface;
import com.app.taza_price.notification.GcmUtility;
import com.app.taza_price.utility.Config;
import com.app.taza_price.utility.Utility;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.app.taza_price.utility.AppConstants.SUCESS;

public class SplashScreenActivity extends AppCompatActivity {

    String regid;
    InstanceID instanceID;
    Context context;    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        context=getApplicationContext();
        boolean playServicesAvailable=GcmUtility.checkPlayServices(this);
        if (playServicesAvailable) {

            instanceID = InstanceID.getInstance(this);
            regid = GcmUtility.getRegistrationId(context);

            if (regid.isEmpty()) {
                GcmUtility.setGcmIdSent(this, false);
                new RegisterBackground().execute();
            } else {
                if (!GcmUtility.isGcmIdSent(this)) {
                    callREgisterGcmApi();
                } else {
                    handler.postDelayed(runnable, 3000);
                }

            }

        } else {
            handler.postDelayed(runnable, 3000);
        }

    }


    Handler handler = new Handler();

    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            Intent intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    };


    class RegisterBackground extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            String msg = "";
            try {
                if (instanceID == null) {
                    instanceID = InstanceID.getInstance(context);

                }
                int count = 5;
                while (count != 0 && regid == "") {
                    regid = instanceID.getToken(GcmUtility.SENDER_ID,
                            GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);


                    count--;
                }
                // regid = gcm.register(SENDER_ID);
                msg = "Dvice registered, registration ID=" + regid;
                Log.d("tag", msg);


                // Persist the regID - no need to register again.
                GcmUtility.storeRegistrationId(context, regid);
            } catch (IOException ex) {
                msg = "Error :" + ex.getMessage();
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String msg) {
            if (!TextUtils.isEmpty(regid)) {
                callREgisterGcmApi();
            } else {
                handler.postDelayed(runnable, 1000);
            }

        }
    }

    private void callREgisterGcmApi() {
        String android_id = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);



        ConnectWebService connectWebService = new ConnectWebService();

        connectWebService.setOnServerResponse(new ServerResponseInterface() {

            @Override
            public void setLoading(boolean value) {


            }

            @Override
            public void onServerResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals(SUCESS)) {

                            GcmUtility.setGcmIdSent(SplashScreenActivity.this, true);
                        handler.postDelayed(runnable, 0);


                    } else {
                        Utility.alerDialog(SplashScreenActivity.this, SplashScreenActivity.this.getResources().getString(R.string.please_try_again));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onServerError(String s) {
                GcmUtility.setGcmIdSent(SplashScreenActivity.this, false);
                handler.postDelayed(runnable, 500);

            }

        });

        Map<String, String> param = new HashMap<>();
        param.put("device_id", android_id + "");
        param.put("gcm_id", regid + "");

        connectWebService.setLoadingMessage("Please wait...");
        connectWebService.stringPostRequest(Config.SEND_GCM_ID, this, param, true);



    }



}
