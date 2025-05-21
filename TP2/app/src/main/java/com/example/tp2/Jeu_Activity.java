package com.example.tp2;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
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

public class Jeu_Activity extends AppCompatActivity {
    DatabaseHelper instance;
    Button menuPrincipale;
    LinearLayout main;
    Chronometer chrono;
    LinearLayout ligne1, ligne2;
    Partie partie;
    TextView nbCarte, score;
    String carte1 = "0",carte2= "0",carte3= "98",carte4= "98";
    Popup pop;
    String statue = "";

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
        partie = new Partie();
        partie.genererListe();
        pop = new Popup(Jeu_Activity.this);
        menuPrincipale = findViewById(R.id.btnRetourMenu);
        main = findViewById(R.id.main);
        ligne1 = findViewById(R.id.Linear1);
        ligne2 = findViewById(R.id.Linear2);
        nbCarte = findViewById(R.id.txtNbCarte);
        score = findViewById(R.id.txtScoreActuel);
        chrono = findViewById(R.id.chronometerTemps);
        chrono.setBase(SystemClock.elapsedRealtime());
        chrono.start();
        instance = DatabaseHelper.getInstance(getApplicationContext());
        instance.ouvrirConnexion();
        Ecouteur ec = new Ecouteur();

        appliquerListeners(findViewById(R.id.main), ec, ec);
        menuPrincipale.setOnClickListener(ec);
        partie.insererNombreDansCarte(ligne1,ligne2);
        nbCarte.setText(String.valueOf(partie.retournerNombreCarte()));
    }
    private void appliquerListeners(View view, View.OnDragListener dragListener, View.OnTouchListener touchListener) {
        if (view instanceof LinearLayout) {
            LinearLayout layout = (LinearLayout) view;

            int id = layout.getId();
            if (id != View.NO_ID) {
                String nom = getResources().getResourceEntryName(id);
                if (nom != null && !nom.isEmpty() && !nom.contains("Linear")) {

                    if (!"main".equals(nom)) {
                        layout.setOnDragListener(dragListener);
                    }

                    //Seuls les layouts "laX"(Carte a jouer) peuvent être déplacés (draggables)
                    if (nom.startsWith("la")) {
                        layout.setOnTouchListener(touchListener);
                    } else {
                        layout.setOnTouchListener(null);
                    }
                }
            }
        }
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
        ViewGroup parentOrigine = null;
        Boolean fini = false;

        @Override
        public boolean onDrag(View source, DragEvent event) {

            long time = SystemClock.elapsedRealtime() - chrono.getBase();

            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_ENTERED:
                    source.setBackground(select);
                    break;

                case DragEvent.ACTION_DRAG_EXITED:
                    source.setBackground(normal);
                    break;

                case DragEvent.ACTION_DROP:
                    if (carte != null && parentOrigine != null) {
                        //Identifier la zone cible(4 Carte du haut)
                        String nomDestination = getResources().getResourceEntryName(source.getId());
                        String noCarteOrigine = "";
                        if (carte instanceof LinearLayout){
                            TextView c = (TextView)((LinearLayout) carte).getChildAt(0);
                            noCarteOrigine = c.getText().toString();
                        }

                        //Vérifie si c'est une zone de drop valide(Si c'est l'une des cartes du haut)
                        boolean estDestinationValide = nomDestination.contains("lCarte");

                        //Remettre la carte à sa place d’origine
                        ViewGroup parentActuel = (ViewGroup) carte.getParent();
                        if (parentActuel != null && parentActuel != parentOrigine) {
                            parentActuel.removeView(carte);
                        }
                        if (carte.getParent() != parentOrigine) {
                            parentOrigine.addView(carte);
                        }

                        if (source instanceof LinearLayout){
                            TextView v = (TextView)((LinearLayout) source).getChildAt(0);
                            int differencielle = 0;
                            if(!v.getText().toString().isEmpty()){
                                differencielle = Integer.parseInt(v.getText().toString()) - Integer.parseInt(noCarteOrigine);
                            }
                            //Initialisation au début
                            if(v.getText().toString().isEmpty() && (nomDestination.equals("lCarte1")||nomDestination.equals("lCarte2")) ){
                                v.setText("0");
                            }
                            if(v.getText().toString().isEmpty() && (nomDestination.equals("lCarte3")||nomDestination.equals("lCarte4")) ){
                                v.setText("98");
                            }
                            //Vérifier si les nombres des cartes d'origines sont supérieur ou inférieur à ce qui est demander
                            if(Integer.parseInt(v.getText().toString()) < Integer.parseInt(noCarteOrigine) && nomDestination.equals("lCarte1")  || (differencielle == 10 || differencielle == -10)){
                                v.setText(noCarteOrigine);
                                carte1 = noCarteOrigine;
                                TextView c = (TextView)((LinearLayout) carte).getChildAt(0);
                                partie.retirerCarte(Integer.parseInt(noCarteOrigine));
                                nbCarte.setText(String.valueOf(partie.retournerNombreCarte()));
                                partie.appliquerPoint(time);
                                c.setText("");
                            }
                            else if(Integer.parseInt(v.getText().toString()) < Integer.parseInt(noCarteOrigine) && nomDestination.equals("lCarte2")  || (differencielle == 10 || differencielle == -10)){
                                v.setText(noCarteOrigine);
                                carte2 = noCarteOrigine;
                                TextView c = (TextView)((LinearLayout) carte).getChildAt(0);
                                partie.retirerCarte(Integer.parseInt(noCarteOrigine));
                                nbCarte.setText(String.valueOf(partie.retournerNombreCarte()));
                                partie.appliquerPoint(time);
                                c.setText("");
                            }
                            else if(Integer.parseInt(v.getText().toString()) > Integer.parseInt(noCarteOrigine) && nomDestination.equals("lCarte3") || (differencielle == 10 || differencielle == -10) ){
                                v.setText(noCarteOrigine);
                                carte3 = noCarteOrigine;
                                TextView c = (TextView)((LinearLayout) carte).getChildAt(0);
                                partie.retirerCarte(Integer.parseInt(noCarteOrigine));
                                nbCarte.setText(String.valueOf(partie.retournerNombreCarte()));
                                partie.appliquerPoint(time);
                                c.setText("");
                            }
                            else if(Integer.parseInt(v.getText().toString()) > Integer.parseInt(noCarteOrigine) && nomDestination.equals("lCarte4")  || (differencielle == 10 || differencielle == -10)){
                                v.setText(noCarteOrigine);
                                carte4 = noCarteOrigine;
                                TextView c = (TextView)((LinearLayout) carte).getChildAt(0);
                                partie.retirerCarte(Integer.parseInt(noCarteOrigine));
                                nbCarte.setText(String.valueOf(partie.retournerNombreCarte()));
                                partie.appliquerPoint(time);
                                c.setText("");
                            }
                            else{
                                estDestinationValide = false;
                            }
                            //visible ou invisible selon la contrainte boolean plus tôt
                            if (estDestinationValide) {
                                carte.setVisibility(View.INVISIBLE);
                            } else {
                                carte.setVisibility(View.VISIBLE);
                            }
                            score.setText(String.valueOf(partie.getScore()));

                            if(partie.compter8Carte() == 2 || partie.retournerNombreCarte() > 7){
                                partie.verifier2Carte(ligne1,ligne2);
                            }



                            fini = partie.partieTerminer(carte1,carte2,carte3,carte4);
                            //Validation si la partie est Fini
                            if (fini) {
                                if(partie.retournerNombreCarte() == 0){
                                    statue = "Réussi";
                                }
                                else{
                                    statue = "Défaite";
                                }
                                pop.show();
                                if(!pop.isShowing()){
                                    Intent i = new Intent(Jeu_Activity.this, Fin_Activity.class);
                                    Pointage point = new Pointage(partie.getScore());
                                    instance.ajouterPointage(point);
                                    startActivity(i);
                                    finish();
                                }

                            }

                        }

                    }
                    break;

                case DragEvent.ACTION_DRAG_ENDED:
                    source.setBackground(normal);

                    //Si le drop n'est pas dans la bonne zone : drop dans le vide
                    if (!event.getResult() && carte != null && parentOrigine != null) {
                        //Remettre à la position d’origine
                        ViewGroup parentActuel = (ViewGroup) carte.getParent();
                        if (parentActuel != null && parentActuel != parentOrigine) {
                            parentActuel.removeView(carte);
                        }
                        if (carte.getParent() != parentOrigine) {
                            parentOrigine.addView(carte);
                        }
                        //Garder la carte reste visible, s'il n'a pas atterrit
                        carte.setVisibility(View.VISIBLE);
                    }
                    break;
            }
            return true;
        }

        @Override
        public boolean onTouch(View source, MotionEvent event) {

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                carte = source;
                parentOrigine = (ViewGroup) source.getParent();

                String numero = "";
                ClipData clip = null;

                if (source instanceof LinearLayout) {
                    LinearLayout layout = (LinearLayout) source;
                    for (int i = 0; i < layout.getChildCount(); i++) {
                        View child = layout.getChildAt(i);
                        if (child instanceof TextView) {
                            numero = ((TextView) child).getText().toString();
                            clip = ClipData.newPlainText("label", numero);
                            break;
                        }
                    }
                }

                View.DragShadowBuilder builder = new View.DragShadowBuilder(source);
                source.startDragAndDrop(clip, builder, source, 0);
                source.setVisibility(View.INVISIBLE);
                return true;
            }
            return false;
        }
        @Override
        public void onClick(View v) {
            Intent i = new Intent(Jeu_Activity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        instance.fermerConnexion();
    }


}