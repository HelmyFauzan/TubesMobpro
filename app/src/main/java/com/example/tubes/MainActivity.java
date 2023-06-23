package com.example.tubes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private Button btnAddFakultas, btnAddMahasiswa, btnUpdateMahasiswa, btnDeleteMahasiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        btnAddFakultas = findViewById(R.id.btnAddFakultas);
        btnAddMahasiswa = findViewById(R.id.btnAddMahasiswa);
        btnUpdateMahasiswa = findViewById(R.id.btnUpdateMahasiswa);
        btnDeleteMahasiswa = findViewById(R.id.btnDeleteMahasiswa);

        btnAddFakultas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFakultas();
            }
        });

        btnAddMahasiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMahasiswa();
            }
        });

        btnUpdateMahasiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMahasiswa();
            }
        });

        btnDeleteMahasiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMahasiswa();
            }
        });
    }

    private void addFakultas() {
        Fakultas fakultas = new Fakultas(1, "Fakultas Teknik");
        databaseHelper.addFakultas(fakultas);
        Toast.makeText(this, "Fakultas added", Toast.LENGTH_SHORT).show();
    }

    private void addMahasiswa() {
        Mahasiswa mahasiswa = new Mahasiswa(1, "John Doe", 1);
        databaseHelper.addMahasiswa(mahasiswa);
        Toast.makeText(this, "Mahasiswa added", Toast.LENGTH_SHORT).show();
    }

    private void updateMahasiswa() {
        Mahasiswa mahasiswa = new Mahasiswa(1, "Jane Smith", 1);
        databaseHelper.updateMahasiswa(mahasiswa);
        Toast.makeText(this, "Mahasiswa updated", Toast.LENGTH_SHORT).show();
    }

    private void deleteMahasiswa() {
        databaseHelper.deleteMahasiswa(1);
        Toast.makeText(this, "Mahasiswa deleted", Toast.LENGTH_SHORT).show();
    }
}

