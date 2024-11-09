package com.example.myhealthapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class ReminderListActivity extends BaseActivity {

    private ListView listView;
    private ReminderDatabaseHelper dbHelper;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_list);

        listView = findViewById(R.id.listView);
        dbHelper = new ReminderDatabaseHelper(this);

        initToolbar();
        setToolbarTitle("Напоминания");

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(ReminderListActivity.this, AddReminderActivity.class);
            startActivity(intent);
        });

        loadReminders();
    }

    private void loadReminders() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(ReminderDatabaseHelper.TABLE_REMINDERS,
                null, null, null, null, null, ReminderDatabaseHelper.COLUMN_DATE + " DESC");

        adapter = new SimpleCursorAdapter(this,
                R.layout.reminder_list_item,
                cursor,
                new String[]{ReminderDatabaseHelper.COLUMN_DATE, ReminderDatabaseHelper.COLUMN_TIME},
                new int[]{R.id.dateTextView, R.id.timeTextView},
                0);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(ReminderListActivity.this, ReminderDetailActivity.class);
            intent.putExtra("reminder_id", id);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadReminders();
    }
}

