package com.example.e222460.myapplication;

/**
 * Created by E222460 on 4/19/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CuderDatabase extends SQLiteOpenHelper {

    private static final String DB_NAME = "Cuder";
    private static final int DB_VERSION = 2;

    CuderDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion){
        if(oldVersion < 1){
            //Creat SQLite data table for drink
            db.execSQL("CREATE TABLE DRINK (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "DESCRIPTION TEXT, "
                    + "IMAGE_RESOURCE_ID INTEGER);");

            insertDrink(db, "C2", "Thanh lọc cơ thể", R.drawable.c2);
            insertDrink(db, "Coca Cola", "Sảng khoái, mát lạnh", R.drawable.cocacola);
            insertDrink(db, "Lavie", "Nước khoáng từ thiên nhiên", R.drawable.lavie);
            insertDrink(db, "Pepsi", "Nước uống có ga", R.drawable.pepsi);
            insertDrink(db, "RedBull", "Nước tăng lực", R.drawable.redbull);
            insertDrink(db, "Twister", "Nước cam ép", R.drawable.twister);



        }

        if(oldVersion < 2){
            db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC;");
        }
    }
    //insert database for drink
    private static void insertDrink(SQLiteDatabase db, String name, String description, int resourceID){
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("NAME", name);
        drinkValues.put("DESCRIPTION", description);
        drinkValues.put("IMAGE_RESOURCE_ID", resourceID);
        db.insert("DRINK", null, drinkValues);
    }


}
