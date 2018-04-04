package com.example.rohit.carwash.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.rohit.carwash.Database.LoginDatabase;
import com.example.rohit.carwash.R;
import com.example.rohit.carwash.RecyclerView.CustomAdapter;
import com.example.rohit.carwash.RecyclerView.DataItems;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private LoginDatabase loginDatabase;

    private TextView Name,Slot;

    private SharedPreferences sharedPreferences;

    Toolbar toolbar;

    String slot[] = {"1","2","3","4","5","6","7","8","9","10","11","12"};
    String timing[] = {"9 AM - 10 AM","10 AM - 11 AM","11 AM - 12 PM","12 PM - 1 PM","1 PM - 2 PM",
            "2 PM - 3 PM","3 PM - 4 PM","4 PM - 5 PM","5 PM - 6 PM","6 PM - 7 PM","7 PM - 8 PM","8 PM - 9 PM"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (TextView)findViewById(R.id.name);
        Slot = (TextView)findViewById(R.id.slot);

        sharedPreferences = getSharedPreferences("CAR",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        loginDatabase = new LoginDatabase(this);
        Cursor cursor = loginDatabase.getAllData();
        /*String email = getIntent().getStringExtra("email");*/
        String email = sharedPreferences.getString("EMAIL",null);

        while (cursor.moveToNext())
        {
            if (email.equals(cursor.getString(2)))
            {
                Name.setText(cursor.getString(1));
                editor.putString("COL", cursor.getString(0));
                editor.apply();
                checkSlot(cursor.getString(4));
                break;
            }
        }

        toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new android.support.v7.widget.Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.action_settings) {

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("EMAIL","logout");
                    editor.apply();

                    Intent intent =new Intent(MainActivity.this,Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });


        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CustomAdapter(getDetails(slot,timing),getApplicationContext());

        recyclerView.setAdapter(adapter);
    }

    private void checkSlot(String slotNum) {
        if (slotNum.equals("None"))
        {
            Slot.setText("None");
        }else {
            int slot = Integer.parseInt(slotNum) - 1;
            Slot.setText(timing[slot]);
        }
    }


    private List<DataItems> getDetails(String[] slot,String[] timing)
    {
        List<DataItems> myData = new ArrayList<>();

        Cursor cursor = loginDatabase.getAllData();
        int count = cursor.getCount();

        for (int i = 0; i<slot.length ; i++)
        {
            DataItems dataItems = new DataItems();
            dataItems.setSlot(slot[i]);
            dataItems.setTiming(timing[i]);
            myData.add(dataItems);
        }

        return myData;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
}
