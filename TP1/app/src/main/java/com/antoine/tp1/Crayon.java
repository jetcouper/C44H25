package com.antoine.tp1;

public class Crayon {
    public Crayon(int couleur, int largeurTrait) {
        this.couleur = couleur;
        this.largeurTrait = largeurTrait;

    }

    private int couleur;
    private int largeurTrait;
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
