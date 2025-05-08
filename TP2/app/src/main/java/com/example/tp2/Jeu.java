package com.example.tp2;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Jeu extends AppCompatActivity {
    DatabaseHelper instance;
    Button menuPrincipale;
    TextView txtNbCarte, txtScoreActuel,txtCarteHG,txtCarteHD,txtCarteBG,txtCarteBD,txtC1,txtC2,txtC3,txtC4,txtC5,txtC6,txtC7,txtC8;
    LinearLayout main,lHG,lHD,lBG,lBD,lC1,lC2,lC3,lC4,lC5,lC6,lC7,lC8;
    Chronometer chrono;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_jeu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        menuPrincipale = findViewById(R.id.btnRetourMenu);
        main = findViewById(R.id.main);

        instance = DatabaseHelper.getInstance(getApplicationContext());
        instance.ouvrirConnexion();
        Ecouteur ec = new Ecouteur();


        for(int i = 0; i < main.getChildCount(); i++){
            LinearLayout colonne = (LinearLayout) main.getChildAt(i);
            colonne.setOnDragListener(ec);
            colonne.getChildAt(0).setOnTouchListener(ec);
        }





    }
    private class Ecouteur implements View.OnDragListener, View.OnTouchListener {
        Drawable normal = getResources().getDrawable(R.drawable.bg_card, null);
        Drawable select = getResources().getDrawable(R.drawable.bg_card_selectionne, null);

        View carte = null;

        @Override
        public boolean onDrag(View v, DragEvent event) {


            return true;
        }

        @Override
        public boolean onTouch(View source, MotionEvent event) {

            View.DragShadowBuilder builder = new View.DragShadowBuilder(source); //Créer une ombre du jeton
            source.startDragAndDrop(null/*Dans le tp on peut mettre le no. de la carte*/,builder,source, 0);
            source.setVisibility(View.INVISIBLE); //Cacher le jeton car on est en train de le déplacer

            return true;
        }
    }




    @Override
    protected void onStop() {
        super.onStop();
        instance.fermerConnexion();
    }


}