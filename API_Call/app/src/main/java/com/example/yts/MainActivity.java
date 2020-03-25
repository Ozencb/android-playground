package com.example.yts;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btnPrev, btnNext;

    private CustomListViewAdapter listViewAdapter;
    private ListView listView;

    private List<Movie> allMovies;
    private int movieCount;

    private RequestQueue queue;

    private int page_num = 1;
    private String base_url = "https://yts.mx/api/v2/list_movies.json?page=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(this);
        fetchData(page_num);

        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);

        listView = findViewById(R.id.lvMovies);

        listViewAdapter = new CustomListViewAdapter(MainActivity.this, allMovies);
        listView.setAdapter(listViewAdapter);

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (page_num > 1){
                    page_num -= 1;
                    fetchData(page_num);
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (page_num < movieCount / 30){
                    page_num += 1;
                    fetchData(page_num);
                }
            }
        });
    }

    private void fetchData(int page){
        String url = base_url + page;
        allMovies = new ArrayList<Movie>();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject data = response.getJSONObject("data");
                    JSONArray movies = data.getJSONArray("movies");

                    movieCount = data.getInt("movie_count");

                    for(int i = 0; i < movies.length(); i++){
                        JSONObject movie = movies.getJSONObject(i);

                        String movieTitle = movie.getString("title_long");
                        String moviePosterURL = movie.getString("small_cover_image") == null ? movie.getString("small_cover_image") : movie.getString("medium_cover_image");

                        allMovies.add(new Movie(i, movieTitle, moviePosterURL));
                    }
                    listViewAdapter.updateResults(allMovies);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(req);
    }
}
