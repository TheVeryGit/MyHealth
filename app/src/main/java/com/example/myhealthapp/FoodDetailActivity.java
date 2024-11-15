package com.example.myhealthapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FoodDetailActivity extends AppCompatActivity {

    private ImageView detailFoodImage; // Изображение продукта
    private TextView detailFoodName; // Название продукта
    private TextView detailFoodNutrition; // Информация о питательной ценности
    private EditText foodWeightInput; // Поле для ввода веса продукта
    private Button closeButton; // Кнопка закрытия экрана

    private Food food; // Продукт, данные которого показываются

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        detailFoodImage = findViewById(R.id.detailFoodImage);
        detailFoodName = findViewById(R.id.detailFoodName);
        detailFoodNutrition = findViewById(R.id.detailFoodNutrition);
        foodWeightInput = findViewById(R.id.foodWeightInput);
        closeButton = findViewById(R.id.closeButton);

        // Получение объекта Food из Intent
        food = (Food) getIntent().getSerializableExtra("food");

        // Установка изображения и имени продукта
        detailFoodImage.setImageResource(food.getImageResId());
        detailFoodName.setText(food.getName());
        updateNutritionInfo(100); // Обновляем информацию о питательных веществах для 100 грамм

        // Слушатель для изменения веса продукта
        foodWeightInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int weight = Integer.parseInt(s.toString()); // Преобразуем введенное значение в вес
                    updateNutritionInfo(weight); // Обновляем информацию о питательных веществах
                } catch (NumberFormatException e) {
                    if (!s.toString().isEmpty()) {
                        // Если введен неверный вес
                        Toast.makeText(FoodDetailActivity.this, "Введите правильный вес", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Слушатель для кнопки закрытия экрана
        closeButton.setOnClickListener(v -> finish());
    }

    private void updateNutritionInfo(int weight) {
        int calories = (food.getCalories() * weight) / 100; // Расчет калорий
        int protein = (food.getProtein() * weight) / 100; // Расчет белков
        int fat = (food.getFat() * weight) / 100; // Расчет жиров
        int carbs = (food.getCarbs() * weight) / 100; // Расчет углеводов

        // Формируем строку с информацией о питательной ценности
        String nutritionInfo = "Калории: " + calories + " ккал\n" +
                "Белки: " + protein + " г\n" +
                "Жиры: " + fat + " г\n" +
                "Углеводы: " + carbs + " г";
        detailFoodNutrition.setText(nutritionInfo); // Отображаем информацию
    }
}
