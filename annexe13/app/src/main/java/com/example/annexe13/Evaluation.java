package com.example.annexe13;

public class Evaluation {


    private String nom;
    private String microbrasserie;
    private double evaluation;


    public Evaluation(String nom, String microbrasserie, double evaluation) {
        this.nom = nom;
        this.microbrasserie = microbrasserie;
        this.evaluation = evaluation;
    }

    public String getNom() {
        return nom;
    }

    public String getMicrobrasserie() {
        return microbrasserie;
    }

    public double getEvaluation() {
        return evaluation;
    }



}
