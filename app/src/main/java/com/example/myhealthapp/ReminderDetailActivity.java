package com.example.myhealthapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ReminderDetailActivity extends AppCompatActivity {

    private long reminderId; // Идентификатор напоминания
    private ReminderDatabaseHelper dbHelper; // Помощник для работы с базой данных

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_detail);

        dbHelper = new ReminderDatabaseHelper(this); // Инициализация помощника

        reminderId = getIntent().getLongExtra("reminder_id", -1); // Получение ID напоминания из Intent
        if (reminderId == -1) {
            finish(); // Если ID не найдено, закрываем активность
            return;
        }

        TextView dateTextView = findViewById(R.id.dateTextView); // Поле для отображения даты
        TextView timeTextView = findViewById(R.id.timeTextView); // Поле для отображения времени
        TextView messageTextView = findViewById(R.id.messageTextView); // Поле для отображения сообщения

        SQLiteDatabase db = dbHelper.getReadableDatabase(); // Открытие базы данных для чтения
        Cursor cursor = db.query(ReminderDatabaseHelper.TABLE_REMINDERS,
                null,
                ReminderDatabaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(reminderId)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            // Если напоминание найдено в базе данных, отображаем его
            dateTextView.setText(cursor.getString(cursor.getColumnIndex(ReminderDatabaseHelper.COLUMN_DATE)));
            timeTextView.setText(cursor.getString(cursor.getColumnIndex(ReminderDatabaseHelper.COLUMN_TIME)));
            messageTextView.setText(cursor.getString(cursor.getColumnIndex(ReminderDatabaseHelper.COLUMN_MESSAGE)));
            cursor.close();
        }

        Button deleteButton = findViewById(R.id.deleteButton); // Кнопка для удаления напоминания
        deleteButton.setOnClickListener(v -> {
            SQLiteDatabase writableDb = dbHelper.getWritableDatabase(); // Открытие базы данных для записи
            writableDb.delete(ReminderDatabaseHelper.TABLE_REMINDERS, ReminderDatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(reminderId)}); // Удаление напоминания из базы

            // Отмена напоминания
            Intent intent = new Intent(ReminderDetailActivity.this, ReminderBroadcastReceiver.class); // Создание Intent для удаления уведомления
            PendingIntent pendingIntent = PendingIntent.getBroadcast(ReminderDetailActivity.this, (int) reminderId, intent, PendingIntent.FLAG_NO_CREATE | PendingIntent.FLAG_IMMUTABLE); // Создание PendingIntent для уведомления
            if (pendingIntent != null) {
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE); // Получение AlarmManager для отмены уведомления
                if (alarmManager != null) {
                    alarmManager.cancel(pendingIntent); // Отмена уведомления
                }
            }

            finish(); // Закрытие активности
        });

        Button closeButton = findViewById(R.id.closeButton); // Кнопка для закрытия активности
        closeButton.setOnClickListener(v -> finish()); // Закрытие активности при нажатии
    }
}
