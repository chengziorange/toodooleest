package top.orange233.toodooleest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Todos> todosList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTodos();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        TodosAdapter adapter = new TodosAdapter(todosList);
        recyclerView.setAdapter(adapter);
    }

    private void initTodos() {
        for (int i = 1; i < 10; i++) {
            Todos aTodo = new Todos("第" + i + "个假装是任务的东西");
            todosList.add(aTodo);

        }
    }
}
