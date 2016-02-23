package com.example.rishab.moodle;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Rishab on 21-02-2016.
 */
//This is a generic class to send request to the server using the volley request
public class Re {
    Context context;
    Intent intent;
//    String url="http://10.192.57.72:8000";
//    String url="http://192.168.0.100:8000";
    String url="http://tapi.cse.iitd.ernet.in:1805";
    String URL="http://10.192.57.72:8000/default/login.json?userid=vinay&password=vinay";
    int reqtype;
    public Re(Intent i, Context c, String u, int t){
        context=c;
        url+=u;
        intent=i;
        reqtype=t;
    }
    public void request() {
        Log.d("url",url);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                //add the logic after recieving the data
                try {
                    if (reqtype==0){
                        JSONObject temp = new JSONObject(string);
                        boolean success = temp.getBoolean("success");
                        if(success==true){
                            Intent courseList = new Intent(context,CourseList.class);
                            Re login = new Re(courseList,context,"/courses/list.json",1);
                            login.request();
                        }
                        else {
                            Toast.makeText(context, "Invalid Username or Password",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if(reqtype==1){
                        intent.putExtra("data", string);
                        context.startActivity(intent);
                    }
                    else if(reqtype==3){
                        String course_code=intent.getStringExtra("coursecode3");
                        Re req=new Re(intent,context,"/courses/course.json/"+course_code+"/threads",1);
                        req.request();

                    }
                    else{
                        String a=intent.getStringExtra("id");
                        Re req=new Re(intent,context,"/threads/thread.json/"+a,1);
                        req.request();
                    }

                    JSONObject data = new JSONObject("{s:'s'}");
                } catch (JSONException e) {
                    Log.d("check", "error in getting the threads");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError string) {
                Log.d("check", string.toString());
                Toast.makeText(context, "Network Error : Something went wrong",
                        Toast.LENGTH_SHORT).show();

            }

            ;
        });
        //the following is the global request queue to prevent construction of
        // request queue again and again
        SingletonNetworkClass.getInstance(context).addToRequestQueue(stringRequest);

        //        queue.add(stringRequest);

    }
}
