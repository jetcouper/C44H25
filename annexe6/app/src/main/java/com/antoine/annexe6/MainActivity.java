package com.antoine.annexe6;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity {

    SurfaceDessin surf;
    ConstraintLayout main;
    private Paint cercle;
    private Paint cercle2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        main = findViewById(R.id.main);


        //1
        surf = new SurfaceDessin(this);

        //2
        //surf.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //surf.setLayoutParams(new ConstraintLayout.LayoutParams(600, 400)); //Autre moyen
        surf.setLayoutParams(new ViewGroup.LayoutParams(dpToPx(200),dpToPx(200)));
        // Logique car ... Pixel 4 1080 pixel de large, 200dp * 2.75 (densité) = 550, a peu près
        //3
        main.addView(surf);


    }
    public int dpToPx(int dp){
        float density = this.getResources().getDisplayMetrics().density;
        Log.i("densité", "" + density);
        Log.i("largeur", "" + getResources().getDisplayMetrics().widthPixels+ "");
        return Math.round(dp*density); //Arrondir
    }


    private class SurfaceDessin extends View {


        public SurfaceDessin(Context context) {

            super(context);
            this.setBackgroundColor(Color.BLACK);
        }

        @Override
        protected void onDraw(@NonNull Canvas canvas)
        {
            super.onDraw(canvas);

            cercle = new Paint(Paint.ANTI_ALIAS_FLAG); //Antialias: adoucir la courbure des courbe
            cercle.setColor(Color.YELLOW);
            canvas.drawCircle(80,80,80,cercle);
            cercle.setStyle(Paint.Style.STROKE); // pcq par défault avec le style Fill
            cercle.setStrokeWidth(8);
            canvas.drawCircle(280,80,80,cercle);

            cercle.setStyle(Paint.Style.FILL);

            cercle.setColor(Color.BLUE);
            canvas.drawArc(360,0,520,160,0,120,true,cercle);
            cercle.setColor(Color.RED);
            canvas.drawArc(360,0,520,160,120,120,true,cercle);
            cercle.setColor(Color.GREEN);
            //cercle.setColor(Color.argb(55,223,67,200)); //Autre façon de faire
            canvas.drawArc(360,0,520,160,240,120,true,cercle);




            //cercle.setColor(getResources().getColor());
            //canvas.drawArc(60,60,60,60,40,50,true,cercle);

//            cercle2 = new Paint(Paint.ANTI_ALIAS_FLAG);
//            cercle2.setColor(Color.RED);
//            canvas.drawArc(60,60,60,60,40,50,true,cercle2);
            //canvas.drawCircle(450,80,80,cercle2);

        }
    }

}