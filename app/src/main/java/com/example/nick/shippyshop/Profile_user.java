package com.example.nick.shippyshop;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile_user extends AppCompatActivity {

    private static final String KEY_IMAGE = "image", KEY_ID = "id";
    private TextView tv_username, tv_useremail, tv_usergender, tv_userphone, tv_useraddress;
    private CircleImageView iv_profile;
    public UserLocal localUser;
    private User user;
    CameraPhoto cameraPhoto;
    GalleryPhoto galleryPhoto;
    final int CAMERA_REQUEST =1, GALLERY_REQUEST =2;
    String selectedPhoto;
    public static final String upload_image_url="http://192.168.1.5/shippyshop_server/dp_up.php" ;
    private final String TAG = this.getClass().getName();
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
            if(user.user_Pic){
                byte[] decodedString = Base64.decode(localUser.get_profile_pic(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                iv_profile.setImageBitmap(decodedByte);
            }
    }

    private void init(){
        tv_username = (TextView)findViewById(R.id.tv_Profile_Name);
        tv_useremail = (TextView)findViewById(R.id.tv_Profile_Email);
        tv_userphone = (TextView)findViewById(R.id.tv_Profile_Contact);
        tv_usergender = (TextView)findViewById(R.id.tv_Profile_Gender);
        tv_useraddress = (TextView)findViewById(R.id.tv_Profile_Address);
        galleryPhoto = new GalleryPhoto(this);
        cameraPhoto = new CameraPhoto(this);
        iv_profile = (CircleImageView)findViewById(R.id.iv_imageProfile);

    }

    private void set_Values(){
        tv_username.setText(user.user_Name);
        tv_usergender.setText("Male");
        tv_useremail.setText(user.email);
        tv_userphone.setText(user.phone);
        tv_useraddress.setText(user.addr);
    }

    public void selectImage(View v) {
        final CharSequence[] items = { "Take Photo", "Choose from Gallery",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(Profile_user.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    try {
                        startActivityForResult(cameraPhoto.takePhotoIntent(), CAMERA_REQUEST);
                        cameraPhoto.addToGallery();
                    } catch (IOException e) {
                        Toast.makeText(Profile_user.this, "Something wrong", Toast.LENGTH_SHORT).show();
                    }
                } else if (items[item].equals("Choose from Gallery")) {
                    startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQUEST);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == CAMERA_REQUEST) {
                String photoPath = cameraPhoto.getPhotoPath();
                selectedPhoto = photoPath;
                try {
                    Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(300, 320).getBitmap();
                    iv_profile.setImageBitmap(getRotatedBitmap(bitmap,270));
                    user.user_Pic=true;
                    user.user_picture = getRotatedBitmap(bitmap,270);
                    volley_connect(ImageBase64.encode(getRotatedBitmap(bitmap,270)));
                } catch (FileNotFoundException e) {
                    Toast.makeText(Profile_user.this, "Something wrong while loading", Toast.LENGTH_SHORT).show();
                }
                Log.d(TAG, photoPath);
            } else if (requestCode == GALLERY_REQUEST) {
                Uri uri = data.getData();
                galleryPhoto.setPhotoUri(uri);
                String photoPath = galleryPhoto.getPath();
                selectedPhoto = photoPath;
                try {
                    Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
                    iv_profile.setImageBitmap(bitmap);
                    user.user_Pic=true;
                    user.user_picture=bitmap;
                    volley_connect(ImageBase64.encode(bitmap));
                } catch (FileNotFoundException e) {
                    Toast.makeText(Profile_user.this, "Something wrong while choosing", Toast.LENGTH_SHORT).show();
                }
                Log.d(TAG, photoPath);
            }
        }
    }

    private Bitmap getRotatedBitmap (Bitmap source, float angle){
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        Bitmap bitmap1 = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        return bitmap1;
    }

    public void volley_connect(final String encodedImage){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, upload_image_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.toString().equals("uploaded sucessfuly")){
                    user.user_Pic=true;
                }
                Toast.makeText(Profile_user.this, response.toString(), Toast.LENGTH_LONG).show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Profile_user.this, error.toString()+"\n please check your internet connection", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_IMAGE, encodedImage);
                map.put(KEY_ID, user.user_id);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        localUser.storeUserDetails(user);
        super.onBackPressed();
    }

}
