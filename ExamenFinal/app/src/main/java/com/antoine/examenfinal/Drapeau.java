package com.antoine.examenfinal;

public class Drapeau {

    private String couleurG;
    private String couleurC;
    private String couleurD;
    private String pays;

    public Drapeau(String couleurGauche, String couleurCentre, String couleurDroit, String nomPays) {
        this.couleurG = couleurGauche;
        this.couleurC = couleurCentre;
        this.couleurD = couleurDroit;
        this.pays = nomPays;
    }

    public String getCouleurG() {
        return couleurG;
    }

    public String getCouleurC() {
        return couleurC;
    }

    public String getCouleurD() {
        return couleurD;
    }

    public String getPays() {
        return pays;
    }
}
