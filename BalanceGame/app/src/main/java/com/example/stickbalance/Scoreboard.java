package com.example.stickbalance;

import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Scoreboard extends AppCompatActivity {
    String finishType, message;
    int score;
    TextView tvScore, tvMessage;
    Button btnRestart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_scoreboard);

        tvScore = findViewById(R.id.tvScore);
        tvMessage = findViewById(R.id.tvMessage);
        btnRestart = findViewById(R.id.btnRestart);

        score = getIntent().getExtras().getInt("score");
        finishType = getIntent().getExtras().getString("finishType");

        System.out.println(finishType);
        message = finishType.equals("death") ? "Çubuğu devirdin :(" : "Süren doldu!";

        tvMessage.setText(message);
        tvScore.setText("Score: " + score);

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
    }
}
