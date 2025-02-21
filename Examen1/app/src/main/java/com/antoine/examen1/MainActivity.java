package com.antoine.examen1;

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

import java.text.DecimalFormat;
import java.util.Hashtable;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerAge;
    Spinner spinnerPartie;

    Button boutonEnregistrer;
    TextView chamPartieBleu;
    TextView chamPartieVert;
    TextView chamPartieRouge;

    Vector<String> arrayAge;
    Vector<String> arrayPartie;

    String trancheAge;
    String partie;

    Sondage sondage;

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

        sondage = new Sondage();
        spinnerAge = findViewById(R.id.spinnerTrancheAge);
        spinnerPartie = findViewById(R.id.spinnerPartie);
        chamPartieBleu = findViewById(R.id.txtPartieBleu);
        chamPartieRouge = findViewById(R.id.txtPartieRouge);
        chamPartieVert = findViewById(R.id.txtPartieVert);
        boutonEnregistrer = findViewById(R.id.btnEnregistrer);
        chamPartieBleu.setText("0.0%");
        chamPartieRouge.setText("0.0%");
        chamPartieVert.setText("0.0%");

        arrayAge = new Vector<>();
        arrayAge.add("18-35");
        arrayAge.add("35-65");
        arrayAge.add("plus de 65");

        arrayPartie = new Vector<>();
        arrayPartie.add("Parti Bleu");
        arrayPartie.add("Parti Vert");
        arrayPartie.add("Parti Rouge");

        ArrayAdapter<String> adapterAge = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arrayAge);
        ArrayAdapter<String> adapterPartie = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arrayPartie);
        spinnerAge.setAdapter(adapterAge);
        spinnerPartie.setAdapter(adapterPartie);

        Ecouteur ec = new Ecouteur();

        boutonEnregistrer.setOnClickListener(ec);
        spinnerPartie.setOnItemSelectedListener(ec);
        spinnerAge.setOnItemSelectedListener(ec);
    }

    private class Ecouteur implements View.OnClickListener, AdapterView.OnItemSelectedListener {
        @Override
        public void onClick(View source) {
            Electeur electeur;
            DecimalFormat df = new DecimalFormat("0.0");
            String pourcentbleu = "";
            String pourcentRouge = "";
            String pourcentVert = "";


            if(source == boutonEnregistrer){
                electeur = new Electeur(partie,trancheAge);
                sondage.ajouterElecteur(electeur);

                pourcentbleu = df.format(sondage.calculTrancheAgePourcent("Parti Bleu"));
                pourcentVert = df.format(sondage.calculTrancheAgePourcent("Parti Vert"));
                pourcentRouge = df.format(sondage.calculTrancheAgePourcent("Parti Rouge"));

                chamPartieBleu.setText(pourcentbleu+"%");
                chamPartieVert.setText(pourcentVert + "%");
                chamPartieRouge.setText(pourcentRouge + "%");
                if(partie.equals("Parti Bleu")){
                    Toast.makeText(MainActivity.this, "Évaluation enregistrer!", Toast.LENGTH_LONG).show();
                } else if (partie.equals("Parti Vert")) {
                    Toast.makeText(MainActivity.this, "Évaluation enregistrer!", Toast.LENGTH_LONG).show();
                } else if (partie.equals("Parti Rouge")) {
                    Toast.makeText(MainActivity.this, "Évaluation enregistrer!", Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            trancheAge = (String)spinnerAge.getItemAtPosition(position);
            partie = (String)spinnerPartie.getItemAtPosition(position);

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}