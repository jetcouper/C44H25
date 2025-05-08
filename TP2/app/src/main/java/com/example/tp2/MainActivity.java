package com.example.tp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

        demarrer = findViewById(R.id.btnDemarer);

        meilleurScore = findViewById(R.id.txtMeilleurScore);

        instance = DatabaseHelper.getInstance(getApplicationContext());
        instance.ouvrirConnexion();

        Ecouteur ec = new Ecouteur();

        demarrer.setOnClickListener(ec);
        int imeilleur = 0;

        try {
            imeilleur = instance.retournerMeilleurPointage();
            if(imeilleur == 0){
                meilleurScore.setText(imeilleur);
                finish();
                return;
            }
        } catch (Exception e) {
            Toast.makeText(this, "Erreur: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
            return;
        }


    }

    private class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            if(v == demarrer) {
                Intent i = new Intent(MainActivity.this, Jeu.class);
                startActivity(i);
            }
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        instance.fermerConnexion();
    }
}