package com.example.myhealthapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Открытие калькулятора калорий
        findViewById(R.id.btn_calorie_calculator).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CalorieCalculatorActivity.class));
            }
        });

        // Открытие экрана с сжигаемыми калориями
        findViewById(R.id.btn_calorie_burning).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CalorieBurningActivity.class));
            }
        });

        // Открытие экрана с упражнениями
        findViewById(R.id.btn_exercises).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ExercisesActivity.class));
            }
        });

        // Открытие калькулятора сна
        findViewById(R.id.btn_sleep_duration).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SleepCalculatorActivity.class));
            }
        });

        // Открытие экрана с приемами пищи
        findViewById(R.id.btn_meal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MealListActivity.class));
            }
        });

        // Открытие списка продуктов питания
        findViewById(R.id.btn_food_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FoodListActivity.class));
            }
        });

        // Открытие экрана с напоминаниями
        findViewById(R.id.btn_reminders).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ReminderListActivity.class));
            }
        });
    }
}
