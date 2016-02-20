package com.example.rishab.moodle;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;


public class All_Grades extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__grades);
        ListView listView = (ListView)findViewById(R.id.list2);

        //receiving the object JSON
        Intent intent_r = getIntent();
        JSONObject data_received = new JSONObject();
        String js = intent_r.getStringExtra("data");
        try{data_received = new JSONObject(js);}
        catch(JSONException e)
        {
            e.printStackTrace();
        }

        //String URL = "/default/grades.json";
        //Request a = new Request(this,URL);
        //a.request();
        //while(a.data==null){}
        //JSONObject data_received = a.data;
        ArrayList<HashMap<String, String>> gradelist = new ArrayList<HashMap<String, String>>();
        String newline = System.getProperty("line.separator");//to skip to next line

        int no_of_courses=0;

        int[] ids = new int[20];//it will later serve as boolean to the course which are presently there

        for(int i=0;i<20;i++){ids[i]=-1;}//setting them false so to say

        float[] current_scores = new float[20];//to keep track of current actual score in the course

        float[] max_scores = new float[20];//to keep track of maximum possible marks within a course

        String[] codes = new String[20];//the code of a course

        if(data_received!=null)
        {
            try
            {
                JSONArray courses = data_received.getJSONArray("courses");//array of courses

                //looping through various objects of courses which may have same ids

                //loop to set boolean value so to say

                for(int i=0;i<courses.length();i++)
                {

                    JSONObject c = courses.getJSONObject(i);

                    int id = c.getInt("id");//id of the course

                    if(!(ids[id]==id))//checking if that id is not set yet
                    {
                        no_of_courses++;
                        ids[id]=id;
                        codes[id] = c.getString("code");
                    }
                }

                JSONArray grd = data_received.getJSONArray("grades");//various grades array

                //looping to see accumulate score of various courses

                for(int i=0;i<grd.length();i++)
                {

                    JSONObject c = grd.getJSONObject(i);

                    int rcid = c.getInt("registered_course_id"); //course id of the component

                    float weight = BigDecimal.valueOf(c.getDouble("weightage")).floatValue();//weightage of this component

                    String name = c.getString("name");//name or type of the component as in assignment or minor or major

                    float out_of = BigDecimal.valueOf(c.getDouble("out_of")).floatValue();//pseudo maximum possible

                    float score = BigDecimal.valueOf(c.getDouble("score")).floatValue();//pseudo scored

                    float ratio = weight/out_of;//scaling

                    current_scores[rcid] = current_scores[rcid] + (ratio*score);//accumulating the cuurent actual score

                    max_scores[rcid] = max_scores[rcid] + (weight);//accumulating the maximum possible scores

                }

                HashMap<String, String> course_grade = new HashMap<String, String>();

                for(int i=0;i<20;i++)
                {

                    if(ids[i]==i)//if that course is part of student's

                    {

                        course_grade.put("key1",codes[i]);
                        course_grade.put("key2","Current Grade: "+Float.toString(current_scores[i])+" out of "+Float.toString(max_scores[i]));
                        gradelist.add(course_grade);
                        ListAdapter adapt = new SimpleAdapter
                                (
                                        All_Grades.this,
                                        gradelist,
                                        R.layout.list_item2,
                                        new String[] { "key1", "key2"},
                                        new int[] { R.id.t1, R.id.t2}
                                );

                        listView.setAdapter(adapt);

                    }

                }

            }
            catch(JSONException E)
            {
                E.printStackTrace();
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all__grades, menu);
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
