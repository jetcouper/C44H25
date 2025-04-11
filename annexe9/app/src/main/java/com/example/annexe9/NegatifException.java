package com.example.annexe9;


//C'est une exception contrôlée
public class NegatifException extends Exception{

    private double montantErrone;

    public NegatifException(double montantErrone){
        super("Le montant: " + montantErrone + " entré ne peut pa être négatif");
        this.montantErrone = montantErrone;

    }

    public double getMontantErrone() {
        return montantErrone;
    }
}
