package com.example.myhealthapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ReminderDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "reminders.db"; // Имя базы данных
    private static final int DATABASE_VERSION = 1; // Версия базы данных

    public static final String TABLE_REMINDERS = "reminders"; // Имя таблицы напоминаний
    public static final String COLUMN_ID = "_id"; // Столбец с уникальным идентификатором
    public static final String COLUMN_DATE = "date"; //Столбец с датой напоминания
    public static final String COLUMN_TIME = "time"; // Столбец с временем напоминания
    public static final String COLUMN_MESSAGE = "message"; // Столбец с сообщением напоминания

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_REMINDERS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DATE + " TEXT NOT NULL, " + // Обязательное поле с датой
                    COLUMN_TIME + " TEXT NOT NULL, " + // Обязательное поле с временем
                    COLUMN_MESSAGE + " TEXT NOT NULL);"; // Обязательное поле с сообщением

    // Конструктор для создания базы данных
    public ReminderDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE); // Выполнение запроса для создания таблицы
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDERS); // Удаление старой таблицы
        onCreate(db); // Создание новой таблицы
    }
}
