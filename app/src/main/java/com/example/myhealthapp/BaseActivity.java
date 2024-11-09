package com.example.myhealthapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Метод для инициализации тулбара с обработчиком кнопки назад
    protected void initToolbar() {
        // Находим кнопку и задаем обработчик
        if (findViewById(R.id.btnBack) != null) {
            findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Переход на MainActivity
                    Intent intent = new Intent(BaseActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    // Метод для установки заголовка тулбара
    public void setToolbarTitle(String title) {
        if (findViewById(R.id.toolbarTitle) != null) {
            ((TextView) findViewById(R.id.toolbarTitle)).setText(title);
        }
    }
}
