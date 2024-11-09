package com.example.myhealthapp;

import java.io.Serializable;

public class Exercise implements Serializable {
    private String name;
    private int imageResource;
    private String description;
    private String instructions; // Новое поле для инструкций

    public Exercise(String name, int imageResource, String description, String instructions) {
        this.name = name;
        this.imageResource = imageResource;
        this.description = description;
        this.instructions = instructions; // Инициализация нового поля
    }

    public String getName() {
        return name;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getDescription() {
        return description;
    }

    public String getInstructions() {
        return instructions; // Геттер для инструкций
    }
}
