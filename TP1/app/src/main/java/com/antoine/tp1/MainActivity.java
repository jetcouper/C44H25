package com.antoine.tp1;

import static android.view.MotionEvent.ACTION_UP;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import android.Manifest;
import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
    Point point;
    Path pathDessin;
    Paint ligneDessin;
    Effacer effacer;
    float CoordX, CoordY, dernierX, dernierY;
    private Bitmap bitmapImage;
    String vueSelectionner;
    Enregistrer enregistrer;
    int epaisseurCrayon,couleurBackground;
    int nomCouleur = 0;
    Crayon crayon;
    DialogLargeur dialog;
    Cercle cercle;
    Rectangle rectangle;
    Triangle triangle;
    boolean estTriangle, estRectangle, estCercle,estCrayon, estEfface;

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

        //couleurBackground = Color.valueOf(R.color.blanc);
        //dessin.setCouleur(nomCouleur);
        //dessin.setLargeurTrait(epaisseurCrayon);

        //nomCouleur = getResources().getColor(R.color.noir,null);
        dialog = new DialogLargeur(MainActivity.this);
        list_dessin = new ArrayList<Dessin>();
        epaisseurCrayon = 10;
        //epaisseurCrayon = Integer.parseInt(dialog.txtNombre.getText().toString());
        //crayon = new Crayon(nomCouleur,epaisseurCrayon);

        LiDessin = findViewById(R.id.linearDessin);
        LiDessin.setBackgroundColor(getResources().getColor(R.color.blanc));
        couleurBackground = getResources().getColor(R.color.blanc);
        //effacer = new Effacer(couleurBackground,epaisseurCrayon,pathDessin,ligneDessin);
        surf = new SurfaceDessin(this);
        surf.setLayoutParams(new ViewGroup.LayoutParams(-1,-1));
        LiDessin.addView(surf);
        Ecouteur ec = new Ecouteur();
        LiCouleur = findViewById(R.id.linearCouleur);


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

            if (estCercle){
                if (action == MotionEvent.ACTION_DOWN){
                    cercle = new Cercle(dessin.getCouleur(),dessin.getLargeurTrait(),ligneDessin);
                    //cercle.getPathDessin().moveTo(CoordX, CoordY);
                    cercle.setCx(CoordX);
                    cercle.setCy(CoordY);
                }
                if (action == MotionEvent.ACTION_MOVE){
                    cercle.setCx(CoordX);
                    cercle.setCy(CoordY);
                }
                if (action == ACTION_UP){
                    cercle.setCx(CoordX);
                    cercle.setCy(CoordY);
                    list_dessin.add(cercle);
                }

            }
            else if(estCrayon){
                if (action == MotionEvent.ACTION_DOWN ) {

                    crayon = new Crayon(nomCouleur, epaisseurCrayon);
//                    crayon.setCouleur(nomCouleur);
//                    crayon.setLargeurTrait(epaisseurCrayon);
                    crayon.setPathDessin(pathDessin);
                    crayon.getPathDessin().moveTo(CoordX, CoordY);

                }
                else if(action == MotionEvent.ACTION_MOVE){
                    crayon.getPathDessin().lineTo(CoordX, CoordY);
                    list_dessin.add(crayon);
                    surf.invalidate();
                }

            }
            else if(estEfface)
                if (action == MotionEvent.ACTION_DOWN ) {

                    effacer = new Effacer(nomCouleur, epaisseurCrayon);
                    effacer.setPathDessin(pathDessin);
                    effacer.getPathDessin().moveTo(CoordX, CoordY);

                }
                else if(action == MotionEvent.ACTION_MOVE){
                    effacer.getPathDessin().lineTo(CoordX, CoordY);
                    list_dessin.add(effacer);
                    surf.invalidate();
                }

            return true;
        }

        @Override
        public void onClick(View source) {
            int idVue = source.getId();
            String nomVue = source.getResources().getResourceEntryName(idVue);


            if(source instanceof Button)
            {
                String couleurString = (String)source.getTag();
                nomCouleur = Color.parseColor(couleurString);

            }
            else{
                if(vueSelectionner == null){
                    vueSelectionner = nomVue;
                }


                if(idVue == R.id.imgCrayon){
                    estCrayon = true;
                    estCercle = false;
                    estTriangle = false;
                    estRectangle = false;
                    vueSelectionner = nomVue;

                }
                else if(idVue == R.id.imgEffacer){
                    estCrayon = true;
                    estCercle = false;
                    estTriangle = false;
                    estRectangle = false;


                    effacer.setCouleur(couleurBackground);
                    vueSelectionner = nomVue;
                }
                else if(idVue == R.id.imgCercle){
                    estCrayon = false;
                    estCercle = true;
                    estTriangle = false;
                    estRectangle = false;
                }
                else if(idVue == R.id.imgTriangle){
                    estCrayon = false;
                    estCercle = false;
                    estTriangle = true;
                    estRectangle = false;
                }
                else if(idVue == R.id.imgLargeurTrait){
                    dialog.show();
                }
                else if(idVue == R.id.imgRectangle){
                    estCrayon = false;
                    estCercle = false;
                    estTriangle = false;
                    estRectangle = true;
                }
                else if(idVue == R.id.imgPipette){

                }
                else if(idVue == R.id.imgRemplir){

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













