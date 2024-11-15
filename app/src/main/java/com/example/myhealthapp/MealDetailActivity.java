package com.example.myhealthapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MealDetailActivity extends AppCompatActivity {

    private long mealId;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_detail);

        dbHelper = new DatabaseHelper(this);

        mealId = getIntent().getLongExtra("meal_id", -1); // Получение ID приема пищи
        if (mealId == -1) {
            finish(); // Закрытие активности, если ID не найден
            return;
        }

        TextView dateTextView = findViewById(R.id.dateTextView);
        TextView breakfastTextView = findViewById(R.id.breakfastTextView);
        TextView lunchTextView = findViewById(R.id.lunchTextView);
        TextView dinnerTextView = findViewById(R.id.dinnerTextView);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_MEALS,
                null,
                DatabaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(mealId)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            dateTextView.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE))); // Отображение даты
            breakfastTextView.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_BREAKFAST))); // Отображение завтрака
            lunchTextView.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LUNCH))); // Отображение обеда
            dinnerTextView.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DINNER))); // Отображение ужина
            cursor.close();
        }

        Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase writableDb = dbHelper.getWritableDatabase();
                writableDb.delete(DatabaseHelper.TABLE_MEALS, DatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(mealId)}); // Удаление приема пищи по ID
                finish(); // Закрытие активности после удаления
            }
        });

        Button closeButton = findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Закрытие активности
            }
        });
    }
}
