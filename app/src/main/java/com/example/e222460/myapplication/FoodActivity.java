package com.example.e222460.myapplication;

/**
 * Created by E222460 on 4/19/2016.
 */

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FoodActivity extends Activity {

    public static final String EXTRA_FOODNO = "foodNo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        //Get the drink from the intent
        int foodNo = (Integer)getIntent().getExtras().get(EXTRA_FOODNO);
        Log.d("FoodActivity", " "+foodNo);
        //Create a cursor
        try {
            SQLiteOpenHelper foodDatabaseHelper = new FoodDatabase(this);
            SQLiteDatabase db = foodDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query ("FOOD",
                    new String[] {"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID"},
                    "_id = ?",
                    new String[] {Integer.toString(foodNo)},
                    null, null,null);

            //Move to the first record in the Cursor
            if (cursor.moveToFirst()) {

                //Get the drink details from the cursor
                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
                int photoId = cursor.getInt(2);

                //Populate the drink name
                TextView name = (TextView)findViewById(R.id.foodname);
                name.setText(nameText.toString());
                Log.d("FoodActivity", " "+nameText.toString());
                //Populate the drink description
                TextView description = (TextView)findViewById(R.id.fooddescription);
                description.setText(descriptionText.toString());
                Log.d("FoodActivity", " "+descriptionText.toString());
                //Populate the drink image
                ImageView photo = (ImageView)findViewById(R.id.foodphoto);
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText.toString());
                Log.d("FoodActivity", " "+nameText.toString());
            }
            cursor.close();
            db.close();
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
