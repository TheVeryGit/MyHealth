package com.example.myhealthapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ExerciseDetailActivity extends AppCompatActivity {
    private ImageView imageExerciseDetail;
    private TextView textExerciseNameDetail;
    private TextView textExerciseDescription;
    private TextView textExerciseInstructions; // Новый TextView для инструкций
    private Button buttonClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);

        imageExerciseDetail = findViewById(R.id.imageExerciseDetail);
        textExerciseNameDetail = findViewById(R.id.textExerciseNameDetail);
        textExerciseDescription = findViewById(R.id.textExerciseDescription);
        textExerciseInstructions = findViewById(R.id.textExerciseInstructions); // Инициализация нового TextView
        buttonClose = findViewById(R.id.buttonClose);

        Exercise exercise = (Exercise) getIntent().getSerializableExtra("exercise");

        if (exercise != null) {
            imageExerciseDetail.setImageResource(exercise.getImageResource());
            textExerciseNameDetail.setText(exercise.getName());
            textExerciseDescription.setText(exercise.getDescription());

            // Установка инструкций по выполнению упражнения
            textExerciseInstructions.setText(exercise.getInstructions());
        }

        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
