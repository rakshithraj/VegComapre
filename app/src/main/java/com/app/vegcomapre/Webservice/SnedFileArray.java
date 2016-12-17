package com.app.vegcomapre.Webservice;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by Rakshith on 7/27/2016.
 */
public class SnedFileArray  extends Request {
    private MultipartEntity entity = new MultipartEntity();

    private static final String FILE_PART_NAME = "file";
    private static final String STRING_PART_NAME = "string";

    private final Response.Listener mListener;
    private final ArrayList<File> mFilePartList;

    String boundary = "apiclient-" + System.currentTimeMillis();
    String mimeType = "multipart/form-data;boundary=" + boundary;
    public SnedFileArray(int method, String url, Response.Listener resplistener, Response.ErrorListener errorListener, ArrayList<File> file) {
        super(method, url, errorListener);
        mListener = resplistener;
        mFilePartList = file;
        buildMultipartEntity();
    }


    public SnedFileArray(int method, String url, Response.Listener resplistener, Response.ErrorListener errorListener, MultipartEntity entity ) {
        super(method, url, errorListener);
        mListener = resplistener;
        this.entity = entity;
        mFilePartList = null;
    }

    private void buildMultipartEntity() {
        entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

        for(int i=0;i<mFilePartList.size();i++) {
            entity.addPart(FILE_PART_NAME+"["+i+"]", new FileBody(mFilePartList.get(i)));
        }

    }

    @Override
    public String getBodyContentType()
    {
        return mimeType;
    }

    @Override
    public byte[] getBody() throws AuthFailureError
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try
        {
            entity.writeTo(bos);
        }
        catch (IOException e)
        {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }


    @Override
    protected Response parseNetworkResponse(NetworkResponse networkResponse) {
        //return Response.success("Uploaded", getCacheEntry());

        Object parsed;
        try {
            parsed = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
        } catch (UnsupportedEncodingException var4) {
            parsed = new String(networkResponse.data);
        }

        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(networkResponse));
    }

    @Override
    protected void deliverResponse(Object response) {
        mListener.onResponse(response);
    }
}
