package com.app.vegcomapre.Webservice;

import android.R.id;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.TextUtils;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.vegcomapre.appInterface.ServerResponseInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rakshith on 9/25/2015.
 */
public class ConnectWebService {


    private static final int TIME_OUT = 30000;

    public void setLoadingMessage(String loadingMessage) {
        this.loadingMessage = loadingMessage;
    }

    private String loadingMessage;
    private ServerResponseInterface serverResponse;

    public HashMap<String, String> getHeaders() {
        return this.headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    private HashMap<String, String> headers = new HashMap<>();

    public void setOnServerResponse(ServerResponseInterface serverResponse) {
        this.serverResponse = serverResponse;
    }




    public void stringGetRequest(String url, final Activity activity, final boolean dialog) {
        String tag_string_req = "string_req";
        final ProgressDialog pDialog;
        if (null != activity) {
            pDialog = new ProgressDialog(activity);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setCancelable(false);
        } else {
            pDialog = null;
        }

        if (dialog && null != activity) {

            if (TextUtils.isEmpty(this.loadingMessage)) {
                pDialog.setMessage(Html.fromHtml("<font color='#F0A134'>" + "Loading..." + "</font>."));
            } else {
                pDialog.setMessage(Html.fromHtml("<font color='#F0A134'>" + this.loadingMessage + "</font>."));
            }

            pDialog.show();
        }


        StringRequest strReq = new StringRequest(Method.GET,
                url, new Listener<String>() {


            @Override
            public void onResponse(String response) {
                if (dialog && null != activity) {
                    pDialog.hide();
                }

                ConnectWebService.this.serverResponse.setLoading(false);
                // Toast.makeText(activity, "response=" + response, Toast.LENGTH_LONG).show();
                if (null != ConnectWebService.this.serverResponse) {
                    ConnectWebService.this.serverResponse.onServerResponse(response);
                }


            }
        }, new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (dialog && null != activity) {
                    pDialog.hide();
                }
                ConnectWebService.this.serverResponse.setLoading(false);
                if (null != ConnectWebService.this.serverResponse)
                    // Toast.makeText(activity, "response=" + error, Toast.LENGTH_LONG).show();
                {
                    ConnectWebService.this.serverResponse.onServerError(error + "");
                }

            }

        }


        ) {

            @Override
            public Map<String, String> getHeaders() {

                return ConnectWebService.this.headers;
            }


        };



        strReq.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
// Adding request to request queue

        strReq.setRetryPolicy(new DefaultRetryPolicy(
                TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }





    public void stringPostRequest(String url, final Context context, Map<String, String> tempParams, final boolean dialog) {
        String tag_string = "stringPost";
        final Map<String, String> params = tempParams;

        final ProgressDialog pDialog;
        if (null != context) {
            pDialog = new ProgressDialog(context);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setCancelable(false);

        } else {
            pDialog = null;
        }

        if (dialog && null != context) {

            if (TextUtils.isEmpty(this.loadingMessage)) {
                pDialog.setMessage(Html.fromHtml("<font color='#F0A134'>" + "Loading..." + "</font>."));
            } else {
                pDialog.setMessage(Html.fromHtml("<font color='#F0A134'>" + this.loadingMessage + "</font>."));
            }
            pDialog.show();
            TextView tv = (TextView) pDialog.findViewById(id.message);
            tv.setTextColor(Color.parseColor("#3EA59C"));
        }
        this.serverResponse.setLoading(true);
        CustomStringRequest jsonObjReq = new CustomStringRequest(Method.POST,
                url, null,
                new Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        if (dialog && null != context) {
                            pDialog.dismiss();
                        }
                        if (null != ConnectWebService.this.serverResponse) {
                            ConnectWebService.this.serverResponse.setLoading(false);
                            ConnectWebService.this.serverResponse.onServerResponse(response);
                        }

                        // Toast.makeText(activity, "response=" + response, Toast.LENGTH_LONG).show();
                    }
                }, new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (dialog && null != context) {
                    pDialog.dismiss();
                }
                if (null != ConnectWebService.this.serverResponse) {
                    ConnectWebService.this.serverResponse.setLoading(false);
                    ConnectWebService.this.serverResponse.onServerError(error.getMessage());
                }

                //Toast.makeText(activity, "response=" + error, Toast.LENGTH_LONG).show();
            }

        }) {


            @Override
            protected Map<String, String> getParams() {


                return params;
            }

            @Override
            public Map<String, String> getHeaders() {


                return ConnectWebService.this.headers;
            }

        };


        /*jsonObjReq.setRetryPolicy(
                new DefaultRetryPolicy(
                        500000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
        );*/
// Adding request to request queue
       // int socketTimeout = 5000;//30 seconds - change to what you want
    //    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
       // jsonObjReq.setRetryPolicy(policy);

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjReq,tag_string);

    }





}