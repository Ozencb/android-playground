package com.example.wordgame;

import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

    public static int skor = 0;
    public static int sure = 60000;
    public static int seviye = 0;
    public static ArrayList<String> usedWords = new ArrayList<>();
    public static SQLiteDatabase db;
    GridView gridView;
    TextView tvGuess, tvScore;
    Button btnTry, btnClear;
    ArrayList<Integer> vowelPos = randomVowelPos();
    ArrayList<String> letters = generateLetters(vowelPos);
    String guess = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        db = this.openOrCreateDatabase("dictionary.db", MODE_PRIVATE, null);

        if (isTableExists() == false) {
            db.execSQL("DROP TABLE IF EXISTS words");
            db.execSQL("CREATE TABLE words (word text);");
            fillDB();
        }

        tvGuess = findViewById(R.id.tvGuess);
        tvScore = findViewById(R.id.tvScore);
        btnTry = findViewById(R.id.btnTry);
        btnClear = findViewById(R.id.btnClear);
        gridView = findViewById(R.id.puzzleGrid);

        tvScore.setText("Skor: 0");

        gridView.setAdapter(new GridAdapter(this, letters));

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
                if(guess != ""){
                    String query = "SELECT word FROM words WHERE LOWER(word) LIKE LOWER('" + guess + "')";
                    Cursor crs = db.rawQuery(query, null);

                    if (crs.moveToFirst() && !usedWords.contains(guess)) {
                        Toast.makeText(MainActivity.this, "Doğru!", Toast.LENGTH_SHORT).show();
                        usedWords.add(guess);
                        skor += guess.length();
                        tvScore.setText("Skor: " + skor);
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
    }

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

    private boolean isTableExists() {
        try {
            String query = "SELECT word FROM words";
            Cursor cursor = db.rawQuery(query, null);

            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    return true;
                }
            }
            return false;

        } catch (Exception e){
            e.printStackTrace();
            Log.e("IO","IO"+e);
            return false;
        }
    }

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
