package com.antoine.annexe3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Vector;

public class voyageActivity extends AppCompatActivity {


    TextView chamTotalAvion;
    TextView chamTotalHotel;
    TextView chamTotal;
    ImageView boutonAvion;
    ImageView boutonHotel;
    Button boutonTotal;

    int iQuantiteAvion;
    int iQuantiteHotel;
    double prixTotal;
    Vector<String> produit;

    Commande commande;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_voyage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        commande = new Commande();
        chamTotalAvion = findViewById(R.id.txtTotalAvion);
        chamTotalHotel = findViewById(R.id.txtTotalHotel);
        chamTotal = findViewById(R.id.txtTotal);
        boutonAvion = findViewById(R.id.imgAvion);
        boutonHotel = findViewById(R.id.imgHotel);
        boutonTotal = findViewById(R.id.btnTotal);

        //1ere étape
        voyageActivity.Ecouteur ec = new MainActivity.Ecouteur();

        //2e étape
        boutonTotal.setOnClickListener(ec);
        boutonHotel.setOnClickListener(ec);
        boutonAvion.setOnClickListener(ec);



    }

    private class Ecouteur implements View.OnClickListener{
        @Override
        public void onClick(View source) {

            if (source == boutonAvion){


                //commande.ajouterProduit();

            }



            if (source == boutonTotal){

            }



        }
    }
}