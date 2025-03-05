package com.antoine.annnexe7;

import static android.view.MotionEvent.ACTION_BUTTON_PRESS;
import static android.view.MotionEvent.ACTION_BUTTON_RELEASE;
import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_UP;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Point;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout main;
    SurfaceDessin surf;

    private Paint carre;
    //List<Point> points = new ArrayList<Point>();
    Point depart;
    //Point ligne;
    Point fin;

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
        main = findViewById(R.id.main);

        surf = new SurfaceDessin(this);
        surf.setLayoutParams(new ConstraintLayout.LayoutParams(-1,-1));//-1 : MATCH_PARENT
        main.addView(surf);


        Ecouteur ec = new Ecouteur();
        surf.setOnTouchListener(ec);
    }

    private class SurfaceDessin extends View {



        public SurfaceDessin(Context context) {
            super(context);
            setBackgroundResource(R.drawable.carte2);
            carre = new Paint(Paint.ANTI_ALIAS_FLAG);
            carre.setColor(Color.RED);
            carre.setStrokeWidth(10);
        }
        @Override
        protected void onDraw(@NonNull Canvas canvas){
            super.onDraw(canvas);

//            for (Point point : points){ //Pour dessiner un nombre infinit de point
//                canvas.drawRect(point.x-20,point.y-20,point.x+20,point.y+20,carre);
//            }
            if(depart != null){
                canvas.drawRect(depart.x-20,depart.y-20,depart.x+20,depart.y+20,carre);
            }
            if (fin != null){
                canvas.drawRect(fin.x-20,fin.y-20,fin.x+20,fin.y+20,carre);
                canvas.drawLine(depart.x,depart.y,fin.x,fin.y,carre);
            }

        }
    }
    private class Ecouteur implements View.OnTouchListener {
        @Override
        public boolean onTouch(View source, MotionEvent event) {

            int action = event.getAction();//PAS FINI

                if(action == ACTION_DOWN){
                    depart = new Point();
                    depart.x = (int)event.getX();
                    depart.y = (int)event.getY();
                    //points.add(depart); //Pour avoir une infini de point
                    surf.invalidate();
                }
                else if(action == ACTION_MOVE){

                    fin = new Point();
                    fin.x = (int)event.getX();
                    fin.y = (int)event.getY();
                    surf.invalidate();
                }
                if(action == ACTION_UP){
                    depart = null;
                    fin = null;
                    //points.add(fin); //Pour avoir une infini de point
                }


            return true;
        }
    }
}