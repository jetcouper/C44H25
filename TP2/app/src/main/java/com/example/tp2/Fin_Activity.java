package com.example.tp2;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Vector;

public class Fin_Activity extends AppCompatActivity {

    ListView listPoint;
    Button quitter;
    DatabaseHelper instance;
    LinearLayout main;

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
        main = findViewById(R.id.main);
        AnimationDrawable animationDrawable = (AnimationDrawable) main.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();


        //Initialiser mes composantes(Widget)
        listPoint = findViewById(R.id.listView);
        quitter = findViewById(R.id.btnMenu);
        //Ouverture de la base de donnée
        instance = DatabaseHelper.getInstance(getApplicationContext());

        //Création d'un vecteur pour recevoir tout les pointages de la BD.
        Vector<String> v = null;
        try {
            v = instance.retourerPointages();
            //Fermeture de la BD.
            instance.fermerConnexion();
            if (v == null || v.isEmpty()) {
                //Affiche un message au cas où il n'y a pas d'items
                Toast.makeText(this, "Il n'y a pas d'item.", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        } catch (Exception e) {
            //Affiche un message au cas où il y a eu une erreur.
            Toast.makeText(this, "Erreur: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            //Fermeture de la page.
            finish();
            return;
        }
        //Va placer tout les éléments obtenue de ma base de données dans mon ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, v);
        listPoint.setAdapter(adapter);


        Ecouteur ec = new Ecouteur();
        
        quitter.setOnClickListener(ec);


    }


    @Override
    protected void onStop() {
        super.onStop();
        //Fermeture de la page.
        finish();
    }

    private class Ecouteur implements View.OnClickListener {


        @Override
        public void onClick(View v) {
            //Va fermer ma page pour retourner au menu principale.
            Intent i = new Intent(Fin_Activity.this, MainActivity.class);
            startActivity(i);

        }
    }
}