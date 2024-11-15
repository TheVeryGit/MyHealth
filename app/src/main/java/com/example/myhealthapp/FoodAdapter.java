package com.example.myhealthapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<Food> foodList; // Список продуктов
    private OnItemClickListener onItemClickListener; // Слушатель для обработки кликов по элементам

    // Интерфейс для обработки кликов по элементам списка продуктов
    public interface OnItemClickListener {
        void onItemClick(Food food); // Метод, вызываемый при клике на продукт
    }

    // Конструктор адаптера
    public FoodAdapter(List<Food> foodList, OnItemClickListener onItemClickListener) {
        this.foodList = foodList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Создание и возврат нового ViewHolder для продукта
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        // Привязка данных из списка к элементам ViewHolder
        Food food = foodList.get(position);
        holder.foodName.setText(food.getName()); // Установка имени продукта
        holder.foodImage.setImageResource(food.getImageResId()); // Установка изображения продукта
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(food)); // Обработка клика
    }

    @Override
    public int getItemCount() {
        // Возвращает количество элементов в списке
        return foodList.size();
    }

    // Внутренний класс ViewHolder для отображения элементов списка
    static class FoodViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImage; // Изображение продукта
        TextView foodName; // Название продукта

        FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.foodImage); // Инициализация ImageView
            foodName = itemView.findViewById(R.id.foodName); // Инициализация TextView
        }
    }
}
