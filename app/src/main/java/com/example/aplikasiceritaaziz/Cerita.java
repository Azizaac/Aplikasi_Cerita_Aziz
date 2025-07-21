package com.example.aplikasiceritaaziz;

public class Cerita {
    private int id;
    private String judul;
    private String isi;

    public Cerita(int id, String judul, String isi) {
        this.id = id;
        this.judul = judul;
        this.isi = isi;
    }

    // Tambahkan getter
    public int getId() {
        return id;
    }

    public String getJudul() {
        return judul;
    }

    public String getIsi() {
        return isi;
    }
}
