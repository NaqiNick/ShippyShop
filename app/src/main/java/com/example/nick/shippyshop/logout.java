package com.example.nick.shippyshop;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class logout extends AppCompatActivity {

    Button btn_logout;
    public  UserLocal localUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        init();
    }

    public void init(){

        btn_logout = (Button)findViewById(R.id.btn_logout);
        localUser = new UserLocal(this);
    }

    public void logOut(View v){
        localUser.setUserLoggedIn(false);
        localUser.clearUserdata();
        finish();
    }
}
