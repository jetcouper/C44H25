package com.ericlabonte.appmariage;

public class Invite {

    private String nom;
    private String noTable;

    public Invite(String nom, String noTable) {
        this.nom = nom;
        this.noTable = noTable;
    }

    public String getNom() {
        return nom;
    }

    public String getNoTable() {
        return noTable;
    }
}
