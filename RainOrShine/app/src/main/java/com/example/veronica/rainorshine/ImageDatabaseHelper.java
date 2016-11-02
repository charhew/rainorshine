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
    private static final int DATABASE_VERSION = 3;

    // Database Name
    private static final String DATABASE_NAME = "imagedatabase";

    // Contacts table name
    private static final String TABLE_NAME = "CAMERAINPUTTABLE";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_CAPTION = "caption";

    long id;

    public ImageDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_IMAGE + " BLOB,"
                + KEY_CAPTION + " TEXT" + ")";


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

        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        id = db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    // Getting single image
    CameraInput getCameraInput() {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {KEY_ID, KEY_IMAGE, KEY_CAPTION};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);

        if(cursor != null) {
            cursor.moveToLast();
        }

        CameraInput input = new CameraInput(Integer.parseInt(cursor.getString(0)),
                cursor.getBlob(1), cursor.getString(2));

        // return contact
        return input;

    }

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

                // Adding contact to list
                inputList.add(input);
            } while (cursor.moveToNext());
        }
        // close inserting data from database
        db.close();
        // return contact list
        return inputList;
    }
}
