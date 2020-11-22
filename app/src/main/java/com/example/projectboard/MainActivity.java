package com.example.projectboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.projectboard.Adapter.TodoAdapter;
import com.example.projectboard.Model.TodoBModel;
import com.example.projectboard.Utils.DatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements  DialogCloseListerner{

    private RecyclerView  tasksRecyclerView;
    private TodoAdapter   tasksAdapter;
    private FloatingActionButton fab;

    private List<TodoBModel>  taskList;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        db = new DatabaseHandler(this);
        db.openDataBase();

        taskList = new ArrayList<>();

        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);

        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        tasksAdapter = new TodoAdapter(db, this);

        tasksRecyclerView.setAdapter(tasksAdapter);

        fab = findViewById(R.id.fab);

        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);



        TodoBModel task = new TodoBModel();
        task.setTask("This is a sample project");
        task.setStatus(0);
        task.setId(1);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });

        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);

        tasksAdapter.setTasks(taskList);

    }
    @Override
    public void handleDialogClose(DialogInterface dialog) {
        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();
    }
}