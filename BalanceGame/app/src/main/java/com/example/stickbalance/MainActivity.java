package com.example.stickbalance;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private LineView lineView;
    private TextView tvScore, tvTime;

    private SensorManager sensorManager;
    private Sensor rotationSensor;
    private SensorEventListener rotationListener;

    private double angle;
    private int score = 0;
    private int time = 15000;

    Intent i;

    CountDownTimer timer = new CountDownTimer(time, 1000) {
        public void onTick(long millisUntilFinished) {
            tvTime.setText("Süre: " + millisUntilFinished / 1000);
        }

        public void onFinish() {
            endGame("timeout");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        angle = initialAngle(265, 275);

        lineView = findViewById(R.id.lineView);
        tvScore = findViewById(R.id.tvScore);
        tvTime = findViewById(R.id.tvTime);

        i = new Intent(getApplicationContext(), Scoreboard.class);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        timer.start();

        if (rotationSensor == null) {
            Toast.makeText(this, "Cihazda ivmeölçer bulunmuyor.", Toast.LENGTH_SHORT).show();
        }

        rotationListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (angle < 180 || angle > 360) {
                    endGame("death");
                } else if (angle >= 180 && angle <= 270) {
                    angle -= 0.05;
                } else if (angle > 270 && angle <= 360) {
                    angle += 0.05;
                }

                if (angle > 269 && angle < 271){
                    score += 1;
                }
                angle += event.values[1];
                lineView.setLocation(angle);
                tvScore.setText("Skor: " + score);
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(rotationListener, rotationSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(rotationListener);
    }

    private double initialAngle(double rangeMin, double rangeMax) {
        Random rnd = new Random();
        return (rangeMin + (rangeMax - rangeMin) * rnd.nextDouble());
    }

    private void endGame(String type){
        sensorManager.unregisterListener(rotationListener);
        timer.cancel();
        i.putExtra("score", score);
        i.putExtra("finishType", type);
        startActivity(i);
        finish();
    }
}
