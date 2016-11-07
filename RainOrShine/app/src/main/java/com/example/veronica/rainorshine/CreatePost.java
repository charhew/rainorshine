package com.example.veronica.rainorshine;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CreatePost extends AppCompatActivity {

    ListView dataList;
    EditText captionEditText;
    String caption = "";

    ArrayList<CameraInput> imageArry = new ArrayList<CameraInput>();
    ImageAdapter imageAdapter;
    CameraInput input;

    ImageDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        dataList = (ListView) findViewById(R.id.list);
        captionEditText = (EditText) findViewById(R.id.captionEditText);

        db = new ImageDatabaseHelper(this);

        input = db.getCameraInput();
        imageArry.add(input);

        imageAdapter = new ImageAdapter(this, R.layout.grid_item,
                imageArry);
        dataList.setAdapter(imageAdapter);
    }

    public void submitPost(View v) {
        caption = captionEditText.getText().toString();
        int id = input.getID(); // I get the ID of the current row

        ContentValues cv = new ContentValues();
        cv.put("caption", caption);

        db.getWritableDatabase().update("CAMERAINPUTTABLE", cv, "_id=" + id, null);

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
