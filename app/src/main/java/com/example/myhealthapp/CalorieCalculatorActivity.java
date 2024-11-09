package com.example.myhealthapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class CalorieCalculatorActivity extends BaseActivity {
    private EditText inputAge, inputHeight, inputWeight;
    private TextView errorAge, errorHeight, errorWeight;
    private RadioGroup radioGroupGender;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_calculator);

        inputAge = findViewById(R.id.inputAge);
        inputHeight = findViewById(R.id.inputHeight);
        inputWeight = findViewById(R.id.inputWeight);
        initToolbar();
        setToolbarTitle("Расчет нормы калорий");

        errorAge = findViewById(R.id.errorAge);
        errorHeight = findViewById(R.id.errorHeight);
        errorWeight = findViewById(R.id.errorWeight);

        radioGroupGender = findViewById(R.id.radioGroupGender);

        Button btnCalculate = findViewById(R.id.btnCalculate);
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCalories();
            }
        });
    }

    private void calculateCalories() {
        String ageStr = inputAge.getText().toString().trim();
        String heightStr = inputHeight.getText().toString().trim();
        String weightStr = inputWeight.getText().toString().trim();

        boolean isValid = true;

        if (ageStr.isEmpty()) {
            errorAge.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            errorAge.setVisibility(View.GONE);
        }

        if (heightStr.isEmpty()) {
            errorHeight.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            errorHeight.setVisibility(View.GONE);
        }

        if (weightStr.isEmpty()) {
            errorWeight.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            errorWeight.setVisibility(View.GONE);
        }

        if (!isValid) {
            Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        int age;
        int height;
        int weight;
        try {
            age = Integer.parseInt(ageStr);
            height = Integer.parseInt(heightStr);
            weight = Integer.parseInt(weightStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Пожалуйста, используйте корректные значения", Toast.LENGTH_SHORT).show();
            return;
        }

        // Perform calorie calculation (dummy values used here)
        double bmr;
        if (radioGroupGender.getCheckedRadioButtonId() == R.id.radioMale) {
            bmr = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        } else {
            bmr = 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
        }

        // Assuming daily calorie needs to maintain weight (example value)
        double dailyCalories = bmr * 1.2;

        // Display results
        String result = String.format("Норма калорий: %.2f калорий", dailyCalories);
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }
}