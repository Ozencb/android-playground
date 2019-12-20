package com.example.wordgame;

import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<String> usedWords = new ArrayList<>();
    public static SQLiteDatabase db;

    Score scoreObj = new Score();
    int time = 60000;
    int level = 1;

    String guess = "";
    GridView gridView;
    TextView tvGuess, tvScore, tvLevel, tvTime;
    Button btnTry, btnClear;

    CountDownTimer timer = new CountDownTimer(time, 1000) {
        public void onTick(long millisUntilFinished) {
            tvTime.setText("Süre: " + millisUntilFinished / 1000);
        }

        public void onFinish() {
            tvTime.setText("Süre doldu!");
            gridView.setAdapter(null);
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

        db = this.openOrCreateDatabase("dictionary.db", MODE_PRIVATE, null);
        // Tablo daha önceden yaratılmadıysa önce tabloyu yaratan,
        // sonrasında içine gerekli kelimeleri koyan koşul
        if (isTableExists() == false) {
            db.execSQL("DROP TABLE IF EXISTS words");
            db.execSQL("CREATE TABLE words (word text);");
            fillDB();
        }

        tvGuess = findViewById(R.id.tvGuess);
        tvScore = findViewById(R.id.tvScore);
        tvLevel = findViewById(R.id.tvLevel);
        tvTime = findViewById(R.id.tvTime);
        btnTry = findViewById(R.id.btnTry);
        btnClear = findViewById(R.id.btnClear);
        gridView = findViewById(R.id.puzzleGrid);

        tvScore.setText("Skor: 0");
        tvLevel.setText("Seviye: 1");
        scoreObj.setScore(0);

        timer.start();

        gridView.setAdapter(new GridAdapter(this, generateLetters(randomVowelPos())));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = view.findViewById(R.id.tvGrid);
                String selectedItem = textView.getText().toString();
                guess += selectedItem;
                tvGuess.setText(guess);
            }
        });

        btnTry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (guess != "") {
                    String query = "SELECT word FROM words WHERE LOWER(word) LIKE LOWER('" + guess + "')";
                    Cursor crs = db.rawQuery(query, null);

                    if (crs.moveToFirst() && !usedWords.contains(guess)) {
                        Toast.makeText(MainActivity.this, "Doğru!", Toast.LENGTH_SHORT).show();
                        usedWords.add(guess);
                        scoreObj.increaseScore(guess.length());

                        tvScore.setText("Skor: " + scoreObj.getScore());
                        guess = "";
                        tvGuess.setText(guess);
                    } else if (usedWords.contains(guess)) {
                        Toast.makeText(MainActivity.this, "Bu kelimeyi denedin.", Toast.LENGTH_SHORT).show();
                        guess = "";
                        tvGuess.setText(guess);
                    } else {
                        Toast.makeText(MainActivity.this, "Yanlış!", Toast.LENGTH_SHORT).show();
                        guess = "";
                        tvGuess.setText(guess);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Bir kelime giriniz.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guess = "";
                tvGuess.setText(guess);
            }
        });

        // Skor değerini dinleyerek seviye atlama şartı sağlandığında
        // GridView'u ve başka değişkenleri yenileyen koşul
        scoreObj.setListener(new Score.ChangeListener() {
            @Override
            public void onChange() {
                if (scoreObj.getScore() >= level * 2) {
                    GridAdapter adapter = new GridAdapter(MainActivity.this, generateLetters(randomVowelPos()));
                    adapter.notifyDataSetChanged();
                    gridView.setAdapter(adapter);

                    level++;
                    timer.cancel();
                    timer.start();
                    scoreObj.setScore(0);
                    tvLevel.setText("Seviye: " + level);
                    tvScore.setText("Skor: " + scoreObj.getScore());
                }
            }
        });


    }

    // Rastgele 25 tane harf seçen fonksiyon.
    // sesli kelimelerin koyulacagi yerlerle karşılaştığında sesli, diğer durumlarda sessiz harf koyar.
    // randomVowelPos fonksiyonu 25'e kadar 5 tane sayı belirleyip döndürdüğü için
    // rastgele harf dizisinde gelişigüzel yerleştirilmiş 5 tane sesli harf oluşmuş olur
    private ArrayList<String> generateLetters(ArrayList<Integer> vowelPos) {
        ArrayList<String> tmp = new ArrayList<>();
        Random rand = new Random();
        String[] consonants = {"b", "c", "ç", "d", "f", "g", "ğ", "h", "j", "k", "l", "m", "n", "p", "r", "s", "ş", "t", "v", "y", "z"};
        String[] vowels = {"a", "e", "ı", "i", "o", "ö", "u", "ü"};
        for (int i = 0; i < 25; i++) {
            if (vowelPos.contains(i)) {
                tmp.add(vowels[rand.nextInt(vowels.length)]);
            } else {
                tmp.add(consonants[rand.nextInt(consonants.length)]);
            }
        }
        return tmp;
    }

    // Sesli harflerin yerleştirileceği yerleri belirleyen fonksiyon
    private ArrayList<Integer> randomVowelPos() {
        ArrayList<Integer> tmp = new ArrayList<>();
        Random rand = new Random();
        int i = 0;
        while (i < 5) {
            int randNum = rand.nextInt(25);
            if (!tmp.contains(randNum)) {
                tmp.add(randNum);
                i++;
            }
        }
        return tmp;
    }

    // İlgili tablonun daha önceden yaratılıp yaratılmadığını kontrol eden fonksiyon
    private boolean isTableExists() {
        try {
            String query = "SELECT word FROM words";
            Cursor cursor = db.rawQuery(query, null);

            if (cursor != null) {
                return cursor.getCount() > 0;
            }
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("IO", "IO" + e);
            return false;
        }
    }

    // Assets altındaki csv dosyasını okuyarak içindeki kelimeleri
    // veritabanına kaydeden fonksiyon
    private void fillDB() {
        String csvFile = "dictionary.csv";
        AssetManager manager = this.getAssets();
        InputStream inStream = null;
        try {
            inStream = manager.open(csvFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));
        String line = "";
        db.beginTransaction();
        try {
            while ((line = buffer.readLine()) != null) {
                Log.d("CSV", "added word: " + line.replaceAll("['/\\s\\s+/g, ' ']", ""));
                db.execSQL("INSERT INTO words(word) VALUES ('" + line.replaceAll("['/\\s\\s+/g, ' ']", "") + "')");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }
}
