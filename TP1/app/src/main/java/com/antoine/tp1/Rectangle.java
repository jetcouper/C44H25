package com.antoine.tp1;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;

import java.util.List;

public class Rectangle extends Dessin{

    private int couleur;
    private int largeurTrait;

    private Path pathDessin;
    private Paint ligneDessin;


    private float cxDepart,cyDepart;

    public float getCxDepart() {
        return cxDepart;
    }

    public float getCyDepart() {
        return cyDepart;
    }

    private float cxFin,cyFin;

    public Rectangle(int couleur, int largeurTrait) {
        super(couleur,largeurTrait);
        this.couleur = couleur;
        this.largeurTrait = largeurTrait;

        ligneDessin = new Paint(Paint.ANTI_ALIAS_FLAG);
        ligneDessin.setColor(this.couleur);
        ligneDessin.setStrokeWidth(this.largeurTrait);
        ligneDessin.setAntiAlias(true);
        ligneDessin.setStyle(Paint.Style.STROKE);
        ligneDessin.setStrokeJoin(Paint.Join.ROUND);
        ligneDessin.setStrokeCap(Paint.Cap.ROUND);
        ligneDessin.setDither(true);

    }

    public void placerCoordonees(float cxDepart,float cyDepart,float cxFin,float cyFin){
        this.cxDepart = cxDepart;
        this.cyDepart = cyDepart;
        this.cxFin = cxFin;
        this.cyFin = cyFin;
    }



    @Override
    public void dessiner(Canvas canvas) {

        float gauche = Math.min(cxDepart, cxFin);
        float haut = Math.min(cyDepart, cyFin);
        float droite = Math.max(cxDepart, cxFin);
        float bas = Math.max(cyDepart, cyFin);

        canvas.drawRect(new RectF(gauche,haut,droite,bas),ligneDessin);


    }

}
