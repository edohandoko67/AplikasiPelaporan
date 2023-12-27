package com.example.aplikasipelaporan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasipelaporan.R;
import com.example.aplikasipelaporan.model.Report;
import com.example.aplikasipelaporan.ui.EditActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<Report> list;
    private DatabaseReference database;

    public MyAdapter(Context context, ArrayList<Report> list) {
        this.context = context;
        this.list = list;
        database = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_laporan, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Report report = list.get(position);
        holder.tvHasil.setText(report.getHasil());
        holder.tvEtdate.setText(report.getTanggalMasuk());
        holder.tvDate.setText(report.getTanggalUpdate());
        holder.tvLap.setText(report.getLaporan());
        holder.tvStatus.setText(report.getStatus());
        holder.btnEdit.setOnClickListener(view -> {
            Intent intent = new Intent(context, EditActivity.class);
            //intent.putExtra("name", report.getName());
            intent.putExtra("hasil", report.getHasil());
            intent.putExtra("tanggalMasuk", report.getTanggalMasuk());
            intent.putExtra("tanggalUpdate", report.getTanggalUpdate());
            intent.putExtra("status", report.getStatus());
            intent.putExtra("Key", report.getKey());
            context.startActivity(intent);
        });
        holder.btnDelete.setOnClickListener(view -> {
            database.child("reports").child(report.getKey()).removeValue();
            notifyDataSetChanged();
            Toast.makeText(view.getContext(), "Menghapus " + report.getKey(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvHasil, tvDate, tvEtdate, tvStatus, tvLap;
        ImageView btnEdit, btnDelete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHasil = itemView.findViewById(R.id.tv_hasil_user);
            tvLap = itemView.findViewById(R.id.tv_lp_user);
            tvEtdate = itemView.findViewById(R.id.tv_etdate_user);
            tvDate = itemView.findViewById(R.id.tv_etdate_update);
            tvStatus = itemView.findViewById(R.id.tv_status_user);
            btnEdit = itemView.findViewById(R.id.btn_update_user);
            btnDelete = itemView.findViewById(R.id.btn_delete_user);
        }
    }
}
