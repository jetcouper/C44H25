package com.example.tp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button demarrer;
    TextView meilleurScore;
    DatabaseHelper instance;


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
        //Initialiser mes composantes(Widget)
        demarrer = findViewById(R.id.btnDemarer);
        meilleurScore = findViewById(R.id.txtMeilleurScore);

        //Ouverture de la base de donnée
        instance = DatabaseHelper.getInstance(getApplicationContext());
        instance.ouvrirConnexion();

        Ecouteur ec = new Ecouteur();

        demarrer.setOnClickListener(ec);
        int imeilleur = 0;

        try {
            //Méthode pour obtenir le meilleur résultat de ma base de donnée
            imeilleur = instance.retournerMeilleurPointage();
            if(imeilleur != 0){
                //Afficher le meilleur pointage.
                meilleurScore.setText(String.valueOf(imeilleur));
            }
        } catch (Exception e) {
            //Affiche un message d'érreur s'il y a un problème.
            Toast.makeText(this, "Erreur: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        //Fermeture de la base de donnée
        instance.fermerConnexion();
    }

    private class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //Démarrer la partie(Jeu_Activity)
            if(v == demarrer) {
                Intent i = new Intent(MainActivity.this, Jeu_Activity.class);
                startActivity(i);
            }
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        //Fermer la page.
        finish();
    }
}