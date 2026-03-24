package com.example.myapplication;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class HomeActivity extends AppCompatActivity {

    private TextInputLayout nameInputLayout;
    private TextInputEditText nameEditText;
    private MaterialButton goToTasksButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        nameInputLayout = findViewById(R.id.nameInputLayout);
        nameEditText = findViewById(R.id.nameEditText);
        goToTasksButton = findViewById(R.id.goToTasksButton);

        if (savedInstanceState != null) {
            String savedName = savedInstanceState.getString("saved_name", "");
            nameEditText.setText(savedName);
        }

        goToTasksButton.setOnClickListener(v -> {
            String username = nameEditText.getText().toString().trim();

            if (username.isEmpty()) {
                nameInputLayout.setError(getString(R.string.name_required));
            } else {
                nameInputLayout.setError(null);

                Intent intent = new Intent(HomeActivity.this, TaskListActivity.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString("saved_name", nameEditText.getText().toString());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("HomeActivity", "onStart called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("HomeActivity", "onResume called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("HomeActivity", "onPause called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("HomeActivity", "onStop called");
    }
}