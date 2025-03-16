package com.antoine.appdessin;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ImageView image;
    int lageurTrait;
    //SeekBar seek;


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
        image = findViewById(R.id.imageTrai);

        Ecouteur ec = new Ecouteur();
        image.setOnClickListener(ec);




    }

    public int getLageurTrait() {
        return lageurTrait;
    }

    public void setLageurTrait(int lageurTrait) {
        this.lageurTrait = lageurTrait;
    }


    private class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //On veut faire apparaitre un boite de dialogue
            DialogLargeur dialog = new DialogLargeur(MainActivity.this);
            dialog.show();

        }
    }
}