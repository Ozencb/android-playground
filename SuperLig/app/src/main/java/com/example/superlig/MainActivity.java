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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//Aktivitede Main Aktivite sadece uygulamanızın logosunu sayfada ortalı bir şekilde barındıracaksınız.
//+Toolbarı düzenlemeniz ve menu oluşturarak bu 3 aktivite arasında geçiş yapmanız gereklidir.
//+Toolbar bütün aktivitelerden erişilebilir olmalıdır.

public class MainActivity extends AppCompatActivity {
    List<Takim> takimlar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Toolbar tb = findViewById(R.id.tbMenu);
        tb.setTitle("Anasayfa");
        tb.inflateMenu(R.menu.menu);
        setSupportActionBar(tb);

        takimlar = new ArrayList<Takim>();
        takimlar.add(new Takim(1, "Antalyaspor", "24", R.drawable.antalyaspor));
        takimlar.add(new Takim(1, "Alanyaspor", "12", R.drawable.alanyaspor));
        takimlar.add(new Takim(1, "Besiktas", "4", R.drawable.besiktas));
        takimlar.add(new Takim(1, "Malatyaspor", "54", R.drawable.malatyaspor));
        takimlar.add(new Takim(1, "Rizespor", "24", R.drawable.rizespor));
        takimlar.add(new Takim(1, "Sivasspor", "25", R.drawable.sivasspor));
        takimlar.add(new Takim(1, "Fenerbahçe", "21", R.drawable.fenerbahce));
        takimlar.add(new Takim(1, "Galatasaray", "20", R.drawable.galatasaray));
        takimlar.add(new Takim(1, "Gaziantep", "2", R.drawable.gaziantep));
        takimlar.add(new Takim(1, "Genclerbirligi", "6", R.drawable.genclerbirligi));
        takimlar.add(new Takim(1, "Göztepe", "8", R.drawable.goztepe));
        takimlar.add(new Takim(1, "Kayserispor", "5", R.drawable.kayserispor));
        takimlar.add(new Takim(1, "Konyaspor", "7", R.drawable.konyaspor));
        takimlar.add(new Takim(1, "Kasımpaşa", "8", R.drawable.kasimpasa));
        takimlar.add(new Takim(1, "Başakşehir", "29", R.drawable.basaksehir));
        takimlar.add(new Takim(1, "Ankaragücü", "24", R.drawable.ankaragucu));
        takimlar.add(new Takim(1, "Trabzonspor", "34", R.drawable.trabzonspor));
        takimlar.add(new Takim(1, "Denizlispor", "22", R.drawable.denizlispor));
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