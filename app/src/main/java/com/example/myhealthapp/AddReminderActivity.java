package com.example.myhealthapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddReminderActivity extends AppCompatActivity {

    private EditText dateEditText; // Поле для ввода даты
    private EditText timeEditText; // Поле для ввода времени
    private EditText messageEditText; // Поле для ввода сообщения напоминания
    private ReminderDatabaseHelper dbHelper; // Помощник для работы с базой данных напоминаний

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder); // Устанавливаем layout активности

        dbHelper = new ReminderDatabaseHelper(this); // Инициализация базы данных напоминаний

        // Инициализация полей ввода
        dateEditText = findViewById(R.id.dateEditText);
        timeEditText = findViewById(R.id.timeEditText);
        messageEditText = findViewById(R.id.messageEditText);

        // Кнопка для добавления напоминания
        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> addReminder());

        // Кнопка для закрытия активности
        Button closeButton = findViewById(R.id.closeButton);
        closeButton.setOnClickListener(v -> finish());

        // Проверка разрешения на установку точных будильников (для API >= S)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null && !alarmManager.canScheduleExactAlarms()) {
                Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(intent);
            }
        }
    }

    // Метод для добавления нового напоминания
    private void addReminder() {
        // Проверка разрешения на отправку уведомлений для API 33 и выше
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // Запросить разрешение на отправку уведомлений
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 101);
                return; // Останавливаем метод, ждем ответ пользователя
            }
        }

        // Получаем данные из полей ввода
        String date = dateEditText.getText().toString();
        String time = timeEditText.getText().toString();
        String message = messageEditText.getText().toString();

        // Проверка на пустые поля
        if (TextUtils.isEmpty(date) || TextUtils.isEmpty(time) || TextUtils.isEmpty(message)) {
            Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        // Проверка даты и времени на правильный формат
        if (!isValidDate(date)) {
            Toast.makeText(this, "Дата должна быть в формате ДД.ММ.ГГГГ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidTime(time)) {
            Toast.makeText(this, "Время должно быть в формате ЧЧ:ММ", Toast.LENGTH_SHORT).show();
            return;
        }

        // Вставка напоминания в базу данных
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ReminderDatabaseHelper.COLUMN_DATE, date);
        values.put(ReminderDatabaseHelper.COLUMN_TIME, time);
        values.put(ReminderDatabaseHelper.COLUMN_MESSAGE, message);
        long id = db.insert(ReminderDatabaseHelper.TABLE_REMINDERS, null, values);

        // Преобразование даты и времени в миллисекунды
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
        Date reminderDate = null;
        try {
            reminderDate = dateFormat.parse(date + " " + time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Настройка AlarmManager
        if (reminderDate != null) {
            Intent intent = new Intent(this, ReminderBroadcastReceiver.class);
            intent.putExtra("message", message);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, (int) id, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, reminderDate.getTime(), pendingIntent);
            }
        }

        Toast.makeText(this, "Напоминание добавлено", Toast.LENGTH_SHORT).show();
        finish();
    }

    // Проверка корректности даты
    private boolean isValidDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.US);
        try {
            Date parsedDate = format.parse(date);
            return parsedDate != null && format.format(parsedDate).equals(date);
        } catch (ParseException e) {
            return false;
        }
    }

    // Проверка корректности времени
    private boolean isValidTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.US);
        try {
            Date parsedTime = format.parse(time);
            return parsedTime != null && format.format(parsedTime).equals(time);
        } catch (ParseException e) {
            return false;
        }
    }

    // Обработка разрешений на уведомления
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            addReminder(); // Повторно вызовем метод добавления, если разрешение получено
        } else {
            Toast.makeText(this, "Разрешение на уведомления не предоставлено", Toast.LENGTH_SHORT).show();
        }
    }
}
