package com.antoine.tp1;

import android.graphics.Canvas;
public abstract class Dessin{

    private int couleur,largeurTrait;
    public Dessin(int couleur, int largeurTrait) {
        this.couleur = couleur;
        this.largeurTrait = largeurTrait;
    }

    public void dessiner(Canvas canvas){
    };
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
