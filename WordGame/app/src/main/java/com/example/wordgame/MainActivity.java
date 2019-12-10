package com.example.wordgame;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    TextView tvGuess;
    Button btnTry, btnClear;

    ArrayList<Integer> vowelPos = randomVowelPos();
    ArrayList<String> letters = generateLetters(vowelPos);

    String guess = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvGuess = findViewById(R.id.tvGuess);
        btnTry = findViewById(R.id.btnTry);
        btnClear = findViewById(R.id.btnClear);

        gridView = findViewById(R.id.puzzleGrid);

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
                Boolean result = WordChecker.checkWord(guess);
                Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_SHORT).show();
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
}
