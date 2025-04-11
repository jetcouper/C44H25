package com.example.applicationannexe11bcapitales;


import java.util.Hashtable;

import bla.HashtableAssociation;

public class Inscrit {
    private String nom;
    private String prenom;
    private String adresse;
    private String capitale;
    private String etat;
    private String codeZip;

    public Inscrit(String nom, String prenom, String adresse, String capitale, String etat, String codeZip) throws AdresseException {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.codeZip = codeZip;



        // vérifier si la capitale fait partie de l'état à l'aide d'une Hashtable secrète ( classe HashtableAssociation )
        HashtableAssociation hash = new HashtableAssociation();
        String etatRetour = hash.get(capitale);

        if(!etatRetour.equals(etat)){ //Si c'est pas pareil, ca veut dire qu'il n'a pas entré une adresse valide
            throw new AdresseException(capitale,etat);
        }



    }
}
