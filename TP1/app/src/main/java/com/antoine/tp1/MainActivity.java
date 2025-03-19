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
    private Paint ligneDessin;
    List<Point> points = new ArrayList<Point>();
    Point point;
    Path pathDessin;
    float CoordX, CoordY, dernierX, dernierY;
    private Bitmap bitmapImage;
    Dessin dessin;
    int epaisseurCrayon = 20;
    int nomCouleur = 0;
    Trait trait;


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
            points = new ArrayList<Point>();
            pathDessin = new Path();
        }

        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);


            if (dessin != null)
                dessin.dessiner(canvas);


//            if(points.size() > 1){ //Pour que ca ne plante pas au démmarage
//                pathDessin.reset();
//                pathDessin.moveTo(points.get(0).x,points.get(0).y);
//                for (Point point : points) {
//                    pathDessin.lineTo(point.x,point.y);
//                }
////                canvas.drawPath(pathDessin, ligneDessin);
////                for (Point point : points) {
////                    canvas.drawCircle(point.x, point.y, 10, ligneDessin);
////                }
//            }
//            canvas.drawPath(pathDessin,ligneDessin);



        }
    }

//    private void interpolatePoints(Point p1, Point p2) { //A EFFACER SI CE N'EST PAS NÉSSÈSSAIRE
//        int dx = Math.abs(p2.x - p1.x);
//        int dy = Math.abs(p2.y - p1.y);
//        int steps = Math.max(dx, dy); // Determine number of points to add
//
//        for (int i = 1; i < steps; i++) {
//            int interpolatedX = p1.x + i * (p2.x - p1.x) / steps;
//            int interpolatedY = p1.y + i * (p2.y - p1.y) / steps;
//            points.add(new Point(interpolatedX, interpolatedY));
//        }
//    }



    private class Ecouteur implements View.OnTouchListener, View.OnClickListener {
        @Override
        public boolean onTouch(View source, MotionEvent event) {

            int action = event.getAction();//PAS FINI
            CoordX = event.getX();
            CoordY = event.getY();

            if (action == MotionEvent.ACTION_DOWN ) {
                if (pathDessin.isEmpty()){
                    dessin = new Dessin(trait.getCouleur(),trait.getLargeurTrait());
                }

                //points.add(new Point((int)CoordX, (int)CoordY));

                //dessin.getPathDessin().lineTo(CoordX,CoordY);
                dessin.getPathDessin().moveTo(CoordX, CoordY);

                surf.invalidate();
                return true;
            }
            if(action == MotionEvent.ACTION_MOVE){
                if (pathDessin.isEmpty()) {
                    //dessin.getPathDessin().moveTo(CoordX, CoordY);
                    dessin.getPathDessin().lineTo(CoordX, CoordY);
                    //pathDessin.moveTo(CoordX,CoordY);
                }


                else if (!pathDessin.isEmpty()) {

                    dessin.getPathDessin().lineTo(CoordX, CoordY);
                    //dessin.getPathDessin().moveTo(CoordX, CoordY);
                }
                surf.invalidate();
                return true;
            }

            return false;


        }

        @Override
        public void onClick(View source) {
            int idVue = source.getId();
            String nomVue = source.getResources().getResourceEntryName(idVue);

            String couleurString = (String)source.getTag();
            nomCouleur = Color.parseColor(couleurString);
            //if(source.toString().equals() != null)
            //getResources().getColor(R.color.noir,null);

            if(nomVue.equals("imgCrayon")){

            }
            else if(source.toString().equals("imgEffacer")){

            }
            else if(source.toString().equals("imgCercle")){

            }
            else if(source.toString().equals("imgTriangle")){

            }
            else if(nomVue.equals("imgLargeurTrait")){
                DialogLargeur dialog = new DialogLargeur(MainActivity.this);
                dialog.show();
            }
            else if(source.toString().equals("imgRectangle")){

            }
            else if(source.toString().equals("imgPipette")){

            }
            else if(source.toString().equals("imgRemplir")){

            }
            else if(source.toString().equals("imgRedo")){

            }
            else if(source.toString().equals("imgUndo")){

            }
            else if(source.toString().equals("imgEnregistrer")){

            }


        }
    }


}













