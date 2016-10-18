package com.example.veronica.rainorshine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity implements View.OnClickListener {

    ContactDatabaseHelper helper = new ContactDatabaseHelper(this);

    Button loginButton;
    EditText emailEditText, passwordEditText;
    TextView registerLink;

    boolean alreadyLoggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.loginButton);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        registerLink = (TextView) findViewById(R.id.registerLink);

        loginButton.setOnClickListener(this);
        registerLink.setOnClickListener(this);

        if (alreadyLoggedIn) {
            startActivity(new Intent(this, NewsFeed.class));
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.loginButton:
                String enteredEmail = emailEditText.getText().toString();
                String enteredPassword = passwordEditText.getText().toString();

                String userPassword = helper.searchPassword(enteredEmail);

                if(userPassword.equals(enteredPassword)) {
                    alreadyLoggedIn = true;
                    Intent i = new Intent(this, NewsFeed.class);
                    i.putExtra("Email", enteredEmail);
                    startActivity(i);
                }
                else {
                    Toast.makeText(this, "Your credentials are incorrect", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.registerLink:
                startActivity(new Intent(this, Register.class));
                break;
        }
    }
}
