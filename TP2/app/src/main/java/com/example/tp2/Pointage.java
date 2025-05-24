package com.example.tp2;

import java.time.LocalDate;

public class Pointage {

    //Mes points
    private Integer point;
    //La date d'aujourd'hui selon la date dans l'émulateur(ATTENTION de bien voir la région du temps dans l'émulateur.)
    private LocalDate date;

    //Classe de Pointage qui va s'enregistrer dans ma base de donnée
    public Pointage(Integer point) {
        this.point = point;
        this.date = LocalDate.now();
    }

    public Integer getPoint() {
        return point;
    }

    public LocalDate getDate() {
        return date;
    }
}
