package com.example.veronica.rainorshine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class old_FashionGrid extends AppCompatActivity {

    TextView helloName;
    String firstName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.old_activity_fashion_grid);

        Intent i = getIntent();
        firstName = i.getStringExtra("name");
        Toast.makeText(this, firstName, Toast.LENGTH_SHORT).show();

        helloName = (TextView) findViewById(R.id.helloTextView);
        helloName.setText("Hello,\n" + firstName + ".");
    }
}
