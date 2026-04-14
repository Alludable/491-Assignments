package com.example.sensorproject;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor gyroscope;

    private TextView accelX, accelY, accelZ;
    private TextView gyroX, gyroY, gyroZ;
    private TextView orientationText;

    private float[] accelValues = new float[3];
    private float[] gyroValues = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accelX = findViewById(R.id.accelX);
        accelY = findViewById(R.id.accelY);
        accelZ = findViewById(R.id.accelZ);

        gyroX = findViewById(R.id.gyroX);
        gyroY = findViewById(R.id.gyroY);
        gyroZ = findViewById(R.id.gyroZ);

        orientationText = findViewById(R.id.orientationText);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        }

        if (accelerometer == null || gyroscope == null) {
            orientationText.setText("The required sensors aren't available.");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (sensorManager != null) {
            if (accelerometer != null) {
                sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
            }

            if (gyroscope != null) {
                sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_UI);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelValues = event.values.clone();

            accelX.setText("X: " + accelValues[0]);
            accelY.setText("Y: " + accelValues[1]);
            accelZ.setText("Z: " + accelValues[2]);
        }

        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            gyroValues = event.values.clone();

            gyroX.setText("X: " + gyroValues[0]);
            gyroY.setText("Y: " + gyroValues[1]);
            gyroZ.setText("Z: " + gyroValues[2]);
        }

        updateOrientation();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private void updateOrientation() {
        float x = accelValues[0];
        float y = accelValues[1];
        float z = accelValues[2];

        double pitch = Math.atan2(-x, Math.sqrt(y * y + z * z));
        double roll = Math.atan2(y, z);

        pitch = Math.toDegrees(pitch);
        roll = Math.toDegrees(roll);

        String orientation;

        if (z > 8) {
            orientation = "Face Up";
        } else if (z < -8) {
            orientation = "Face Down";
        } else if (pitch > 30) {
            orientation = "Tilted Up";
        } else if (pitch < -30) {
            orientation = "Tilted Down";
        } else if (roll > 30) {
            orientation = "Tilted Right";
        } else if (roll < -30) {
            orientation = "Tilted Left";
        } else {
            orientation = "Flat";
        }

        orientationText.setText(
                "Pitch: " + (int) pitch +
                        "\nRoll: " + (int) roll +
                        "\n" + orientation
        );
    }
}