package com.example.myhealthapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MealListActivity extends BaseActivity {

    private ListView listView;
    private DatabaseHelper dbHelper;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_list);

        listView = findViewById(R.id.listView); // Инициализация списка
        dbHelper = new DatabaseHelper(this); // Инициализация базы данных

        initToolbar(); // Инициализация тулбара
        setToolbarTitle("Рацион питания"); // Установка заголовка тулбара

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MealListActivity.this, AddMealActivity.class);
                startActivity(intent); // Переход к экрану добавления нового приема пищи
            }
        });

        loadMeals(); // Загрузка списка приемов пищи
    }

    private void loadMeals() {
        SQLiteDatabase db = dbHelper.getReadableDatabase(); // Открытие базы данных для чтения
        Cursor cursor = db.query(DatabaseHelper.TABLE_MEALS,
                null, null, null, null, null, DatabaseHelper.COLUMN_DATE + " DESC"); // Запрос для получения данных о приемах пищи

        adapter = new SimpleCursorAdapter(this,
                R.layout.meal_list_item,
                cursor,
                new String[]{DatabaseHelper.COLUMN_DATE},
                new int[]{R.id.dateTextView},
                0); // Настройка адаптера для отображения данных

        listView.setAdapter(adapter); // Установка адаптера для списка
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(MealListActivity.this, MealDetailActivity.class);
            intent.putExtra("meal_id", id); // Передача ID выбранного приема пищи
            startActivity(intent); // Переход к экрану с подробной информацией о приеме пищи
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMeals(); // Перезагрузка списка при возобновлении активности
    }
}
