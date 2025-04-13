package com.example.applicationannexe11bcapitales;

public class AdresseException extends Exception {
    private String capitale;
    private String etat;

    public AdresseException(String capitale, String etat)
    {
        super ("La capitale " + capitale + " n'est pas dans l'état " + etat);
        this.capitale = capitale;
        this.etat = etat;
    }

    public String getCapitale() {
        return capitale;
    }

    public String getEtat() {
        return etat;
    }
}
