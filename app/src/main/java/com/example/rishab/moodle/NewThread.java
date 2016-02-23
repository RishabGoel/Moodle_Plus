package com.example.rishab.moodle;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

//This activity creates new threads
public class NewThread extends ActionBarActivity {
    String URL = "http://127.0.0.1:8000/";
    String course_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_thread);
        course_code = getIntent().getStringExtra("course_code");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_thread, menu);
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
//        This is the logic for the menu actions
        switch (id) {
            case R.id.action_grades:
                Intent target1 = new Intent(NewThread.this, All_Grades.class);
                Re q1 = new Re(target1, NewThread.this, "/default/grades.json", 1);
                q1.request();
                break;
            case R.id.action_notifications:
                Intent target2 = new Intent(NewThread.this, Notifications.class);
                Re q2 = new Re(target2, NewThread.this, "/default/notifications.json", 1);
                q2.request();
                break;
            case R.id.action_logout:
                Intent target3 = new Intent(NewThread.this, MainActivity.class);
                Re q3 = new Re(target3, NewThread.this, "/default/logout.json", 1);
                q3.request();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
//This ads the new thread to the database by sending the request
    public void confirm(View view) {

        EditText editText = (EditText) findViewById(R.id.thread_title);
        String title = (String) editText.getText().toString();
        editText = (EditText) findViewById(R.id.Thread_Description);
        String description = (String) editText.getText().toString();
        try {
            URL = "/threads/new.json?title=" + URLEncoder.encode(title.replace("\n", " "), "UTF-8") + "&description=" + URLEncoder.encode(description, "UTF-8") + "&course_code=" + course_code;
            Intent intent = new Intent(NewThread.this, CommentList.class);
            intent.putExtra("coursecode3", course_code);
            Re req = new Re(intent, NewThread.this, URL, 3);
            req.request();
        } catch (UnsupportedEncodingException e) {
            Log.d("check", "unknow error");
        }

    }
}
