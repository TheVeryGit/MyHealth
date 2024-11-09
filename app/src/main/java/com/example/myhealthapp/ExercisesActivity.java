package com.example.myhealthapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ExercisesActivity extends BaseActivity {
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private ExercisesAdapter adapter;
    private List<Exercise> exerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        // Инициализируем тулбар и задаем заголовок
        initToolbar();
        setToolbarTitle("Перечень упражнений");

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);

        // Инициализация упражнений и адаптера
        exerciseList = new ArrayList<>();
        exerciseList.add(new Exercise("Отжимания", R.drawable.push_up,
                "Упражнение, направленное на развитие мышц груди, плеч и трицепсов.",
                "1. Примите упор лежа: тело выпрямлено, упор на носки и ладони, расположенные чуть шире плеч.\n2. Согните руки в локтях, опуская корпус вниз до касания грудью пола.\n3. Разгибайте руки, возвращаясь в исходное положение."));
        exerciseList.add(new Exercise("Приседания", R.drawable.squat,
                "Упражнение для укрепления мышц ног и ягодиц.",
                "1. Встаньте прямо, ноги на ширине плеч, носки слегка развернуты в стороны.\n2. Согните колени, словно садясь на стул, следя за тем, чтобы колени не выходили за носки.\n3. Вернитесь в исходное положение."));
        exerciseList.add(new Exercise("Планка", R.drawable.plank,
                "Статическое упражнение для укрепления мышц кора.",
                "1. Примите упор лежа, опираясь на предплечья и носки. Тело должно образовывать прямую линию от макушки до пяток.\n2. Задержитесь в этом положении."));
        exerciseList.add(new Exercise("Выпады", R.drawable.lunge,
                "Упражнение для развития мышц ног и ягодиц. Выполняется попеременно каждой ногой, с глубоким опусканием тела.",
                "1. Встаньте прямо. Сделайте широкий шаг вперед одной ногой. Опустите тело, сгибая оба колена под прямым углом. Заднее колено почти касается пола.\n2. Вернитесь в исходное положение и повторите для другой ноги."));
        exerciseList.add(new Exercise("Берпи", R.drawable.burpee,
                "Комплексное упражнение, задействующее все основные группы мышц. Сочетает в себе элементы приседания, отжимания и прыжка.",
                "1. Из положения стоя опуститесь в присед, затем перейдите в упор лежа. Выполните отжимание. Вернитесь в присед и прыжком выпрямитесь вверх."));
        exerciseList.add(new Exercise("Прыжки 'звёздочкой'", R.drawable.jumping_jack,
                "Аэробное упражнение, направленное на укрепеление сердечно-сосудистой системы и развитие координации движений.",
                "1. Встаньте прямо, ноги вместе, руки опущены вдоль тела. Прыжком разведите ноги в стороны и поднимите руки вверх над головой. Вернитесь в исходное положение."));
        exerciseList.add(new Exercise("Скалолаз", R.drawable.mountain_climber,
                "Динамическое упражнение для укрепления мышц кора и развития кардиореспираторной системы.",
                "1. Примите упор лежа. Поочередно подтягивайте колени к груди, стараясь держать спину ровной."));
        exerciseList.add(new Exercise("Отжимания на коленях", R.drawable.knee_pushup,
                "Упрощенный вариант отжиманий, подходящий для начинающих.",
                "1. Примите упор лежа, но вместо того чтобы опираться на носки, опуститесь на колени. Выполняйте отжимания, сохраняя тело прямым."));
        exerciseList.add(new Exercise("Супермен", R.drawable.superman,
                "Упражнение для укрепления мышц спины и ягодиц.",
                "1. Лягте на живот, вытянув руки и ноги. Одновременно поднимите руки и ноги вверх, задержитесь в этом положении."));
        exerciseList.add(new Exercise("Подтягивания", R.drawable.pull_up,
                "Упражнение для развития мышц спины, бицепсов и предплечий.",
                "1. Вися на перекладине хватом сверху, подтяните тело вверх до касания перекладины подбородком. Медленно опуститесь вниз."));
        exerciseList.add(new Exercise("Подъемы ног в висе", R.drawable.hanging_leg_raise,
                "Упражнение для укрепления мышц пресса.",
                "1. Вися на перекладине, поднимите ноги, согнутые в коленях, к груди. Опустите ноги, не касаясь пола."));
        exerciseList.add(new Exercise("Отжимания на брусьях", R.drawable.dips,
                "Упражнение для развития мышц трицепсов и груди.",
                "1. Упритесь руками в брусья, ноги выпрямлены. Опустите тело вниз, сгибая руки в локтях. Вернитесь в исходное положение."));
        exerciseList.add(new Exercise("Подъемы на бицепс", R.drawable.bicep_curl,
                "Упражнение для изолированной работы мышц бицепса.",
                "1. Встаньте прямо, держа гантели в руках. Согните руки в локтях, поднимая гантели к плечам. Медленно опустите гантели."));
        exerciseList.add(new Exercise("Взрывные приседания", R.drawable.explosive_squat,
                "Плиометрическое упражнение для развития силы и мощности ног.",
                "1. Выполните обычное приседание, а затем мощным движением выпрыгните вверх. Приземлитесь мягко, переходя в следующее повторение."));

        adapter = new ExercisesAdapter(exerciseList, new ExercisesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Exercise exercise) {
                Intent intent = new Intent(ExercisesActivity.this, ExerciseDetailActivity.class);
                intent.putExtra("exercise", exercise);
                startActivity(intent);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                updateProgressBar();
            }
        });
    }

    private void updateProgressBar() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int totalItemCount = layoutManager.getItemCount();
        int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

        int progress = (int) (((float) lastVisibleItem / totalItemCount) * 100);
        progressBar.setProgress(progress);
    }
}
