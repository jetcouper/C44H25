package com.antoine.tp1;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_UP;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;
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
            ligneDessin = new Paint(Paint.ANTI_ALIAS_FLAG);
            ligneDessin.setColor(Color.BLACK);
            ligneDessin.setStrokeWidth(20);
            ligneDessin.setAntiAlias(true);
            ligneDessin.setStyle(Paint.Style.STROKE);
            ligneDessin.setStrokeJoin(Paint.Join.ROUND);
            ligneDessin.setStrokeCap(Paint.Cap.ROUND);
            ligneDessin.setDither(true);
            points = new ArrayList<Point>();
            pathDessin = new Path();
        }

        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);


            if(points.size() > 1){ //Pour que ca ne plante pas au démmarage
                pathDessin.reset();
                pathDessin.moveTo(points.get(0).x,points.get(0).y);
                for (Point point : points) {
                    pathDessin.lineTo(point.x,point.y);
                }
                canvas.drawPath(pathDessin, ligneDessin);
//                for (Point point : points) {
//                    canvas.drawCircle(point.x, point.y, 10, ligneDessin);
//                }
            }
            //canvas.drawPath(pathDessin,ligneDessin);



        }
    }

    private void interpolatePoints(Point p1, Point p2) { //A EFFACER SI CE N'EST PAS NÉSSÈSSAIRE
        int dx = Math.abs(p2.x - p1.x);
        int dy = Math.abs(p2.y - p1.y);
        int steps = Math.max(dx, dy); // Determine number of points to add

        for (int i = 1; i < steps; i++) {
            int interpolatedX = p1.x + i * (p2.x - p1.x) / steps;
            int interpolatedY = p1.y + i * (p2.y - p1.y) / steps;
            points.add(new Point(interpolatedX, interpolatedY));
        }
    }



    private class Ecouteur implements View.OnTouchListener, View.OnClickListener {
        @Override
        public boolean onTouch(View source, MotionEvent event) {

            int action = event.getAction();//PAS FINI
            CoordX = event.getX();
            CoordY = event.getY();


//            if (action == ACTION_DOWN) {
//                pathDessin.moveTo(CoordX, CoordY);
//                dernierX = CoordX;
//                dernierY = CoordY;
//            }
//            if (action == ACTION_MOVE) {
//                float millieuX = (dernierX + CoordX)/2;
//                float millieuy = (dernierY + CoordY)/2;
//                pathDessin.quadTo(dernierX, dernierY, millieuX, millieuy);
//                dernierX = CoordX;
//                dernierY = CoordY;
//            }
//            if (action == ACTION_UP) {
//                pathDessin.lineTo(CoordX, CoordY);
//            }
//            surf.invalidate();
//            return true;



            if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
                points.add(new Point((int)CoordX, (int)CoordY));


                if (points.size() > 1) {
                    Point lastPoint = points.get(points.size() - 2);
                    interpolatePoints(lastPoint, new Point((int)CoordX, (int)CoordY));
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

            if(source.toString().equals("imgCrayon")){

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













