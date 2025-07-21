package com.example.aplikasiceritaaziz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    Button btnBukaCerita, btnKeluar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnBukaCerita = findViewById(R.id.btn_buka_cerita);
        btnKeluar = findViewById(R.id.btn_keluar);

        btnBukaCerita.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CeritaActivity.class);
            startActivity(intent);
        });

        btnKeluar.setOnClickListener(v -> {
            finishAffinity(); // menutup semua activity
        });
    }
}
