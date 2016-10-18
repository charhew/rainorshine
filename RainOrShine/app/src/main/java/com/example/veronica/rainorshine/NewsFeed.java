package com.example.veronica.rainorshine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class NewsFeed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        String email = getIntent().getStringExtra("Email");

        Toast.makeText(this, "Welcome " + email, Toast.LENGTH_SHORT).show();
    }
}
