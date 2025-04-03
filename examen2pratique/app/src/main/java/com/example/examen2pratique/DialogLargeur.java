package com.example.examen2pratique;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DialogLargeur extends Dialog {

    MainActivity principale;
    SeekBar seek;
    TextView txtNombre;
    Button btnok;
    int traitdefaut = 10;


    public DialogLargeur(@NonNull Context context) {
        super(context);

        principale = (MainActivity)context;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_largeur);
        seek = findViewById(R.id.seekBar);
        txtNombre = findViewById(R.id.txtNombre);
        btnok = findViewById(R.id.btnOK);
        Ecouteur ec = new Ecouteur();
        seek.setOnSeekBarChangeListener(ec);
        btnok.setOnClickListener(ec);
        seek.setProgress(traitdefaut);
        txtNombre.setText(String.valueOf(traitdefaut));

    }

    private class Ecouteur implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {
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

        @Override
        public void onClick(View v) {
            principale.epaisseurCrayon = (Integer.parseInt(txtNombre.getText().toString()));
            dismiss();
        }
    }
}