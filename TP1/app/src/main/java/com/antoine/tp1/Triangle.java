package com.antoine.tp1;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class Triangle extends Dessin{

    private int couleur,largeurTrait,etape;
    private float cx1,cy1,cx2,cy2,cx3,cy3;
    private Path pathDessin;
    private Paint ligneDessin;
    public Triangle(int couleur, int largeurTrait) {
        super(couleur, largeurTrait);
        this.couleur = couleur;
        this.largeurTrait = largeurTrait;

        pathDessin = new Path();
        ligneDessin = new Paint(Paint.ANTI_ALIAS_FLAG);
        ligneDessin.setColor(this.couleur);
        ligneDessin.setStrokeWidth(this.largeurTrait);
        ligneDessin.setAntiAlias(true);
        ligneDessin.setStyle(Paint.Style.FILL_AND_STROKE);
        ligneDessin.setStrokeJoin(Paint.Join.ROUND);
        ligneDessin.setStrokeCap(Paint.Cap.ROUND);
        ligneDessin.setDither(true);
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
    public void setCx3(float cx3) {
        this.cx3 = cx3;
    }

    public void setCy3(float cy3) {
        this.cy3 = cy3;
    }
    public int getEtape() {
        return etape;
    }

    public void setEtape(int etape) {
        this.etape = etape;
    }

    public void placerCoordoneesDepart(float cxDepart,float cyDepart){
        this.cx1 = cxDepart;
        this.cy1 = cyDepart;
    }
    public void placerCoordoneesEnCour(float cxFin,float cyFin){

        this.cx2 = cxFin;
        this.cy2 = cyFin;
    }
    @Override
    public void dessiner(Canvas canvas) {
        if(etape ==1){
            pathDessin.moveTo(cx1,cy1);
        }
        else if(etape ==2){
            pathDessin.lineTo(cx2,cy2);

        }
        else if (etape ==4){
            pathDessin.lineTo(cx3,cy3);
            pathDessin.close();
        }
        canvas.drawPath(pathDessin, ligneDessin);
    }
}
