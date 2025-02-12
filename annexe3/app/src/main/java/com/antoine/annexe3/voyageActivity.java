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

import java.text.DecimalFormat;
import java.util.Vector;

public class voyageActivity extends AppCompatActivity {


    TextView chamTotalAvion;
    TextView chamTotalHotel;
    TextView chamTotal;
    ImageView boutonAvion;
    ImageView boutonHotel;
    Button boutonTotal;
    Button boutonReset;
    int iQuantiteAvion;
    int iQuantiteHotel;

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
        chamTotalAvion.setText("0");
        chamTotalHotel.setText("0");
        boutonReset = findViewById(R.id.btnReset);



        //1ere étape
        Ecouteur ec = new Ecouteur();

        //2e étape
        boutonTotal.setOnClickListener(ec);
        boutonHotel.setOnClickListener(ec);
        boutonAvion.setOnClickListener(ec);
        boutonReset.setOnClickListener(ec);



    }

    private class Ecouteur implements View.OnClickListener{
        @Override
        public void onClick(View source) {

            DecimalFormat df = new DecimalFormat("0.00$"); //DecimalFormat pour l'argent, deux chiffres après la virgule.

            if (source == boutonAvion){
                commande.ajouterProduit(new BilletAvion());
                iQuantiteAvion++;
                chamTotalAvion.setText(Integer.toString(iQuantiteAvion));
            }
            if (source == boutonHotel){
                commande.ajouterProduit(new HebergementHotel());
                iQuantiteHotel++;
                chamTotalHotel.setText(Integer.toString(iQuantiteHotel));
                }
            if(source == boutonReset){
                commande = new Commande();
                iQuantiteAvion = 0;
                iQuantiteHotel = 0;
                chamTotalHotel.setText(Integer.toString(iQuantiteHotel));
                chamTotalAvion.setText(Integer.toString(iQuantiteAvion));
                chamTotal.setText(df.format(commande.grandTotal()));

            }


            if (source == boutonTotal){
                chamTotal.setText(df.format(commande.grandTotal()));
            }

        }
    }
}