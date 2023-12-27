package com.example.aplikasipelaporan.Unit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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

public class ListUnitAct extends AppCompatActivity {
    private DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<Report> list;
    Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_unit);
        FirebaseApp.initializeApp(this);

        databaseReference = FirebaseDatabase.getInstance().getReference("reports");
        list = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerViewUnit);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this, list);
        recyclerView.setAdapter(adapter);
        getDataUnit();
    }

    private void getDataUnit() {
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                list.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    UnitModel unit = dataSnapshot.getValue(UnitModel.class);
//                    unit.setKey(dataSnapshot.getKey());
//                    list.add(unit);
//                }
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }
}