package com.example.nick.shippyshop;

import android.graphics.Bitmap;

/**
 * Created by NicK on 8/17/2016.
 */
public class User {

    String user_id, user_Name, email, phone, password, addr;
    boolean user_Pic;
    int user_type;
    Bitmap user_picture;

    public User(String user_id, String user_Name, String email,  String password, String phone, String addr, int type, boolean user_Pic){
        this.user_id = user_id;
        this.user_Name = user_Name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.addr = addr;
        this.user_type = type;
        this.user_Pic =user_Pic;
    }

    public User(String user_Name, String email,  String password, String phone, String addr, int type){
        this.user_id = user_id;
        this.user_Name = user_Name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.addr = addr;
        this.user_type = type;
        this.user_Pic =user_Pic;
    }
    public User (User user){
        this.user_Name = user.user_Name;
        this.email =user.email;
        this.password = user.password;
        this.phone = user.phone;
        this.addr = user.addr;
        this.user_type = user.user_type;
        this.user_Pic = user.user_Pic;
        this.user_id = user.user_id;
    }

    public User (String email, String password){

        this.email = email;
        this.password = password;
        this.phone = "";
        this.addr = "";
        this.user_Name = "";
    }


    public String getUser_Name() {
        return user_Name;
    }

    public void setUser_Name(String user_Name) {
        this.user_Name = user_Name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
