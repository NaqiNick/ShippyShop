package com.example.nick.shippyshop;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class forgot_password extends Activity {

    private Button btn_Submit;
    private EditText et_Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_forgot_password);
        init();
    }

    public void init(){
        btn_Submit = (Button)findViewById(R.id.btn_sub);

        et_Email = (EditText)findViewById(R.id.et_email_forgot);
    }

    public void sub_forgot(View v){
        //send email for new password
        //Intent backtomain = new Intent();   //call main activity
    }
}
