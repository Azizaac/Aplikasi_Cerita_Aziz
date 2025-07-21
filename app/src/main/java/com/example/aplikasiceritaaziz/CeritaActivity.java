package com.example.aplikasiceritaaziz;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CeritaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button btnTambah, btnKembaliHome;
    private DatabaseHelper db;
    private CeritaAdapter adapter;
    private ArrayList<Cerita> listCerita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerita);

        // Inisialisasi komponen
        recyclerView = findViewById(R.id.recyclerCerita);
        btnTambah = findViewById(R.id.btn_tambah_cerita);
        btnKembaliHome = findViewById(R.id.btn_kembali_home);
        db = new DatabaseHelper(this);
        listCerita = new ArrayList<>();

        // Setup RecyclerView & Adapter
        adapter = new CeritaAdapter(this, listCerita);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Tombol tambah cerita
        btnTambah.setOnClickListener(v -> {
            Intent intent = new Intent(CeritaActivity.this, FormCeritaActivity.class);
            startActivity(intent);
        });

        // Tombol kembali ke home
        btnKembaliHome.setOnClickListener(v -> {
            Intent intent = new Intent(CeritaActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });

        // Load data pertama kali
        loadData();
    }

    // Fungsi untuk memuat data dari database
    private void loadData() {
        listCerita.clear();
        Cursor cursor = db.getSemuaCerita();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                listCerita.add(new Cerita(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)
                ));
            }
            cursor.close();
        }

        Log.d("LOAD_DATA", "Jumlah cerita: " + listCerita.size());

        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    // Refresh data saat kembali dari form
    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}
