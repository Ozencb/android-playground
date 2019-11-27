package com.example.superlig;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomListViewAdapter extends ArrayAdapter<Takim> {

    private final LayoutInflater inflater;
    private final Context context;
    private final List<Takim> takimlar;
    private ViewHolder holder;

    public CustomListViewAdapter(Context context, List<Takim> takimlar) {
        super(context, 0, takimlar);
        this.context = context;
        this.takimlar = takimlar;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return takimlar.size();
    }

    @Override
    public Takim getItem(int position) {
        return takimlar.get(position);
    }

    @Override
    public long getItemId(int position) {
        return takimlar.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, null);

            holder = new ViewHolder();
            holder.takimLogo = convertView.findViewById(R.id.ivLogo);
            holder.takimAd = convertView.findViewById(R.id.tvTakim);
            convertView.setTag(holder);

        } else {
            //Get viewholder we already created
            holder = (ViewHolder) convertView.getTag();
        }

        Takim takim = takimlar.get(position);
        if (takim != null) {
            holder.takimLogo.setImageResource(takim.getImage());
            holder.takimAd.setText(takim.getTitle());
        }
        return convertView;
    }

    private static class ViewHolder {
        ImageView takimLogo;
        TextView takimAd;
    }
}