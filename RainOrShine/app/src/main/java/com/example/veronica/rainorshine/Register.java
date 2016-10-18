package com.example.veronica.rainorshine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    ContactDatabaseHelper helper = new ContactDatabaseHelper(this);
    Button registerButton;
    EditText nameEditText, emailEditText, passwordEditText, password2EditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerButton = (Button) findViewById(R.id.registerButton);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        password2EditText = (EditText) findViewById(R.id.password2EditText);
    }

    public void onSignUpClick(View v) {
        if (v.getId() == R.id.registerButton) {
            String name = nameEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String pass1 = passwordEditText.getText().toString();
            String pass2 = password2EditText.getText().toString();

            if (!pass1.equals(pass2)) {
                Toast.makeText(this, "Passwords don't match!", Toast.LENGTH_SHORT).show();
            } else {
                Contact c = new Contact();
                c.setName(name);
                c.setEmail(email);
                c.setPassword(pass1);

                helper.insertContact(c);
                startActivity(new Intent(this, Login.class));
            }
        }
    }
}
