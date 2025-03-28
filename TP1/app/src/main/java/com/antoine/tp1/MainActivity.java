package com.antoine.tp1;

import static android.view.MotionEvent.ACTION_UP;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    LinearLayout LiCouleur,LiOptions,LiDessin;
    SurfaceDessin surf;
    List<Dessin> list_dessin;
    Dessin dessin;
    Path pathDessin;
    Effacer effacer;
    float CoordX, CoordY;
    Enregistrer enregistrer;
    int epaisseurCrayon,couleurBackground,nomCouleur = 0;
    Crayon crayon;
    DialogLargeur dialog;
    Cercle cercle;
    Rectangle rectangle;
    Triangle triangle;
    Pipette pipette;
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
        LiDessin = findViewById(R.id.linearDessin);
        LiCouleur = findViewById(R.id.linearCouleur);
        dialog = new DialogLargeur(MainActivity.this);
        list_dessin = new ArrayList<Dessin>();
        epaisseurCrayon = 10;
        LiDessin.setBackgroundColor(getResources().getColor(R.color.blanc,null));
        couleurBackground = getResources().getColor(R.color.blanc,null);
        estCrayon = true;//Initialiser le crayon au démarrage



        surf = new SurfaceDessin(this);
        surf.setLayoutParams(new ViewGroup.LayoutParams(-1,-1));
        LiDessin.addView(surf);
        Ecouteur ec = new Ecouteur();

        //Initialisation des boutons et des ImageViews
        for(int i = 0; i < LiCouleur.getChildCount(); i++){
            View petit = LiCouleur.getChildAt(i);
            if(petit instanceof Button){
                LiCouleur.getChildAt(i).setOnClickListener(ec);
            }
        }
        LiOptions = findViewById(R.id.linearImages);
        for(int i = 0; i < LiOptions.getChildCount(); i++){
            View petit = LiOptions.getChildAt(i);
            if(petit instanceof ImageView){
                LiOptions.getChildAt(i).setOnClickListener(ec);
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
            for(Dessin d : list_dessin){
                d.dessiner(canvas);
            }
            if (dessin != null)
                dessin.dessiner(canvas);
        }
    }
    private class Ecouteur implements View.OnTouchListener, View.OnClickListener {
        @Override
        public boolean onTouch(View source, MotionEvent event) {

            int action = event.getAction();
            CoordX = event.getX();
            CoordY = event.getY();
            if(estPotPeinture){

                if (action == MotionEvent.ACTION_DOWN){
                    LiDessin.setBackgroundColor(nomCouleur);
                    couleurBackground = nomCouleur;
                }
                for (Dessin d : list_dessin) {
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
                    pipette = new Pipette(LiDessin);
                    bitmap = pipette.getBitmapImage();
                    nomCouleur = bitmap.getPixel((int)CoordX,(int)CoordY);
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
                    if(triangle == null || !triangle.isEstFini()){
                        triangle = new Triangle(nomCouleur,epaisseurCrayon);
                        triangle.setEstDebut(true);
                        triangle.placerCoordonees(CoordX,CoordY,CoordX,CoordY);
                        dessin = triangle;
                    }
                    if(triangle.isEstFini()){
                            triangle.setCx3(CoordX);
                            triangle.setCy3(CoordY);
                            list_dessin.add(triangle);
                            dessin = null;
                    }
                }
                if (action == MotionEvent.ACTION_MOVE){
                    triangle.setEstDebut(false);
                    triangle.setEstEnCour(true);
                    triangle.placerCoordonees(triangle.getCx1(),triangle.getCy1(),CoordX,CoordY);
                    dessin = triangle;
                }
                if (action == ACTION_UP && !triangle.isEstFini()){
                    triangle.placerCoordonees(triangle.getCx1(),triangle.getCy1(),CoordX,CoordY);
                    dessin = triangle;
                    triangle.setEstFini(true);
                }

            }

            if(estRectangle){
                if (action == MotionEvent.ACTION_DOWN){
                    rectangle = new Rectangle(nomCouleur,epaisseurCrayon);
                    rectangle.placerCoordonees(CoordX,CoordY,CoordX,CoordY);
                }
                if (action == MotionEvent.ACTION_MOVE){
                    rectangle.placerCoordonees(rectangle.getCxDepart(),rectangle.getCyDepart(),CoordX,CoordY);
                    dessin = rectangle;
                }
                if (action == ACTION_UP){
                    rectangle.placerCoordonees(rectangle.getCxDepart(),rectangle.getCyDepart(),CoordX,CoordY);
                    list_dessin.add(rectangle);
                    dessin = null;
                }
            }
            if (estCercle){
                if (action == MotionEvent.ACTION_DOWN){
                    cercle = new Cercle(nomCouleur,epaisseurCrayon);
                    cercle.placerCoordonees(CoordX,CoordY,CoordX,CoordY);
                }
                if (action == MotionEvent.ACTION_MOVE){
                    cercle.placerCoordonees(cercle.getCxDepart(),cercle.getCyDepart(),CoordX,CoordY);
                    dessin = cercle;
                }
                if (action == ACTION_UP){
                    cercle.placerCoordonees(cercle.getCxDepart(),cercle.getCyDepart(),CoordX,CoordY);
                    list_dessin.add(cercle);
                    dessin = null;
                }
            }
            else if(estCrayon){
                if (action == MotionEvent.ACTION_DOWN ) {
                    crayon = new Crayon(nomCouleur, epaisseurCrayon, pathDessin);
                    crayon.getPathDessin().moveTo(CoordX, CoordY);
                }
                else if(action == MotionEvent.ACTION_MOVE){
                    crayon.getPathDessin().lineTo(CoordX, CoordY);
                    list_dessin.add(crayon);
                }
            }
            else if(estEfface)
                if (action == MotionEvent.ACTION_DOWN ) {
                    effacer = new Effacer(couleurBackground, epaisseurCrayon, pathDessin);
                    effacer.getPathDessin().moveTo(CoordX, CoordY);
                }
                else if(action == MotionEvent.ACTION_MOVE){
                    effacer.getPathDessin().lineTo(CoordX, CoordY);
                    list_dessin.add(effacer);
                }
            surf.invalidate();

            return true;
        }

        @Override
        public void onClick(View source) {
            int idVue = source.getId();


            if(source instanceof Button)
            {
                String couleurString = (String)source.getTag();
                nomCouleur = Color.parseColor(couleurString);
            }
            else{
                if(idVue == R.id.imgCrayon){
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

                }
                else if(idVue == R.id.imgUndo){

                }
                else if(idVue == R.id.imgEnregistrer){
                    enregistrer = new Enregistrer();
                    enregistrer.enregistrerImage(MainActivity.this,LiDessin);
                }
            }



        }
    }


}













