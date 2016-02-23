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


        //Assignment button intended to lead to list of assignments related to course

        button1.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent intent_s = new Intent(Course_Template.this, Course_Assignments.class);//
                        intent_s.putExtra("coursecode1", message);//course code message
                        Re req = new Re(intent_s,Course_Template.this,"/courses/course.json/"+message+"/assignments",1);//course assignment api
                        req.request();

                    }
                }
        );


        //Grades button intended to lead to showing grade components of the course
        button2.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        Intent intent_s = new Intent(Course_Template.this, Course_Grade.class);
                        intent_s.putExtra("coursecode2", message);//course code message
                        Re req = new Re(intent_s,Course_Template.this,"/courses/course.json/"+message+"/grades",1);//course grade api
                        req.request();

                    }
                }
        );


        //Threads button intended to lead to list of threads related to course
        button3.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        Intent intent_s = new Intent(Course_Template.this, CommentList.class);
                        intent_s.putExtra("coursecode3", message);//course code message
                        Re req = new Re(intent_s,Course_Template.this,"/courses/course.json/"+message+"/threads",1);//course threads api
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
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        switch(id){
            case R.id.action_grades:
                Intent target1 = new Intent(Course_Template.this,All_Grades.class);
                Re q1 = new Re(target1,Course_Template.this,"/default/grades.json",1);
                q1.request();
                break;
            case R.id.action_notifications:
                Intent target2 = new Intent(Course_Template.this,Notifications.class);
                Re q2 = new Re(target2,Course_Template.this,"/default/notifications.json",1);
                q2.request();
                break;
            case R.id.action_logout:
                Intent target3 = new Intent(Course_Template.this,MainActivity.class);
                Re q3 = new Re(target3,Course_Template.this,"/default/logout.json",1);
                q3.request();
                break;
            default: break;
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
