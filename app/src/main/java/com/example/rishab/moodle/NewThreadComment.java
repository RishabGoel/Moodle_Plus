package com.example.rishab.moodle;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class NewThreadComment extends ActionBarActivity {
    String idd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_thread_comment);
        String com_text=((EditText)findViewById(R.id.comment_content)).getText().toString();
        idd=getIntent().getStringExtra("id");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_thread_comment, menu);
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
                Intent target1 = new Intent(NewThreadComment.this,All_Grades.class);
                Re q1 = new Re(target1,NewThreadComment.this,"/default/grades.json",1);
                q1.request();
                break;
            case R.id.action_notifications:
                Intent target2 = new Intent(NewThreadComment.this,Notifications.class);
                Re q2 = new Re(target2,NewThreadComment.this,"/default/notifications.json",1);
                q2.request();
                break;
            case R.id.action_logout:
                Intent target3 = new Intent(NewThreadComment.this,MainActivity.class);
                Re q3 = new Re(target3,NewThreadComment.this,"/default/logout.json",1);
                q3.request();
                break;
            default: break;
        }

        return super.onOptionsItemSelected(item);
    }
    public void add(View view){
        String id=getIntent().getStringExtra("id");
        EditText editText=(EditText) findViewById(R.id.comment_content);
        String desc=(String)editText.getText().toString();
        try {
            final String URL="/threads/post_comment.json?description="+ URLEncoder.encode(desc,"UTF-8")+"&thread_id="+URLEncoder.encode(idd,"UTF-8");
            Intent intent=new Intent(NewThreadComment.this,ThreadComments.class);
            intent.putExtra("id",idd);
            Re req=new Re(intent,NewThreadComment.this,URL,2);
            req.request();
        }
        catch (UnsupportedEncodingException e){
            Log.d("check","dam u");
        }



    }
}
