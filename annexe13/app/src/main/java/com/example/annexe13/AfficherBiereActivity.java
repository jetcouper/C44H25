package com.example.annexe13;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Vector;

public class AfficherBiereActivity extends AppCompatActivity {

    ListView liste;
    DatabaseHelper instance;
    Button quitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_afficher_biere);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        liste = findViewById(R.id.listBiere);
        quitter = findViewById(R.id.btnQuitter);

        instance = DatabaseHelper.getInstance(getApplicationContext());

        instance.ouvrirConnexion();

        Vector<String> v = null;
        try {
            v = instance.retourerEvaluation();
            if (v == null || v.isEmpty()) {
                Toast.makeText(this, "Il n'y a pas d'item.", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        } catch (Exception e) {
            Toast.makeText(this, "Erreur: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, v);
        liste.setAdapter(adapter);

        Ecouteur ec = new Ecouteur();

        quitter.setOnClickListener(ec);
    }

    @Override
    protected void onStop() {
        super.onStop();
        instance.fermerConnexion();

    }

    private class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            finish();
        }
    }
}