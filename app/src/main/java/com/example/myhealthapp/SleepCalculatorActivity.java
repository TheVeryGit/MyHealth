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

    private RadioGroup radioGroup;  // Группа радиокнопок для выбора типа расчета
    private RadioButton radioCalculateWakeup;  // Радиокнопка для расчета времени пробуждения
    private RadioButton radioCalculateBedtime;  // Радиокнопка для расчета времени засыпания
    private EditText timeInput;  // Поле ввода времени
    private Button calculateButton;  // Кнопка для выполнения расчета

    private TextView poorSleepTime1, poorSleepTime2;  // Время для плохого сна
    private TextView normalSleepTime1, normalSleepTime2;  // Время для нормального сна
    private TextView excellentSleepTime1, excellentSleepTime2;  // Время для отличного сна

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_calculator);


        initToolbar();
        setToolbarTitle("Расчет качества сна");
        radioGroup = findViewById(R.id.radioGroup);  // Инициализация группы радиокнопок
        radioCalculateWakeup = findViewById(R.id.radio_calculate_wakeup);  // Радиокнопка для пробуждения
        radioCalculateBedtime = findViewById(R.id.radio_calculate_bedtime);  // Радиокнопка для времени засыпания
        timeInput = findViewById(R.id.timeInput);  // Поле ввода времени
        calculateButton = findViewById(R.id.calculateButton);  // Кнопка расчета

        poorSleepTime1 = findViewById(R.id.poorSleepTime1);  // Инициализация для плохого сна
        poorSleepTime2 = findViewById(R.id.poorSleepTime2);  // Инициализация для плохого сна
        normalSleepTime1 = findViewById(R.id.normalSleepTime1);  // Инициализация для нормального сна
        normalSleepTime2 = findViewById(R.id.normalSleepTime2);  // Инициализация для нормального сна
        excellentSleepTime1 = findViewById(R.id.excellentSleepTime1);  // Инициализация для отличного сна
        excellentSleepTime2 = findViewById(R.id.excellentSleepTime2);  // Инициализация для отличного сна

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {  // Обработчик изменений в радиокнопках
            if (checkedId == R.id.radio_calculate_wakeup) {
                timeInput.setHint("Введите время засыпания");  // Подсказка для расчета времени засыпания
            } else if (checkedId == R.id.radio_calculate_bedtime) {
                timeInput.setHint("Введите время пробуждения");  // Подсказка для расчета времени пробуждения
            }
        });

        calculateButton.setOnClickListener(v -> calculateTime());  // Обработчик нажатия на кнопку расчета
    }

    // Метод для выполнения расчета времени
    private void calculateTime() {
        String time = timeInput.getText().toString();  // Получаем введенное время
        if (TextUtils.isEmpty(time)) {
            Toast.makeText(this, "Пожалуйста, заполните поле ввода", Toast.LENGTH_SHORT).show();  // Проверка на пустое поле
            return;
        }

        if (!isValidTimeFormat(time)) {
            Toast.makeText(this, "Пожалуйста, введите время в формате ЧЧ:ММ", Toast.LENGTH_SHORT).show();  // Проверка формата времени
            return;
        }

        boolean isCalculateWakeup = radioCalculateWakeup.isChecked();  // Проверяем, выбран ли расчет для времени пробуждения
        calculateSleepCycles(time, isCalculateWakeup);  // Выполняем расчет циклов сна
    }

    // Проверка правильности формата времени
    private boolean isValidTimeFormat(String time) {
        return time.matches("^([01]?\\d|2[0-3]):[0-5]\\d$");  // Регулярное выражение для проверки времени
    }

    // Метод для расчета времени сна в зависимости от типа расчета
    private void calculateSleepCycles(String time, boolean isCalculateWakeup) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        try {
            Date date = sdf.parse(time);  // Парсим введенное время
            Calendar calendar = Calendar.getInstance();  // Получаем текущий календарь
            calendar.setTime(date);  // Устанавливаем начальное время

            String[] poorSleepTimes = new String[2], normalSleepTimes = new String[2], excellentSleepTimes = new String[2];  // Массивы для разных типов сна

            int[] sleepOffsets = {1, 2, 3, 4, 5, 6};  // Смещения для циклов сна

            for (int i = 0; i < sleepOffsets.length; i++) {  // Проходим по циклам
                int offset = sleepOffsets[i] * 90;  // Смещение времени на 90 минут
                if (isCalculateWakeup) {
                    calendar.add(Calendar.MINUTE, offset);  // Прибавляем смещение для расчета пробуждения
                } else {
                    calendar.add(Calendar.MINUTE, -offset);  // Отнимаем смещение для расчета засыпания
                }

                String calculatedTime = sdf.format(calendar.getTime());  // Рассчитываем новое время
                if (i < 2) {
                    poorSleepTimes[i] = calculatedTime;  // Сохраняем для плохого сна
                } else if (i < 4) {
                    normalSleepTimes[i - 2] = calculatedTime;  // Сохраняем для нормального сна
                } else {
                    excellentSleepTimes[i - 4] = calculatedTime;  // Сохраняем для отличного сна
                }
                calendar.setTime(date);  // Сбрасываем календарь
            }

            // Устанавливаем рассчитанные времена в соответствующие поля
            poorSleepTime1.setText(poorSleepTimes[0]);
            poorSleepTime2.setText(poorSleepTimes[1]);
            normalSleepTime1.setText(normalSleepTimes[0]);
            normalSleepTime2.setText(normalSleepTimes[1]);
            excellentSleepTime1.setText(excellentSleepTimes[0]);
            excellentSleepTime2.setText(excellentSleepTimes[1]);

        } catch (Exception e) {
            e.printStackTrace();  // Логируем ошибки
        }
    }
}
