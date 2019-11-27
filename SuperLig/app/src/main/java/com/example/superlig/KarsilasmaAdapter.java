package com.example.superlig;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class KarsilasmaAdapter extends RecyclerView.Adapter<KarsilasmaAdapter.KarsilasmaViewHolder> {
    private Context ctx;
    private List<Takim> grup1;
    private List<Takim> grup2;

    public KarsilasmaAdapter(Context ctx, List<Takim> grup1, List<Takim> grup2) {
        this.ctx = ctx;
        this.grup1 = grup1;
        this.grup2 = grup2;
    }


    @Override
    public KarsilasmaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.cards, null);
        return new KarsilasmaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(KarsilasmaAdapter.KarsilasmaViewHolder holder, int position) {
        Takim takim1 = grup1.get(position);
        Takim takim2 = grup2.get(position);

        holder.tvTakimAdi1.setText(takim1.getTitle());
        holder.tvTakimAdi2.setText(takim2.getTitle());
        holder.tvTakimStat1.setText("Puan: " + takim1.getStat());
        holder.tvTakimStat2.setText("Puan: " + takim2.getStat());
        holder.tvTarih.setText("Tarih: 01.01.2020");
        holder.ivTakim1.setImageDrawable(ctx.getResources().getDrawable(takim1.getImage()));
        holder.ivTakim2.setImageDrawable(ctx.getResources().getDrawable(takim2.getImage()));
    }

    @Override
    public int getItemCount() {
        return grup1.size();
    }

    class KarsilasmaViewHolder extends RecyclerView.ViewHolder {
        TextView tvTakimAdi1, tvTakimAdi2, tvTakimStat1, tvTakimStat2, tvTarih;
        ImageView ivTakim1, ivTakim2;

        public KarsilasmaViewHolder(View itemView) {
            super(itemView);

            tvTakimAdi1 = itemView.findViewById(R.id.tvTakimAdi1);
            tvTakimAdi2 = itemView.findViewById(R.id.tvTakimAdi2);
            tvTakimStat1 = itemView.findViewById(R.id.tvTakimStat1);
            tvTakimStat2 = itemView.findViewById(R.id.tvTakimStat2);
            tvTarih = itemView.findViewById(R.id.tvTarih);
            ivTakim1 = itemView.findViewById(R.id.ivTakim1);
            ivTakim2 = itemView.findViewById(R.id.ivTakim2);
        }
    }
}
