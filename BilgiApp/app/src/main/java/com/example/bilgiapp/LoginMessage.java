package com.example.bilgiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginMessage extends AppCompatActivity {

    ImageView ivStatus;
    TextView tvMessage;
    String mail, password;
    String correctMail = "admin@bilgi.edu.tr";
    String correctPassword = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_message);
        final Handler handler = new Handler();

        mail = getIntent().getExtras().getString("Mail");
        password = getIntent().getExtras().getString("Password");


        ivStatus = findViewById(R.id.ivStatus);
        tvMessage = findViewById(R.id.tvMessage);

        if (mail.equals(correctMail) && password.equals(correctPassword)){
            ivStatus.setBackgroundResource(R.drawable.success);

            CountDownTimer Count = new CountDownTimer(4000, 1000) {
                public void onTick(long millisUntilFinished) {
                    tvMessage.setText("Giriş Başarılı. " + millisUntilFinished / 1000 + " saniye içinde önceki ekrana dönülecek.");
                }

                public void onFinish() {
                    finish();
                }
            };
            Count.start();
        }

        else {
            ivStatus.setBackgroundResource(R.drawable.fail);

            CountDownTimer Count = new CountDownTimer(4000, 1000) {
                public void onTick(long millisUntilFinished) {
                    tvMessage.setText("Hatalı Giriş. " + millisUntilFinished / 1000 + " saniye içinde önceki ekrana dönülecek.");
                }

                public void onFinish() {
                    finish();
                }
            };
            Count.start();
        }

    }
}
