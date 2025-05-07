package com.example.tp2;

import java.time.LocalDate;

public class Pointage {


    private Integer point;

    private LocalDate date;

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
