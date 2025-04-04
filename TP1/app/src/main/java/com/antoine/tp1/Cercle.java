package com.antoine.tp1;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
public class Cercle extends Dessin {


    private int couleur,largeurTrait;
    private float cxDepart,cyDepart,cxFin,cyFin;
    private Paint ligneDessin;
    private Boolean plein;
    public Cercle(int couleur,int largeurTrait, Boolean plein) {
        super(couleur,largeurTrait);
        this.couleur = couleur;
        this.largeurTrait = largeurTrait;
        this.plein = plein;

        ligneDessin = new Paint(Paint.ANTI_ALIAS_FLAG);
        ligneDessin.setColor(this.couleur);
        ligneDessin.setStrokeWidth(this.largeurTrait);
        ligneDessin.setAntiAlias(true);
        ligneDessin.setStyle(Paint.Style.FILL_AND_STROKE);
        ligneDessin.setStrokeCap(Paint.Cap.ROUND);
        ligneDessin.setStrokeJoin(Paint.Join.ROUND);


    }
    //Va redéfinir les coordonnées(x,y) plus facilement
    public void placerCoordonees(float cxDepart,float cyDepart,float cxFin,float cyFin){
        this.cxDepart = cxDepart;
        this.cyDepart = cyDepart;
        this.cxFin = cxFin;
        this.cyFin = cyFin;
    }

    @Override
    public void dessiner(Canvas canvas){
        if(plein){
            ligneDessin.setStyle(Paint.Style.FILL_AND_STROKE);
        } else if (!plein) {
            ligneDessin.setStyle(Paint.Style.STROKE);
        }


        float gauche = Math.min(cxDepart, cxFin);
        float haut = Math.min(cyDepart, cyFin);
        float droite = Math.max(cxDepart, cxFin);
        float bas = Math.max(cyDepart, cyFin);

        canvas.drawOval(new RectF(gauche, haut, droite, bas), ligneDessin);
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
    public float getCxDepart() {
        return cxDepart;
    }

    public float getCyDepart() {
        return cyDepart;
    }

}
