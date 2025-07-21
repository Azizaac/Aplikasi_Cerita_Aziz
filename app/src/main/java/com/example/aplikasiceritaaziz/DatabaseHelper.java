package com.example.aplikasiceritaaziz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserDB.db";
    private static final int DATABASE_VERSION = 1;

    // Table Users
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    // Table Cerita
    private static final String TABLE_CERITA = "cerita";
    private static final String COLUMN_JUDUL = "judul";
    private static final String COLUMN_ISI = "isi";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsers = "CREATE TABLE " + TABLE_USERS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_EMAIL + " TEXT," +
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(createUsers);

        String createCerita = "CREATE TABLE " + TABLE_CERITA + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_JUDUL + " TEXT," +
                COLUMN_ISI + " TEXT)";
        db.execSQL(createCerita);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CERITA);
        onCreate(db);
    }

    // Sign up
    public boolean registerUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Cek apakah email sudah terdaftar
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_ID},
                COLUMN_EMAIL + "=?", new String[]{email}, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.close();
            return false; // email sudah terdaftar
        }

        cursor.close();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    // Login
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_ID},
                COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{email, password},
                null, null, null);

        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    // Tambah cerita
    public boolean tambahCerita(String judul, String isi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_JUDUL, judul);
        values.put(COLUMN_ISI, isi);
        long result = db.insert(TABLE_CERITA, null, values);
        return result != -1;
    }

    // Ambil semua cerita
    public Cursor getSemuaCerita() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_CERITA + " ORDER BY id DESC", null);
    }

    // Update cerita
    public boolean updateCerita(int id, String judul, String isi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_JUDUL, judul);
        values.put(COLUMN_ISI, isi);
        return db.update(TABLE_CERITA, values, "id=?", new String[]{String.valueOf(id)}) > 0;
    }

    // Hapus cerita
    public boolean hapusCerita(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CERITA, "id=?", new String[]{String.valueOf(id)}) > 0;
    }
}
