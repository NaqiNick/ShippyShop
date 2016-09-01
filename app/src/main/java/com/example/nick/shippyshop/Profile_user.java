package com.example.nick.shippyshop;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Profile_user extends AppCompatActivity {

    private TextView tv_username, tv_useremail, tv_usergender, tv_userphone, tv_useraddress;
    public UserLocal localUser;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);

        setTitle("Profile");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_profile_user);

        localUser = new UserLocal(this);
        user = localUser.getLoggedinUser();
        init();
        set_Values();
    }

    private void init(){
        tv_username = (TextView)findViewById(R.id.tv_Profile_Name);
        tv_useremail = (TextView)findViewById(R.id.tv_Profile_Email);
        tv_userphone = (TextView)findViewById(R.id.tv_Profile_Contact);
        tv_usergender = (TextView)findViewById(R.id.tv_Profile_Gender);
        tv_useraddress = (TextView)findViewById(R.id.tv_Profile_Address);
    }

    private void set_Values(){
        tv_username.setText(user.user_Name);
        tv_usergender.setText("Male");
        tv_useremail.setText(user.email);
        tv_userphone.setText(user.phone);
        tv_useraddress.setText(user.addr);
    }

    public void selectImage(View v) {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(Profile_user.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                //boolean result=Utility.checkPermission(MainActivity.this);
                if (items[item].equals("Take Photo")) {
                    Toast.makeText(Profile_user.this, "take photo", Toast.LENGTH_SHORT).show();
                } else if (items[item].equals("Choose from Library")) {
                    Toast.makeText(Profile_user.this, "choose", Toast.LENGTH_SHORT).show();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
}
