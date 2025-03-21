package com.antoine.tp1;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public abstract class Dessin{

    private int couleur;
    private int largeurTrait;
    public Dessin(int couleur, int largeurTrait) {
        this.couleur = couleur;
        this.largeurTrait = largeurTrait;
    }

    public abstract void dessiner(Canvas canvas);


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
