package com.example.myhealthapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("message"); // Получение сообщения для уведомления

        // Создание канала уведомлений
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("REMINDER_CHANNEL", "Reminders", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel); // Регистрация канала уведомлений
        }

        // Построение уведомления
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "REMINDER_CHANNEL")
                .setSmallIcon(R.drawable.notification_logo) // Векторный логотип MyHealth рядом с уведомлением
                .setContentTitle("Напоминание") // Заголовок уведомления
                .setContentText(message) // Текст уведомления
                .setPriority(NotificationCompat.PRIORITY_HIGH) // Приоритет уведомления
                .setAutoCancel(true); // Автоматическое закрытие уведомления при нажатии

        // Запуск уведомления
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(200, builder.build()); // Отправка уведомления
    }
}
