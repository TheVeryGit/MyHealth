package com.example.myhealthapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;



public class CalorieBurningActivity extends BaseActivity {
    private EditText inputWeight, inputDistance, inputTimeSwimming;
    private TextView errorWeight, errorDistance, errorTimeSwimming;
    private RadioGroup radioGroupActivityType, radioGroupWalkingType, radioGroupSwimmingStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_burning);

        // Инициализируем элементы интерфейса
        inputWeight = findViewById(R.id.inputWeight);
        inputDistance = findViewById(R.id.inputDistance);
        inputTimeSwimming = findViewById(R.id.inputTimeSwimming);

        errorWeight = findViewById(R.id.errorWeight);
        errorDistance = findViewById(R.id.errorDistance);
        errorTimeSwimming = findViewById(R.id.errorTimeSwimming);

        radioGroupActivityType = findViewById(R.id.radioGroupActivityType);
        radioGroupWalkingType = findViewById(R.id.radioGroupWalkingType);
        radioGroupSwimmingStyle = findViewById(R.id.radioGroupSwimmingStyle);


        initToolbar();
        setToolbarTitle("Расчет сжигания калорий");

        // Получаем ссылки на поля для ввода в зависимости от выбранной активности
        final View walkingFields = findViewById(R.id.walkingFields);
        final View swimmingFields = findViewById(R.id.swimmingFields);

        radioGroupActivityType.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioWalking) {
                walkingFields.setVisibility(View.VISIBLE); //Если выбрана ходьба как активность, то прячем поля ввода для плавания
                swimmingFields.setVisibility(View.GONE);
            } else {
                walkingFields.setVisibility(View.GONE); //Если выбрано плавание как активность, то прячем поля ввода ходьбы
                swimmingFields.setVisibility(View.VISIBLE);
            }
        });

        // Инициализируем кнопку рассчета
        Button btnCalculateBurn = findViewById(R.id.btnCalculateBurn);
        btnCalculateBurn.setOnClickListener(v -> calculateCaloriesBurned());
    }

    //Метод нахождения соженных каллорий
    private void calculateCaloriesBurned() {
        String weightStr = inputWeight.getText().toString().trim();
        boolean isValid = true;

        //Проверка на пустое поле ввода
        if (weightStr.isEmpty()) {
            errorWeight.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            errorWeight.setVisibility(View.GONE);
        }

        //Проверка на корректность введенного веса
        int weight;
        try {
            weight = Integer.parseInt(weightStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Пожалуйста, используйте корректные значения", Toast.LENGTH_SHORT).show();//Вывод предупреждения
            return;
        }

        int checkedActivityId = radioGroupActivityType.getCheckedRadioButtonId();
        double caloriesBurned = 0;
        // Если пользователь выбрал ходьбу
        if (checkedActivityId == R.id.radioWalking) {
            String distanceStr = inputDistance.getText().toString().trim();
            //Проверка на пустое поле ввода
            if (distanceStr.isEmpty()) {
                errorDistance.setVisibility(View.VISIBLE);
                isValid = false;
            } else {
                errorDistance.setVisibility(View.GONE);
            }
            //Вывод предупреждения
            if (!isValid) {
                Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
                return;
            }

            double distance;
            //Проверка на корректность введенной дистанции
            try {

                distance = Double.parseDouble(distanceStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Пожалуйста, используйте корректные значения", Toast.LENGTH_SHORT).show();//Вывод предупреждения
                return;
            }

            //Различные множители в зависимости от выбранной скорости ходьбы
            int checkedWalkingTypeId = radioGroupWalkingType.getCheckedRadioButtonId();
            if (checkedWalkingTypeId == R.id.radioSlow) {
                caloriesBurned = weight * distance * 0.5; // Расчет для медленной ходьбы
            } else if (checkedWalkingTypeId == R.id.radioFast) {
                caloriesBurned = weight * distance * 0.9; // Расчет для быстрой ходьбы
            } else {
                caloriesBurned = weight * distance * 0.7; // Расчет для обычной ходьбы
            }

        } else { // Если выбрано плавание
            String timeStr = inputTimeSwimming.getText().toString().trim();
            if (timeStr.isEmpty()) {
                errorTimeSwimming.setVisibility(View.VISIBLE);
                isValid = false;
            } else {
                errorTimeSwimming.setVisibility(View.GONE);
            }

            if (!isValid) {
                Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();//Вывод предупреждения
                return;
            }

            double time;
            try {
                time = Double.parseDouble(timeStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Пожалуйста, используйте корректные значения", Toast.LENGTH_SHORT).show();//Вывод предупреждения
                return;
            }

            int checkedSwimmingStyleId = radioGroupSwimmingStyle.getCheckedRadioButtonId();
            double met;
            //Различные множители met зависящие от выбранной интенсивности плавания
            if (checkedSwimmingStyleId == R.id.radioSwimmingSlow) {
                met = 4.2; // Медленное плавание
            } else if (checkedSwimmingStyleId == R.id.radioSwimmingFast) {
                met = 8.0; // Быстрое плавание
            } else {
                met = 6.5; // Обычное плавание
            }

            // Формула для расчета сожженных калорий при плавании
            caloriesBurned = met * weight * time;
        }

        String result = String.format("Сожжено калорий: %.2f", caloriesBurned);
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }
}
