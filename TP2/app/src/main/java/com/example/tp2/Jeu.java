package com.example.tp2;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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

//        lC1 = findViewById(R.id.la1);
//        lC2 = findViewById(R.id.la2);
//        lC3 = findViewById(R.id.la3);
//        lC4 = findViewById(R.id.la4);
//        lC5 = findViewById(R.id.la5);
//        lC6 = findViewById(R.id.la6);
//        lC7 = findViewById(R.id.la7);
//        lC8 = findViewById(R.id.la8);



//        for(int i = 0; i < main.getChildCount(); i++){
//            LinearLayout colonne = (LinearLayout) main.getChildAt(i);
//            String nom = getResources().getResourceName(colonne.getId());
//            if(nom.contains("la")){
//                colonne.setOnDragListener(ec);
//                colonne.getChildAt(0).setOnTouchListener(ec);
//            }
//
//        }


        appliquerListeners(findViewById(R.id.main), ec, ec);
        menuPrincipale.setOnClickListener(ec);


    }
    private void appliquerListeners(View view, View.OnDragListener dragListener, View.OnTouchListener touchListener) {
        if (view instanceof LinearLayout) {
            LinearLayout layout = (LinearLayout) view;

            int id = layout.getId();
            if (id != View.NO_ID) {
                try {
                    String nom = getResources().getResourceEntryName(id);
                    if (nom != null && !nom.isEmpty() && nom.contains("la")) {
                        layout.setOnDragListener(dragListener);
                        layout.setOnTouchListener(touchListener);  // <- C'est ici qu'on met le OnTouchListener
                    }
                } catch (Resources.NotFoundException ignored) {}
            }
        }

        // Explorer récursivement les enfants
        if (view instanceof ViewGroup) {
            ViewGroup groupe = (ViewGroup) view;
            for (int i = 0; i < groupe.getChildCount(); i++) {
                appliquerListeners(groupe.getChildAt(i), dragListener, touchListener);
            }
        }
    }



    private class Ecouteur implements View.OnDragListener, View.OnTouchListener, View.OnClickListener {
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

        @Override
        public void onClick(View v) {
            finish();
        }
    }




    @Override
    protected void onStop() {
        super.onStop();
        instance.fermerConnexion();
    }


}