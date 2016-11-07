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

    int arrayCounter = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        Intent i = getIntent();
        curArrayPosition = i.getIntExtra("POSITION", 0);

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

    public void delete(View v) {
        Toast.makeText(this, "Delete Photo Requested", Toast.LENGTH_LONG).show();


        //CODE BELOW IS WHAT CALLS THE DELETE METHOD AND DELETES THE IMAGE IN THE POSITION CALLED
        db.deleteCameraInput(arrayCounter);

        startActivity(new Intent(this, MainActivity.class));
    }
}
