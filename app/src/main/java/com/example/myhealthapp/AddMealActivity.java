package com.example.myhealthapp;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddMealActivity extends AppCompatActivity {

    private EditText dateEditText; // Поле для ввода даты
    private EditText breakfastEditText; // Поле для ввода завтрака
    private EditText lunchEditText; // Поле для ввода обеда
    private EditText dinnerEditText; // Поле для ввода ужина
    private DatabaseHelper dbHelper; // Объект для работы с базой данных

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Locale locale = new Locale("ru"); // Устанавливаем локаль для русскоязычных пользователей
        Locale.setDefault(locale);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal); // Устанавливаем layout активности

        dbHelper = new DatabaseHelper(this); // Инициализация помощника для работы с базой данных

        // Инициализация полей ввода
        dateEditText = findViewById(R.id.dateEditText);
        breakfastEditText = findViewById(R.id.breakfastEditText);
        lunchEditText = findViewById(R.id.lunchEditText);
        dinnerEditText = findViewById(R.id.dinnerEditText);

        // Кнопка для добавления приемов пищи
        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> addMeal()); // Обработчик клика по кнопке

        // Кнопка для закрытия активности
        Button closeButton = findViewById(R.id.closeButton);
        closeButton.setOnClickListener(v -> finish());
    }

    // Метод для добавления нового приема пищи в базу данных
    private void addMeal() {
        // Получение данных из полей ввода
        String date = dateEditText.getText().toString();
        String breakfast = breakfastEditText.getText().toString();
        String lunch = lunchEditText.getText().toString();
        String dinner = dinnerEditText.getText().toString();

        // Проверка, что все поля заполнены
        if (TextUtils.isEmpty(date) || TextUtils.isEmpty(breakfast) || TextUtils.isEmpty(lunch) || TextUtils.isEmpty(dinner)) {
            Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        // Проверка корректности формата даты
        if (!isValidDate(date)) {
            Toast.makeText(this, "Дата должна быть в формате ДД.ММ.ГГГГ", Toast.LENGTH_SHORT).show();
            return;
        }

        // Вставка данных в базу данных
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("INSERT INTO " + DatabaseHelper.TABLE_MEALS +
                        " (" + DatabaseHelper.COLUMN_DATE + ", " +
                        DatabaseHelper.COLUMN_BREAKFAST + ", " +
                        DatabaseHelper.COLUMN_LUNCH + ", " +
                        DatabaseHelper.COLUMN_DINNER + ") VALUES (?, ?, ?, ?)",
                new Object[]{date, breakfast, lunch, dinner});

        finish(); // Закрытие активности после добавления
    }

    // Метод для проверки корректности даты
    private boolean isValidDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.US);
        try {
            Date parsedDate = format.parse(date); // Парсим дату
            return parsedDate != null && format.format(parsedDate).equals(date); // Проверка корректности
        } catch (ParseException e) {
            return false;
        }
    }
}
