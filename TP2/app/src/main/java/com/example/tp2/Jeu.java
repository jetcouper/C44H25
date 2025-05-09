package com.example.tp2;

import android.content.ClipData;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Jeu extends AppCompatActivity {
    DatabaseHelper instance;
    Button menuPrincipale;
    TextView txtNbCarte, txtScoreActuel,txtCarteHG,txtCarteHD,txtCarteBG,txtCarteBD,txtC1,txtC2,txtC3,txtC4,txtC5,txtC6,txtC7,txtC8;
    LinearLayout main,lHG,lHD,lBG,lBD,lC1,lC2,lC3,lC4,lC5,lC6,lC7,lC8;
    Chronometer chrono;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_jeu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        menuPrincipale = findViewById(R.id.btnRetourMenu);
        main = findViewById(R.id.main);

        instance = DatabaseHelper.getInstance(getApplicationContext());
        instance.ouvrirConnexion();
        Ecouteur ec = new Ecouteur();

//        lC1 = findViewById(R.id.la1);
//        lC2 = findViewById(R.id.la2);
//        lC3 = findViewById(R.id.la3);
//        lC4 = findViewById(R.id.la4);
//        lC5 = findViewById(R.id.la5);
//        lC6 = findViewById(R.id.la6);
//        lC7 = findViewById(R.id.la7);
//        lC8 = findViewById(R.id.la8);



//        for(int i = 0; i < main.getChildCount(); i++){
//            LinearLayout colonne = (LinearLayout) main.getChildAt(i);
//            String nom = getResources().getResourceName(colonne.getId());
//            if(nom.contains("la")){
//                colonne.setOnDragListener(ec);
//                colonne.getChildAt(0).setOnTouchListener(ec);
//            }
//
//        }


        appliquerListeners(findViewById(R.id.main), ec, ec);
        menuPrincipale.setOnClickListener(ec);


    }
    private void appliquerListeners(View view, View.OnDragListener dragListener, View.OnTouchListener touchListener) {
        if (view instanceof LinearLayout) {
            LinearLayout layout = (LinearLayout) view;

            int id = layout.getId();
            if (id != View.NO_ID) {
                try {
                    String nom = getResources().getResourceEntryName(id);
                    if (nom != null && !nom.isEmpty()) {
                        // Tous peuvent recevoir un drop
                        if (!"main".equals(nom)) {
                            layout.setOnDragListener(dragListener);
                        }

                        // 🔐 Seuls les layouts "laX" peuvent être déplacés (draggables)
                        if (nom.startsWith("la")) {
                            layout.setOnTouchListener(touchListener);
                        } else {
                            layout.setOnTouchListener(null); // protection explicite
                        }
                    }
                } catch (Resources.NotFoundException ignored)
                {
                    
                }
            }
        }

        // Explorer récursivement les enfants
        if (view instanceof ViewGroup) {
            ViewGroup groupe = (ViewGroup) view;
            for (int i = 0; i < groupe.getChildCount(); i++) {
                appliquerListeners(groupe.getChildAt(i), dragListener, touchListener);
            }
        }
    }



    private class Ecouteur implements View.OnDragListener, View.OnTouchListener, View.OnClickListener {
        Drawable normal = getResources().getDrawable(R.drawable.bg_card, null);
        Drawable select = getResources().getDrawable(R.drawable.bg_card_selectionne, null);

        View carte = null;
        ViewGroup parentOrigine = null;

        @Override
        public boolean onDrag(View source, DragEvent event) {

            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_ENTERED:
                    source.setBackground(select);
                    break;

                case DragEvent.ACTION_DRAG_EXITED:
                    source.setBackground(normal);
                    break;

                case DragEvent.ACTION_DROP:
                    if (carte != null && parentOrigine != null) {
                        // Identifier la zone cible
                        String nomDestination = getResources().getResourceEntryName(source.getId());

                        // 🔍 Vérifie si c'est une zone de drop valide
                        boolean estDestinationValide = nomDestination.contains("lCarte"); // adapte cette logique

                        // Toujours remettre la carte à sa place d’origine
                        ViewGroup parentActuel = (ViewGroup) carte.getParent();
                        if (parentActuel != null && parentActuel != parentOrigine) {
                            parentActuel.removeView(carte);
                        }
                        if (carte.getParent() != parentOrigine) {
                            parentOrigine.addView(carte);
                        }

                        // Appliquer la visibilité selon validité
                        if (estDestinationValide) {
                            carte.setVisibility(View.INVISIBLE);
                        } else {
                            carte.setVisibility(View.VISIBLE);
                        }
                    }
                    break;

                case DragEvent.ACTION_DRAG_ENDED:
                    source.setBackground(normal);

                    // 💡 Si le drop n'a pas été "consommé" → cas : drop dans le vide
                    if (!event.getResult() && carte != null && parentOrigine != null) {
                        // Remettre à la position d’origine
                        ViewGroup parentActuel = (ViewGroup) carte.getParent();
                        if (parentActuel != null && parentActuel != parentOrigine) {
                            parentActuel.removeView(carte);
                        }
                        if (carte.getParent() != parentOrigine) {
                            parentOrigine.addView(carte);
                        }

                        // Dans ce cas, la carte reste visible
                        carte.setVisibility(View.VISIBLE);
                    }
                    break;
            }

            return true;
        }

        @Override
        public boolean onTouch(View source, MotionEvent event) {

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                carte = source;
                parentOrigine = (ViewGroup) source.getParent();

                String numero = "";
                ClipData clip = null;

                if (source instanceof LinearLayout) {
                    LinearLayout layout = (LinearLayout) source;
                    for (int i = 0; i < layout.getChildCount(); i++) {
                        View child = layout.getChildAt(i);
                        if (child instanceof TextView) {
                            numero = ((TextView) child).getText().toString();
                            clip = ClipData.newPlainText("label", numero);
                            break;
                        }
                    }
                }

                View.DragShadowBuilder builder = new View.DragShadowBuilder(source);
                source.startDragAndDrop(clip, builder, source, 0);
                source.setVisibility(View.INVISIBLE);
                return true;
            }
            return false;
        }


        @Override
        public void onClick(View v) {
            finish();
        }
    }




    @Override
    protected void onStop() {
        super.onStop();
        instance.fermerConnexion();
    }


}