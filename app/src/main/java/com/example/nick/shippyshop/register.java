package com.example.nick.shippyshop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class register extends Activity{


    private static final String KEY_USERNAME = "user_name";
    private static final String KEY_EMAIL = "user_email";
    private static final String KEY_PASSWORD = "user_password";
    private static final String KEY_PHONE = "user_phone";
    private static final String KEY_ADDRESS = "user_address";
    private static final String KEY_TYPE = "user_type";


    private static final String register_url ="http://192.168.10.6/shippyshop_server/register.php";
    private int type;
    private Button btn_Register;
    private EditText et_Fname, et_Sname, et_Pass, et_Email, et_Repass, et_Phone, et_Address;
    private int user_type;
    private Spinner spin;
    String msg = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        init();
    }

    public void init(){

        btn_Register = (Button)findViewById(R.id.btn_signIn);

        et_Email = (EditText)findViewById(R.id.et_email_register);
        et_Address = (EditText)findViewById(R.id.et_addr);
        et_Fname = (EditText)findViewById(R.id.et_fname);
        et_Sname = (EditText)findViewById(R.id.et_sname);
        et_Pass = (EditText)findViewById(R.id.et_pass);
        et_Repass = (EditText)findViewById(R.id.et_rePass);
        et_Phone = (EditText)findViewById(R.id.et_phone);

        spin = (Spinner)findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getSelectedItem().toString().equalsIgnoreCase("buyer")){
                    user_type = 1;
                    Toast.makeText(register.this, "1", Toast.LENGTH_SHORT).show();
                }else if(parent.getSelectedItem().toString().equalsIgnoreCase("seller")){
                    user_type = 2;
                    Toast.makeText(register.this, "2", Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(register.this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
               msg+="\nSelect Type";
            }
        });
    }

    public boolean check_info(){
        boolean flag=true;
        if (et_Address.getText().toString().equalsIgnoreCase("")) {
            flag=false;
            msg+="\nAddress";
        }if(et_Phone.getText().toString().equalsIgnoreCase("")){
            flag=false;
            msg+="\nPhone";
        }if(!et_Repass.getText().toString().equals(et_Pass.getText().toString()) || et_Pass.getText().toString().equalsIgnoreCase("")){
            flag=false;
            msg+="\nRe-enter Password";
        }if(et_Sname.getText().toString().equalsIgnoreCase("")){
            flag=false;
            msg+="\nSecond Name";
        }if(et_Fname.getText().toString().equalsIgnoreCase("")){
            flag=false;
            msg+="\nFirst Name";
        }if(et_Email.getText().toString().equalsIgnoreCase("")){
            flag=false;
            msg+="\nEmail";
        }if(et_Pass.getText().length()<6){
            msg+="\nPassword must be atleast 6 digit";
        }
        return flag;
    }

    public void register_user(View v){

        if(check_info()){
            User new_user = getUserDataFromView();
            register(new_user);

        }else {
            Toast.makeText(this,"please check the following information:\n"+msg,Toast.LENGTH_LONG).show();
        }
        msg="";
    }

    public User getUserDataFromView(){

        String user_Name = et_Fname.getText().toString()+" "+et_Sname.getText().toString();
        String email = et_Email.getText().toString();
        String password = et_Pass.getText().toString();
        String address = et_Address.getText().toString();
        String phone = et_Phone.getText().toString();
        int type = user_type;
        return  new User(user_Name, email, password, phone, address, type);
    }

    private void register(final User user){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, register_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(register.this, response, Toast.LENGTH_LONG).show();
               if(!response.equals("user already exists!")){
                finish();
               }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(register.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_USERNAME, user.user_Name);
                params.put(KEY_EMAIL, user.email);
                params.put(KEY_PASSWORD, user.password);
                params.put(KEY_PHONE, user.phone);
                params.put(KEY_ADDRESS, user.addr);
                params.put(KEY_TYPE, String.valueOf(user.user_type));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
