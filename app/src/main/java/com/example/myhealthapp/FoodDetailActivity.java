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

    private ImageView detailFoodImage;
    private TextView detailFoodName;
    private TextView detailFoodNutrition;
    private EditText foodWeightInput;
    private Button closeButton;

    private Food food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        detailFoodImage = findViewById(R.id.detailFoodImage);
        detailFoodName = findViewById(R.id.detailFoodName);
        detailFoodNutrition = findViewById(R.id.detailFoodNutrition);
        foodWeightInput = findViewById(R.id.foodWeightInput);
        closeButton = findViewById(R.id.closeButton);

        food = (Food) getIntent().getSerializableExtra("food");

        detailFoodImage.setImageResource(food.getImageResId());
        detailFoodName.setText(food.getName());
        updateNutritionInfo(100);

        foodWeightInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int weight = Integer.parseInt(s.toString());
                    updateNutritionInfo(weight);
                } catch (NumberFormatException e) {
                    if (!s.toString().isEmpty()) {
                        Toast.makeText(FoodDetailActivity.this, "Введите правильный вес", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        closeButton.setOnClickListener(v -> finish());
    }

    private void updateNutritionInfo(int weight) {
        int calories = (food.getCalories() * weight) / 100;
        int protein = (food.getProtein() * weight) / 100;
        int fat = (food.getFat() * weight) / 100;
        int carbs = (food.getCarbs() * weight) / 100;

        String nutritionInfo = "Калории: " + calories + " ккал\n" +
                "Белки: " + protein + " г\n" +
                "Жиры: " + fat + " г\n" +
                "Углеводы: " + carbs + " г";
        detailFoodNutrition.setText(nutritionInfo);
    }
}

