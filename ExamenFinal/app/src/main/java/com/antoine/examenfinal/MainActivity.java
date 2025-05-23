package com.antoine.examenfinal;

import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    LinearLayout couleurG,couleurC,couleurD;
    LinearLayout couleursLayout;
    LinearLayout couleurDrapeau;
    TextView txtQuestion;
    String couleurSelectionner;
    Button confirmer;

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

        couleursLayout = findViewById(R.id.conteneurCouleurs);
        couleurDrapeau = findViewById(R.id.conteneurDrapeau);
//        couleurC = findViewById(R.id.conteneurC);
//        couleurD = findViewById(R.id.conteneurD);
//        couleurG = findViewById(R.id.conteneurG);
//        bleu = findViewById(R.id.bleu);
//        blanc = findViewById(R.id.blanc);
//        rouge = findViewById(R.id.rouge);
//        noir = findViewById(R.id.noir);
//        jaune = findViewById(R.id.jaune);
        confirmer = findViewById(R.id.boutonConfirmer);
        txtQuestion = findViewById(R.id.texteQuestion);


        Ecouteur ec = new Ecouteur();
        confirmer.setOnClickListener(ec);

        for (int i = 0; i < couleursLayout.getChildCount(); i++) {
            TextView textCouleur = (TextView) couleursLayout.getChildAt(i);
            textCouleur.setOnTouchListener(ec);
        }
        for (int i = 0; i < couleurDrapeau.getChildCount(); i++) {
            LinearLayout couleur = (LinearLayout) couleurDrapeau.getChildAt(i);
            couleur.setOnDragListener(ec);
        }


    }

    private class Ecouteur implements View.OnClickListener, View.OnTouchListener, View.OnDragListener {

        TextView couleur = null;
        LinearLayout colonneOrigine = null;
        boolean aEteDepose = false;
        @Override
        public boolean onDrag(View source, DragEvent event) {

            switch (event.getAction()){
                case DragEvent.ACTION_DRAG_ENTERED:
                    //source.setBackground(select);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //source.setBackground(normal);
                    break;
                case DragEvent.ACTION_DROP:
                    //Récupérer le jeton resté sur la colonne de départ
                    couleur = (TextView) event.getLocalState();
                    couleurSelectionner = couleur.getBackground().toString();
                    //Aller chercher le conteneur d'origine du jeton
                    //colonneOrigine =  (LinearLayout)jeton.getParent();
                    //Retiner pour de bon le jeton invisible de sa colonne invisible
                    //colonneOrigine.removeView(jeton);
                    //La nouvelle colonne
                    LinearLayout nouvelleColonne = (LinearLayout)source;
                    //Ajouter le jeton à la nouvelle colonne

                    //nouvelleColonne.setBackground(couleurSelectionner);
                    //nouvelleColonne.addView(jeton);
                    //Remettre le jeton visible
                    //jeton.setVisibility(View.VISIBLE);

                    aEteDepose = true;// On indique que le jeton a bien été déposé ailleurs
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //source.setBackground(normal);

                    // Si le drag s'est terminé sans qu'il y ait eu de dépôt
//                    if (!aEteDepose && couleur != null && colonneOrigine != null) {
//                        if (couleur.getParent() != colonneOrigine) {
//                            if (couleur.getParent() != null) {
//                                ((LinearLayout) couleur.getParent()).removeView(couleur);
//                            }
//                            colonneOrigine.addView(jeton);
//                        }
//                        jeton.setVisibility(View.VISIBLE);
//                    }
                    couleur = null;
                    colonneOrigine = null;
                    aEteDepose = false;
                    break;
            }


            return false;
        }
        @Override
        public void onClick(View v) {



        }

        @Override
        public boolean onTouch(View v /*Text*/, MotionEvent event) {
            couleur = (TextView)v;
            colonneOrigine = (LinearLayout) couleur.getParent();
            aEteDepose = false;

            View.DragShadowBuilder builder = new View.DragShadowBuilder(v);
            v.startDragAndDrop(null,builder,v, 0);
            return true;
        }


    }
}