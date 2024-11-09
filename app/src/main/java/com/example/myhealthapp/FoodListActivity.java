package com.example.myhealthapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FoodListActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private FoodAdapter foodAdapter;
    private List<Food> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        initToolbar();
        setToolbarTitle("Перечень продуктов питания");

        foodList = new ArrayList<>();

// Обновленный список продуктов с нормальными названиями изображений
        foodList.add(new Food("Авокадо", R.drawable.avocado, 160, 2, 15, 9));
        foodList.add(new Food("Ананас", R.drawable.pineapple, 50, 0, 0, 13));
        foodList.add(new Food("Апельсин", R.drawable.orange, 47, 1, 0, 12));
        foodList.add(new Food("Банан", R.drawable.banana, 89, 1, 0, 23));
        foodList.add(new Food("Брокколи", R.drawable.broccoli, 34, 3, 0, 7));
        foodList.add(new Food("Виноград", R.drawable.grapes, 69, 0, 0, 18));
        foodList.add(new Food("Говядина", R.drawable.beef, 250, 26, 15, 0));
        foodList.add(new Food("Гречка", R.drawable.buckwheat, 343, 13, 3, 72));
        foodList.add(new Food("Картофель", R.drawable.potato, 77, 2, 0, 17));
        foodList.add(new Food("Капуста", R.drawable.cabbage, 25, 1, 0, 6));
        foodList.add(new Food("Курица", R.drawable.chicken, 150, 20, 5, 0));
        foodList.add(new Food("Лосось", R.drawable.salmon, 208, 20, 13, 0));
        foodList.add(new Food("Малина", R.drawable.raspberry, 52, 1, 0, 12));
        foodList.add(new Food("Молоко 3.2%", R.drawable.milk, 60, 3, 3, 5));
        foodList.add(new Food("Морковь", R.drawable.carrot, 41, 1, 0, 10));
        foodList.add(new Food("Огурец", R.drawable.cucumber, 16, 0, 0, 4));
        foodList.add(new Food("Оливковое масло", R.drawable.olive_oil, 884, 0, 100, 0));
        foodList.add(new Food("Орехи грецкие", R.drawable.walnuts, 654, 15, 65, 14));
        foodList.add(new Food("Помидор", R.drawable.tomato, 18, 0, 0, 4));
        foodList.add(new Food("Рис", R.drawable.rice, 130, 2, 0, 28));
        foodList.add(new Food("Сельдерей", R.drawable.celery, 14, 1, 0, 3));
        foodList.add(new Food("Скумбрия", R.drawable.mackerel, 189, 18, 13, 0));
        foodList.add(new Food("Сыр", R.drawable.cheese, 402, 25, 33, 1));
        foodList.add(new Food("Творог", R.drawable.cottage_cheese, 98, 11, 5, 3));
        foodList.add(new Food("Треска", R.drawable.cod, 82, 18, 0, 0));
        foodList.add(new Food("Хлеб", R.drawable.bread, 266, 9, 3, 49));
        foodList.add(new Food("Чечевица", R.drawable.lentils, 116, 9, 0, 20));
        foodList.add(new Food("Яблоко", R.drawable.apple, 52, 0, 0, 14));
        foodList.add(new Food("Яйца", R.drawable.eggs, 155, 13, 11, 1));


        foodAdapter = new FoodAdapter(foodList, this::openFoodDetail);
        recyclerView.setAdapter(foodAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                updateProgressBar();
            }
        });
    }

    private void updateProgressBar() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        int totalItemCount = layoutManager.getItemCount();

        if (totalItemCount == 0) {
            progressBar.setProgress(0);
        } else {
            int progress = (firstVisibleItemPosition * 100) / totalItemCount;
            progressBar.setProgress(progress);
        }
    }

    private void openFoodDetail(Food food) {
        Intent intent = new Intent(this, FoodDetailActivity.class);
        intent.putExtra("food", food);
        startActivity(intent);
    }
}

