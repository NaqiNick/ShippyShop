package com.example.nick.shippyshop;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import com.kosalgeek.android.photoutil.ImageBase64;

/**
 * Created by NicK on 8/17/2016.
 */
public class UserLocal {

    public static final String sp_name = "User Details";
    SharedPreferences localDatabase;

    public UserLocal(Context context){

        localDatabase =context.getSharedPreferences(sp_name, context.MODE_PRIVATE);
    }

    public void storeUserDetails(User user){

        SharedPreferences.Editor spEditor = localDatabase.edit();
        spEditor.putString("userid", user.user_id);
        spEditor.putString("user name",user.getUser_Name());
        spEditor.putString("email",user.getEmail());
        spEditor.putString("password",user.getPassword());
        spEditor.putString("phone",user.getPhone());
        spEditor.putString("address",user.getAddr());
        spEditor.putInt("type", user.user_type);
       spEditor.putBoolean("user_pic", user.user_Pic);
        spEditor.commit();
    }

    public User getLoggedinUser (){

        String user_id = localDatabase.getString("userid", "");
        String user_Name = localDatabase.getString("user name", "");
        String email = localDatabase.getString("email","");
        String password = localDatabase.getString("password","");
        String phone = localDatabase.getString("phone","");
        String addr = localDatabase.getString("address","");
        int type = localDatabase.getInt("type",0);
        boolean user_pic = localDatabase.getBoolean("user_pic",false);
        User storedUser = new User(user_id, user_Name, email, password, phone, addr, type, user_pic);

        return storedUser;
    }

    public void setUserLoggedIn (boolean loggedIn){

        SharedPreferences.Editor spEditor = localDatabase.edit();
        spEditor.putBoolean("loggedIn",loggedIn);
        spEditor.commit();
    }

    public void add_profile_pic(String picture){
        SharedPreferences.Editor spEditor = localDatabase.edit();
        spEditor.putString("picture",picture);
        spEditor.commit();
    }

    public String get_profile_pic(){
        return localDatabase.getString("picture","");
    }

    public boolean getUserLoggedIn (){
        return localDatabase.getBoolean("loggedIn",false);
    }

    public void clearUserdata(){

        SharedPreferences.Editor spEditor = localDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }
}
