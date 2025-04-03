package com.example.examen2pratique;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class Effacer extends Dessin{

    private int couleur, largeurTrait;
    private Path path;
    private Paint paint;

    public Effacer(int couleur, int largeurTrait) {
        super(couleur, largeurTrait);

        this.couleur = couleur;
        this.largeurTrait = largeurTrait;

        this.path = new Path();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(this.couleur);
        paint.setStrokeWidth(this.largeurTrait);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);


    }

    @Override
    public void dessiner(Canvas canvas) {
        super.dessiner(canvas);

        canvas.drawPath(this.path,this.paint);
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

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }
}
