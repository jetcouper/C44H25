package com.example.annexe15;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper instance;
    TextView nbEquipe;
    TextView moyenne;
    Spinner nomEquipe;
    TextView reponse;
    Button btnReponse;
    String arenaSelectionner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nbEquipe = findViewById(R.id.txtNbEquipe);
        moyenne = findViewById(R.id.txtMoyenne);
        nomEquipe = findViewById(R.id.spinner);
        reponse = findViewById(R.id.txtReponse);
        btnReponse = findViewById(R.id.btnReponse);

        instance = DatabaseHelper.getInstance(getApplicationContext());

        instance.ouvrirConnexion();

        Vector<String> v = null;

        try {
            v = instance.retourerArena();
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
        nomEquipe.setAdapter(adapter);

        Ecouteur ec = new Ecouteur();

        nomEquipe.setOnItemSelectedListener(ec);
        btnReponse.setOnClickListener(ec);

        nbEquipe.setText((String.valueOf(instance.trouverNombre("Ouest"))));
        moyenne.setText(instance.trouverMoyenne());


    }

    private class Ecouteur implements View.OnClickListener, AdapterView.OnItemSelectedListener {
        @Override
        public void onClick(View v) {

            reponse.setText(instance.trouverEquipe(arenaSelectionner));
        }


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            arenaSelectionner = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        instance.fermerConnexion();
    }
}