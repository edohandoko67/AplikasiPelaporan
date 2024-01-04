package com.example.aplikasipelaporan.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.aplikasipelaporan.R;
import com.example.aplikasipelaporan.adapter.Adapter;
import com.example.aplikasipelaporan.adapter.MyAdapter;
import com.example.aplikasipelaporan.model.Report;
import com.example.aplikasipelaporan.model.UnitModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListUserActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<Report> list;
    Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);
        FirebaseApp.initializeApp(this);

        databaseReference = FirebaseDatabase.getInstance().getReference("reports");
        list = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerViewUser);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this, list);
        recyclerView.setAdapter(adapter);
        getDataUser();
    }

    private void getDataUser() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Report report = dataSnapshot.getValue(Report.class);
                    report.setKey(dataSnapshot.getKey());
                    list.add(report);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ListUserActivity.this, "Data not found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}