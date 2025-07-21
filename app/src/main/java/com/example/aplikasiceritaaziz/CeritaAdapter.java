package com.example.aplikasiceritaaziz;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CeritaAdapter extends RecyclerView.Adapter<CeritaAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Cerita> ceritaList;

    public CeritaAdapter(Context context, ArrayList<Cerita> ceritaList) {
        this.context = context;
        this.ceritaList = ceritaList;
    }

    @NonNull
    @Override
    public CeritaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cerita, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CeritaAdapter.ViewHolder holder, int position) {
        Cerita cerita = ceritaList.get(position);
        holder.txtJudul.setText(cerita.getJudul());
        holder.txtIsi.setText(cerita.getIsi());

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, FormCeritaActivity.class);
            i.putExtra("id", cerita.getId());
            i.putExtra("judul", cerita.getJudul());
            i.putExtra("isi", cerita.getIsi());
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return ceritaList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtJudul, txtIsi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtJudul = itemView.findViewById(R.id.item_judul);
            txtIsi = itemView.findViewById(R.id.item_isi);
        }
    }
}
