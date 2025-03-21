package com.antoine.tp1;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class Dessin extends Crayon {


    public void setPathDessin(Path pathDessin) {
        this.pathDessin = pathDessin;
    }

    public void setLigneDessin(Paint ligneDessin) {
        this.ligneDessin = ligneDessin;
    }

    private Path pathDessin;
    private Paint ligneDessin;




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

        canvas.drawPath(pathDessin,ligneDessin);
    }
}
