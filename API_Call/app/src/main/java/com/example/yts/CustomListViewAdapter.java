package com.example.yts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomListViewAdapter extends ArrayAdapter<Movie> {
    private final LayoutInflater inflater;
    private final Context context;
    private List<Movie> movies;
    private ViewHolder holder;

    public CustomListViewAdapter(Context context, List<Movie> movies) {
        super(context, 0, movies);
        this.context = context;
        this.movies = movies;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Movie getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return movies.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, null);

            holder = new ViewHolder();
            holder.movieTitle = convertView.findViewById(R.id.tvTitle);
            holder.moviePoster = convertView.findViewById(R.id.ivPoster);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Movie movie = movies.get(position);
        if (movie != null) {
            holder.movieTitle.setText(movie.getTitle());
            Picasso.with(convertView.getContext()).load(movie.getPosterUrl()).into(holder.moviePoster);
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView movieTitle;
        ImageView moviePoster;
    }

    public void updateResults(List<Movie> new_list) {
        movies = new_list;
        //Triggers the list update
        notifyDataSetChanged();
    }
}
