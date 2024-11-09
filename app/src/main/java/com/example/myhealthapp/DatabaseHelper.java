package com.example.myhealthapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "nutrition.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_MEALS = "meals";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_BREAKFAST = "breakfast";
    public static final String COLUMN_LUNCH = "lunch";
    public static final String COLUMN_DINNER = "dinner";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_MEALS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DATE + " TEXT NOT NULL, " +
                    COLUMN_BREAKFAST + " TEXT, " +
                    COLUMN_LUNCH + " TEXT, " +
                    COLUMN_DINNER + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEALS);
        onCreate(db);
    }
}