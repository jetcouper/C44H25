package com.example.applicationannexe11bcapitales;

import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;


import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import bla.HashtableAssociation;


public class MainActivity extends AppCompatActivity {

    EditText champPrenom, champNom, champAdresse, champZip;
    Spinner spinnerCapitale, spinnerEtat;

    Button bouton;
    Vector<String> capital;
    Vector<String> etat;
    HashtableAssociation hash = new HashtableAssociation();
    String stringCapital, stringEtat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        champPrenom = findViewById(R.id.champPrenom);
        champNom= findViewById(R.id.champNom);
        champAdresse = findViewById(R.id.champAdresse);
        champZip = findViewById(R.id.champZip);

        spinnerCapitale = findViewById(R.id.spinnerCapitale);
        spinnerEtat = findViewById(R.id.spinnerEtat);

        bouton = findViewById(R.id.boutonInscrire);


        // remplir les spinner à l'aide de la Hashtable
        capital = new Vector<>();
        etat = new Vector<>();

        List<String> tmp = Collections.list(hash.keys());
        Collections.sort(tmp);//Les trier dans le même ordre

        capital.addAll(hash.keySet());
        etat.addAll(hash.values());
        ArrayAdapter<String> adapterCapital = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,capital);
        ArrayAdapter<String> adapterEtat = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,etat);

        spinnerCapitale.setAdapter(adapterCapital);
        spinnerEtat.setAdapter(adapterEtat);

        //1ere étape
        Ecouteur ec = new Ecouteur();

        //2e étape
        bouton.setOnClickListener(ec);
        spinnerEtat.setOnItemSelectedListener(ec);
        spinnerCapitale.setOnItemSelectedListener(ec);



    }
    private class Ecouteur implements View.OnClickListener, AdapterView.OnItemSelectedListener {

        @Override
        public void onClick(View v) {

            try {
                Inscrit inscrit = new Inscrit(champNom.getText().toString(),champPrenom.getText().toString(),champAdresse.getText().toString(),stringCapital,stringEtat,champZip.getText().toString());
                Toast.makeText(MainActivity.this, "Inscription réussi.", Toast.LENGTH_SHORT).show();
            } catch (AdresseException e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }


        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if(parent == spinnerCapitale){
                String cleCapital = (String)spinnerCapitale.getItemAtPosition(position);
                stringCapital = cleCapital;
            }
            else if(parent == spinnerEtat){
                String cleEtat = (String)spinnerEtat.getItemAtPosition(position);
                stringEtat = cleEtat;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}