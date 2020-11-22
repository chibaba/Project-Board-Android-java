package com.example.projectboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.projectboard.Adapter.TodoAdapter;
import com.example.projectboard.Model.TodoBModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView  tasksRecyclerView;
    private TodoAdapter   tasksAdapter;

    private List<TodoBModel>  taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);

        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        tasksAdapter = new TodoAdapter(this);

        tasksRecyclerView.setAdapter(tasksAdapter);

        TodoBModel task = new TodoBModel();
        task.setTask("This is a sample project");
        task.setStatus(0);
        task.setId(1);

        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);


    }
}