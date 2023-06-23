package com.example.tubes;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "fakultas_db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_FAKULTAS = "fakultas";
    private static final String COLUMN_FAKULTAS_ID = "id";
    private static final String COLUMN_FAKULTAS_NAMA = "nama";

    private static final String TABLE_MAHASISWA = "mahasiswa";
    private static final String COLUMN_MAHASISWA_ID = "id";
    private static final String COLUMN_MAHASISWA_NAMA = "nama";
    private static final String COLUMN_MAHASISWA_ID_FAKULTAS = "id_fakultas";
    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        // Membuat tabel fakultas
        String createFakultasTable = "CREATE TABLE " + TABLE_FAKULTAS + "("
                + COLUMN_FAKULTAS_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_FAKULTAS_NAMA + " TEXT)";
        db.execSQL(createFakultasTable);

        // Membuat tabel mahasiswa
        String createMahasiswaTable = "CREATE TABLE " + TABLE_MAHASISWA + "("
                + COLUMN_MAHASISWA_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_MAHASISWA_NAMA + " TEXT,"
                + COLUMN_MAHASISWA_ID_FAKULTAS + " INTEGER,"
                + "FOREIGN KEY (" + COLUMN_MAHASISWA_ID_FAKULTAS + ") REFERENCES "
                + TABLE_FAKULTAS + "(" + COLUMN_FAKULTAS_ID + "))";
        db.execSQL(createMahasiswaTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Menghapus tabel lama jika ada dan membuat ulang
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAHASISWA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAKULTAS);
        onCreate(db);
    }

    // Metode untuk menambahkan data fakultas
    public void addFakultas(Fakultas fakultas) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FAKULTAS_NAMA, fakultas.getNama());
        db.insert(TABLE_FAKULTAS, null, values);
        db.close();
    }

    // Metode untuk menambahkan data mahasiswa
    public void addMahasiswa(Mahasiswa mahasiswa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MAHASISWA_NAMA, mahasiswa.getNama());
        values.put(COLUMN_MAHASISWA_ID_FAKULTAS, mahasiswa.getIdFakultas());
        db.insert(TABLE_MAHASISWA, null, values);
        db.close();
    }

    // Metode untuk mendapatkan daftar fakultas
    public List<Fakultas> getAllFakultas() {
        List<Fakultas> fakultasList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_FAKULTAS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_FAKULTAS_ID));
                @SuppressLint("Range") String nama = cursor.getString(cursor.getColumnIndex(COLUMN_FAKULTAS_NAMA));

                Fakultas fakultas = new Fakultas(id, nama);
                fakultasList.add(fakultas);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return fakultasList;
    }

    // Metode untuk mendapatkan daftar mahasiswa berdasarkan id fakultas
    public List<Mahasiswa> getMahasiswaByFakultas(int idFakultas) {
        List<Mahasiswa> mahasiswaList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_MAHASISWA
                + " WHERE " + COLUMN_MAHASISWA_ID_FAKULTAS + " = " + idFakultas;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_MAHASISWA_ID));
                @SuppressLint("Range") String nama = cursor.getString(cursor.getColumnIndex(COLUMN_MAHASISWA_NAMA));

                Mahasiswa mahasiswa = new Mahasiswa(id, nama, idFakultas);
                mahasiswaList.add(mahasiswa);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return mahasiswaList;
    }

    // Metode untuk mengupdate data mahasiswa
    public void updateMahasiswa(Mahasiswa mahasiswa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MAHASISWA_NAMA, mahasiswa.getNama());
        values.put(COLUMN_MAHASISWA_ID_FAKULTAS, mahasiswa.getIdFakultas());

        db.update(TABLE_MAHASISWA, values, COLUMN_MAHASISWA_ID + " = ?",
                new String[]{String.valueOf(mahasiswa.getId())});
        db.close();
    }

    // Metode untuk menghapus data mahasiswa
    public void deleteMahasiswa(int idMahasiswa) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MAHASISWA, COLUMN_MAHASISWA_ID + " = ?",
                new String[]{String.valueOf(idMahasiswa)});
        db.close();
    }


    }


