package com.antoine.annexe2;

public class Compte {
    private String nom;
    private double solde;

    public Compte(String nom, double solde) {
        this.nom = nom;
        this.solde = solde;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public boolean tranfert(double montantATransferer){

        if (solde >= montantATransferer){
            solde -= montantATransferer;
            return true;
        }
        else{
            return false;
        }
    }


}
