package com.example.rishab.moodle;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Paras Gupta on 19-02-2016.
 */
public class LoginFailedPop extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_failed_popup);

        int height = 400;
        int width = 500;
        getWindow().setLayout(width,height);
    }

    public void finishButton(View view){
        finish();
    }
}
