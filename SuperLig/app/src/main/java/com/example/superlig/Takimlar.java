package com.example.superlig;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.Serializable;
import java.util.List;

// Aktivitede CustomListView Yapısını kullanarak Türkiye Süper Liginde 18 takımı Logoları ve Adları ile listeleyeceksiniz.

public class Takimlar extends AppCompatActivity {
    List<Takim> takimlar;
    CustomListViewAdapter listViewAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_takimlar);
        Toolbar tb = findViewById(R.id.tbMenu);
        tb.setTitle("Takımlar");
        tb.inflateMenu(R.menu.menu);
        setSupportActionBar(tb);

        takimlar = (List<Takim>) getIntent().getSerializableExtra("takimlar");

        listView = findViewById(R.id.lvTakimlar);
        listViewAdapter = new CustomListViewAdapter(Takimlar.this, takimlar);
        listView.setAdapter(listViewAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
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
}
