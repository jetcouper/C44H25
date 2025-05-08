package com.example.tp2;

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

public class Fin extends AppCompatActivity {

    ListView listPoint;
    Button quitter;
    DatabaseHelper instance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listPoint = findViewById(R.id.listView);
        quitter = findViewById(R.id.btnMenu);
        
        instance = DatabaseHelper.getInstance(getApplicationContext());

        Vector<String> v = null;
        try {
            v = instance.retourerPointages();
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
        listPoint.setAdapter(adapter);


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