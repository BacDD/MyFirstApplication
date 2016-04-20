package com.example.e222460.myapplication;

/**
 * Created by E222460 on 4/19/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FoodDatabase extends SQLiteOpenHelper {

    private static final String DB_NAME = "Food";
    private static final int DB_VERSION = 2;

    FoodDatabase(Context context) {
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
           //Creat SQLite data for food
            db.execSQL("CREATE TABLE FOOD (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "DESCRIPTION TEXT, "
                    + "IMAGE_RESOURCE_ID INTEGER);");
            insertFood(db, "Bún Bò", "Bún Bò Huế cực ngon", R.drawable.bunbo);
            insertFood(db, "Bún Chả", "Đặc sản Hà Thành", R.drawable.buncha);
            insertFood(db, "Bún Cua", "Thơm ngon đến sợi cuối cùng", R.drawable.buncua);
            insertFood(db, "Bún Gà", "Thịt gà ta thơm ngon", R.drawable.bunga);
            insertFood(db, "Bún Giò", "Thị heo quê ", R.drawable.bungio);
            //Creat SQLite data for Address store

        }

        if(oldVersion < 2){

            // db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC;");
        }
    }

    //insert database for food
    private  static void insertFood(SQLiteDatabase db, String name, String description, int resourceID){
        ContentValues foodValues = new ContentValues();
        foodValues.put("NAME", name);
        foodValues.put("DESCRIPTION", description);
        foodValues.put("IMAGE_RESOURCE_ID", resourceID);
        db.insert("FOOD", null, foodValues);
    }

}
