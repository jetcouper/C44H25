package com.antoine.examenfinal;

import android.graphics.drawable.Drawable;
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

    SingletonDrapeaux instance;
    LinearLayout couleurG,couleurC,couleurD;
    LinearLayout couleursLayout;
    LinearLayout couleurDrapeau;
    TextView txtQuestion;
    String couleurSelectionnerG;
    String couleurSelectionnerC;
    String couleurSelectionnerD;
    String pays = "";
    Button confirmer;
    int id;

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
        instance = SingletonDrapeaux.getInstance(getApplicationContext());
        instance.ouvrirConnexion();
        couleursLayout = findViewById(R.id.conteneurCouleurs);
        couleurDrapeau = findViewById(R.id.conteneurDrapeau);
        couleurC = findViewById(R.id.conteneurC);
        couleurD = findViewById(R.id.conteneurD);
        couleurG = findViewById(R.id.conteneurG);
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
//        for (int i = 0; i < couleurDrapeau.getChildCount(); i++) {
//            LinearLayout couleur = (LinearLayout) couleurDrapeau.getChildAt(i);
//            couleur.setOnDragListener(ec);
//        }
        pays = instance.retournerHasard();
        txtQuestion.setText(("Dessinez le drapeau : " + pays));
        couleurC.setOnDragListener(ec);
        couleurG.setOnDragListener(ec);
        couleurD.setOnDragListener(ec);

    }

    private class Ecouteur implements View.OnClickListener, View.OnTouchListener, View.OnDragListener {

        TextView couleur = null;
        LinearLayout colonneOrigine = null;
        boolean aEteDepose = false;
        @Override
        public boolean onDrag(View source, DragEvent event) {

            if (event.getAction() == DragEvent.ACTION_DROP) {//Récupérer le jeton resté sur la colonne de départ
                couleur = (TextView) event.getLocalState();
                Drawable s = couleur.getBackground();

                LinearLayout nouvelleColonne = (LinearLayout) source;
                nouvelleColonne.setBackground(s);
            }


            return true;
        }
        @Override
        public void onClick(View v) {

            boolean estPays = false;
            String scouleurG = "";
            String scouleurC = "";
            String scouleurD = "";
            String i = "";

            couleurSelectionnerC = String.valueOf(couleurC.getId());

            //i = getResources().getResourceName(i);
            //String ss= couleurC.getId();
            i = (String.valueOf(couleurC.getBackground()));
            scouleurC = String.valueOf(i);

            estPays = instance.verifierPays(scouleurG,scouleurC,scouleurD,pays);

            if(estPays){
                txtQuestion.setText("Bravo!");
            }
            else {
                txtQuestion.setText("dsl");
            }


        }

        @Override
        public boolean onTouch(View source /*Text*/, MotionEvent event) {
            couleur = (TextView)source;
            colonneOrigine = (LinearLayout) couleur.getParent();
            aEteDepose = false;

            View.DragShadowBuilder builder = new View.DragShadowBuilder(source);
            source.startDragAndDrop(null,builder,source, 0);
            return true;
        }


    }
    @Override
    protected void onStop() {
        super.onStop();
        instance.fermerConnexion();
    }
}