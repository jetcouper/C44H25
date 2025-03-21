package com.antoine.tp1;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.List;

public class Cercle extends Dessin {


    public float getCx() {
        return cx;
    }

    public void setCx(float cx) {
        this.cx = cx;
    }

    public float getCy() {
        return cy;
    }

    public void setCy(float cy) {
        this.cy = cy;
    }

    private float cx;
    private float cy;
    private Paint paint;

    public Cercle(int couleur,int largeurTrait, Paint paint) {
        super(couleur,largeurTrait);
        this.paint = paint;

        paint.setColor(couleur);
        paint.setStrokeWidth(largeurTrait);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setDither(true);




    }

    public void dessiner(Canvas canvas){
        float height = getCy();
        float width = getCx();
        float rayon = ((width*width) / (8 * height) + height / 2);

        canvas.drawCircle(getCx(),getCy(),rayon,paint);
    }


}
