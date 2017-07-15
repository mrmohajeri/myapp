package com.example.nefrin.newprojectstartup;

import android.app.ProgressDialog;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by Nefrin on 6/25/2017.
 */
public class NetUtils {
    private static final String BASE_URL = "http://192.168.8.9/";
    private static AsyncHttpClient client = new AsyncHttpClient();
    ProgressDialog progressDialog;

    public static void get(String url, RequestParams params, JsonHttpResponseHandler responseHandler) {
        client.setTimeout(30000);
        client.setConnectTimeout(30000);
        client.setResponseTimeout(30000);
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.setTimeout(30000);
        client.setConnectTimeout(30000);
        client.setResponseTimeout(30000);
        Log.d("The url: ", getAbsoluteUrl(url));
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void refresh(String url, RequestParams params, JsonHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }


    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
