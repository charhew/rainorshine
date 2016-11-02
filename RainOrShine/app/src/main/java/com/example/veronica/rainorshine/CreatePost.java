package com.example.veronica.rainorshine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class CreatePost extends AppCompatActivity {

    ListView dataList;
    ArrayList<CameraInput> imageArry = new ArrayList<CameraInput>();
    ImageAdapter imageAdapter;

    ImageDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        dataList = (ListView) findViewById(R.id.list);

        db = new ImageDatabaseHelper(this);

        CameraInput input = db.getCameraInput();
        imageArry.add(input);

        imageAdapter = new ImageAdapter(this, R.layout.screen_list,
                imageArry);
        dataList.setAdapter(imageAdapter);
    }

    public void submitPost(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
