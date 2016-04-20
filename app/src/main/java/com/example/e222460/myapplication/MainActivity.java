package com.example.e222460.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class MainActivity extends Activity {

    private SQLiteDatabase db;
    private Cursor favoritesCursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Create an OnItemClickListener
        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> listView,
                                            View v,
                                            int position,
                                            long id) {
                        if (position == 0) {
                            Intent intent = new Intent(MainActivity.this,
                                    DrinkCategoryActivity.class);
                            startActivity(intent);
                        }
                        if (position == 1) {
                            Intent intent = new Intent(MainActivity.this,
                                    FoodCategoryActivity.class);
                            startActivity(intent);
                        }

                    }
                };
        //Add the listener to the list view
        ListView listView = (ListView) findViewById(R.id.list_options);
        listView.setOnItemClickListener(itemClickListener);

        //Update favorite drink to mainactivity
        ListView listFavorites = (ListView)findViewById(R.id.list_favorites);
        try{
            SQLiteOpenHelper drinkDatabaseHelper = new CuderDatabase(this);
            db = drinkDatabaseHelper.getReadableDatabase();
            favoritesCursor = db.query("DRINK",
                    new String[]{"_id", "NAME"},
                    "FAVORITE = 1",
                    null, null, null, null);
            CursorAdapter favoriteAdapter = new SimpleCursorAdapter(MainActivity.this,
                    android.R.layout.simple_list_item_1,
                    favoritesCursor,
                    new String[]{"NAME"},
                    new int[]{android.R.id.text1},
                    0);
            listFavorites.setAdapter(favoriteAdapter);

        }catch(SQLException e){
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show();
        }
        //Xu li khi click vao favorite drink
        listFavorites.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DrinkActivity.class);
                intent.putExtra(DrinkActivity.EXTRA_DRINKNO, (int)id);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        favoritesCursor.close();
        db.close();
    }

    public void OnRestart(){
        super.onRestart();
        try{
            CuderDatabase drinkDatabase = new CuderDatabase(this);
            db = drinkDatabase.getReadableDatabase();
            Cursor newCusor = db.query("DRINK",
                    new String[]{ "_id", "NAME"},
                    "FAVORITE = 1",
                    null, null, null, null);
            ListView listFavorite = (ListView)findViewById(R.id.list_favorites);

        }catch(SQLException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


}
