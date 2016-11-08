package com.example.veronica.rainorshine;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * Created by Charlene on 2016-11-02.
 */

public class CreatePostUpload extends Activity {
    ImageView incomingImage;

    String caption;
    String weather;

    ImageDatabaseHelper db;
    byte[] byteImage;
    Uri imageUri;

    ListView dataList;
    ArrayList<CameraInput> imageArry = new ArrayList<CameraInput>();
    ImageAdapter imageAdapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post_upload);

        db = new ImageDatabaseHelper(this);

        incomingImage = (ImageView) findViewById(R.id.incomingImage);


        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
                if (imageUri != null) {
                    incomingImage.setImageURI(imageUri);
                }

            }
        }

    }

    public void submitPost(View v) {

        //convert the uri to a bitmap
        Bitmap myBitmap = null;
        try {
            myBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            Toast.makeText(this, "img converted to bitmap", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //convert bitmap to byte. store it in the global byteImage variable.
        if (myBitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            myBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteImage = stream.toByteArray();
            Toast.makeText(this, "bitmap converted to byte[]", Toast.LENGTH_SHORT).show();
        }

        //convert byte[] to CameraInput
        CameraInput camInput = new CameraInput(byteImage, "", 0, "");

        Toast.makeText(this, "byte converted to CameraInput", Toast.LENGTH_SHORT).show();

        //add image to database
        db.addCameraInput(new CameraInput(byteImage, "", 0, ""));
        Toast.makeText(this, "image added to database", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        this.finish();

    }

}
