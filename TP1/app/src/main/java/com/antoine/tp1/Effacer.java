package com.antoine.tp1;

import android.graphics.Canvas;
import android.graphics.Color;

public class Effacer extends Dessin{


    public Effacer(int couleur, int largeurTrait) {
        super(couleur, largeurTrait);

        setCouleur(couleur);
        setLargeurTrait(largeurTrait);
    }

    @Override
    public void dessiner(Canvas canvas) {
        super.dessiner(canvas);
    }
}
