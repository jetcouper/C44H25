package com.example.examen2pratique;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
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


    }
}