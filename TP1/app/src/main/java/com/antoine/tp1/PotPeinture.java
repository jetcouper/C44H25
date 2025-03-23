package com.antoine.tp1;

import android.graphics.Canvas;
import android.graphics.Paint;

public class PotPeinture extends Dessin{

    private int couleur;
    private int largeurTrait;

    public PotPeinture(int couleur, int largeurTrait) {
        super(couleur, largeurTrait);

        this.couleur = couleur;
        this.largeurTrait = largeurTrait;

    }

    @Override
    public void dessiner(Canvas canvas) {
        super.dessiner(canvas);

        Paint paint2 = new Paint();
        paint2.setColor(couleur);
        paint2.setStyle(Paint.Style.FILL);
        canvas.drawPaint(paint2);

    }
}
