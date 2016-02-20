package com.example.rishab.moodle;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONObject;


public class Course_Template extends ActionBarActivity {
    public final static String EXTRA_MESSAGE = "com.example.rishab.moodle.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course__template);


        ImageButton button1 = (ImageButton)findViewById(R.id.b1);
        ImageButton button2 = (ImageButton)findViewById(R.id.b2);
        ImageButton button3 = (ImageButton)findViewById(R.id.b3);

        TextView t = (TextView)findViewById(R.id.t1);

        Intent intent_r = getIntent();
        final String message = intent_r.getStringExtra("coursecode");//Receiving course code as the identifier
        t.setText(message);//setting course code on top

        //String server = "localhost:8000";
        //Json_Returning obj = new Json_Returning;
        //Assignment button intended to lead to list of assignments related to course

        button1.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                            //Assignments
                        //Intent intent_r = getIntent();
                        //String message = intent_r.getStringExtra("coursecode");
                        //String URL = server + "/courses/course.json/coursecode/assignment";
                        //JSONObject data_received = obj.Data_Received(URL);
                        //use JSON object
                        Intent intent_s = new Intent(Course_Template.this, Course_Assignments.class);//
                        intent_s.putExtra("coursecode1", message);//course code message
                        Request1 req = new Request1(intent_s,Course_Template.this,"/courses/course.json/"+message+"/assignment");//course assignment api
                        req.request();
                        //startActivity(intent_s);
                    }
                }
        );


        //Grades button intended to lead to showing grade components of the course
        button2.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                            //Grades
                        //Intent intent_r = getIntent();
                        //String message = intent_r.getStringExtra("coursecode");
                        //String URL = server + "/courses/course.json/coursecode/grades";
                        //JSONObject data_received = obj.Data_Received(URL);
                        //use JSON object
                        Intent intent_s = new Intent(Course_Template.this, Course_Grade.class);
                        intent_s.putExtra("coursecode2", message);//course code message
                        Request1 req = new Request1(intent_s,Course_Template.this,"/courses/course.json/"+message+"/grades");//course grade api
                        req.request();
                        //startActivity(intent_s);
                    }
                }
        );


        //Threads button intended to lead to list of threads related to course
        button3.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                            //Threads
                        //Intent intent_r = getIntent();
                        //String message = intent_r.getStringExtra("coursecode");
                        //String URL = server + "/courses/course.json/coursecode/threads";
                        //JSONObject data_received = obj.Data_Received(URL);
                        //use JSON object
                        Intent intent_s = new Intent(Course_Template.this, CommentList.class);
                        intent_s.putExtra("coursecode3", message);//course code message
                        Request1 req = new Request1(intent_s,Course_Template.this,"/courses/course.json/"+message+"/threads");//course threads api
                        req.request();
                        //startActivity(intent_s);
                    }
                }
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course__template, menu);
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

    /*public void sendMessage(View view) {
        Intent intent = new Intent(this, Course_Assignments.class);
        //EditText editText = (EditText) findViewById(R.id.edit_message);
        //String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }*/
}
