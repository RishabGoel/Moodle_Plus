package com.example.rishab.moodle;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.HashMap;


public class Course_Assignments extends ActionBarActivity {
    //variables related to different keys

    final static String Name = "name";
    final static String File = "file_";
    final static String CreatedAt = "created_at";
    final static String CourseId = "registered_course_id";
    final static String Late = "late_days_allowed";
    final static String Type = "type_";
    final static String Deadline = "deadline";
    final static String Id = "id";
    final static String Description = "description";
    final static String Assignments = "assignment";


    ArrayList<HashMap<String, String>> assignmentList;

    JSONArray ass = new JSONArray();//array which will provide objects


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course__assignments);
        ListView listView = (ListView)findViewById(R.id.list);

        Intent intent_r = getIntent();
        String message = intent_r.getStringExtra("coursecode1");//course code message
        JSONObject data_received = new JSONObject();//JSON Object which will be parsed throughout this assignment
        String js = intent_r.getStringExtra("data");//receiving string form of JSON object
        //String URL = "/courses/course.json/"+message+"/assignment";
        //Request a = new Request(this,URL);
        //a.request();
        //while(a.data==null){}
        try
        {
            data_received = new JSONObject(js);//type casting to JSON object
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        //String data_string = data_received.toString();
        //use JSONObject

        assignmentList = new ArrayList<HashMap<String, String>>();

        ListView lv = (ListView)findViewById(R.id.list);
        //private static final String Name = "name";
        if(data_received!=null)
        {
            try
            {
                ass = data_received.getJSONArray(Assignments);

                //looping through various JSON objects of assignment
                for (int i = 0; i < ass.length(); i++)
                {

                    JSONObject c = ass.getJSONObject(i);

                    final String name = c.getString(Name);
                    final String file = c.getString(File);
                    final String createdat = c.getString(CreatedAt);
                    final String courseid = Integer.toString(c.getInt(CourseId));
                    final String late = Integer.toString(c.getInt(Late));
                    final String type = Integer.toString(c.getInt(Type));
                    final String deadline = c.getString(Deadline);
                    final String id = Integer.toString(c.getInt(Id));
                    final String description = (Jsoup.parse(c.getString("description")).text());//parsing HTML string

                    HashMap<String, String> contact = new HashMap<String, String>();

                    contact.put(Name, name);
                    contact.put(Deadline, deadline);
                    contact.put(Id, id);

                    assignmentList.add(contact);

                    //adapter to display the required list row
                    ListAdapter adapter = new SimpleAdapter
                            (
                                    Course_Assignments.this,
                                    assignmentList,
                                    R.layout.list_item,
                                    new String[] { Name, Deadline, Id },
                                    new int[] { R.id.assignment_number, R.id.submission_date, R.id.status }
                            );

                    /*lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long d) {
                            Intent in = new Intent(getApplicationContext(), Assignment_Detail.class);
                            in.putExtra(Id,id);
                            in.putExtra(Deadline,deadline);
                            in.putExtra(Description,description);
                            in.putExtra(Late,late);
                            in.putExtra(CreatedAt,createdat);
                            in.putExtra(Type,type);
                            in.putExtra(CourseId,courseid);
                            in.putExtra(Name,name);
                            in.putExtra(File,file);
                            startActivity(in);
                        }
                    });*/
                    lv.setAdapter(adapter);
                }
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

        //assigning click event to list item
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long d) {
                Intent in = new Intent(getApplicationContext(), Assignment_Detail.class);

                try

                {

                    JSONObject o = ass.getJSONObject(position);

                    //putting data into next activity
                    in.putExtra(Id,Integer.toString(o.getInt("id")));
                    in.putExtra(Deadline,o.getString("deadline"));
                    in.putExtra(Description,o.getString(Jsoup.parse(o.getString("description")).text()));
                    in.putExtra(Late,Integer.toString(o.getInt("late_days_allowed")));
                    in.putExtra(CreatedAt,o.getString("created_at"));
                    in.putExtra(Type,Integer.toString(o.getInt("type_")));
                    in.putExtra(CourseId,Integer.toString(o.getInt("registered_course_id")));
                    in.putExtra(Name,o.getString("name"));
                    in.putExtra(File,o.getString("file"));

                }

                catch(JSONException e)
                {
                    e.printStackTrace();
                }

                Request1 req = new Request1(in,Course_Assignments.this,"");
                req.request();
                //startActivity(in);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course__assignments, menu);
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
