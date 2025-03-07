package com.antoine.annexe1_tp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Surface s;
    LinearLayout parent;
    Button bouton;

    EditText chamX, chamY;
    int x,y;
    Path p;
    Paint c;

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

        parent = findViewById(R.id.parent);
        chamX = findViewById(R.id.txtCoordX);
        chamY = findViewById(R.id.txtCoordY);
        bouton = findViewById(R.id.btnEntree);

        s = new Surface(this);
        s.setLayoutParams(new LinearLayout.LayoutParams(-1,-1));
        s.setBackgroundColor(Color.BLUE);
        parent.addView(s);

        Ecouteur ec = new Ecouteur();
        bouton.setOnClickListener(ec);
        p = new Path();







    }
    private class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            x = Integer.parseInt(chamX.getText().toString());
            y = Integer.parseInt(chamY.getText().toString());
            if(p.isEmpty()){
                p.moveTo(x,y);
            }
            else{
                p.lineTo(x,y);
            }
            s.invalidate();

        }
    }


    private class Surface  extends View {


        public Surface(Context context) {
            super(context);
            c = new Paint(Paint.ANTI_ALIAS_FLAG);
            c.setStyle(Paint.Style.STROKE);
            c.setStrokeWidth(18);



        }

        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawPath(p,c);



        }
    }


}