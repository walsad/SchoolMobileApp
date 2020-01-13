package com.example.schoolapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class MainActivity  extends AppCompatActivity {

    EditText editId;
    EditText editName;
    EditText edtAdd;
    EditText edtForm;
    EditText edtSpec;

    Button btnAdd;
    Button btnList;


    ImageView imageView;


    final  int REQUEST_CODE_GALLERY = 999;

    public  static MyDBHandler sqlHelper;



    public static  byte[] imageViewToByte(ImageView image){
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return  byteArray;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editId = findViewById(R.id.editId);
        editName = findViewById(R.id.editName);
        edtAdd = findViewById(R.id.edtAdd);
        edtForm = findViewById(R.id.edtForm);
        edtSpec = findViewById(R.id.edtSpec);

        btnAdd = findViewById(R.id.btnAdd);
        btnList=findViewById(R.id.btnList);

        imageView = findViewById(R.id.imageView);

        sqlHelper = new MyDBHandler(this, "RECORDDB.sqlite",null,1);
        sqlHelper.queryData("CREATE TABLE IF NOT EXISTS ecole (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name VARCHAR, address VARCHAR, form VARCHAR,spec VARCHAR, logo BLOB)");
        System.out.println("Created");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        MainActivity.this ,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );

            }
        });






        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("try");
                System.out.println(imageViewToByte(imageView).toString());
                try {
                    sqlHelper.insertData(
                            editName.getText().toString().trim(),
                            edtAdd.getText().toString().trim(),
                            edtForm.getText().toString().trim(),
                            edtSpec.getText().toString().trim(),
                            imageViewToByte(imageView)
                    );
                    Toast.makeText(MainActivity.this, "Added successfully ", Toast.LENGTH_SHORT).show();
                    editId.setText("");
                    editName.setText("");
                    edtAdd.setText("");
                    edtForm.setText("");
                    edtSpec.setText("");
                    imageView.setImageResource(R.drawable.addphoto);
                }
                catch (Exception ex ){
                    Toast.makeText(MainActivity.this, "Not added", Toast.LENGTH_SHORT).show();

                    ex.printStackTrace();
                }

            }
        });



        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,RecordList.class));

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CODE_GALLERY){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(this, "Dont have permission", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_GALLERY && resultCode ==RESULT_OK){
            Uri imageUrl =data.getData();
            CropImage.activity(imageUrl).setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK){
                Uri resultUri = result.getUri();
                imageView.setImageURI(resultUri);

            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error=result.getError();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}

