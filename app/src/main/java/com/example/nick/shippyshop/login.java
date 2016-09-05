package com.example.nick.shippyshop;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends Activity {
    public  UserLocal localUser;
    private Button bt_Signin;
    private EditText et_Email, et_Password;
    private TextView tv_Forgot, tv_Register;

    private static final String register_url ="http://192.168.1.2/shippyshop_server/login.php";
    private static final String KEY_EMAIL = "user_email";
    private static final String KEY_PASSWORD = "user_password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        init();
    }

    protected void init(){

        localUser = new UserLocal(this);
        localUser.setUserLoggedIn(false);

        bt_Signin = (Button)findViewById(R.id.btn_signIn);

        et_Email = (EditText)findViewById(R.id.et_email_register);
        et_Password = (EditText)findViewById(R.id.et_pass);

        tv_Forgot = (TextView)findViewById(R.id.tv_forgotPass);
        tv_Register = (TextView)findViewById(R.id.tv_notRegistered);

    }

    public void register(View v){
        Intent register = new Intent(login.this, register.class);
        startActivity(register);
    }

    public void forgotPass (View v){
        //change the font settings
        Intent forgot_activity = new Intent(login.this, forgot_password.class);
        startActivity(forgot_activity);
    }

    public void login_Function_reg(View v){
        User userLogin = new User(et_Email.getText().toString(), et_Password.getText().toString());
        logUser_in(userLogin);
        if(localUser.getUserLoggedIn()){
            Intent main_activ = new Intent(login.this,Main_view.class);
            startActivity(main_activ);
        }
    }




    private void logUser_in(final User user){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, register_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String code = jsonObject.getString("message");
                    if(code.equals("No data")){
                        Toast.makeText(login.this, "Login Failed Try Again\nInvalid User Name or Password", Toast.LENGTH_LONG).show();
                    }else if (code.equals("login success")){
                                user.user_id=jsonObject.getString("user_id");
                                user.user_Name=jsonObject.getString("user_name");
                                user.email=jsonObject.getString("user_email");
                                user.password=jsonObject.getString("user_password");
                                user.phone=jsonObject.getString("user_phone");
                                user.addr=jsonObject.getString("user_address");
                                user.user_type=jsonObject.getInt("user_type");
                                String pic = jsonObject.getString("user_pic");
                        if (pic.equals("1")){
                            user.user_Pic=true;
                        }else{
                            user.user_Pic=false;
                        }
                        localUser.storeUserDetails(user);
                        localUser.setUserLoggedIn(true);
                        User user_new = localUser.getLoggedinUser();
                    }else{
                        Toast.makeText(login.this, "unknown error", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(login.this, error.toString()+"\n please check your internet connection", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_EMAIL, user.email);
                map.put(KEY_PASSWORD, user.password);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
