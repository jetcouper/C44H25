package com.example.examen2pratique;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

public class Etoile extends Dessin{



    private int couleur,largeurTrait;

    private float cxDepart,cyDepart,cxFin,cyFin;

    private Paint ligneDessin;

    public Etoile(int couleur, int largeurTrait) {
        super(couleur, largeurTrait);
        this.couleur = couleur;
        this.largeurTrait = largeurTrait;

        ligneDessin = new Paint(Paint.ANTI_ALIAS_FLAG);
        ligneDessin.setColor(this.couleur);
        ligneDessin.setStrokeWidth(this.largeurTrait);
        ligneDessin.setAntiAlias(true);
        ligneDessin.setStyle(Paint.Style.STROKE);

    }
    public void placerCoordonees(float cxDepart,float cyDepart,float cxFin,float cyFin){
        this.cxDepart = cxDepart;
        this.cyDepart = cyDepart;
        this.cxFin = cxFin;
        this.cyFin = cyFin;
    }

    @Override
    public void dessiner(Canvas canvas) {
        super.dessiner(canvas);
        Path path = new Path();
        float cxCentre = (cxDepart + cxFin) / 2;
        float cyCentre = (cyDepart + cyFin) / 2;
        float rayonExterne = Math.min(Math.abs(cxFin - cxDepart) / 2, Math.abs(cyFin - cyDepart) / 2);
        float rayonInterne = rayonExterne * 0.4f;

        float[] points = new float[20];
        int index = 0;

        for (int i = 0; i < 10; i++) {
            double angle = Math.toRadians(36 * i - 90);
            float rayon = (i % 2 == 0) ? rayonExterne : rayonInterne;
            points[index++] = cxCentre + (float) (rayon * Math.cos(angle));
            points[index++] = cyCentre + (float) (rayon * Math.sin(angle));
        }

        path.moveTo(points[0], points[1]);
        for (int i = 1; i < 10; i++) {
            path.lineTo(points[i * 2], points[i * 2 + 1]);
        }
        path.close();

        canvas.drawPath(path, ligneDessin); // Dessiner l'étoile sur le Canvas
    }

    public float getCxDepart() {
        return cxDepart;
    }

    public float getCyDepart() {
        return cyDepart;
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
}
