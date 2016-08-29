package com.example.nick.shippyshop;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class logout extends AppCompatActivity {

    Button btn_logout;
    public  UserLocal localUser;
    TextView tv_userdisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        init();
    }

    public void init(){

        btn_logout = (Button)findViewById(R.id.btn_logout);
        tv_userdisplay = (TextView)findViewById(R.id.tv_user);
        localUser = new UserLocal(this);
        User new_user = localUser.getLoggedinUser();
        tv_userdisplay.setText(new_user.user_Name);
    }

    public void logOut(View v){
        localUser.setUserLoggedIn(false);
        localUser.clearUserdata();
        finish();
    }
}
