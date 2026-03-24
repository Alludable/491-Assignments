package com.example.myapplication;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TaskListActivity extends AppCompatActivity {

    private TextView welcomeText;
    private Button task1Button, task2Button, task3Button, task4Button, task5Button, task6Button, task7Button, task8Button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task_list);

        welcomeText = findViewById(R.id.welcomeText);
        task1Button = findViewById(R.id.task1Button);
        task2Button = findViewById(R.id.task2Button);
        task3Button = findViewById(R.id.task3Button);
        task4Button = findViewById(R.id.task4Button);
        task5Button = findViewById(R.id.task5Button);
        task6Button = findViewById(R.id.task6Button);
        task7Button = findViewById(R.id.task7Button);
        task8Button = findViewById(R.id.task8Button);


        String username = getIntent().getStringExtra("USERNAME");
        if (username == null || username.trim().isEmpty()) {
            username = "User";
        }

        welcomeText.setText("Welcome, " + username);

        task1Button.setOnClickListener(v -> openTaskDetail(
                "Apply for jobs",
                "Look for some junior level dev roles",
                "High"
        ));

        task2Button.setOnClickListener(v -> openTaskDetail(
                "Order a charcoal canister",
                "Order a brand new charcoal canister for the fin",
                "Low"
        ));

        task3Button.setOnClickListener(v -> openTaskDetail(
                "Try out spice kitchen",
                "Try out the spice kitchen restaurant with a friend",
                "Low"
        ));

        task4Button.setOnClickListener(v -> openTaskDetail(
                "Attend Lecture",
                "Attend computer science lecture at 10 AM",
                "Medium"
        ));

        task5Button.setOnClickListener(v -> openTaskDetail(
                "Complete Assignment",
                "Finish mobile computing assignment",
                "High"
        ));

        task6Button.setOnClickListener(v -> openTaskDetail(
                "Group Project Meeting",
                "Meet with group to discuss project progress",
                "Medium"
        ));

        task7Button.setOnClickListener(v -> openTaskDetail(
                "Review Notes",
                "Go over class notes for upcoming exam",
                "Low"
        ));

        task8Button.setOnClickListener(v -> openTaskDetail(
                "Check Emails",
                "Respond/View important emails",
                "Medium"
        ));

    }

    private void openTaskDetail(String Title, String Description, String Priority) {
        Intent intent = new Intent(TaskListActivity.this, TaskDetailActivity.class);
        intent.putExtra("TITLE", Title);
        intent.putExtra("DESCRIPTION", Description);
        intent.putExtra("PRIORITY", Priority);
        startActivity(intent);
    }
}