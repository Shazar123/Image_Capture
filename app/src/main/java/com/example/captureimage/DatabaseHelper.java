package com.example.captureimage;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ImageDatabase.db";
    private static final String TABLE_NAME = "images";
    private static final String COL_ID = "id";
    private static final String COL_IMAGE = "image";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_IMAGE + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean saveImage(String imageString) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_IMAGE, imageString);

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1; // Return true if successful
    }

    public String getImage() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COL_IMAGE + " FROM " + TABLE_NAME + " LIMIT 1", null);
        if (cursor.moveToFirst()) {
            String imageString = cursor.getString(0);
            cursor.close();
            return imageString;
        }
        cursor.close();
        return null;
    }
}
