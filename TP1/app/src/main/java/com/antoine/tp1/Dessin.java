package com.antoine.tp1;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

public class Dessin extends Trait{


    public void setPathDessin(Path pathDessin) {
        this.pathDessin = pathDessin;
    }

    public void setLigneDessin(Paint ligneDessin) {
        this.ligneDessin = ligneDessin;
    }

    private Path pathDessin;
    private Paint ligneDessin;
    //private List<Point> points;




    public Dessin(int couleur, int largeurTrait) {
        super(couleur, largeurTrait);
        pathDessin = new Path();
        ligneDessin = new Paint();
        //points = Listpoints;
        ligneDessin = new Paint(Paint.ANTI_ALIAS_FLAG);
        ligneDessin.setColor(couleur);
        ligneDessin.setStrokeWidth(largeurTrait);
        ligneDessin.setAntiAlias(true);
        ligneDessin.setStyle(Paint.Style.STROKE);
        ligneDessin.setStrokeJoin(Paint.Join.ROUND);
        ligneDessin.setStrokeCap(Paint.Cap.ROUND);
        ligneDessin.setDither(true);
    }

    public Path getPathDessin() {
        return pathDessin;
    }

    public Paint getLigneDessin() {
        return ligneDessin;
    }



    public void dessiner(Canvas canvas){



        //pathDessin.reset();
//        if (pathDessin.isEmpty())
//            pathDessin.moveTo(points.get(0).x,points.get(0).y);
//        for (Point point : points) {
//            if (!pathDessin.isEmpty())
//                pathDessin.lineTo(point.x,point.y);
//        }

        canvas.drawPath(pathDessin,ligneDessin);
    }
}
