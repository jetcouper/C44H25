package com.antoine.examenfinal;

public class Drapeau {

    private String couleurGauche;
    private String couleurCentre;
    private String couleurDroit;
    private String nomPays;

    public Drapeau(String couleurGauche, String couleurCentre, String couleurDroit, String nomPays) {
        this.couleurGauche = couleurGauche;
        this.couleurCentre = couleurCentre;
        this.couleurDroit = couleurDroit;
        this.nomPays = nomPays;
    }

    public String getCouleurGauche() {
        return couleurGauche;
    }

    public String getCouleurCentre() {
        return couleurCentre;
    }

    public String getCouleurDroit() {
        return couleurDroit;
    }

    public String getNomPays() {
        return nomPays;
    }
}
