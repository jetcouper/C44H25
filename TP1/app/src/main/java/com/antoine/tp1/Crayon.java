package com.antoine.tp1;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class Crayon extends Dessin{

    private int couleur,largeurTrait;
    private Path pathDessin;
    private Paint ligneDessin;
    public Crayon(int couleur, int largeurTrait, Path pathDessin) {
        super(couleur,largeurTrait);
        this.couleur = couleur;
        this.largeurTrait = largeurTrait;

        this.pathDessin = new Path(pathDessin);
        ligneDessin = new Paint(Paint.ANTI_ALIAS_FLAG);
        ligneDessin.setColor(this.couleur);
        ligneDessin.setStrokeWidth(this.largeurTrait);
        ligneDessin.setAntiAlias(true);
        ligneDessin.setStyle(Paint.Style.STROKE);

    }
    @Override
    public void dessiner(Canvas canvas){
        canvas.drawPath(this.pathDessin,this.ligneDessin);
    }
    public Path getPathDessin() {
        return pathDessin;
    }

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
