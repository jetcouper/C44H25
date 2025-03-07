package com.antoine.appdessin;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DialogLargeur extends Dialog {

    MainActivity fenetrePrincipale;


    public DialogLargeur(@NonNull Context context) {
        super(context);
        fenetrePrincipale = (MainActivity)context;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_largeur);





    }
}