package com.antoine.annexe4b;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView chamPass;

    LinearLayout main;

    String input = "";

    String test = "1234";

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
        //1er etape
        Ecouteur ec = new Ecouteur();

        //2e etape
        main = findViewById(R.id.main);
        chamPass = findViewById(R.id.txtPassword);
        for(int i = 0; i < main.getChildCount(); i++){ //Parcourir chaque LinearLayout
            LinearLayout ly = (LinearLayout)main.getChildAt(i);
            for(int j = 0; j < ly.getChildCount(); j++){//Pour chaque LinearLayout, parcourir pour trouver toutes les composantes(Bouton)
                View petit =  ly.getChildAt(j);
                if(petit instanceof Button){//Si c'est un bouton
                    ly.getChildAt(j).setOnClickListener(ec);
                }
            }
        }


    }

    private class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View source) {
            //View c'est la superclasse de toutes les composantes Android
            Button bouton = (Button)source; //On le transtype car on est certains que nos sources sont uniquement des Boutons
            input += bouton.getText().toString();//On le transtype en Button car on veut avoir accès au Texte

            if(input.length() < 4){
                chamPass.setText(input);
                main.setBackgroundColor(Color.WHITE);

            }
            else if(input.length() == 4){

                if(input.equals(test)){
                    main.setBackgroundColor(Color.GREEN);
                    Toast.makeText(MainActivity.this, "Vous avez trouver le code!!!", Toast.LENGTH_LONG).show();
                    chamPass.setText("");
                    input = "";
                }
                else{
                    main.setBackgroundColor(Color.RED);
                    Toast.makeText(MainActivity.this, "Vous vous êtes trompé!!!", Toast.LENGTH_LONG).show();
                    chamPass.setText("");
                    input = "";
                }
            }








        }
    }
}