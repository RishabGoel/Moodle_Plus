package com.example.rishab.moodle;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rishab on 21-02-2016.
 */
public class Re {
    Context context;
    Intent intent;
    String url="http://10.192.57.72:8000";
    String URL="http://10.192.57.72:8000/default/login.json?userid=vinay&password=vinay";
    public Re(Intent i, Context c, String u){
        context=c;
        url+=u;
        intent=i;

    }
    public void request() {
        Log.d("url",url);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                //add the logic after recieving the data
                try {

                    Log.d("asd",string);
                    intent.putExtra("data", string);
                    context.startActivity(intent);
                    JSONObject data = new JSONObject("s");
                } catch (JSONException e) {
                    Log.d("check", "error in getting the threads");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError string) {
                Log.d("check", string.toString());

            }

            ;
        });
        //the following is the global request queue to prevent construction of
        // request queue again and again
        SingletonNetworkClass.getInstance(context).addToRequestQueue(stringRequest);

        //        queue.add(stringRequest);

    }
}