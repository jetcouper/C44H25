package com.example.annexe13;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button ajouter;
    Button voir;



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
        ajouter = findViewById(R.id.btnAjouterEvaluation);
        voir = findViewById(R.id.btnMeilleurBiere);


        Ecouteur ec = new Ecouteur();

        ajouter.setOnClickListener(ec);
        voir.setOnClickListener(ec);

    }

    private class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            if(v == ajouter){
                Intent i = new Intent(MainActivity.this, EnregistrerBiereActivity.class);
                //i.putExtra() = Transféré des informations d'une page à une autre
                startActivity(i);
            }
            else if(v == voir){
                Intent i = new Intent(MainActivity.this, AfficherBiereActivity.class);
                startActivity(i);
            }
        }
    }
}