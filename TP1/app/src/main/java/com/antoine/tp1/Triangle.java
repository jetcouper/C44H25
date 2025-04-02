package com.antoine.tp1;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class Triangle extends Dessin{

    private int couleur,largeurTrait,etape;

    private Boolean dessin1,dessin2;
    private float cx1,cy1,cx2,cy2,cx3,cy3;
    private Path pathDessin;
    private Paint ligneDessin;
    public Triangle(int couleur, int largeurTrait) {
        super(couleur, largeurTrait);
        this.couleur = couleur;
        this.largeurTrait = largeurTrait;

        dessin1 = false;
        dessin2 = false;
        pathDessin = new Path();
        ligneDessin = new Paint(Paint.ANTI_ALIAS_FLAG);
        ligneDessin.setColor(this.couleur);
        ligneDessin.setStrokeWidth(this.largeurTrait);
        ligneDessin.setAntiAlias(true);
        ligneDessin.setStyle(Paint.Style.STROKE);
        ligneDessin.setStrokeJoin(Paint.Join.ROUND);
        ligneDessin.setStrokeCap(Paint.Cap.ROUND);
        ligneDessin.setDither(true);
    }

    public Path getPathDessin() {
        return pathDessin;
    }

    public void setDessin1(Boolean dessin1) {
        this.dessin1 = dessin1;
    }

    public void setDessin2(Boolean dessin2) {
        this.dessin2 = dessin2;
    }

    public float getCx1() {
        return cx1;
    }

    public float getCy1() {
        return cy1;
    }

    public float getCx2() {
        return cx2;
    }

    public float getCy2() {
        return cy2;
    }

    public float getCx3() {
        return cx3;
    }

    public float getCy3() {
        return cy3;
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
        //canvas.drawPath(this.pathDessin,this.ligneDessin);


//        if(etape ==1){
//           //pathDessin.moveTo(cx1,cy1);
//        }
        if(dessin1){
            //pathDessin.lineTo(cx2,cy2);
            canvas.drawLine(cx1,cy1,cx2,cy2,ligneDessin);
        }
        if (dessin2){
            //pathDessin.lineTo(cx3,cy3);
            ligneDessin.setStyle(Paint.Style.FILL_AND_STROKE);
            canvas.drawLine(cx1,cy1,cx3,cy3,ligneDessin);
            canvas.drawLine(cx2,cy2,cx3,cy3,ligneDessin);
            //pathDessin.close();
        }
        //canvas.drawPath(pathDessin, ligneDessin);
    }
}
