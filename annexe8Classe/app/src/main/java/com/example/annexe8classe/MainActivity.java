package com.example.annexe8classe;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class MainActivity extends AppCompatActivity {

    ChipGroup groupe;


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


        groupe = findViewById(R.id.groupe);


        Ecouteur ec = new Ecouteur();
        //Version du prof
//        for(int i = 0; i < groupe.getChildCount(); i++){
//            Chip temp = (Chip)groupe.getChildAt(i);
//            temp.setOnCheckedChangeListener(ec);
//        }



        //Boucler sur les enfants du groupe pour ajouter l'écouteur OnCheckedChangeListener
        for(int i = 0; i < groupe.getChildCount(); i++){
            View petit = groupe.getChildAt(i);
            if(petit instanceof Chip){
                ((Chip)groupe.getChildAt(i)).setOnCheckedChangeListener(ec);
            }
        }


    }

    private class Ecouteur implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


            //int nomCouleur = Color.parseColor(couleurString);
            if (isChecked){ //Je veux celui dont le checked est à true
                //String couleurString = (String)buttonView.getTag();
                Toast.makeText(MainActivity.this, (String)buttonView.getTag(), Toast.LENGTH_SHORT).show();
            }

        }
    }
}