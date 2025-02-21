package com.antoine.examen1;

public class Electeur {

    private String nomPartie;
    private String trancheAge;

    public Electeur(String nomPartie, String trancheAge) {
        this.nomPartie = nomPartie;
        this.trancheAge = trancheAge;
    }

    public String getNomPartie() {
        return nomPartie;
    }

    public void setNomPartie(String nomPartie) {
        this.nomPartie = nomPartie;
    }

    public String getTrancheAge() {
        return trancheAge;
    }

    public void setTrancheAge(String trancheAge) {
        this.trancheAge = trancheAge;
    }
}
