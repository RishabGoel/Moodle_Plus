package com.example.rishab.moodle;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;


public class Course_Grade extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course__grade);
        Intent intent_r = getIntent();
        String message = intent_r.getStringExtra("coursecode2");
        //String URL = "/courses/course.json/"+message+"/grades";
        JSONObject data_received = new JSONObject();
        String js = intent_r.getStringExtra("data");
        try
        {
            data_received = new JSONObject(js);
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        //Request a = new Request(this,URL);
        //a.request();
        //while(a.data==null){}
        //JSONObject data_received = a.data;
        //JSON
        //JSONObject data_received = obj.Data_Received(URL);
        TextView textview1 = (TextView)findViewById(R.id.t1);
        TextView textview2 = (TextView)findViewById(R.id.t2);

        String newline = System.getProperty("line.separator");//to skip the line

        float current_score = 0;//Current actual score in the course
        float current_max = 0;//Current maximum possible score in the course

        if(data_received!=null)
        {
            try
            {
                JSONArray gr = data_received.getJSONArray("grades");
                //looping through the various scoring components related to course
                for (int i = 0; i < gr.length(); i++)
                {
                    JSONObject c = gr.getJSONObject(i);
                    float weight = BigDecimal.valueOf(c.getDouble("weightage")).floatValue();//actual weightage in the course

                    String name = c.getString("name");//name as in assignment or minor or major

                    float out_of = BigDecimal.valueOf(c.getDouble("out_of")).floatValue();//maximum pseudo possible marks

                    float score = BigDecimal.valueOf(c.getDouble("score")).floatValue();//pseudo score in the component

                    int id = c.getInt("id");//id of the component

                    float ratio = weight/out_of;//to scale to the weightage

                    current_max+=weight;//

                    current_score = current_score+ (ratio*score);

                    textview2.append
                            (
                                    ("Type: " + name) +
                                    newline +
                                    ("Course Weightage: "+Float.toString(weight)) +
                                    newline +
                                    ("Assignment Score: "+Float.toString(score) + " out of "+Float.toString(out_of)) +
                                    newline +
                                    ("Contribution to Course: "+Float.toString(ratio*score) + " out of "+Float.toString(weight)) +
                                    newline + "" + newline + "" + newline
                            );

                }
                textview1.setText
                        (

                                "Total Score " +
                                newline +
                                (Float.toString(current_score)+" out of "+Float.toString(current_max)+" :")
                        );

            }
        catch(JSONException E)
        {
            E.printStackTrace();
        }
        }
    else
        {
        Log.e("ServiceHandler", "Couldn't get any data from the url");
    }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course__grade, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
