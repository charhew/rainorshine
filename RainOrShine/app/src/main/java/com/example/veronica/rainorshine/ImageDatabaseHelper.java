package com.example.veronica.rainorshine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Veronica on 2016-10-30.
 */

public class ImageDatabaseHelper extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 21;

    // Database Name
    public static final String DATABASE_NAME = "imagedatabase";

    // Contacts table name
    public static final String TABLE_NAME = "CAMERAINPUTTABLE";


    // Contacts Table Columns names
    public static final String KEY_ID = "_id";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_CAPTION = "caption";
    public static final String KEY_WEATHER_CONDITION = "weatherCondition";
    public static final String KEY_WEATHER_TEMP = "weatherTemp";


    public ImageDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_IMAGE + " BLOB,"
                + KEY_CAPTION + " TEXT," + KEY_WEATHER_TEMP + " INTEGER," + KEY_WEATHER_CONDITION + " TEXT" + ")";

        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public void addCameraInput(CameraInput input) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_IMAGE, input.image);
        values.put(KEY_CAPTION, input.caption);
        values.put(KEY_WEATHER_TEMP, input.weatherTemp);
        values.put(KEY_WEATHER_CONDITION, input.weatherCondition);

        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    public boolean deleteCameraInput(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "_id=" + id, null) > 0;
    }

    // Getting single image
    CameraInput getCameraInput() {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {KEY_ID, KEY_IMAGE, KEY_CAPTION, KEY_WEATHER_TEMP, KEY_WEATHER_CONDITION};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);

        if(cursor != null) {
            cursor.moveToLast();
        }

        CameraInput input = new CameraInput(Integer.parseInt(cursor.getString(0)),
                cursor.getBlob(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4));

        // return contact
        return input;

    }

    //USE THIS ONE IF YOU WANT TO SHOW ALL ON THE GRID
    public List<CameraInput> getAllCameraInputs() {
        List<CameraInput> inputList = new ArrayList<CameraInput>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CameraInput input = new CameraInput();
                input.setID(Integer.parseInt(cursor.getString(0)));
                input.setImage(cursor.getBlob(1));
                input.setCaption(cursor.getString(2));
                input.setWeatherTemp(cursor.getInt(3));
                input.setWeatherCondition(cursor.getString(4));

                // Adding contact to list
                inputList.add(input);
            } while (cursor.moveToNext());
        }
        // close inserting data from database
        db.close();
        // return contact list
        return inputList;
    }

    //USE THIS ONE IF YOU ONLY WANT TO FILTER BY WEATHER CONDITION
    public List<CameraInput> getAllCameraInputs2(String type) {
        List<CameraInput> inputList = new ArrayList<CameraInput>();

        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {KEY_ID, KEY_IMAGE, KEY_CAPTION, KEY_WEATHER_TEMP, KEY_WEATHER_CONDITION};

        String selection = KEY_WEATHER_CONDITION + "='" +type+"'";
        Cursor cursor = db.query(TABLE_NAME, columns, selection, null, null, null, null);


        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CameraInput input = new CameraInput();
                input.setID(Integer.parseInt(cursor.getString(0)));
                input.setImage(cursor.getBlob(1));
                input.setCaption(cursor.getString(2));
                input.setWeatherTemp(cursor.getInt(3));
                input.setWeatherCondition(cursor.getString(4));

                // Adding contact to list
                inputList.add(input);
            } while (cursor.moveToNext());
        }
        // close inserting data from database
        db.close();
        // return contact list
        return inputList;
    }

    //USE THIS ONE IF YOU WANT TO FILTER BY WEATHER CONDITION AND (TEMPERATURE +- 5)
    public List<CameraInput> getAllCameraInputs3(String type, String min, String max) {
        List<CameraInput> inputList = new ArrayList<CameraInput>();

        SQLiteDatabase db = this.getWritableDatabase();
        String[] selectionArgs = {min, max, type};
        String[] columns = {KEY_ID, KEY_IMAGE, KEY_CAPTION, KEY_WEATHER_TEMP, KEY_WEATHER_CONDITION};

        String selection = KEY_WEATHER_TEMP + " >= ? AND " + KEY_WEATHER_TEMP + " <= ? AND " +KEY_WEATHER_CONDITION + " = ?";
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CameraInput input = new CameraInput();
                input.setID(Integer.parseInt(cursor.getString(0)));
                input.setImage(cursor.getBlob(1));
                input.setCaption(cursor.getString(2));
                input.setWeatherTemp(cursor.getInt(3));
                input.setWeatherCondition(cursor.getString(4));

                // Adding contact to list
                inputList.add(input);
            } while (cursor.moveToNext());
        }
        // close inserting data from database
        db.close();
        // return contact list
        return inputList;
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {KEY_ID, KEY_IMAGE, KEY_CAPTION, KEY_WEATHER_TEMP, KEY_WEATHER_CONDITION};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        return cursor;
    }

    public String getSelectedData (String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {KEY_ID, KEY_IMAGE, KEY_CAPTION, KEY_WEATHER_TEMP, KEY_WEATHER_CONDITION};

        String selection = KEY_WEATHER_CONDITION + "='" +type+"'";
        Cursor cursor = db.query(TABLE_NAME, columns, selection, null, null, null, null);
        StringBuffer buffer = new StringBuffer();

        while(cursor.moveToNext()) {
            int index = cursor.getColumnIndex(KEY_ID);
            int index1 = cursor.getColumnIndex(KEY_IMAGE);
            int index2 = cursor.getColumnIndex(KEY_CAPTION);
            int index3 = cursor.getColumnIndex(KEY_WEATHER_CONDITION);
            int index4 = cursor.getColumnIndex(KEY_WEATHER_TEMP);
            String id = cursor.getString(index);
            String image = cursor.getString(index1);
            String caption = cursor.getString(index2);
            String weatherCondition = cursor.getString(index3);
            String weatherTemp = cursor.getString(index4);

            buffer.append(id + " " + image + " " + caption + " " + weatherCondition + " " + weatherTemp + "\n");
        }
        return buffer.toString();
    }

    public String getSelectedData2 (String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {KEY_ID, KEY_IMAGE, KEY_CAPTION, KEY_WEATHER_TEMP, KEY_WEATHER_CONDITION};

        String selection = KEY_WEATHER_CONDITION + "='" +type+"'";
        Cursor cursor = db.query(TABLE_NAME, columns, selection, null, null, null, null);
        StringBuffer buffer = new StringBuffer();



        while(cursor.moveToNext()) {
            int index3 = cursor.getColumnIndex(KEY_WEATHER_CONDITION);
            int index4 = cursor.getColumnIndex(KEY_WEATHER_TEMP);
            String weatherCondition = cursor.getString(index3);
            String weatherTemp = cursor.getString(index4);

            buffer.append(weatherCondition + " " + weatherTemp + "\n");
        }
        return buffer.toString();
    }
}
