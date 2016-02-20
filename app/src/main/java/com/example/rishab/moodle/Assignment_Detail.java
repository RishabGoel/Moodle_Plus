package com.example.rishab.moodle;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

//

public class Assignment_Detail extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment__detail);
        String newline = System.getProperty("line.separator");// to skip to next line
        Intent intent_r = getIntent();

        //displaying various infos related to assignments in different text fileds

        String message1 = intent_r.getStringExtra(Course_Assignments.Id);//Id of the assignemnt
        TextView textView = (TextView)findViewById(R.id.t1);
        textView.setText("Assignment #" + message1 );

        String message2 = intent_r.getStringExtra(Course_Assignments.Name);
        textView = (TextView)findViewById(R.id.t2);
        textView.setText(message2);//Assignment Topic

        String message3 = intent_r.getStringExtra(Course_Assignments.Deadline);
        textView = (TextView)findViewById(R.id.t3);
        textView.setText("Submission Deadline: "+message3);

        String message4 = intent_r.getStringExtra(Course_Assignments.Description);
        textView = (TextView)findViewById(R.id.t4);
        textView.setText("Description: "+newline+message4);//Description

        String message5 = intent_r.getStringExtra(Course_Assignments.CreatedAt);
        textView = (TextView)findViewById(R.id.t5);
        textView.setText("Uploaded on:  "+(message4));

        String message6 = intent_r.getStringExtra(Course_Assignments.Late);
        textView = (TextView)findViewById(R.id.t6);
        textView.setText("Late days allowed: "+message6);//late days

        String message7 = intent_r.getStringExtra(Course_Assignments.Type);
        textView = (TextView)findViewById(R.id.t7);
        textView.setText("Type: "+message7);

       // File left

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_assignment__detail, menu);
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
