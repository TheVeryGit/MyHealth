package com.example.myhealthapp;

import java.io.Serializable;

public class Exercise implements Serializable {
    private String name; // Название упражнения
    private int imageResource; // Ресурс изображения для упражнения
    private String description; // Описание упражнения
    private String instructions; // Поле для инструкций по выполнению упражнения

    // Конструктор класса Exercise
    public Exercise(String name, int imageResource, String description, String instructions) {
        this.name = name;
        this.imageResource = imageResource;
        this.description = description;
        this.instructions = instructions; // Инициализация поля инструкций
    }

    // Геттер для названия упражнения
    public String getName() {
        return name;
    }

    // Геттер для ресурса изображения
    public int getImageResource() {
        return imageResource;
    }

    // Геттер для описания упражнения
    public String getDescription() {
        return description;
    }

    // Геттер для инструкций по выполнению упражнения
    public String getInstructions() {
        return instructions;
    }
}
