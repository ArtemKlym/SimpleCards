package com.example.simplecards.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDataBaseHelper extends SQLiteOpenHelper {
    private Context context;
    public static final String DATABASE_NAME = "WordsList.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "words";
    public static final String COLUMN_TABLE_ID = "id";
    public static final String COLUMN_ORIGIN_TEXT = "origin";
    public static final String COLUMN_TRANSLATE_TEXT = "translate";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    COLUMN_TABLE_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_ORIGIN_TEXT + " TEXT," +
                    COLUMN_TRANSLATE_TEXT + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public MyDataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    public void addRawToDataBase(String originText, String translateText){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ORIGIN_TEXT,originText);
        cv.put(COLUMN_TRANSLATE_TEXT,translateText);

        long result = db.insert(TABLE_NAME,null,cv);
        if(result == -1){
            Toast.makeText(context, "Fail to add to database", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Added to Database", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllWordsFromDB(){
        String queryGetAllData = "SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            //Store data in cursor
           cursor = db.rawQuery(queryGetAllData,null);
        }
        return cursor;
    }

    public boolean deleteItem(String originText, String translatedText){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,
                COLUMN_ORIGIN_TEXT+"=? and "+COLUMN_TRANSLATE_TEXT+"=?",
                new String[]{originText,translatedText}) > 0;
    }

    public boolean updateData(String id, String origin, String translate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ORIGIN_TEXT, origin.toString().trim());
        cv.put(COLUMN_TRANSLATE_TEXT,translate);

        return db.update(TABLE_NAME,cv,"id=?",new String[]{id}) > 0;
    }
}
