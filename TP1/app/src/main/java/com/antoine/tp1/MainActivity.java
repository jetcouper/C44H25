package com.antoine.tp1;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_UP;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    LinearLayout LiCouleur;
    LinearLayout LiOptions;
    LinearLayout LiDessin;
    SurfaceDessin surf;
    List<Dessin> list_dessin;
    Point point;
    Path pathDessin;
    float CoordX, CoordY, dernierX, dernierY;
    private Bitmap bitmapImage;
    Dessin dessin;
    String vueSelectionner;


    private int epaisseurCrayon;
    int nomCouleur = 0;
    Trait trait;
    DialogLargeur dialog;
    Cercle cercle;
    Rectangle rectangle;
    Triangle triangle;

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

        nomCouleur = getResources().getColor(R.color.noir,null);
        dialog = new DialogLargeur(MainActivity.this);
        list_dessin = new ArrayList<Dessin>();
        epaisseurCrayon = 10;
        //epaisseurCrayon = Integer.parseInt(dialog.txtNombre.getText().toString());
        trait = new Trait(nomCouleur,epaisseurCrayon);

        LiDessin = findViewById(R.id.linearDessin);
        LiDessin.setBackgroundColor(getResources().getColor(R.color.blanc));

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

//    public Bitmap getBitmapImage() {
//
//        this.buildDrawingCache();
//        bitmapImage = Bitmap.createBitmap(this.getDrawingCache());
//        this.destroyDrawingCache();
//
//        return bitmapImage;
//    }
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

            if (action == MotionEvent.ACTION_DOWN ) {
                if (pathDessin.isEmpty()){
                    dessin = new Dessin(trait.getCouleur(),trait.getLargeurTrait());
                }
                dessin.getPathDessin().moveTo(CoordX, CoordY);
            }
            if(action == MotionEvent.ACTION_MOVE){
                if (pathDessin.isEmpty()) {
                    dessin.getPathDessin().lineTo(CoordX, CoordY);
                }
                else if (!pathDessin.isEmpty()) {
                    dessin.getPathDessin().lineTo(CoordX, CoordY);
                }

            }
            if(action == ACTION_UP){
                list_dessin.add(dessin);
                dessin = new Dessin(trait.getCouleur(), trait.getLargeurTrait());
            }
            surf.invalidate();
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
                trait.setCouleur(nomCouleur);
            }
            else{
                if(vueSelectionner == null){
                    vueSelectionner = nomVue;
                }


                if(nomVue.equals("imgCrayon") && vueSelectionner.equals(nomVue)){
                    source.setBackgroundColor(nomCouleur);//Teste
                    vueSelectionner = nomVue;
                }
                else if(nomVue.equals("imgEffacer")&& vueSelectionner.equals(nomVue)){
                    trait.setCouleur(getResources().getColor(R.color.blanc,null));
                    vueSelectionner = nomVue;
                }
                else if(nomVue.equals("imgCercle")&& vueSelectionner.equals(nomVue)){

                }
                else if(nomVue.equals("imgTriangle")&& vueSelectionner.equals(nomVue)){

                }
                else if(nomVue.equals("imgLargeurTrait")&& vueSelectionner.equals(nomVue)){
                    dialog.show();
                }
                else if(nomVue.equals("imgRectangle")&& vueSelectionner.equals(nomVue)){

                }
                else if(nomVue.equals("imgPipette")&& vueSelectionner.equals(nomVue)){

                }
                else if(nomVue.equals("imgRemplir")&& vueSelectionner.equals(nomVue)){

                }
                else if(nomVue.equals("imgRedo")&& vueSelectionner.equals(nomVue)){

                }
                else if(nomVue.equals("imgUndo")&& vueSelectionner.equals(nomVue)){

                }
                else if(nomVue.equals("imgEnregistrer")&& vueSelectionner.equals(nomVue)){

                }
            }



        }
    }


}













