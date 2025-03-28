package com.antoine.tp1;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import java.util.List;

public class Triangle extends Dessin{

    private int couleur;
    private int largeurTrait;
    private int etape;
    private float cx1;
    private float cy1;

    private float cx2;
    private float cy2;
    private float cx3;
    private float cy3;
    private boolean estDebut;
    private boolean estEnCour;
    private boolean estFini;
    private Path pathDessin;
    private Paint ligneDessin;
    public Triangle(int couleur, int largeurTrait) {
        super(couleur, largeurTrait);
        this.couleur = couleur;
        this.largeurTrait = largeurTrait;

        this.estDebut=false;
        this.estEnCour=false;
        this.estFini=false;

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

    public boolean isEstDebut() {
        return estDebut;
    }

    public boolean isEstEnCour() {
        return estEnCour;
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
    public void setCx3(float cx3) {
        this.cx3 = cx3;
    }

    public void setCy3(float cy3) {
        this.cy3 = cy3;
    }

    public void setCx1(float cx1) {
        this.cx1 = cx1;
    }

    public void setCy1(float cy1) {
        this.cy1 = cy1;
    }

    public void setCx2(float cx2) {
        this.cx2 = cx2;
    }

    public void setCy2(float cy2) {
        this.cy2 = cy2;
    }

    public void setEstDebut(boolean estDebut) {
        this.estDebut = estDebut;
    }

    public void setEstEnCour(boolean estEnCour) {
        this.estEnCour = estEnCour;
    }
    public boolean isEstFini() {
        return estFini;
    }
    public void setEstFini(boolean estFini) {
        this.estFini = estFini;
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
