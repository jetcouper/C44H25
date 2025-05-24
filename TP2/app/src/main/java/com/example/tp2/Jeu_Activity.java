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
    Button menuPrincipale, reverse;
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
        //Initialisation de la classe de la partie et de ses composantes.
        partie = new Partie();
        //Va générer la liste de carte du jeux.
        partie.genererListe();
        //Création d'une fenêtre PopUp pour afficher la fin de partie.
        pop = new Popup(Jeu_Activity.this);
        //Initialisation des éléments principaux(Widget).
        menuPrincipale = findViewById(R.id.btnRetourMenu);
        reverse = findViewById(R.id.btnReverse);
        main = findViewById(R.id.main);
        ligne1 = findViewById(R.id.Linear1);
        ligne2 = findViewById(R.id.Linear2);
        nbCarte = findViewById(R.id.txtNbCarte);
        score = findViewById(R.id.txtScoreActuel);
        chrono = findViewById(R.id.chronometerTemps);
        //Initialisation du chronomètre
        chrono.setBase(SystemClock.elapsedRealtime());
        chrono.start();
        //Initialisation de la base de donnée
        instance = DatabaseHelper.getInstance(getApplicationContext());
        instance.ouvrirConnexion();
        Ecouteur ec = new Ecouteur();

        //Désactivation du bouton qui sert à annuler le dernier coup.
        reverse.setEnabled(false);

        //Méthode pour appliqué mes Listerner aux bonne place
        appliquerListeners(findViewById(R.id.main), ec, ec);
        menuPrincipale.setOnClickListener(ec);
        reverse.setOnClickListener(ec);

        //Va insérer les nombre dans toutes les cartes de départ.
        partie.insererNombreDansCarte(ligne1,ligne2);
        //Le nombre de carte disponible dans le jeux
        nbCarte.setText(String.valueOf(partie.retournerNombreCarte()));
    }

    //Méthode pouvant appliquer les listener aux bonnex vue.
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
        //Les resources Drawable qui change pour les quatres piles.
        Drawable normal = getResources().getDrawable(R.drawable.bg_card, null);
        Drawable select = getResources().getDrawable(R.drawable.bg_card_selectionne, null);

        //La carte sélectionné dans la zone des 8 cartes.
        View carte = null;
        //La position d'origine de la carte si elle n'est pas placé correctement.
        ViewGroup parentOrigine = null;
        //Si la partie est fini.
        Boolean fini = false;

        @Override
        public boolean onDrag(View source, DragEvent event) {
            //Pour obtenir la valeur du chrono.
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
                            //Le différencielle représentant la différence de nombre entre ma carte(noCarteOrigine) choisie et le numéro de la pile(source) sélectionné dans l'une de mes quatre piles.
                            int differencielle = 0;
                            if(!v.getText().toString().isEmpty()){
                                differencielle = Integer.parseInt(v.getText().toString()) - Integer.parseInt(noCarteOrigine);
                            }
                            //Initialisation au début de la partie pour dire que les cartes valent 0 ou 98 au début de la partie.
                            if(v.getText().toString().isEmpty() && (nomDestination.equals("lCarte1")||nomDestination.equals("lCarte2")) ){
                                v.setText("0");
                            }
                            if(v.getText().toString().isEmpty() && (nomDestination.equals("lCarte3")||nomDestination.equals("lCarte4")) ){
                                v.setText("98");
                            }
                            boolean placementReussi = partie.essayerPlacerCarte(
                                    source, nomDestination, carte, noCarteOrigine, time, nbCarte, score, reverse
                            );


                            //Vérifier si les nombres des cartes d'origines sont supérieur ou inférieur à ce qui est demander
                            if (placementReussi) {
                                if (nomDestination.equals("lCarte1"))
                                    carte1 = noCarteOrigine;

                                if (nomDestination.equals("lCarte2"))
                                    carte2 = noCarteOrigine;

                                if (nomDestination.equals("lCarte3"))
                                    carte3 = noCarteOrigine;

                                if (nomDestination.equals("lCarte4"))
                                    carte4 = noCarteOrigine;

                                carte.setVisibility(View.INVISIBLE);
                            } else {
                                carte.setVisibility(View.VISIBLE);
                            }
                            score.setText(String.valueOf(partie.getScore()));

                            //Vérifier s'il y a 2 carte manquante dans le paquet, si oui, en remettre 2.
                            if(partie.compter8Carte() == 2 || partie.retournerNombreCarte() > 7){
                                partie.verifier2Carte(ligne1,ligne2);

                            }
                            //Si le nombre de carte présent dans le bas du jeu est de 8, alors désactiver le bouton reverse qui sert à annuler le dernier coup.
                            if(partie.retournerNombre8Carte() == 8 || partie.retournerNombreCarte() < 8){
                                reverse.setEnabled(false);
                            }


                            //Méthode pour savoir si la partie est terminé.
                            fini = partie.partieTerminer(carte1,carte2,carte3,carte4);
                            //Validation si la partie est Fini
                            if (fini) {
                                if(partie.retournerNombreCarte() == 0){
                                    statue = "Réussi \r\n Avec un score de :" + partie.getScore();
                                }
                                else{
                                    statue = "Défaite \r\n Avec un score de :" + partie.getScore();
                                }
                                pop.show();

                            }

                        }

                    }
                    break;

                case DragEvent.ACTION_DRAG_ENDED:
                    source.setBackground(normal);

                    //Si le drop n'est pas dans la bonne zone : drop dans le vide
                    if (!event.getResult() && carte != null && parentOrigine != null) {
                        // Sécuriser via post pour éviter la modification durant la phase de layout/événement(Des érreurs se produisent avec java.util.ConcurrentModificationException, si je ne fait pas ça)
                        carte.post(() -> {
                            ViewGroup parentActuel = (ViewGroup) carte.getParent();

                            if (parentActuel != null && parentActuel != parentOrigine) {
                                parentActuel.removeView(carte);
                            }

                            if (carte.getParent() != parentOrigine) {
                                parentOrigine.addView(carte);
                            }

                            carte.setVisibility(View.VISIBLE);
                        });
                    }
                    break;
            }
            return true;
        }

        @Override
        public boolean onTouch(View source, MotionEvent event) {

            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                //Garder la carte en mémoire.
                carte = source;
                //Garder le lieux d'origine de la carte.
                parentOrigine = (ViewGroup) source.getParent();

                //Pour garder le numéro de la carte.
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

            //Si je veux retourner au menu principale.
            if(v == menuPrincipale){
                Intent i = new Intent(Jeu_Activity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
            //Si je veux annuler le dernier coup.
            else if (v == reverse) {
                partie.annulerDernierCoup(ligne1,ligne2, findViewById(R.id.main));
                score.setText(String.valueOf(partie.getScorePrecedant()));
                partie.setScore(partie.getScorePrecedant());
                nbCarte.setText(String.valueOf(partie.retournerNombreCarte()));
                reverse.setEnabled(false);
            }


        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        //Fermeture de la base de donnée
        instance.fermerConnexion();
    }


}