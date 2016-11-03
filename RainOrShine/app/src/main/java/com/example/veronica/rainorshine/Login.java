package com.example.veronica.rainorshine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button loginButton;
    EditText emailEditText, passwordEditText;
    TextView registerLink;

    String name;
    public static final String DEFAULT = "not available";

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

        // move to home if already logged in
        SharedPreferences sharedPrefs = getSharedPreferences("UserCredentials", Context.MODE_PRIVATE);
        String email = sharedPrefs.getString("email", DEFAULT);
        String password = sharedPrefs.getString("password", DEFAULT);

        if (!(email.equals(DEFAULT)) || !(password.equals(DEFAULT))) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.loginButton:
                SharedPreferences sharedPrefs = getSharedPreferences("UserCredentials", Context.MODE_PRIVATE);
                String email = sharedPrefs.getString("email", DEFAULT);
                String password = sharedPrefs.getString("password", DEFAULT);

                String curEmail = emailEditText.getText().toString();
                String curPassword = passwordEditText.getText().toString();

                if (email.equals(curEmail) && password.equals(curPassword)) {
                    startActivity(new Intent(this, MainActivity.class));
                }
                else
                {
                    Toast.makeText(this, "User credentials incorrect", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.registerLink:
                startActivity(new Intent(this, Register.class));
                break;
        }
    }


}
