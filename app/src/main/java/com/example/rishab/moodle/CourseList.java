package com.example.rishab.moodle;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rishab.moodle.Course_Template;
import com.example.rishab.moodle.R;

import org.json.JSONArray;
import org.json.JSONObject;


public class CourseList extends ActionBarActivity {

    ListView listView;

    String[] courseCode, courseName, courseDesc,courseSchedule;
    int[] courseCredits,courseId;
    int year,semester;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Moodle: Course Overview");
        setContentView(R.layout.activity_course_list);
        //String requestUrl = "/courses/list.json";
        Intent intent = getIntent();
        String stringResponse = intent.getStringExtra("data");



        //Replace this by RishabsFunction(requestURL) function
        JSONArray courses;
        try {
            // JSONObject courseList = new JSONObject(stringResponse);
            JSONObject courseList = new JSONObject(stringResponse);



            //startActivity(new Intent(CourseList.this, LoginFailedPop.class));
            //Remove above
            courses = courseList.getJSONArray("courses");

            int length = 1;
            if(courses!=null)   length=courses.length();

            final String[]    courseCode = new String[length];
            final String[]    courseName = new String[length];
            final String[]    courseDesc = new String[length];
            final int[]    courseCredits = new int[length];
            final int[]    courseId = new int[length];
            final String[]    courseSchedule = new String[length];


            System.out.println(stringResponse);

            year = courseList.getInt("current_year");
            semester = courseList.getInt("current_sem");

            System.out.println(year);
            System.out.println(semester);

            JSONObject user = courseList.getJSONObject("user");


            String first_name = user.getString("first_name");
            String last_name  = user.getString("last_name");

            for(int i=0;i<courses.length();i++) {
                JSONObject temp = courses.getJSONObject(i);
                courseCode[i] = temp.getString("code");
                System.out.println(courseCode[i]);
                courseId[i] = temp.getInt("id");
                courseName[i] = temp.getString("name");
                courseCredits[i] = temp.getInt("credits");
                courseDesc[i] = temp.getString("description");
                courseSchedule[i] = temp.getString("l_t_p");
            }

            System.out.println(first_name);
            String userName = first_name+" "+last_name;
            String pageLabel = "\n" + userName + ": " + "Semester " + String.valueOf(semester) + ", " + String.valueOf(year) + "\n";

            System.out.println(pageLabel);

            TextView t=new TextView(this);

            t=(TextView)findViewById(R.id.labelText);
            t.setText(pageLabel);

            final String[] codes = {"COL100","COP290","COL106"};
            String[] names = {"Introduction to Computer Science","Design Practices in Computer Science","Data Structures and Algorithms"};


            final String[] listLabels = new String[length];
            for(int i=0;i<length;i++)    listLabels[i] = courseCode[i] + ": "+ courseName[i];

            listView = (ListView) findViewById(R.id.list);
            ArrayAdapter<String> addapter = new ArrayAdapter<String>(listView.getContext(),android.R.layout.simple_list_item_1,listLabels);
            listView.setAdapter(addapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    Intent intent = new Intent(CourseList.this, Course_Template.class);
                    String course_code = courseCode[position];
                    intent.putExtra("coursecode",course_code);
                    startActivity(intent);
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
        getMenuInflater().inflate(R.menu.menu_course_list, menu);
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
                Intent target1 = new Intent(CourseList.this,All_Grades.class);
                Re q1 = new Re(target1,CourseList.this,"/default/grades.json",1);
                q1.request();
                break;
            case R.id.action_notifications:
                Intent target2 = new Intent(CourseList.this,Notifications.class);
                Re q2 = new Re(target2,CourseList.this,"/default/notifications.json",1);
                q2.request();
                break;
            case R.id.action_logout:
                Intent target3 = new Intent(CourseList.this,MainActivity.class);
                Re q3 = new Re(target3,CourseList.this,"/default/logout.json",1);
                q3.request();
                break;
            default: break;
        }
        return super.onOptionsItemSelected(item);
    }
}

