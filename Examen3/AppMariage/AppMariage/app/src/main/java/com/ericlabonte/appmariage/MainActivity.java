package com.ericlabonte.appmariage;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    EditText champNom;
    Button bouton;
    TextView champNumero;
    ListView liste;
    Vector<String> vector = null;
    Helper instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resultat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        champNom = findViewById(R.id.champNom);
        bouton = findViewById(R.id.bouton);
        champNumero = findViewById(R.id.texteReponse);
        liste = findViewById(R.id.liste);

        instance = Helper.getInstance(getApplicationContext());

        instance.ouvrirConnexion();


        Ecouteur ec = new Ecouteur();

        bouton.setOnClickListener(ec);


    }


    private class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View v) {


            String nom = champNom.getText().toString();

            if(nom.isEmpty()){
                Toast.makeText(MainActivity.this, "Veuillez mettre un nom SVP!!!", Toast.LENGTH_SHORT).show();
                liste.removeAllViewsInLayout();
                champNumero.setText("");
                return;
            }

            try {
                champNumero.setText(instance.retourneNumero(nom));
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Erreur: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
            String noTable = champNumero.getText().toString();



            vector = instance.retournerToutLesNom(noTable,nom);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, vector);
            liste.setAdapter(adapter);
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        instance.fermerConnexion();
    }
}