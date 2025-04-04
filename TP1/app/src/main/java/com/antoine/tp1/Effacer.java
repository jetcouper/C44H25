package com.antoine.tp1;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class Effacer extends Dessin{

    private Path pathDessin;
    private Paint ligneDessin;
    private int couleur,largeurTrait;
    public Effacer(int couleur, int largeurTrait, Path pathDessin) {
        super(couleur,largeurTrait);
        this.couleur = couleur;
        this.largeurTrait = largeurTrait;

        this.pathDessin = new Path(pathDessin);
        ligneDessin = new Paint(Paint.ANTI_ALIAS_FLAG);
        ligneDessin.setColor(this.couleur);
        ligneDessin.setStrokeWidth(this.largeurTrait);
        ligneDessin.setAntiAlias(true);
        ligneDessin.setStyle(Paint.Style.STROKE);
        ligneDessin.setStrokeCap(Paint.Cap.ROUND);
        ligneDessin.setStrokeJoin(Paint.Join.ROUND);

    }
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
    public Paint getLigneDessin() {
        return ligneDessin;
    }
    public void setLigneDessin(Paint ligneDessin) {
        this.ligneDessin = ligneDessin;
    }
    @Override
    public void dessiner(Canvas canvas){
        canvas.drawPath(pathDessin,ligneDessin);
    }
}
