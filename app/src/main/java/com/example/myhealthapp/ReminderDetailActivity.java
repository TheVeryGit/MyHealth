package com.example.myhealthapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ReminderDetailActivity extends AppCompatActivity {

    private long reminderId;
    private ReminderDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_detail);

        dbHelper = new ReminderDatabaseHelper(this);

        reminderId = getIntent().getLongExtra("reminder_id", -1);
        if (reminderId == -1) {
            finish();
            return;
        }

        TextView dateTextView = findViewById(R.id.dateTextView);
        TextView timeTextView = findViewById(R.id.timeTextView);
        TextView messageTextView = findViewById(R.id.messageTextView);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(ReminderDatabaseHelper.TABLE_REMINDERS,
                null,
                ReminderDatabaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(reminderId)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            dateTextView.setText(cursor.getString(cursor.getColumnIndex(ReminderDatabaseHelper.COLUMN_DATE)));
            timeTextView.setText(cursor.getString(cursor.getColumnIndex(ReminderDatabaseHelper.COLUMN_TIME)));
            messageTextView.setText(cursor.getString(cursor.getColumnIndex(ReminderDatabaseHelper.COLUMN_MESSAGE)));
            cursor.close();
        }

        Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(v -> {
            SQLiteDatabase writableDb = dbHelper.getWritableDatabase();
            writableDb.delete(ReminderDatabaseHelper.TABLE_REMINDERS, ReminderDatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(reminderId)});

            // Отмена напоминания
            Intent intent = new Intent(ReminderDetailActivity.this, ReminderBroadcastReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(ReminderDetailActivity.this, (int) reminderId, intent, PendingIntent.FLAG_NO_CREATE | PendingIntent.FLAG_IMMUTABLE);
            if (pendingIntent != null) {
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                if (alarmManager != null) {
                    alarmManager.cancel(pendingIntent);
                }
            }

            finish();
        });

        Button closeButton = findViewById(R.id.closeButton);
        closeButton.setOnClickListener(v -> finish());
    }
}
