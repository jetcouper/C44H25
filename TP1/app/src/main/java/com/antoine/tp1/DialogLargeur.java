package com.antoine.tp1;

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

    MainActivity fenetrePrincipale;
    SeekBar seek;
    TextView txtNombre;
    //float valeur;
    Button btnok;

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
//        if(!(txtNombre.getText().toString().equals("0"))){
//            txtNombre.setText(String.valueOf(fenetrePrincipale.getLageurTrait()));
//            seek.setProgress(fenetrePrincipale.getLageurTrait());
//        }
        seek.setOnSeekBarChangeListener(ec);
        btnok.setOnClickListener(ec);


//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_dialog_largeur);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

    }

    private class Ecouteur implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
        @Override
        public void onClick(View v) {
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