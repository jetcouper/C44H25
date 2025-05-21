package com.example.tp2;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class Popup extends Dialog {

    Button ok;
    TextView statue;
    Jeu_Activity jeu;

    public Popup(@NonNull Context context) {
        super(context);
        jeu = (Jeu_Activity)context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);
        ok = findViewById(R.id.btnStatue);
        statue = findViewById(R.id.txtStatue);
        statue.setText(jeu.statue);


        Ecouteur ec = new Ecouteur();

        ok.setOnClickListener(ec);
    }


    private class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            Intent i = new Intent(jeu, Fin_Activity.class);
            Pointage point = new Pointage(jeu.partie.getScore());
            jeu.instance.ajouterPointage(point);
            jeu.startActivity(i);
            jeu.finish();
            dismiss();
        }
    }
}
