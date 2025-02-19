package com.antoine.annexe2;

import java.util.Hashtable;

public class Compte {


    private String nom;

    private double solde;

    public void mofifierSolde(double soldeCompte){

        solde = (solde - soldeCompte);
    }

    public Compte(String nom, double solde){
        this.nom = nom;
        this.solde = solde;
    }

    public String getName(){
        return nom;
    }
    public double getSolde(){
        return solde;
    }
    @Override
    public String toString() {
        return getName();
    }

}
