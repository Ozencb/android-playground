package com.example.bilgiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView ivIcon;
    EditText etMail, etPassword;
    Button btnLogin;
    TextView tvSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivIcon = findViewById(R.id.ivIcon);
        etMail = findViewById(R.id.etMail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignup = findViewById(R.id.tvSignup);

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Yapım aşamasında", Toast.LENGTH_LONG).show();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etMail.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Lütfen e-posta ve şifre giriniz.", Toast.LENGTH_LONG).show();
                }

                else if (!etMail.getText().toString().isEmpty() && !etPassword.getText().toString().isEmpty()){
                    Intent i = new Intent(getApplicationContext(), LoginMessage.class);
                    i.putExtra("Mail", etMail.getText().toString());
                    i.putExtra("Password", etPassword.getText().toString());
                    startActivity(i);
                }
            }
        });
    }
}
