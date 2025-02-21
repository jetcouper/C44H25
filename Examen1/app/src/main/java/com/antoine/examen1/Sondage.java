package com.antoine.examen1;

import java.util.Vector;

public class Sondage {

    private Vector<Electeur> listeElecteur;


    public Sondage() {
        listeElecteur = new Vector<>();
    }

    public void ajouterElecteur(Electeur e){
        listeElecteur.add(e);
    }

    public double calculTrancheAgePourcent(String partie){
        double pourcent = 0;
        int nombrePartie =0;
        int nb2 = 0;
        
        for(Electeur p :listeElecteur){

            if(p.getNomPartie().equals(partie)){
                nombrePartie++;
                //return pourcent;
            }

        }
        pourcent = ((nombrePartie)*100/listeElecteur.size());
        return pourcent;
    }



}
