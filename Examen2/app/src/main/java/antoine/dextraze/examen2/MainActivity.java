package antoine.dextraze.examen2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout conteneur;
    SurfaceDessin surf;
    ChipGroup chipTypes;
    SeekBar seek;
    Button rouge,vert,apercu;
    TextView texteCouleur,texteLargeur;
    String type;
    int couleur,largeur;

    ItemMinecraft dessin;

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
        conteneur = findViewById(R.id.conteneur);
        chipTypes = findViewById(R.id.groupe);
        texteCouleur = findViewById(R.id.texteCouleur);
        texteLargeur = findViewById(R.id.texteLargeur);

        surf = new SurfaceDessin(this);
        surf.setLayoutParams(new ViewGroup.LayoutParams(-1,-1));
        conteneur.addView(surf);
        Ecouteur ec = new Ecouteur();
        for(int i = 0; i < chipTypes.getChildCount(); i++){
            View petit = chipTypes.getChildAt(i);
            if(petit instanceof Chip){
                ((Chip)chipTypes.getChildAt(i)).setOnCheckedChangeListener(ec);
            }
        }
        seek = findViewById(R.id.seekBar);
        rouge = findViewById(R.id.boutonRouge);
        vert = findViewById(R.id.boutonVert);
        apercu = findViewById(R.id.boutonApercu);
        largeur = seek.getProgress();
        texteLargeur.setText("Largeur : " + largeur);

        apercu.setOnClickListener(ec);
        seek.setOnSeekBarChangeListener(ec);
        rouge.setOnClickListener(ec);
        vert.setOnClickListener(ec);
    }

    private class SurfaceDessin extends View {
        public SurfaceDessin(Context context) {
            super(context);
            this.setBackgroundColor(Color.GRAY);

        }

        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);

            if(dessin != null){
                dessin.dessiner(canvas);
            }

        }
    }

    private class Ecouteur implements CompoundButton.OnCheckedChangeListener, View.OnClickListener, SeekBar.OnSeekBarChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                type = (String)buttonView.getText();
                Toast.makeText(MainActivity.this, type, Toast.LENGTH_SHORT).show();
            }


        }

        @Override
        public void onClick(View v) {
            int idVue = v.getId();
                if(idVue == R.id.boutonRouge){
                    texteCouleur.setText((String)"Couleur de l'item : Rouge");
                    couleur = getColor(R.color.rouge);
                    
                } else if (idVue == R.id.boutonVert) {
                    texteCouleur.setText((String)"Couleur de l'item : Vert");
                    couleur = getColor(R.color.vert);
                    
                } else if (idVue == R.id.boutonApercu) {
                    if(couleur != 0 && type != null){
                        dessin = new ItemMinecraft(couleur,largeur,type);
                        surf.invalidate();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Veuillez choisir la couleur et le type SVP.", Toast.LENGTH_SHORT).show();
                    }
                }


        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            largeur = progress;
            texteLargeur.setText("Largeur : "+String.valueOf(progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
}