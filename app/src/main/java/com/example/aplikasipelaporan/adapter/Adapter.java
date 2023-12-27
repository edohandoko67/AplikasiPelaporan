package com.example.aplikasipelaporan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasipelaporan.R;
import com.example.aplikasipelaporan.model.Report;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    Context context;
    ArrayList<Report> list;
    private DatabaseReference database;

    public Adapter(Context context, ArrayList<Report> list) {
        this.context = context;
        this.list = list;
        database = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_unit, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Report report = list.get(position);
        holder.tvDateUser.setText(report.getTanggalMasuk());
        holder.tvLaporan.setText(report.getLaporan());
        holder.tvHasilLap.setText(report.getHasil());
        holder.tvStatusUser.setText(report.getStatus());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvHasilLap, tvDateUser, tvStatusUser, tvLaporan;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHasilLap = itemView.findViewById(R.id.tv_hasilLap_user);
            tvDateUser = itemView.findViewById(R.id.tv_date_user);
            tvStatusUser = itemView.findViewById(R.id.tv_statusLaporan_user);
            tvLaporan = itemView.findViewById(R.id.tv_laporan_user);
        }
    }
}
