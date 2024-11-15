package com.example.myhealthapp;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ExercisesAdapter extends RecyclerView.Adapter<ExercisesAdapter.ExerciseViewHolder> {
    private List<Exercise> exercises; // Список упражнений для адаптера
    private OnItemClickListener listener; // Слушатель для обработки кликов по элементам списка

    // Интерфейс для обработки кликов на элементах списка
    public interface OnItemClickListener {
        void onItemClick(Exercise exercise); // Метод для обработки клика на упражнении
    }

    // Конструктор, инициализирует список упражнений и слушателя
    public ExercisesAdapter(List<Exercise> exercises, OnItemClickListener listener) {
        this.exercises = exercises;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Создание представления для элемента списка из XML-шаблона
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        // Привязка данных упражнения к ViewHolder на текущей позиции
        Exercise exercise = exercises.get(position);
        holder.bind(exercise, listener);
    }

    @Override
    public int getItemCount() {
        return exercises.size(); // Возвращает общее количество элементов в списке
    }

    // Вложенный класс ViewHolder, представляющий один элемент в списке
    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageExercise; // Изображение упражнения
        private TextView textExerciseName; // Название упражнения

        // Конструктор, инициализирует визуальные элементы одного элемента списка
        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            imageExercise = itemView.findViewById(R.id.imageExercise);
            textExerciseName = itemView.findViewById(R.id.textExerciseName);
        }

        // Метод bind связывает данные упражнения с View элементами
        public void bind(final Exercise exercise, final OnItemClickListener listener) {
            imageExercise.setImageResource(exercise.getImageResource()); // Устанавливает изображение упражнения
            textExerciseName.setText(exercise.getName()); // Устанавливает название упражнения

            // Установка обработчика клика для текущего элемента
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(exercise); // Обработчик клика
                }
            });
        }
    }
}
