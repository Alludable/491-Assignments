package com.example.locationlogger;

import android.os.Bundle;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.location.LocationServices;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_CODE = 100;
    private static final String FILE_NAME = "location_log.txt";

    private TextView statusText;
    private TextView locationText;
    private TextView loggedText;
    private Button startButton;
    private Button stopButton;

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        statusText = findViewById(R.id.statusText);
        locationText = findViewById(R.id.locationText);
        loggedText =  findViewById(R.id.loggedText);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        loadSavedLocations();

        startButton.setOnClickListener(v -> startTracking());
        stopButton.setOnClickListener(v -> stopTracking());
    }

    private void startTracking() {
        if (!hasLocationPermission()) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                 Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_CODE
            );
            return;
        }

        statusText.setText("Location Tracking is now active.");

        Intent serviceIntent = new Intent(this, LocationService.class);
        startService(serviceIntent);

        getCurrentLocation();
    }

    private void stopTracking() {
        statusText.setText("Location Tracking has stopped.");

        Intent serviceIntent = new Intent(this, LocationService.class);
        stopService(serviceIntent);
    }

    private boolean hasLocationPermission() {
        return ( ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

                &&

                ActivityCompat.checkSelfPermission(
                this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
                );
    }

    private void getCurrentLocation() {
        if (!hasLocationPermission()) {
            locationText.setText("Location Permissions denied.");
            return;
        }

        CancellationTokenSource cts = new CancellationTokenSource();

        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                cts.getToken()
                )
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        saveAndDisplayLocation(location);
                    } else {
                        locationText.setText("Location unavailable. Make sure that your GPS is enabled and try again.");
                    }
                })
                .addOnFailureListener(e -> {
                    locationText.setText("Failed to get location.");
                });
    }

    private void saveAndDisplayLocation(Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();

        String time = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss",
                Locale.getDefault()
        ).format(new Date());

        String entry = time + " | Latitude: " + lat + ",Longitude: " + lng + "\n";
        locationText.setText("Latest location:\nLatitude: " + lat + "\nLogitude: " + lng);

        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, MODE_APPEND);
            fos.write(entry.getBytes());
            fos.close();
        } catch (Exception e) {
            locationText.setText("Location was found, but failed to save.");
        }

        loadSavedLocations();
    }

    private void loadSavedLocations() {
        StringBuilder builder = new StringBuilder();

        try {
            FileInputStream fis = openFileInput(FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }

            reader.close();

            if (builder.length() == 0) {
                loggedText.setText("No saved entries yet.");
            } else {
                loggedText.setText(builder.toString());
            }
        } catch (Exception e) {
            loggedText.setText("No saved entries yet.");
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantedResults
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantedResults);

        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (grantedResults.length > 1 &&
                grantedResults[0] == PackageManager.PERMISSION_GRANTED && grantedResults[1] == PackageManager.PERMISSION_GRANTED) {
                startTracking();
            }  else {
                statusText.setText("Permission(s) Denied");
                locationText.setText("Location permission is required to track lcoation.");
            }
        }
    }
}