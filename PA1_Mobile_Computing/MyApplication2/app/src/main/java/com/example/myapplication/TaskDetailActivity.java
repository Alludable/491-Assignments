package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class TaskDetailActivity extends AppCompatActivity {

    private TextView taskTitleText;
    private TextView taskDescriptionText;
    private TextView taskPriorityText;
    private MaterialButton markCompleteButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task_detail);

        taskTitleText = findViewById(R.id.taskTitleText);
        taskDescriptionText = findViewById(R.id.taskDescriptionText);
        taskPriorityText = findViewById(R.id.taskPriorityText);
        markCompleteButton = findViewById(R.id.markCompleteButton);

        String title = getIntent().getStringExtra("TITLE");
        String description = getIntent().getStringExtra("DESCRIPTION");
        String priority = getIntent().getStringExtra("PRIORITY");

        if (title == null || title.trim().isEmpty()) {
            title = getString(R.string.default_title);
        }

        if (description == null || description.trim().isEmpty()) {
            description = getString(R.string.default_description);
        }

        if (priority == null || priority.trim().isEmpty()) {
            priority = "Unknown";
        }

        taskTitleText.setText(title);
        taskDescriptionText.setText(description);
        taskPriorityText.setText("Priority: " + priority);

        markCompleteButton.setOnClickListener(v -> {
            new AlertDialog.Builder(TaskDetailActivity.this)
                    .setTitle(R.string.complete_task_title)
                    .setMessage(R.string.complete_task_message)
                    .setPositiveButton(R.string.yes, (dialog, which) -> {
                        Toast.makeText(TaskDetailActivity.this,
                                R.string.task_completed,
                                Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton(R.string.no, null)
                    .show();
        });
    }
}