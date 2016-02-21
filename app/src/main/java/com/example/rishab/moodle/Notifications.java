package com.example.rishab.moodle;

import android.app.Notification;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;


public class Notifications extends ActionBarActivity {

    ListView listView;
    String[] description,createdAt;
    int[] userId,isSeen,id;
    String[] link,display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Moodle: Notifications");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        String requestUrl = "/default/notifications.json";
        JSONArray notificationsArray;
        JSONObject notifications;
        // get the data from rishabs ffunction into notification array and notification
        //subsequently put the data into String arrays

        int size = 2;
        description = new String[2];
        link = new String[size];
        display = new String[size];
        description[0] = "<a href='/users/user/1'>Shubham Jindal</a> posted a new <a href='/threads/thread/3'>thread</a> for <a href='/courses/course/col380'>col380</a>.";
        description[1] = "<a href='/users/user/1'>Shubham Jindal</a> posted a new <a href='/threads/thread/2'>thread</a> for <a href='/courses/course/col380'>col380</a>.";

        for(int i=0;i<size;i++){
            NotificationEncodedObject temp = new NotificationEncodedObject();
            temp = EncodeNotificationDescription.encode(description[i]);
            link[i] = temp.link;
            display[i] = temp.display;
        }

        listView = (ListView) findViewById(R.id.list_notifications);
        ArrayAdapter<String> addapter = new ArrayAdapter<String>(listView.getContext(),android.R.layout.simple_list_item_1,display);
        listView.setAdapter(addapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(Notifications.this, Course_Template.class);
                String course_code = link[position];
                intent.putExtra("coursecode",course_code);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notifications, menu);
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
