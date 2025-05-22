package com.example.annexe14;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.chip.Chip;

public class MainActivity extends AppCompatActivity {

    LinearLayout main;


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

        main = findViewById(R.id.main);

        Ecouteur ec = new Ecouteur();

        for(int i = 0; i < main.getChildCount(); i++){
            LinearLayout colonne = (LinearLayout) main.getChildAt(i);
            colonne.setOnDragListener(ec);
            colonne.getChildAt(0).setOnTouchListener(ec);
        }
    }

    private class Ecouteur implements View.OnDragListener, View.OnTouchListener {

        Drawable normal = getResources().getDrawable(R.drawable.background_contenant, null);
        Drawable select = getResources().getDrawable(R.drawable.background_contenant_selectionne, null);
        View jeton = null;
        LinearLayout colonneOrigine = null;
        boolean aEteDepose = false;
        @Override
        public boolean onDrag(View source /*Colonne*/, DragEvent event) {

            switch (event.getAction()){
                case DragEvent.ACTION_DRAG_ENTERED:
                    source.setBackground(select);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    source.setBackground(normal);
                    break;
                case DragEvent.ACTION_DROP:
                    //Récupérer le jeton resté sur la colonne de départ
                    jeton = (View)event.getLocalState();
                    //Aller chercher le conteneur d'origine du jeton
                    colonneOrigine =  (LinearLayout)jeton.getParent();
                    //Retiner pour de bon le jeton invisible de sa colonne invisible
                    colonneOrigine.removeView(jeton);
                    //La nouvelle colonne
                    LinearLayout nouvelleColonne = (LinearLayout)source;
                    //Ajouter le jeton à la nouvelle colonne
                    nouvelleColonne.addView(jeton);
                    //Remettre le jeton visible
                    jeton.setVisibility(View.VISIBLE);

                    aEteDepose = true;// On indique que le jeton a bien été déposé ailleurs
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    source.setBackground(normal);

                    // Si le drag s'est terminé sans qu'il y ait eu de dépôt
                    if (!aEteDepose && jeton != null && colonneOrigine != null) {
                        if (jeton.getParent() != colonneOrigine) {
                            if (jeton.getParent() != null) {
                                ((LinearLayout) jeton.getParent()).removeView(jeton);
                            }
                            colonneOrigine.addView(jeton);
                        }
                        jeton.setVisibility(View.VISIBLE);
                    }
                    jeton = null;
                    colonneOrigine = null;
                    aEteDepose = false;
                    break;
            }

            return true;
        }

        @Override
        public boolean onTouch(View source/*Jeton*/, MotionEvent event) {//Déplacer le jeton

            jeton = source;
            colonneOrigine = (LinearLayout) jeton.getParent();
            aEteDepose = false;



            View.DragShadowBuilder builder = new View.DragShadowBuilder(source); //Créer une ombre du jeton
            source.startDragAndDrop(null/*Dans le tp on peut mettre le no. de la carte*/,builder,source, 0);
            source.setVisibility(View.INVISIBLE); //Cacher le jeton car on est en train de le déplacer
            return true;
        }
    }
}