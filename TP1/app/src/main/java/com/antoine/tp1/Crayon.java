package com.antoine.tp1;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class Crayon extends Dessin{

    private Path pathDessin;
    private Paint ligneDessin;

    public Crayon(int couleur, int largeurTrait, Path pathDessin) {
        super(couleur,largeurTrait);
        this.couleur = couleur;
        this.largeurTrait = largeurTrait;
        this.pathDessin = pathDessin;

        ligneDessin = new Paint(Paint.ANTI_ALIAS_FLAG);
        ligneDessin.setColor(couleur);
        ligneDessin.setStrokeWidth(largeurTrait);
        ligneDessin.setAntiAlias(true);
        ligneDessin.setStyle(Paint.Style.STROKE);
        ligneDessin.setStrokeJoin(Paint.Join.ROUND);
        ligneDessin.setStrokeCap(Paint.Cap.ROUND);
        ligneDessin.setDither(true);

    }
    public void setPathDessin(Path pathDessin) {
        this.pathDessin = pathDessin;
    }

    public void setLigneDessin(Paint ligneDessin) {
        this.ligneDessin = ligneDessin;
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


    private int couleur;
    private int largeurTrait;
    public int getCouleur() {
        return couleur;
    }

    public void setCouleur(int couleur) {
        this.couleur = couleur;
    }

    public int getLargeurTrait() {
        return largeurTrait;
    }

    public void setLargeurTrait(int largeurTrait) {
        this.largeurTrait = largeurTrait;
    }



}
