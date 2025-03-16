package com.antoine.tp1;

public class Dessin {
    String couleur;
    int largeurTrait;
    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public int getLargeurTrait() {
        return largeurTrait;
    }

    public void setLargeurTrait(int largeurTrait) {
        this.largeurTrait = largeurTrait;
    }




    public Dessin(String couleur, int largeurTrait) {
        this.couleur = couleur;
        this.largeurTrait = largeurTrait;
    }

}
