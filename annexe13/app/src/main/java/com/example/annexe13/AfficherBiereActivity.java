package com.example.annexe13;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
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

        instance = DatabaseHelper.getInstance(getApplicationContext());

        instance.ouvrirConnexion();

        Vector<String> v = null;
        try {
            v = instance.retourerEvaluation();
        } catch (Exception e) {

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,v);

        if(adapter.isEmpty()){
            Toast.makeText(this, "Il n'y a pas d'item.", Toast.LENGTH_SHORT).show();
        }
        else{
            liste.setAdapter(adapter);
        }



    }

    @Override
    protected void onStop() {
        super.onStop();
        instance.fermerConnexion();

    }
}