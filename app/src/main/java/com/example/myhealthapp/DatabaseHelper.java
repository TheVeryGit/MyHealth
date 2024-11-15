package com.example.myhealthapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "nutrition.db"; // Название базы данных
    private static final int DATABASE_VERSION = 1;

    // Названия таблицы и колонок для хранения информации о приёмах пищи
    public static final String TABLE_MEALS = "meals";
    public static final String COLUMN_ID = "_id"; // Уникальный ID записи
    public static final String COLUMN_DATE = "date"; // Дата записи
    public static final String COLUMN_BREAKFAST = "breakfast"; // Завтрак
    public static final String COLUMN_LUNCH = "lunch"; // Обед
    public static final String COLUMN_DINNER = "dinner"; // Ужин

    // SQL-запрос для создания таблицы meals
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_MEALS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + // ID будет автоматически увеличиваться
                    COLUMN_DATE + " TEXT NOT NULL, " + // Дата обязательна для каждой записи
                    COLUMN_BREAKFAST + " TEXT, " + // Поле для завтрака
                    COLUMN_LUNCH + " TEXT, " + // Поле для обеда
                    COLUMN_DINNER + " TEXT);"; // Поле для ужина

    // Конструктор для создания экземпляра DatabaseHelper
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Метод вызывается при первом создании базы данных
        db.execSQL(TABLE_CREATE); // Создаём таблицу meals
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Метод вызывается при обновлении версии базы данных
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEALS); // Удаляем старую таблицу, если она существует
        onCreate(db); // Создаём новую версию таблицы
    }
}
