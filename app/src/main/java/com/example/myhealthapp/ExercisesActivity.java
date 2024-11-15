package com.example.myhealthapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ExercisesActivity extends BaseActivity {
    private ProgressBar progressBar;  // Прогресс-бар для отслеживания прокрутки
    private RecyclerView recyclerView;  // Список для отображения упражнений
    private ExercisesAdapter adapter;  // Адаптер для списка упражнений
    private List<Exercise> exerciseList;  // Список упражнений

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        // Инициализируем тулбар и задаем заголовок
        initToolbar();
        setToolbarTitle("Перечень упражнений");

        progressBar = findViewById(R.id.progressBar);  // Инициализация прогресс-бара
        recyclerView = findViewById(R.id.recyclerView);  // Инициализация RecyclerView

        // Инициализация списка упражнений и их добавление
        exerciseList = new ArrayList<>();
        exerciseList.add(new Exercise("Отжимания", R.drawable.push_up,
                "Упражнение направлено на развитие мышц груди, плеч и трицепсов.",
                "1. Примите упор лёжа: тело выпрямлено, упор на носки и ладони, расположенные чуть шире плеч.\n2. Согните руки в локтях, опуская корпус вниз до касания грудью пола.\n3. Разогните руки, возвращаясь в исходное положение."));

        exerciseList.add(new Exercise("Приседания", R.drawable.squat,
                "Упражнение для укрепления мышц ног и ягодиц.",
                "1. Встаньте прямо, ноги на ширине плеч, носки слегка развёрнуты в стороны.\n2. Согните колени, будто вы садитесь на стул, следя за тем, чтобы колени не выходили за носки.\n3. Вернитесь в исходное положение."));

        exerciseList.add(new Exercise("Планка", R.drawable.plank,
                "Статическое упражнение для укрепления мышц кора.",
                "1. Примите упор лёжа, опираясь на предплечья и носки. Ваше тело должно образовывать прямую линию от макушки до пяток.\n2. Задержитесь в этом положении."));

        exerciseList.add(new Exercise("Выпады", R.drawable.lunge,
                "Упражнение для развития мышц ног и ягодиц. Выполняется попеременно каждой ногой, с глубоким опусканием тела.",
                "1. Встаньте прямо. Сделайте широкий шаг вперёд одной ногой. Опустите тело, сгибая оба колена под прямым углом. Заднее колено почти касается пола.\n2. Вернитесь в исходное положение и повторите для другой ноги."));

        exerciseList.add(new Exercise("Бёрпи", R.drawable.burpee,
                "Комплексное упражнение, задействующее все основные группы мышц. Сочетает в себе элементы приседа, отжимания и прыжка.",
                "1. Из положения стоя опуститесь в присед, затем перейдите в упор лёжа. Выполните отжимание. Вернитесь в присед и прыжком выпрямитесь вверх."));

        exerciseList.add(new Exercise("Прыжки «звёздочка»", R.drawable.jumping_jack,
                "Аэробное упражнение, направленное на укрепление сердечно-сосудистой системы и развитие координации движений.",
                "1. Встаньте прямо, ноги вместе, руки опущены вдоль тела. Прыжком разведите ноги в стороны и поднимите руки вверх над головой. Вернитесь в исходное положение."));

        exerciseList.add(new Exercise("Скалолаз", R.drawable.mountain_climber,
                "Динамическое упражнение для укрепления мышц кора и развития кардиореспираторной системы.",
                "1. Примите упор лёжа. Поочерёдно подтягивайте колени к груди, стараясь держать спину ровной."));

        exerciseList.add(new Exercise("Отжимания на коленях", R.drawable.knee_pushup,
                "Упрощённый вариант отжиманий, подходящий для начинающих.",
                "1. Примите упор лёжа, но вместо того чтобы опираться на носки, опуститесь на колени. Выполняйте отжимания, сохраняя тело прямым."));

        exerciseList.add(new Exercise("Супермен", R.drawable.superman,
                "Упражнение для укрепления мышц спины и ягодиц.",
                "1. Лягте на живот, вытянув руки и ноги. Одновременно поднимите руки и ноги вверх, задержитесь в этом положении."));

        exerciseList.add(new Exercise("Подтягивания", R.drawable.pull_up,
                "Упражнение для развития мышц спины, бицепсов и предплечий.",
                "1. Вися на перекладине хватом сверху, подтяните тело вверх до касания перекладины подбородком. Медленно опуститесь вниз."));

        exerciseList.add(new Exercise("Подъёмы ног в висе", R.drawable.hanging_leg_raise,
                "Упражнение для укрепления мышц пресса.",
                "1. Вися на перекладине, поднимите ноги, согнутые в коленях, к груди. Опустите ноги, не касаясь пола."));

        exerciseList.add(new Exercise("Отжимания на брусьях", R.drawable.dips,
                "Упражнение для развития мышц трицепсов и груди.",
                "1. Удерживаясь на брусьях, медленно опуститесь вниз, сгибая руки в локтях, пока плечи не будут параллельны полу.\n2. Поднимайтесь обратно, разгибая руки."));

        exerciseList.add(new Exercise("Подъемы на бицепс", R.drawable.bicep_curl,
                "Упражнение для изолированной работы мышц бицепса.",
                "1. Встаньте прямо, держа гантели в руках. Согните руки в локтях, поднимая гантели к плечам. Медленно опустите гантели."));

        exerciseList.add(new Exercise("Взрывные приседания", R.drawable.explosive_squat,
                "Плиометрическое упражнение для развития взрывной силы ног.",
                "1. Выполните обычное приседание, а затем мощным движением выпрыгните вверх. Приземлитесь мягко, переходя в следующее повторение."));

        // Инициализация адаптера и настройка его на список упражнений
        adapter = new ExercisesAdapter(exerciseList, new ExercisesAdapter.OnItemClickListener() {
            @Override
            // Метод обработки клика на элемент списка
            public void onItemClick(Exercise exercise) {
                Intent intent = new Intent(ExercisesActivity.this, ExerciseDetailActivity.class);  // Создаем интент для перехода
                intent.putExtra("exercise", exercise);  // Передаем данные о выбранном упражнении
                startActivity(intent);  // Запускаем активность
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));  // Настройка LinearLayoutManager для списка
        recyclerView.setAdapter(adapter);  // Устанавливаем адаптер

        // Слушатель для прокрутки RecyclerView
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            // Метод, который вызывается при прокрутке списка
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                updateProgressBar();  // Обновляем прогресс-бар при прокрутке
            }
        });
    }

    // Метод для обновления прогресс-бара
    private void updateProgressBar() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int totalItemCount = layoutManager.getItemCount();  // Общее количество элементов в списке
        int lastVisibleItem = layoutManager.findLastVisibleItemPosition();  // Позиция последнего видимого элемента

        // Вычисление прогресса и обновление прогресс-бара
        int progress = (int) (((float) lastVisibleItem / totalItemCount) * 100);
        progressBar.setProgress(progress);  // Устанавливаем новый прогресс
    }
}
