package com.example.grocerywithjava;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper mInstance;

    private static final String DATABASE_NAME = "groceryDataBase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_GROCERY = "grocery";
    private static final String KEY_GROCERY_ID = "uid";
    private static final String KEY_GROCERY_NAME = "name";
    private static final String KEY_GROCERY_DESCRIPTION = "description";
    private static final String KEY_GROCERY_PRICE = "price";
    private static final String KEY_GROCERY_UNIT = "unit";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String mCreateGroceryTable = "CREATE TABLE " + TABLE_GROCERY +
                "(" +
                KEY_GROCERY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                KEY_GROCERY_NAME + " INTEGER," +
                KEY_GROCERY_DESCRIPTION + " TEXT," +
                KEY_GROCERY_PRICE + " REAL," +
                KEY_GROCERY_UNIT + " TEXT" +
                ")";

        db.execSQL(mCreateGroceryTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROCERY);
            onCreate(db);
        }
    }


    public static synchronized DatabaseHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    public ArrayList<Grocery_Class> getData() {
        ArrayList<Grocery_Class> list = new ArrayList<Grocery_Class>();
        SQLiteDatabase db = getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_GROCERY, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(new Grocery_Class(cursor.getFloat(3), cursor.getString(1), cursor.getString(2), cursor.getString(4)));
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }


    public void insertAll(ArrayList<Grocery_Class> list) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            for (Grocery_Class item : list) {
                ContentValues values = new ContentValues();
                values.put(KEY_GROCERY_NAME, item.getName());
                values.put(KEY_GROCERY_DESCRIPTION, item.getDescription());
                values.put(KEY_GROCERY_PRICE, item.getPrice());
                values.put(KEY_GROCERY_UNIT, item.getUnit());
                db.insertOrThrow(TABLE_GROCERY, null, values);
            }
        } catch (Exception e) {
            Log.d("mytag", "Error while trying to add post to database");
        }
        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.delete(TABLE_GROCERY, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to delete all posts and users");
        }
        db.close();
    }

}
