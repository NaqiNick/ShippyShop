package com.example.nick.shippyshop;

/**
 * Created by NicK on 8/17/2016.
 */
public class User {

    String user_Name, email, phone, password, addr;
    int user_type;

    public User(String user_Name, String email,  String password, String phone, String addr, int type){

        this.user_Name = user_Name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.addr = addr;
        this.user_type = type;
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
