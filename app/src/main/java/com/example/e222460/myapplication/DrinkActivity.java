package com.example.e222460.myapplication;

/**
 * Created by E222460 on 4/19/2016.
 */

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkActivity extends Activity {

    public static final String EXTRA_DRINKNO = "drinkNo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        //Get the drink from the intent
        int drinkNo = (Integer)getIntent().getExtras().get(EXTRA_DRINKNO);

        //Create a cursor
        try {
            SQLiteOpenHelper cuderDatabaseHelper = new CuderDatabase(this);
            SQLiteDatabase db = cuderDatabaseHelper.getWritableDatabase();
            Cursor cursor = db.query ("DRINK",
                    new String[] {"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID", "FAVORITE"},
                    "_id = ?",
                    new String[] {Integer.toString(drinkNo)},
                    null, null,null);

            //Move to the first record in the Cursor
            if (cursor.moveToFirst()) {

                //Get the drink details from the cursor
                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
                int photoId = cursor.getInt(2);
                boolean isFavorite = (cursor.getInt(3) == 1);

                //Populate the drink name
                TextView name = (TextView)findViewById(R.id.name);
                name.setText(nameText);

                //Populate the drink description
                TextView description = (TextView)findViewById(R.id.description);
                description.setText(descriptionText);

                //Populate the drink image
                ImageView photo = (ImageView)findViewById(R.id.photo);
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);

                //Populate the favorite
                CheckBox favorite = (CheckBox) findViewById(R.id.favorite);
                favorite.setChecked(isFavorite);


            }
            cursor.close();
            db.close();
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    //Update the database when checkbox is clicked
    public void onFavoriteClicked(View view){
        int drinkNo = (Integer)getIntent().getExtras().get("drinkNo");
        new UpdateDrinkTask().execute(drinkNo);
    }

    //Update the drink
    private class UpdateDrinkTask extends AsyncTask<Integer, Void, Boolean>{

        ContentValues drinkValues;
        //Put data to FAVORITE colum of the DRINK table
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            CheckBox favorite = (CheckBox)findViewById(R.id.favorite);
            drinkValues = new ContentValues();
            drinkValues.put("FAVORITE", favorite.isChecked());
        }


        @Override
        protected Boolean doInBackground(Integer... params) {
            int drinkNo = params[0];
            SQLiteOpenHelper drinkDatabaseHelper = new CuderDatabase(DrinkActivity.this);
            try {
                SQLiteDatabase db = drinkDatabaseHelper.getWritableDatabase();
                db.update("DRINK", drinkValues,
                        "_id = ?",
                        new String[] {Integer.toString(drinkNo)});
                db.close();
                return true;
            } catch(SQLiteException e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast toast = Toast.makeText(DrinkActivity.this,
                        "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

    }

}
