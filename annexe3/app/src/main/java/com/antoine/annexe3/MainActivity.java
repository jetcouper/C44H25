package com.antoine.annexe3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView chamLitreRestant;

    ImageView btnBidon;
    ImageView btnBouteille;
    ImageView btnVerre;
    ProgressBar prLitre;


    int ilitreRestantMl; //Quantité d'eau consommée
    int bidonMl;
    int bouteilleMl;
    int verreMl;



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

        chamLitreRestant = findViewById(R.id.txtMl);
        btnBidon = findViewById(R.id.imgBidon);
        btnBouteille = findViewById(R.id.imgBouteille);
        btnVerre = findViewById(R.id.imgVerre);
        prLitre = findViewById(R.id.progressBar);

        bidonMl = 1500;
        verreMl = 150;
        bouteilleMl = 330;
        prLitre.setMax(2000);
        chamLitreRestant.setText("0");
        //1ere étape
        Ecouteur ec = new Ecouteur();

        //2e étape
        btnVerre.setOnClickListener(ec);
        btnBouteille.setOnClickListener(ec);
        btnBidon.setOnClickListener(ec);


    }

    private class Ecouteur implements View.OnClickListener{


        @Override
        public void onClick(View source) {




            if (source == btnBidon){
                if (ilitreRestantMl < (2000 - bidonMl)){
                    ilitreRestantMl += bidonMl;
                    chamLitreRestant.setText(ilitreRestantMl+ "ml");
                    prLitre.setProgress(ilitreRestantMl);
                }
                else{
                    Toast.makeText(MainActivity.this, "Vous buvez trop d'eau!!!", Toast.LENGTH_LONG).show();
                }
            }
            if (source == btnBouteille){
                if (ilitreRestantMl < (2000 - bouteilleMl)){
                    ilitreRestantMl += bouteilleMl;
                    chamLitreRestant.setText(ilitreRestantMl+ "ml");
                    prLitre.setProgress(ilitreRestantMl);
                }
                else{
                    Toast.makeText(MainActivity.this, "Vous buvez trop d'eau!!!", Toast.LENGTH_LONG).show();
                }
            }
            if (source == btnVerre){
                if (ilitreRestantMl < (2000 - verreMl)){
                    ilitreRestantMl += verreMl;
                    chamLitreRestant.setText(ilitreRestantMl+ "ml");
                    prLitre.setProgress(ilitreRestantMl);
                }
                else{
                    Toast.makeText(MainActivity.this, "Vous buvez trop d'eau!!!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}