package com.example.rohit.carwash.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rohit.carwash.Database.LoginDatabase;
import com.example.rohit.carwash.R;


public class Login extends ActionBarActivity {

    private Button login,signup;
    private EditText email,password;
    private LoginDatabase loginDatabase;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        login = (Button)findViewById(R.id.login);
        signup = (Button)findViewById(R.id.signup);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);

        sharedPreferences = getSharedPreferences("CAR",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String login = sharedPreferences.getString("EMAIL","logout");

        if (login.equals("logout"))
        {
            onClickListeners();
        }else {
            startActivity(new Intent(Login.this,MainActivity.class));
            finish();
        }

    }

    private void onClickListeners() {

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, SignUp.class));
            }
        });

    }

    private void check() {

        String Email = email.getText().toString();
        String Password = password.getText().toString();

        loginDatabase = new LoginDatabase(this);
        Cursor cursor = loginDatabase.getAllData();

        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (Email.equals("") || Password.equals("")) {
            showMessage("Sorry","Email or Password is empty !");
        }
        else {
            int count = 0;
            while (cursor.moveToNext())
            {
                if (Email.equals(cursor.getString(2)) && Password.equals(cursor.getString(3)))
                {
                    Intent intent = new Intent(Login.this,MainActivity.class);
                    startActivity(intent);
                    editor.putString("EMAIL", Email);
                    editor.apply();
                    finish();
                    count = 1;
                    break;
                }
            }
            if (count == 0)
            {
                showMessage("Sorry","Incorrect Credentials");
            }
        }
    }

    private void showMessage(String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.show();
    }
}
