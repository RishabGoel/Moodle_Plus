package com.example.rishab.moodle;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

//This activity shows the threads created for a course
public class CommentList extends ActionBarActivity {
    String URL = "";
    String course_code;
    String[] data_title;
    String[] data_desc;
    String[] data_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);
        Log.d("dds", "sta");
        String data = getIntent().getStringExtra("data");
        course_code = getIntent().getStringExtra("coursecode3");
        try {
            JSONObject a = new JSONObject(data);
            JSONArray data_recv = a.getJSONArray("course_threads");
            data_title = new String[data_recv.length()];
            data_desc = new String[data_recv.length()];
            data_id = new String[data_recv.length()];
            for (int i = 0; i < data_recv.length(); i++) {
                JSONObject d = data_recv.getJSONObject(i);
                data_title[i] = d.getString("title");
                data_desc[i] = d.getString("description");
                data_id[i] = Integer.toString(d.getInt("id"));
            }
        } catch (JSONException e1) {
            Log.d("check", "failed to load data");
        }

        CustomListAdapter a = new CustomListAdapter(this, data_title, data_desc);
        ListView listview = (ListView) findViewById(R.id.course_threads);
        listview.setAdapter(a);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String idd = data_id[position];
                Intent intent = new Intent(CommentList.this, ThreadComments.class);
                intent.putExtra("id", idd);
                intent.putExtra("title", data_title[position]);
                intent.putExtra("description", data_desc[position]);
                Re a = new Re(intent, CommentList.this, "/threads/thread.json/" + idd, 1);
                a.request();

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comment_list, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        switch (id) {
            case R.id.action_grades:
                Intent target1 = new Intent(CommentList.this, All_Grades.class);
                Re q1 = new Re(target1, CommentList.this, "/default/grades.json", 1);
                q1.request();
                break;
            case R.id.action_notifications:
                Intent target2 = new Intent(CommentList.this, Notifications.class);
                Re q2 = new Re(target2, CommentList.this, "/default/notifications.json", 1);
                q2.request();
                break;
            case R.id.action_logout:
                Intent target3 = new Intent(CommentList.this, MainActivity.class);
                Re q3 = new Re(target3, CommentList.this, "/default/logout.json", 1);
                q3.request();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    public void createThread(View view){
        Intent intent=new Intent(CommentList.this,NewThread.class);
        intent.putExtra("course_code",course_code);
        startActivity(intent);
    }
}
