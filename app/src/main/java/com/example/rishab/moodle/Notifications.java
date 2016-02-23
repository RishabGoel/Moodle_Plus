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
    String[] userId,isSeen,threadId;
    String[] link,display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTitle("Moodle: Notifications");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);


        Intent intent = getIntent();
        String stringResponse = intent.getStringExtra("data");


        //String requestUrl = "/default/notifications.json";
        try {

            JSONArray notificationsArray;
            JSONObject notifications;
            // get the data from rishabs ffunction into notification array and notification
            //subsequently put the data into String arrays

            notifications = new JSONObject(stringResponse);
            notificationsArray = notifications.getJSONArray("notifications");

            int size = 1;
            if (notificationsArray != null) size = notificationsArray.length();


            description = new String[size];
            link = new String[size];
            display = new String[size];
            threadId = new String[size];

            for (int i = 0; i < size; i++) {
                JSONObject temp = notificationsArray.getJSONObject(i);
                description[i] = temp.getString("description");
                //isSeen[i] = temp.getInt("is_seen");
                //createdAt[i] = temp.getString("created_at");
                // threadId[i] = temp.getInt("id");
                //userId[i] = temp.getInt("user_id");
            }

            // description[0] = "<a href='/users/user/1'>Shubham Jindal</a> posted a new <a href='/threads/thread/3'>thread</a> for <a href='/courses/course/col380'>col380</a>.";
            // description[1] = "<a href='/users/user/1'>Shubham Jindal</a> posted a new <a href='/threads/thread/2'>thread</a> for <a href='/courses/course/col380'>col380</a>.";

            for (int i = 0; i < size; i++) {
                NotificationEncodedObject temp = new NotificationEncodedObject();
                temp = EncodeNotificationDescription.encode(description[i]);
                link[i] = "/threads/thread.json/"+temp.link;
                threadId[i] = temp.link;
                display[i] = temp.display;
                //link[i] = "/threads/thread.json/"+link[i].charAt([link[i].length()-1];
            }

            listView = (ListView) findViewById(R.id.list_notifications);
            ArrayAdapter<String> addapter = new ArrayAdapter<String>(listView.getContext(), android.R.layout.simple_list_item_1, display);
            listView.setAdapter(addapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    /*Intent intent = new Intent(Notifications.this, KunalActivity.class);
                    String course_code = link[position];
                    intent.putExtra("coursecode", course_code);
                    startActivity(intent);*/

                    Intent target = new Intent(Notifications.this,ThreadComments.class);
                    target.putExtra("id",threadId[position]);
                    target.putExtra("thread_name",display[position]);
                    Re q3 = new Re(target,Notifications.this,link[position],1);
                    q3.request();

                }
            });
        }
        catch(org.json.JSONException exception){
            // how you handle the exception
            // e.printStackTrace();
        }

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
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        switch(id){
            case R.id.action_grades:
                Intent target1 = new Intent(Notifications.this,All_Grades.class);
                Re q1 = new Re(target1,Notifications.this,"/default/grades.json",1);
                q1.request();
                break;
            case R.id.action_notifications:
                Intent target2 = new Intent(Notifications.this,Notifications.class);
                Re q2 = new Re(target2,Notifications.this,"/default/notifications.json",1);
                q2.request();
                break;
            case R.id.action_logout:
                Intent target3 = new Intent(Notifications.this,MainActivity.class);
                Re q3 = new Re(target3,Notifications.this,"/default/logout.json",1);
                q3.request();
                break;
            default: break;
        }

        return super.onOptionsItemSelected(item);
    }
}
