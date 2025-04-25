package com.example.annexe13;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EnregistrerBiereActivity extends AppCompatActivity {

    TextView nomBiere;
    TextView microbrasserie;
    RatingBar etoileEvaluation;
    Button enregistrer;
    DatabaseHelper instance;

    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_enregistrer_biere);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        nomBiere = findViewById(R.id.txtNomBiere);
        microbrasserie = findViewById(R.id.txtMicrobrasserie);
        etoileEvaluation = findViewById(R.id.ratingEtoile);


        enregistrer = findViewById(R.id.btnEnregistrer);

        instance = DatabaseHelper.getInstance(getApplicationContext()); // Pas this car le singleton est vivant pour toute l'application

        instance.ouvrirConnexion();


        Ecouteur ec = new Ecouteur();

        enregistrer.setOnClickListener(ec);



    }

    private class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            instance.ajouterEvaluation(new Evaluation(nomBiere.getText().toString(),microbrasserie.getText().toString(),(double)(etoileEvaluation.getRating())));
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        instance.fermerConnexion();
    }

}