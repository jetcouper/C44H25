package com.antoine.tp1;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import java.util.List;

public class Triangle extends Dessin{

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
    public float getCx1() {
        return cx1;
    }

    public float getCy1() {
        return cy1;
    }

    private float cx1,cy1,cx2,cy2;

    public Triangle(int couleur, int largeurTrait) {
        super(couleur, largeurTrait);
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
        this.cx1 = cxDepart;
        this.cy1 = cyDepart;
        this.cx2 = cxFin;
        this.cy2 = cyFin;
    }




    @Override
    public void dessiner(Canvas canvas) {
        //Valeur du haut
        Path path = new Path();
        float cx3 = (cx1 + cx2) / 2;
        float cy3 = cy1 - (cy2 - cy1);

        path.moveTo(cx3,cy3);
        path.lineTo(cx1,cy2);
        path.lineTo(cx2,cy2);
        path.close();

        canvas.drawPath(path, ligneDessin);
    }


}
