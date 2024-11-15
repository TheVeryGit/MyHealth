package com.example.myhealthapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CalorieCalculatorActivity extends BaseActivity {
    private EditText inputAge, inputHeight, inputWeight; // Поля для ввода возраста, роста и веса
    private TextView errorAge, errorHeight, errorWeight; // Поля для отображения ошибок
    private RadioGroup radioGroupGender; // Группа переключателей для выбора пола

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_calculator);

        // Инициализация полей ввода и ошибок
        inputAge = findViewById(R.id.inputAge);
        inputHeight = findViewById(R.id.inputHeight);
        inputWeight = findViewById(R.id.inputWeight);

        // Настройка панели инструментов с названием "Расчет нормы калорий"
        initToolbar();
        setToolbarTitle("Расчет нормы калорий");

        errorAge = findViewById(R.id.errorAge);
        errorHeight = findViewById(R.id.errorHeight);
        errorWeight = findViewById(R.id.errorWeight);

        // Инициализация группы переключателей для выбора пола
        radioGroupGender = findViewById(R.id.radioGroupGender);

        // Настройка кнопки "Рассчитать" с обработчиком нажатия
        Button btnCalculate = findViewById(R.id.btnCalculate);
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCalories(); // Вызов метода для расчета калорий
            }
        });
    }

    // Метод для расчета нормы калорий
    private void calculateCalories() {
        // Получаем введенные данные и убираем лишние пробелы
        String ageStr = inputAge.getText().toString().trim();
        String heightStr = inputHeight.getText().toString().trim();
        String weightStr = inputWeight.getText().toString().trim();

        boolean isValid = true;

        // Проверяем заполнение и корректность поля "Возраст"
        if (ageStr.isEmpty()) {
            errorAge.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            errorAge.setVisibility(View.GONE);
        }

        // Проверяем заполнение и корректность поля "Рост"
        if (heightStr.isEmpty()) {
            errorHeight.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            errorHeight.setVisibility(View.GONE);
        }

        // Проверяем заполнение и корректность поля "Вес"
        if (weightStr.isEmpty()) {
            errorWeight.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            errorWeight.setVisibility(View.GONE);
        }

        // Если есть ошибки в заполнении, показываем уведомление и прерываем выполнение
        if (!isValid) {
            Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        int age;
        int height;
        int weight;
        try {
            // Конвертируем введенные значения в числовой формат
            age = Integer.parseInt(ageStr);
            height = Integer.parseInt(heightStr);
            weight = Integer.parseInt(weightStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Пожалуйста, используйте корректные значения", Toast.LENGTH_SHORT).show();
            return;
        }

        // Расчет базового уровня метаболизма (BMR) на основе пола
        double bmr;
        if (radioGroupGender.getCheckedRadioButtonId() == R.id.radioMale) {
            // Формула для мужчин
            bmr = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        } else {
            // Формула для женщин
            bmr = 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
        }

        // Расчет суточной нормы калорий с учетом минимальной активности
        double dailyCalories = bmr * 1.2;

        // Отображение результата пользователю через всплывающее уведомление
        String result = String.format("Норма калорий: %.2f калорий", dailyCalories);
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }
}