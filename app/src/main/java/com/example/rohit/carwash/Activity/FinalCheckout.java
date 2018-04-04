package com.example.rohit.carwash.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rohit.carwash.Database.LoginDatabase;
import com.example.rohit.carwash.R;

public class FinalCheckout extends ActionBarActivity {

    private SharedPreferences sharedPreferences;
    private TextView house,city,mobile,car,cost;
    private Button button;

    LoginDatabase loginDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_checkout);

        sharedPreferences = getSharedPreferences("CAR",MODE_PRIVATE);

        Toolbar showToolbar = (Toolbar)findViewById(R.id.address_checkout);
        setSupportActionBar(showToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loginDatabase = new LoginDatabase(this);

        house = (TextView)findViewById(R.id.h);
        city = (TextView)findViewById(R.id.c);
        mobile = (TextView)findViewById(R.id.m);
        car = (TextView)findViewById(R.id.cn);
        cost = (TextView)findViewById(R.id.co);

        button = (Button)findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 String column = sharedPreferences.getString("COL", null);
                 String slot = getIntent().getStringExtra("slot_to_final");

                loginDatabase.updateData(column, slot);

                Toast.makeText(FinalCheckout.this, "Order Placed !", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(FinalCheckout.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
                startActivity(intent);
            }
        });

        getText();
    }

    private void getText() {

        String house_ = sharedPreferences.getString("HOUSE",null);
        String city_ = sharedPreferences.getString("CITY",null);
        String car_ = sharedPreferences.getString("CAR_NUM",null);
        String mobile_ = sharedPreferences.getString("MOBILE",null);
        int amount = sharedPreferences.getInt("COST",0);

        house.setText("House no. - " + house_);
        city.setText("City - " + city_);
        car.setText("Car reg no. - " + car_);
        mobile.setText("Mobile - " + mobile_);
        cost.setText("Total Amount - " + amount);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        //noinspection SimplifiableIfStatement
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
