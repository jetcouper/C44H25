package com.example.examen2pratique;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_UP;

import android.content.Context;
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

import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    LinearLayout liOptions,liDessin,liCouleur;
    boolean estCrayon, estEfface,estEtoile, estTracer;
    int epaisseurCrayon,nomCouleur = 0,couleurBackground;
    SurfaceDessin surf;
    List<Dessin> listDessins;
    Dessin dessin;
    float coordX, coordY;
    Path pathDessin;
    Crayon crayon;
    Effacer effacer;
    Etoile etoile;
    Tracer tracer;
    DialogLargeur dialog;
    Paint hazard;


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
        liCouleur = findViewById(R.id.liCouleur);
        liOptions = findViewById(R.id.linearImage);
        liDessin = findViewById(R.id.liDessin);
        listDessins = new ArrayList<Dessin>();
        dialog = new DialogLargeur(MainActivity.this);
        epaisseurCrayon = 10;
        liDessin.setBackgroundColor(getResources().getColor(R.color.blanc,null));
        couleurBackground = getResources().getColor(R.color.blanc,null);
        surf = new SurfaceDessin(this);
        surf.setLayoutParams(new ViewGroup.LayoutParams(-1,-1));
        liDessin.addView(surf);

        Ecouteur ec = new Ecouteur();

        for(int i = 0; i < liCouleur.getChildCount(); i++){
            View petit = liCouleur.getChildAt(i);
            if(petit instanceof Button){
                liCouleur.getChildAt(i).setOnClickListener(ec);
            }
        }
        for(int i = 0; i < liOptions.getChildCount(); i++){
            View petit = liOptions.getChildAt(i);
            if(petit instanceof ImageView){
                liOptions.getChildAt(i).setOnClickListener(ec);
            }
        }
        surf.setOnTouchListener(ec);


    }

    private class SurfaceDessin extends View {


        public SurfaceDessin(Context context) {
            super(context);

            pathDessin = new Path();
        }

        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);
            for(Dessin d : listDessins){
                d.dessiner(canvas);
            }
            if (dessin != null)
                dessin.dessiner(canvas);


            hazard = new Paint(Paint.ANTI_ALIAS_FLAG);//Antialias: adoucir la courbure des courbe
            hazard.setColor(Color.YELLOW);
            canvas.drawCircle(300,300,150,hazard);
            hazard.setStyle(Paint.Style.STROKE); // pcq par défault avec le style Fill
            hazard.setStrokeWidth(8);
            canvas.drawCircle(280,80,80,hazard);

            hazard.setStyle(Paint.Style.FILL);

            hazard.setColor(Color.BLUE);
            canvas.drawArc(500,30,900,400,0,120,true,hazard);
            hazard.setColor(Color.RED);
            canvas.drawArc(500,30,900,400,120,120,true,hazard);
            hazard.setColor(Color.GREEN);
            //cercle.setColor(Color.argb(55,223,67,200)); //Autre façon de faire
            canvas.drawArc(500,30,900,400,240,120,true,hazard);




        }
    }

    private class Ecouteur implements View.OnClickListener, View.OnTouchListener {


        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            coordX = event.getX();
            coordY = event.getY();

            if(estCrayon){
                if (action == MotionEvent.ACTION_DOWN ) {
                    crayon = new Crayon(nomCouleur, epaisseurCrayon);
                    crayon.getPath().moveTo(coordX, coordY);
                }
                else if(action == MotionEvent.ACTION_MOVE){
                    crayon.getPath().lineTo(coordX, coordY);
                    dessin = crayon;
                }
                else if(action == MotionEvent.ACTION_UP){
                    listDessins.add(crayon);
                }
            }
            else if(estEfface) {
                if (action == MotionEvent.ACTION_DOWN) {
                    effacer = new Effacer(couleurBackground, epaisseurCrayon);
                    effacer.getPath().moveTo(coordX, coordY);
                } else if (action == MotionEvent.ACTION_MOVE) {
                    effacer.getPath().lineTo(coordX, coordY);
                    dessin = effacer;
                } else if (action == MotionEvent.ACTION_UP) {
                    listDessins.add(effacer);
                }
            }
            else if(estEtoile) {
                if (action == MotionEvent.ACTION_DOWN) {
                    etoile = new Etoile(nomCouleur, epaisseurCrayon);
                    //Va placer les coordonnées au fur et à mesure.
                    etoile.placerCoordonees(coordX, coordY, coordX, coordY);
                }
                if (action == MotionEvent.ACTION_MOVE) {
                    etoile.placerCoordonees(etoile.getCxDepart(), etoile.getCyDepart(), coordX, coordY);
                    //Va permettre d'afficher la forme en même temps quelle soit ajouter plus tard dans la liste
                    dessin = etoile;
                }
                if (action == ACTION_UP) {
                    etoile.placerCoordonees(etoile.getCxDepart(), etoile.getCyDepart(), coordX, coordY);
                    listDessins.add(etoile);
                    dessin = null;
                }
            } else if (estTracer) {
                if(action == ACTION_DOWN){
                    tracer = new Tracer(nomCouleur, epaisseurCrayon);
                    //depart = new Point();
                    tracer.getDepart().x = (int)event.getX();
                    tracer.getDepart().y = (int)event.getY();
                    tracer.getFin().x = (int)event.getX();
                    tracer.getFin().y = (int)event.getY();
                    dessin = tracer;
                    //points.add(depart); //Pour avoir une infini de point
                    //surf.invalidate();
                }
                else if(action == ACTION_MOVE){

                    //tracer.getDepart() = new Point();
                    tracer.getFin().x = (int)event.getX();
                    tracer.getFin().y = (int)event.getY();
                    dessin = tracer;
                    //surf.invalidate();
                }
                if(action == ACTION_UP){
                    listDessins.add(tracer);
                    dessin = null;
                    //points.add(fin); //Pour avoir une infini de point
                }


            }


            surf.invalidate();
            return true;
        }
        @Override
        public void onClick(View v) {
            int idVue = v.getId();
            String couleur = null;


            if(v instanceof Button){
                couleur = (String) v.getTag();
                nomCouleur= Color.parseColor(couleur);
            }


            else if(idVue == R.id.imgCrayon){
                //Va permettre selon la vue sélectionnée le type de dessin ou de fonction
                estCrayon = true;
                estEfface = false;
                estEtoile = false;
                estTracer = false;
            }
            else if(idVue == R.id.imgEfface){
                estCrayon = false;
                estEfface = true;
                estEtoile= false;
                estTracer = false;
            }
            else if(idVue == R.id.imgEpaisseur){
                //Va ouvrir la fenêtre pour la sélection d'épaisseur de crayon
                dialog.show();
            }
            else if(idVue == R.id.imgEtoile){
                estCrayon = false;
                estEfface = false;
                estEtoile= true;
                estTracer = false;
            }
            else if(idVue == R.id.imgTracer){
                estCrayon = false;
                estEfface = false;
                estEtoile= false;
                estTracer = true;
            }
        }
    }
}