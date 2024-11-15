package com.example.myhealthapp;
import java.io.Serializable;

public class Food implements Serializable {
    private String name; // Название продукта
    private int imageResId; // ID ресурса изображения продукта
    private int calories; // Калорийность продукта
    private int protein; // Количество белков в продукте
    private int fat; // Количество жиров в продукте
    private int carbs; // Количество углеводов в продукте

    // Конструктор, инициализирует все поля продукта
    public Food(String name, int imageResId, int calories, int protein, int fat, int carbs) {
        this.name = name;
        this.imageResId = imageResId;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
    }

    // Геттер для получения названия продукта
    public String getName() {
        return name;
    }

    // Геттер для получения ID изображения продукта
    public int getImageResId() {
        return imageResId;
    }

    // Геттер для получения количества калорий продукта
    public int getCalories() {
        return calories;
    }

    // Геттер для получения количества белков в продукте
    public int getProtein() {
        return protein;
    }

    // Геттер для получения количества жиров в продукте
    public int getFat() {
        return fat;
    }

    // Геттер для получения количества углеводов в продукте
    public int getCarbs() {
        return carbs;
    }
}
