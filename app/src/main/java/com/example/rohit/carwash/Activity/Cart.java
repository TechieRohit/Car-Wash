package com.example.rohit.carwash.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rohit.carwash.Database.LoginDatabase;
import com.example.rohit.carwash.R;

public class Cart extends ActionBarActivity {

    EditText street,city,car,num;
    Button proceed;
    TextView amount;

    SharedPreferences sharedPreferences;

    LoginDatabase loginDatabase;

    int amount_total;
    String slot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        sharedPreferences = getSharedPreferences("CAR",MODE_PRIVATE);

        Toolbar showToolbar = (Toolbar)findViewById(R.id.address_toolbar);
        setSupportActionBar(showToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loginDatabase = new LoginDatabase(this);
        slot = getIntent().getStringExtra("SLOT");

        street = (EditText)findViewById(R.id.house);
        city = (EditText)findViewById(R.id.city);
        car = (EditText)findViewById(R.id.car_no);
        num = (EditText)findViewById(R.id.mobile);
        amount = (TextView)findViewById(R.id.amount);

        int slot_num = Integer.parseInt(slot);
         amount_total = 400 + 100*slot_num;

        amount.setText("Total Amount - " + amount_total);

        proceed = (Button)findViewById(R.id.proceed);

        onClickListener();
    }

    private void onClickListener() {



        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 String house = street.getText().toString();
                 String city_name = city.getText().toString();
                 String car_num = car.getText().toString();
                 String mobile = num.getText().toString();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("HOUSE",house);
                editor.putString("CITY",city_name);
                editor.putString("CAR_NUM",car_num);
                editor.putString("MOBILE",mobile);
                editor.putInt("COST",amount_total);
                editor.apply();

                if (house.equals("") || city_name.equals("") || car_num.equals("") || mobile.equals("")) {
                    showMessage("Sorry", "You need to enter all the details");
                } else {
                    Intent intent = new Intent(Cart.this, FinalCheckout.class);
                    intent.putExtra("slot_to_final",slot);
                    startActivity(intent);
                }
            }
        });
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

    private void showMessage(String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.show();
    }

}
