package com.example.myhealthapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;



import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SleepCalculatorActivity extends BaseActivity {

    private RadioGroup radioGroup;
    private RadioButton radioCalculateWakeup;
    private RadioButton radioCalculateBedtime;
    private EditText timeInput;
    private Button calculateButton;

    private TextView poorSleepTime1;
    private TextView poorSleepTime2;
    private TextView normalSleepTime1;
    private TextView normalSleepTime2;
    private TextView excellentSleepTime1;
    private TextView excellentSleepTime2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_calculator);

        radioGroup = findViewById(R.id.radioGroup);
        radioCalculateWakeup = findViewById(R.id.radio_calculate_wakeup);
        radioCalculateBedtime = findViewById(R.id.radio_calculate_bedtime);
        timeInput = findViewById(R.id.timeInput);
        calculateButton = findViewById(R.id.calculateButton);
        initToolbar();
        setToolbarTitle("Рассчет качества сна");

        poorSleepTime1 = findViewById(R.id.poorSleepTime1);
        poorSleepTime2 = findViewById(R.id.poorSleepTime2);
        normalSleepTime1 = findViewById(R.id.normalSleepTime1);
        normalSleepTime2 = findViewById(R.id.normalSleepTime2);
        excellentSleepTime1 = findViewById(R.id.excellentSleepTime1);
        excellentSleepTime2 = findViewById(R.id.excellentSleepTime2);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radio_calculate_wakeup) {
                timeInput.setHint("Введите время засыпания");
            } else if (checkedId == R.id.radio_calculate_bedtime) {
                timeInput.setHint("Введите время пробуждения");
            }
        });

        calculateButton.setOnClickListener(v -> calculateTime());
    }

    private void calculateTime() {
        String time = timeInput.getText().toString();
        if (TextUtils.isEmpty(time)) {
            Toast.makeText(this, "Пожалуйста, заполните поле ввода", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidTimeFormat(time)) {
            Toast.makeText(this, "Пожалуйста, введите время в формате ЧЧ:ММ", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isCalculateWakeup = radioCalculateWakeup.isChecked();
        calculateSleepCycles(time, isCalculateWakeup);
    }

    private boolean isValidTimeFormat(String time) {
        return time.matches("^([01]?\\d|2[0-3]):[0-5]\\d$");
    }

    private void calculateSleepCycles(String time, boolean isCalculateWakeup) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        try {
            Date date = sdf.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            String[] poorSleepTimes = new String[2];
            String[] normalSleepTimes = new String[2];
            String[] excellentSleepTimes = new String[2];

            int[] sleepOffsets = {1, 2, 3, 4, 5, 6}; // Множитель 1.5 часа для каждого цикла сна

            for (int i = 0; i < sleepOffsets.length; i++) {
                int offset = sleepOffsets[i] * 90; // 1.5 часа в минутах
                if (isCalculateWakeup) {
                    calendar.add(Calendar.MINUTE, offset);
                } else {
                    calendar.add(Calendar.MINUTE, -offset);
                }

                String calculatedTime = sdf.format(calendar.getTime());
                if (i < 2) {
                    poorSleepTimes[i] = calculatedTime;
                } else if (i < 4) {
                    normalSleepTimes[i - 2] = calculatedTime;
                } else {
                    excellentSleepTimes[i - 4] = calculatedTime;
                }
                calendar.setTime(date); // Сбросить календарь на исходное время
            }

            // Установка результатов в TextView
            poorSleepTime1.setText(poorSleepTimes[0]);
            poorSleepTime2.setText(poorSleepTimes[1]);
            normalSleepTime1.setText(normalSleepTimes[0]);
            normalSleepTime2.setText(normalSleepTimes[1]);
            excellentSleepTime1.setText(excellentSleepTimes[0]);
            excellentSleepTime2.setText(excellentSleepTimes[1]);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
