package antoine.dextraze.examen2;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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
    LinearLayout liCouleur;
    SurfaceDessin surf;
    ChipGroup chipTypes;
    Button rouge;
    Button vert;
    String type;
    int couleur;
    int largeur;

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
        //Peut ne pas marcher
        conteneur.setBackgroundColor(getColor((Integer)Color.GRAY));

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
        for(int i = 0; i < liCouleur.getChildCount(); i++){
            View petit = liCouleur.getChildAt(i);
            if(petit instanceof Button){
                liCouleur.getChildAt(i).setOnClickListener(ec);
            }
        }




        surf.setOnTouchListener(ec);
    }

    private class SurfaceDessin extends View {
        public SurfaceDessin(Context context) {
            super(context);


        }
    }

    private class Ecouteur implements CompoundButton.OnCheckedChangeListener, View.OnTouchListener, View.OnClickListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                type = (String)buttonView.getText();
                Toast.makeText(MainActivity.this, type, Toast.LENGTH_SHORT).show();
            }


        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {


            return false;
        }

        @Override
        public void onClick(View v) {
            int idVue = v.getId();
                if(idVue == R.id.boutonRouge){
                    
                    
                } else if (idVue == R.id.boutonVert) {
                    
                    
                } else if (idVue == R.id.boutonApercu) {
                    
                }


        }
    }
}