package com.antoine.tp1;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;

import java.util.List;

public class Cercle extends Dessin {


    private int couleur;
    private int largeurTrait;

    private Path pathDessin;
    private Paint ligneDessin;
    @Override
    public int getCouleur() {
        return couleur;
    }

    @Override
    public void setCouleur(int couleur) {
        this.couleur = couleur;
    }

    @Override
    public int getLargeurTrait() {
        return largeurTrait;
    }

    @Override
    public void setLargeurTrait(int largeurTrait) {
        this.largeurTrait = largeurTrait;
    }

    public Path getPathDessin() {
        return pathDessin;
    }

    public void setPathDessin(Path pathDessin) {
        this.pathDessin = pathDessin;
    }

    public Paint getLigneDessin() {
        return ligneDessin;
    }

    public void setLigneDessin(Paint ligneDessin) {
        this.ligneDessin = ligneDessin;
    }
    public float getCxDepart() {
        return cxDepart;
    }

    public float getCyDepart() {
        return cyDepart;
    }

    private float cxDepart,cyDepart;
    private float cxFin,cyFin;

    public Cercle(int couleur,int largeurTrait) {
        super(couleur,largeurTrait);


        this.pathDessin = new Path(pathDessin);
        ligneDessin = new Paint(Paint.ANTI_ALIAS_FLAG);
        ligneDessin.setColor(couleur);
        ligneDessin.setStrokeWidth(largeurTrait);
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
    public void dessiner(Canvas canvas){

        float gauche = Math.min(cxDepart, cxFin);
        float haut = Math.min(cyDepart, cyFin);
        float droite = Math.max(cxDepart, cxFin);
        float bas = Math.max(cyDepart, cyFin);

        canvas.drawOval(new RectF(gauche, haut, droite, bas), ligneDessin);
    }


}
