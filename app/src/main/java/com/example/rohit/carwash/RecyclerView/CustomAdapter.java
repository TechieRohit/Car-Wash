package com.example.rohit.carwash.RecyclerView;

/**
 * Created by ROHIT on 10/3/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rohit.carwash.Activity.Cart;
import com.example.rohit.carwash.Database.LoginDatabase;
import com.example.rohit.carwash.R;

import java.util.ArrayList;
import java.util.List;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    List<DataItems> dataItemseList = new ArrayList<>();
    Context context;

    private LoginDatabase loginDatabase;
    private Cursor cursor;
    /**Adapter **/
    public CustomAdapter(List<DataItems> dataItemseList, Context context)
    {
        layoutInflater = LayoutInflater.from(context);
        this.dataItemseList = dataItemseList;
        this.context =context;
    }

    /** fetching the data_item xml file **/
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

        View view = layoutInflater.inflate(R.layout.slot, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view,context);

        //Toast.makeText(context,"onCreateViewHolder",Toast.LENGTH_LONG).show();

        return viewHolder;
    }


    /**Bing all the items in data xml file **/
    @Override
    public void onBindViewHolder(CustomAdapter.ViewHolder viewHolder,final int  position) {

        DataItems items = dataItemseList.get(position);

        viewHolder.slot.setText(items.getSlot());
        viewHolder.timing.setText(items.getTiming());
    }

    @Override
    public int getItemCount(){
        return dataItemseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        TextView slot;
        TextView timing;
        LinearLayout linearLayout;

        Context context;

        public ViewHolder(View itemView, final Context context) {
            super(itemView);

            this.context = context;

            slot = (TextView)itemView.findViewById(R.id.slot_);
            timing = (TextView)itemView.findViewById(R.id.timing_);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.click);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    DataItems dataItems = dataItemseList.get(position);
                    String slotNumber = dataItems.getSlot();
                    loginDatabase = new LoginDatabase(context);
                    cursor = loginDatabase.getAllData();

                    int count = 0;
                    while (cursor.moveToNext())
                    {
                        if (slotNumber.equals(cursor.getString(4)))
                        {
                            Toast.makeText(context,"Sorry this slot is not available!",Toast.LENGTH_LONG).show();
                            count = 1;
                            break;
                        }

                    }
                    if (count == 0)
                    {
                        Intent intent = new Intent(context, Cart.class);
                        intent.putExtra("SLOT", slotNumber);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }

                }
            });

        }
    }
}
