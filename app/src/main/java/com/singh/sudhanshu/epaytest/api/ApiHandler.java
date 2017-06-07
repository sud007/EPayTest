package com.singh.sudhanshu.epaytest.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.singh.sudhanshu.epaytest.ui.activity.AppCallback;
import com.singh.sudhanshu.epaytest.utils.PreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Sudhanshu on 6/4/2017.
 */

public class ApiHandler {

    private static final String TAG = ApiHandler.class.getSimpleName();

    // Instantiate the RequestQueue.

    public static void fetchTokenAndSaveIfNull(Context ctx, final AppCallback callback) {
        getRequest(ctx, APIs.LOGIN_URL, new AppCallback() {

            @Override
            public void onSuccess(Object data) {

                JSONObject object = ((JSONObject) data);
                try {
                    String token = object.getString("token");
                    Log.i(TAG, "saving token: " + token);

                    PreferenceUtil.getInstance().putStringValue("token", token);

                    callback.onSuccess(null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Object data) {
                callback.onFailure(null);
            }
        });
    }

    public static void getRequest(Context ctx, final String url, final AppCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(ctx);
        // Request a string response from the provided URL.
        JSONObject params = new JSONObject();
        try {
            params.put("Accept", "application/json");
            params.put("Content-Type", "application/json");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        Log.i(TAG, "url: " + url);
                        Log.i(TAG, "onResponse: " + response);
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "url: " + url);
                Log.i(TAG, "onResponse: " + error.getMessage());
                callback.onFailure(error.getMessage());
            }
        });

//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
// Add the request to the RequestQueue.
        queue.add(request);
    }

    private void appenedHeader() {

    }
}
