package com.antoine.tp1;
import static android.view.MotionEvent.ACTION_UP;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    LinearLayout liOptions, liDessin;
    ChipGroup liCouleur;
    SurfaceDessin surf;
    List<Dessin> listDessins;
    List<Dessin> listPrimaire;
    List<Dessin> listSecondaire;

    Dessin dessin;
    Path pathDessin;
    Effacer effacer;
    float coordX, coordY;
    Enregistrer enregistrer;
    int epaisseurCrayon, couleurBackground, nomCouleur = 0;
    Crayon crayon;
    DialogLargeur dialog;
    Cercle cercle;
    Rectangle rectangle;
    Triangle triangle;
    Pipette pipette;
    //Va me permettre de définir quel type de dessin ou de fonction est choisie
    boolean estTriangle, estRectangle, estCercle,estCrayon, estEfface,estPipette,estPotPeinture;

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
        liDessin = findViewById(R.id.linearDessin);
        liCouleur = findViewById(R.id.linearCouleur);
        dialog = new DialogLargeur(MainActivity.this);
        listDessins = new ArrayList<Dessin>();
        listSecondaire = new ArrayList<Dessin>();
        epaisseurCrayon = 10;
        liDessin.setBackgroundColor(getResources().getColor(R.color.blanc,null));
        couleurBackground = getResources().getColor(R.color.blanc,null);
        //Initialiser le crayon au démarrage
        estCrayon = true;
        surf = new SurfaceDessin(this);
        surf.setLayoutParams(new ViewGroup.LayoutParams(-1,-1));
        liDessin.addView(surf);
        Ecouteur ec = new Ecouteur();

        //Initialisation des Chips et des ImageViews
        for(int i = 0; i < liCouleur.getChildCount(); i++){
            View petit = liCouleur.getChildAt(i);
            if(petit instanceof Chip){
                ((Chip)liCouleur.getChildAt(i)).setOnCheckedChangeListener(ec);
            }
        }
        liOptions = findViewById(R.id.linearImages);
        for(int i = 0; i < liOptions.getChildCount(); i++){
            View petit = liOptions.getChildAt(i);
            if(petit instanceof ImageView){
                liOptions.getChildAt(i).setOnClickListener(ec);
            }
        }
        surf.setOnTouchListener(ec);
    }
    private class SurfaceDessin extends View{
        public SurfaceDessin(Context context) {
            super(context);
            pathDessin = new Path();
        }
        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);
            //Affiche en permanence la liste des dessins enregistré
            for(Dessin d : listDessins){
                d.dessiner(canvas);
            }
            if (dessin != null)
                dessin.dessiner(canvas);
        }
    }
    private class Ecouteur implements View.OnTouchListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener {
        @Override
        public boolean onTouch(View source, MotionEvent event) {
            int action = event.getAction();
            coordX = event.getX();
            coordY = event.getY();

            if(estPotPeinture){
                //Va prendre toute les couleurs de chaque dessin(Effacer) et la changer au fur et a mesure que celle du Background change.
                if (action == MotionEvent.ACTION_DOWN){
                    liDessin.setBackgroundColor(nomCouleur);
                    couleurBackground = nomCouleur;
                }
                for (Dessin d : listDessins) {
                    if (d instanceof Effacer){
                        Paint ligne = ((Effacer) d).getLigneDessin();
                        ligne.setColor(couleurBackground);
                        ((Effacer) d).setLigneDessin(ligne);
                    }
                }
            }
            if(estPipette){
                Bitmap bitmap;
                if (action == MotionEvent.ACTION_DOWN){
                    pipette = new Pipette(liDessin);
                    bitmap = pipette.getBitmapImage();
                    //Va chercher les couleurs selon X et Y
                    nomCouleur = bitmap.getPixel((int) coordX,(int) coordY);
                    //Va prendre en compte le changement de couleur avec les boutons(Chips)
                    changementChip(String.format("#%08X", (0xFFFFFFFF & nomCouleur)));
                }
                if (action == ACTION_UP){
                    estCrayon = true;
                    estCercle = false;
                    estTriangle = false;
                    estRectangle = false;
                    estEfface = false;
                    estPipette = false;
                }
            }
            if(estTriangle){
                if (action == MotionEvent.ACTION_DOWN){
                    if(triangle == null){
                        triangle = new Triangle(nomCouleur,epaisseurCrayon);
                        //Va placer les premières coordonées x,y
                        triangle.placerCoordoneesDepart(coordX, coordY);
                        //Les étapes sont utilisées pour le draw(1,2,4) et également pour éffectuer la dernière action(3)
                        triangle.setEtape(1);
                        dessin = triangle;
                    }
                    //Début de l'étape 3 pour aller vers l'étape 4 dans le draw
                    if(triangle.getEtape()==3){
                        triangle.setEtape(4);
                        triangle.setCx3(coordX);
                        triangle.setCy3(coordY);
                        triangle.setDessin2(true);
                        listDessins.add(triangle);
                        dessin = null;
                        triangle = null;
                    }
                }
                else if (action == MotionEvent.ACTION_MOVE && triangle != null){
                    //Va placer les deuxièmes coordonées x,y en continue
                    triangle.placerCoordoneesEnCour(coordX, coordY);
                    //Étape 2 dans le draw
                    triangle.setDessin1(true);
                    triangle.setEtape(2);
                    dessin = triangle;
                }
                else if (action == ACTION_UP && triangle != null){
                    triangle.placerCoordoneesEnCour(coordX, coordY);
                    triangle.setDessin1(true);
                    dessin = triangle;
                    //Initialise l'étape 3 pour le deuxième if de ACTION_DOWN
                    triangle.setEtape(3);
                }
            }
            if(estRectangle){
                if (action == MotionEvent.ACTION_DOWN){
                    rectangle = new Rectangle(nomCouleur,epaisseurCrayon);
                    //Va placer les coordonnées au fur et à mesure.
                    rectangle.placerCoordonees(coordX, coordY, coordX, coordY);
                }
                if (action == MotionEvent.ACTION_MOVE){
                    rectangle.placerCoordonees(rectangle.getCxDepart(),rectangle.getCyDepart(), coordX, coordY);
                    //Va permettre d'afficher la forme en même temps quelle soit ajouter plus tard dans la liste
                    dessin = rectangle;
                }
                if (action == ACTION_UP){
                    rectangle.placerCoordonees(rectangle.getCxDepart(),rectangle.getCyDepart(), coordX, coordY);
                    listDessins.add(rectangle);
                    dessin = null;
                }
            }
            if (estCercle){
                if (action == MotionEvent.ACTION_DOWN){
                    cercle = new Cercle(nomCouleur,epaisseurCrayon);
                    //Va placer les coordonnées au fur et à mesure.
                    cercle.placerCoordonees(coordX, coordY, coordX, coordY);
                }
                if (action == MotionEvent.ACTION_MOVE){
                    cercle.placerCoordonees(cercle.getCxDepart(),cercle.getCyDepart(), coordX, coordY);
                    //Va permettre d'afficher la forme en même temps quelle soit ajouter plus tard dans la liste
                    dessin = cercle;
                }
                if (action == ACTION_UP){
                    cercle.placerCoordonees(cercle.getCxDepart(),cercle.getCyDepart(), coordX, coordY);
                    listDessins.add(cercle);
                    dessin = null;
                }
            }
            else if(estCrayon){
                if (action == MotionEvent.ACTION_DOWN ) {
                    crayon = new Crayon(nomCouleur, epaisseurCrayon, pathDessin);
                    crayon.getPathDessin().moveTo(coordX, coordY);
                }
                else if(action == MotionEvent.ACTION_MOVE){
                    crayon.getPathDessin().lineTo(coordX, coordY);
                    listDessins.add(crayon);
                }
            }
            else if(estEfface)
                if (action == MotionEvent.ACTION_DOWN ) {
                    effacer = new Effacer(couleurBackground, epaisseurCrayon, pathDessin);
                    effacer.getPathDessin().moveTo(coordX, coordY);
                }
                else if(action == MotionEvent.ACTION_MOVE){
                    effacer.getPathDessin().lineTo(coordX, coordY);
                    listDessins.add(effacer);
                }
            surf.invalidate();
            return true;
        }
        @Override
        public void onClick(View source) {
            int idVue = source.getId();

                if(idVue == R.id.imgCrayon){
                    //Va permettre selon la vue sélectionnée le type de dessin ou de fonction
                    estCrayon = true;
                    estCercle = false;
                    estTriangle = false;
                    estRectangle = false;
                    estEfface = false;
                    estPipette = false;
                    estPotPeinture = false;
                }
                else if(idVue == R.id.imgEffacer){
                    estCrayon = false;
                    estCercle = false;
                    estTriangle = false;
                    estRectangle = false;
                    estEfface = true;
                    estPipette = false;
                    estPotPeinture = false;
                }
                else if(idVue == R.id.imgCercle){
                    estCrayon = false;
                    estCercle = true;
                    estTriangle = false;
                    estRectangle = false;
                    estEfface = false;
                    estPipette = false;
                    estPotPeinture = false;
                }
                else if(idVue == R.id.imgTriangle){
                    estCrayon = false;
                    estCercle = false;
                    estTriangle = true;
                    estRectangle = false;
                    estEfface = false;
                    estPipette = false;
                    estPotPeinture = false;
                }
                else if(idVue == R.id.imgLargeurTrait){
                    //Va ouvrir la fenêtre pour la sélection d'épaisseur de crayon
                    dialog.show();
                }
                else if(idVue == R.id.imgRectangle){
                    estCrayon = false;
                    estCercle = false;
                    estTriangle = false;
                    estRectangle = true;
                    estEfface = false;
                    estPipette = false;
                    estPotPeinture = false;
                }
                else if(idVue == R.id.imgPipette){
                    estCrayon = false;
                    estCercle = false;
                    estTriangle = false;
                    estRectangle = false;
                    estEfface = false;
                    estPipette = true;
                    estPotPeinture = false;
                }
                else if(idVue == R.id.imgRemplir){
                    estCrayon = false;
                    estCercle = false;
                    estTriangle = false;
                    estRectangle = false;
                    estEfface = false;
                    estPipette = false;
                    estPotPeinture = true;
                }
                else if(idVue == R.id.imgRedo){

                    if(!listSecondaire.isEmpty()){
                        listDessins.add(listSecondaire.remove(listSecondaire.size() - 1));
                        surf.invalidate();
                    }

                }
                else if(idVue == R.id.imgUndo){//Pas fini


                    if (!listDessins.isEmpty()) {
                        listSecondaire.add(listDessins.remove(listDessins.size() - 1));
                        surf.invalidate();
                    }

                }
                else if(idVue == R.id.imgEnregistrer){
                    //Enregistrer l'image en cours sur le LinearLayout
                    enregistrer = new Enregistrer();
                    enregistrer.enregistrerImage(MainActivity.this, liDessin);
                }

        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked)
            {
                String couleurString = (String)buttonView.getTag();
                nomCouleur = Color.parseColor(couleurString);

                changementChip(couleurString);
            }
        }
        //Va checker le bouton avec la couleur passer en paramètre.
        private void changementChip(String couleur){

            for(int i = 0; i < liCouleur.getChildCount(); i++){
                View petit = liCouleur.getChildAt(i);
                if(petit instanceof Chip){
                    Chip chip = (Chip)petit;

                    String couleurChip = (String)chip.getTag();

                    if(couleurChip.equalsIgnoreCase(couleur.toUpperCase())){
                        chip.setChecked(true);
                    }
                    else{
                        chip.setChecked(false);
                    }

                }
            }

        }

    }
}