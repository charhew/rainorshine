package com.example.veronica.rainorshine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//this class is used when you tap a thumbnail on the home screen.
public class PostDetails extends AppCompatActivity {
    ImageDatabaseHelper db;

    int curArrayPosition;
    ArrayList<CameraInput> cameraInputArray = new ArrayList<CameraInput>();
    ArrayList<CameraInput> curInputArray = new ArrayList<CameraInput>();
    ImageAdapter imageAdapter;

    ListView cameraInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        Intent i = getIntent();
        curArrayPosition = (int) i.getLongExtra("ID", 0) - 1; // subtract by one cuz accounting for how array index starts at 0

        db = new ImageDatabaseHelper(this);

        cameraInput = (ListView) findViewById(R.id.list);

        List<CameraInput> inputs = db.getAllCameraInputs();
        for (CameraInput ci : inputs) {
            cameraInputArray.add(ci);
        }

        curInputArray.add(cameraInputArray.get(curArrayPosition));
        imageAdapter = new ImageAdapter(this, R.layout.post_item, curInputArray);
        cameraInput.setAdapter(imageAdapter);
    }
}
