package com.example.myhealthapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ExerciseDetailActivity extends AppCompatActivity {

    private ImageView imageExerciseDetail; // Элемент для отображения изображения упражнения
    private TextView textExerciseNameDetail; // Поле для отображения названия упражнения
    private TextView textExerciseDescription; // Поле для отображения описания упражнения
    private TextView textExerciseInstructions; // Поле для инструкций выполнения упражнения
    private Button buttonClose; // Кнопка для закрытия экрана

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);

        // Инициализация визуальных элементов
        imageExerciseDetail = findViewById(R.id.imageExerciseDetail);
        textExerciseNameDetail = findViewById(R.id.textExerciseNameDetail);
        textExerciseDescription = findViewById(R.id.textExerciseDescription);
        textExerciseInstructions = findViewById(R.id.textExerciseInstructions); // Инициализация TextView для инструкций
        buttonClose = findViewById(R.id.buttonClose);

        // Получение объекта Exercise, переданного с Intent
        Exercise exercise = (Exercise) getIntent().getSerializableExtra("exercise");

        // Если объект Exercise не равен null, устанавливаем данные на экран
        if (exercise != null) {
            imageExerciseDetail.setImageResource(exercise.getImageResource()); // Установка изображения упражнения
            textExerciseNameDetail.setText(exercise.getName()); // Установка названия упражнения
            textExerciseDescription.setText(exercise.getDescription()); // Установка описания упражнения

            // Установка инструкций по выполнению упражнения
            textExerciseInstructions.setText(exercise.getInstructions());
        }

        // Назначение действия для кнопки закрытия экрана
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Закрытие текущей активности
            }
        });
    }
}
