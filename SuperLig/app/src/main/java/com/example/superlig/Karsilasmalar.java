package com.example.superlig;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Karsilasmalar extends AppCompatActivity {
    List<Takim> takimlar;
    List<Takim> grup1;
    List<Takim> grup2;

    RecyclerView recyclerView;

    private static List<Takim> takimDagit(List<Takim> takimlar, int grup) {
        List<Takim> takim = new ArrayList<Takim>();

        if (grup == 1) {
            for (int i = 0; i < takimlar.size(); i++) {
                if (i % 2 == 0) {
                    takim.add(takimlar.get(i));
                }
            }
        } else if (grup == 2) {
            for (int i = 0; i < takimlar.size(); i++) {
                if (i % 2 == 1) {
                    takim.add(takimlar.get(i));
                }
            }
        }
        return takim;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_karsilasmalar);
        Toolbar tb = findViewById(R.id.tbMenu);
        tb.setTitle("Karşılaşmalar");
        tb.inflateMenu(R.menu.menu);
        setSupportActionBar(tb);


        takimlar = (List<Takim>) getIntent().getSerializableExtra("takimlar");
        grup1 = takimDagit(takimlar, 1);
        grup2 = takimDagit(takimlar, 2);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        KarsilasmaAdapter adapter = new KarsilasmaAdapter(this, grup1, grup2);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.menuMain:
                i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                break;
            case R.id.menuGames:
                i = new Intent(getApplicationContext(), Karsilasmalar.class);
                i.putExtra("takimlar", (Serializable) takimlar);
                startActivity(i);
                break;
            case R.id.menuTeams:
                i = new Intent(getApplicationContext(), Takimlar.class);
                i.putExtra("takimlar", (Serializable) takimlar);
                startActivity(i);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
