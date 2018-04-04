package com.example.rohit.carwash.Activity;

import android.app.AlertDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rohit.carwash.Database.LoginDatabase;
import com.example.rohit.carwash.R;

public class SignUp extends ActionBarActivity {

    EditText name,email,password;
    Button signup;

    LoginDatabase loginDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        name = (EditText)findViewById(R.id.name);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.pass);

        signup = (Button)findViewById(R.id.up);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterCredentials();

            }
        });
    }

    private void enterCredentials() {

        String Name = name.getText().toString();
        String Email = email.getText().toString();
        String Password = password.getText().toString();

        loginDatabase = new LoginDatabase(this);

        if (Name.equals("") || Email.equals("") || Password.equals(""))
        {
            showMessage("Sorry","You need to enter all the details");
        }else {
            loginDatabase.insertData(Name, Email, Password,"None");
            /*showMessage("Registered","You can now login.");*/
            Toast.makeText(SignUp.this,"Registered,You can now login",Toast.LENGTH_LONG).show();
            finish();
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
