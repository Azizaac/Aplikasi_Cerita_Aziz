package com.example.aplikasiceritaaziz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FormCeritaActivity extends AppCompatActivity {

    private EditText inputJudul, inputIsi;
    private Button btnSimpan;
    private DatabaseHelper db;
    private int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cerita);

        // Inisialisasi view
        inputJudul = findViewById(R.id.input_judul);
        inputIsi = findViewById(R.id.input_isi);
        btnSimpan = findViewById(R.id.btn_simpan);

        // Inisialisasi database helper
        db = new DatabaseHelper(this);

        // Cek apakah ini mode edit atau tambah baru
        Intent intent = getIntent();
        if (intent.hasExtra("id")) {
            id = intent.getIntExtra("id", -1);
            String judul = intent.getStringExtra("judul");
            String isi = intent.getStringExtra("isi");

            // Set text jika data tidak null
            if (judul != null) {
                inputJudul.setText(judul);
            }
            if (isi != null) {
                inputIsi.setText(isi);
            }

            btnSimpan.setText("Update");
        }

        // Set click listener untuk tombol simpan
        btnSimpan.setOnClickListener(v -> {
            String judul = inputJudul.getText().toString().trim();
            String isi = inputIsi.getText().toString().trim();

            // Validasi input
            if (judul.isEmpty()) {
                inputJudul.setError("Judul tidak boleh kosong");
                inputJudul.requestFocus();
                return;
            }

            if (isi.isEmpty()) {
                inputIsi.setError("Isi cerita tidak boleh kosong");
                inputIsi.requestFocus();
                return;
            }

            boolean sukses = false;
            String pesan = "";

            try {
                if (id == -1) {
                    // Mode tambah baru
                    sukses = db.tambahCerita(judul, isi);
                    pesan = sukses ? "Cerita berhasil ditambahkan" : "Gagal menambah cerita";
                } else {
                    // Mode update
                    sukses = db.updateCerita(id, judul, isi);
                    pesan = sukses ? "Cerita berhasil diupdate" : "Gagal mengupdate cerita";
                }

                Toast.makeText(this, pesan, Toast.LENGTH_SHORT).show();

                if (sukses) {
                    // Set result untuk memberi tahu activity sebelumnya bahwa data berhasil disimpan
                    setResult(RESULT_OK);
                    finish();
                }

            } catch (Exception e) {
                Toast.makeText(this, "Terjadi kesalahan: " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        // Tutup database helper untuk mencegah memory leak
        if (db != null) {
            db.close();
        }
        super.onDestroy();
    }
}