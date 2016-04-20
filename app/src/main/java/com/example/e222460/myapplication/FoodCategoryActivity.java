package com.example.e222460.myapplication;

/**
 * Created by E222460 on 4/19/2016.
 */

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class FoodCategoryActivity extends ListActivity {

    private SQLiteDatabase db;
    private Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listFoods = getListView();

        try{

            SQLiteOpenHelper foodDatabaseHelper = new FoodDatabase(this);
            db = foodDatabaseHelper.getReadableDatabase(); //Get a reference to the database
            cursor = db.query("FOOD",
                    new String[]{"_id", "NAME"},
                    null, null, null, null, null);
            CursorAdapter listAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"NAME"},//Display the contents of the NAME column
                    new int[]{android.R.id.text1},
                    0);
            Log.d("FoodCategoryActivity"," "+ android.R.id.text1);
            listFoods.setAdapter(listAdapter);

        }catch (SQLException e){
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onListItemClick(ListView listView,
                                View itemView,
                                int position,
                                long id) {
        Intent intent = new Intent(FoodCategoryActivity.this, FoodActivity.class);
        intent.putExtra(FoodActivity.EXTRA_FOODNO, (int) id);
        startActivity(intent);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }

}
