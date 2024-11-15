package com.example.myhealthapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class ReminderListActivity extends BaseActivity {

    private ListView listView;  // Список напоминаний
    private ReminderDatabaseHelper dbHelper;  // Помощник для работы с базой данных
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_list);  // Устанавливаем layout для экрана

        listView = findViewById(R.id.listView);
        dbHelper = new ReminderDatabaseHelper(this);  // Инициализация помощника для работы с базой

        initToolbar();  // Инициализация тулбара и установка названия
        setToolbarTitle("Напоминания");

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(ReminderListActivity.this, AddReminderActivity.class);
            startActivity(intent);
        });

        loadReminders();  // Загружаем все напоминания из базы данных
    }

    // Метод для загрузки напоминаний из базы данных
    private void loadReminders() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();  // Открываем базу данных для чтения
        Cursor cursor = db.query(ReminderDatabaseHelper.TABLE_REMINDERS,
                null, null, null, null, null, ReminderDatabaseHelper.COLUMN_DATE + " DESC");  // Сортируем по дате

        adapter = new SimpleCursorAdapter(this,
                R.layout.reminder_list_item,
                cursor,
                new String[]{ReminderDatabaseHelper.COLUMN_DATE, ReminderDatabaseHelper.COLUMN_TIME},
                new int[]{R.id.dateTextView, R.id.timeTextView},
                0);

        listView.setAdapter(adapter);  // Устанавливаем адаптер для списка
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(ReminderListActivity.this, ReminderDetailActivity.class);
            intent.putExtra("reminder_id", id);  // Передаём ID выбранного напоминания
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadReminders();
    }
}
