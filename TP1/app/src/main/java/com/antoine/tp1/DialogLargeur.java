package com.antoine.tp1;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class DialogLargeur extends Dialog {

    MainActivity fenetrePrincipale;
    SeekBar seek;
    TextView txtNombre;
    //float valeur;
    Button btnok;
    int traitdefaut = 10;

    public DialogLargeur(@NonNull Context context) {
        super(context);
        fenetrePrincipale = (MainActivity)context;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_largeur);
        seek = findViewById(R.id.seekBar);
        txtNombre = findViewById(R.id.txtLargeurNombre);
        btnok = findViewById(R.id.btnOK);
        Ecouteur ec = new Ecouteur();
        seek.setOnSeekBarChangeListener(ec);
        btnok.setOnClickListener(ec);
        seek.setProgress(traitdefaut);
        txtNombre.setText(String.valueOf(traitdefaut));

    }

    private class Ecouteur implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
        @Override
        public void onClick(View v) {

            fenetrePrincipale.dessin.setLargeurTrait(Integer.parseInt(txtNombre.getText().toString()));
            //fenetrePrincipale.setEpaisseurCrayon(Integer.parseInt(txtNombre.getText().toString()));
            dismiss();
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


            txtNombre.setText(String.valueOf(progress));

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
}