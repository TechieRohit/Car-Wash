package com.example.rohit.carwash.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by J!MMY on 10/3/2017.
 */
public class LoginDatabase extends SQLiteOpenHelper{


    public static final String DATABASE_NAME = "Car.db";
    public static final String TABLE_NAME = "login_table";
    public static final String COLUMN_1 = "ID";
    public static final String COLUMN_2 = "NAME";
    public static final String COLUMN_3 = "EMAIL";
    public static final String COLUMN_4 = "PASSWORD";
    public static final String COLUMN_5 = "SLOT";


    public LoginDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    /** Create the database implementing SQL query **/
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,EMAIL TEXT,PASSWORD TEXT,SLOT TEXT)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    /** Function to insert data in the next column**/
    public void insertData(String name,String email,String password,String slot) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_2,name);
        contentValues.put(COLUMN_3,email);
        contentValues.put(COLUMN_4, password);
        contentValues.put(COLUMN_5,slot);
        db.insert(TABLE_NAME, null, contentValues);

    }

    public boolean updateData(String id,String slot) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_1,id);
        contentValues.put(COLUMN_5,slot);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        return true;
    }



    /** Function to fetch the whole data **/
    public Cursor getAllData() {
        //Instance of SQLiteDatabase class
        SQLiteDatabase db = this.getWritableDatabase();

        //Instance of Cursor class
        Cursor output = db.rawQuery("select * from " + TABLE_NAME, null);
        return output;
    }


    /** Function to delete a column in database **/
    public Integer deleteData (String id) {
        //Instance of SQLiteDatabase class
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }

    /** Function that runs a Query to delete all the data **/
    public  void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
    }
}
