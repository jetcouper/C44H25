package com.antoine.tp1;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

public class Effacer extends Dessin{

    private Path pathDessin;
    private Paint ligneDessin;

    public Effacer(int couleur, int largeurTrait, Path pathDessin, Paint ligneDessin) {
        super(couleur,largeurTrait);
        this.pathDessin = pathDessin;
        this.ligneDessin = ligneDessin;


        ligneDessin = new Paint(Paint.ANTI_ALIAS_FLAG);
        ligneDessin.setColor(couleur);
        ligneDessin.setStrokeWidth(largeurTrait);
        ligneDessin.setAntiAlias(true);
        ligneDessin.setStyle(Paint.Style.STROKE);
        ligneDessin.setStrokeJoin(Paint.Join.ROUND);
        ligneDessin.setStrokeCap(Paint.Cap.ROUND);
        ligneDessin.setDither(true);

    }

    public void dessiner(Canvas canvas){
        canvas.drawPath(pathDessin,ligneDessin);
    }
}
