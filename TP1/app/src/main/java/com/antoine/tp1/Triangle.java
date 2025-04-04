package com.antoine.tp1;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class Triangle extends Dessin{

    private int couleur,largeurTrait;

    private Boolean dessin1,dessin2, etapeFinal;
    private float cx1,cy1,cx2,cy2,cx3,cy3;
    private Path pathDessin;
    private Paint ligneDessin;
    private Boolean plein;
    public Triangle(int couleur, int largeurTrait, Boolean plein) {
        super(couleur, largeurTrait);
        this.couleur = couleur;
        this.largeurTrait = largeurTrait;
        this.plein = plein;

        dessin1 = false;
        dessin2 = false;
        etapeFinal= false;
        pathDessin = new Path();
        ligneDessin = new Paint(Paint.ANTI_ALIAS_FLAG);
        ligneDessin.setColor(this.couleur);
        ligneDessin.setStrokeWidth(this.largeurTrait);
        ligneDessin.setAntiAlias(true);
        ligneDessin.setStyle(Paint.Style.FILL_AND_STROKE);
        ligneDessin.setStrokeCap(Paint.Cap.ROUND);
        ligneDessin.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    public void dessiner(Canvas canvas) {
        if(plein){
            ligneDessin.setStyle(Paint.Style.FILL_AND_STROKE);
        } else if (!plein) {
            ligneDessin.setStyle(Paint.Style.STROKE);
        }


        if (dessin1){
            canvas.drawLine(cx1,cy1,cx2,cy2,ligneDessin);
        }
        if (dessin2){
            ligneDessin.setStyle(Paint.Style.FILL_AND_STROKE);
            canvas.drawLine(cx1,cy1,cx3,cy3,ligneDessin);
            canvas.drawLine(cx2,cy2,cx3,cy3,ligneDessin);
            //Pour remplir le triangle
            if(plein){
                pathDessin.moveTo(cx1, cy1);
                pathDessin.lineTo(cx2, cy2);
                pathDessin.lineTo(cx3, cy3);
                pathDessin.close();
            }
            canvas.drawPath(pathDessin, ligneDessin);
        }
    }

    public void setDessin1(Boolean dessin1) {
        this.dessin1 = dessin1;
    }

    public void setDessin2(Boolean dessin2) {
        this.dessin2 = dessin2;
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

    public Boolean getEtapeFinal() {
        return etapeFinal;
    }

    public void setEtapeFinal(Boolean etapeFinal) {
        this.etapeFinal = etapeFinal;
    }

    public void placerCoordoneesDepart(float cxDepart, float cyDepart){
        this.cx1 = cxDepart;
        this.cy1 = cyDepart;
    }
    public void placerCoordoneesEnCour(float cxFin,float cyFin){

        this.cx2 = cxFin;
        this.cy2 = cyFin;
    }

}
