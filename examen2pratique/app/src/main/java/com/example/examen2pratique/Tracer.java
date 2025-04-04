package com.example.examen2pratique;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

public class Tracer extends Dessin{


    private int couleur, largeurTrait;
    private Path path;
    private Paint paint;
    private Point depart;
    private Point fin;


    public Tracer(int couleur, int largeurTrait) {
        super(couleur, largeurTrait);

        this.couleur = couleur;
        this.largeurTrait = largeurTrait;
        this.depart = new Point();
        this.fin = new Point();

        this.path = new Path();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(this.couleur);
        paint.setStrokeWidth(this.largeurTrait);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

    }

    @Override
    public void dessiner(Canvas canvas) {
        super.dessiner(canvas);


        if(depart != null){
            canvas.drawRect(depart.x-20,depart.y-20,depart.x+20,depart.y+20,paint);
        }
        if (fin != null){
            canvas.drawRect(fin.x-20,fin.y-20,fin.x+20,fin.y+20,paint);
            canvas.drawLine(depart.x,depart.y,fin.x,fin.y,paint);
        }


    }

    public Point getDepart() {
        return depart;
    }

    public void setDepart(Point depart) {
        this.depart = depart;
    }

    public Point getFin() {
        return fin;
    }

    public void setFin(Point fin) {
        this.fin = fin;
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
