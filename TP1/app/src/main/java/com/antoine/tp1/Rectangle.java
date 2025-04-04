package com.antoine.tp1;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
public class Rectangle extends Dessin{

    private int couleur,largeurTrait;
    private Paint ligneDessin;
    private float cxDepart,cyDepart,cxFin,cyFin;
    private Boolean plein;

    public Rectangle(int couleur, int largeurTrait, Boolean plein) {
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
    public void dessiner(Canvas canvas) {

        if(plein){
            ligneDessin.setStyle(Paint.Style.FILL_AND_STROKE);
        } else if (!plein) {
            ligneDessin.setStyle(Paint.Style.STROKE);
        }

        float gauche = Math.min(cxDepart, cxFin);
        float haut = Math.min(cyDepart, cyFin);
        float droite = Math.max(cxDepart, cxFin);
        float bas = Math.max(cyDepart, cyFin);

        canvas.drawRect(new RectF(gauche,haut,droite,bas),ligneDessin);
    }
    public float getCxDepart() {
        return cxDepart;
    }

    public float getCyDepart() {
        return cyDepart;
    }
}
