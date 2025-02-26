package com.antoine.annexe2;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerNomCompte;
    TextView champSolde;

    Button boutonEnvoyer;
    EditText champCourriel;
    EditText champTransfère;

    Vector<String> choix;
    double solde;
    DecimalFormat df = new DecimalFormat("0.00$");
    Compte compteChoisi;
    Hashtable<String,Compte> ht = new Hashtable<>();

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

        spinnerNomCompte = findViewById(R.id.txtDe);
        champSolde = findViewById(R.id.txtSolde);
        champCourriel = findViewById(R.id.txtA);
        champTransfère = findViewById(R.id.txtTransfere);
        boutonEnvoyer = findViewById(R.id.btnEnvoyer);

        //solde = 1400;
        choix = new Vector<String>();
        ht.put("Chèque", new Compte("Chèque",1500));
        ht.put("Épargne", new Compte("Épargne",60));
        ht.put("ÉpargnePlus", new Compte("ÉpargnePlus",2500));

        //On prend les clés et on les ajoute au vecteur donc au spinner
        choix.addAll(ht.keySet());


        //Adaptateur pour remplir le spinner //This = context(Activité) de adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,choix);
        spinnerNomCompte.setAdapter(adapter);

        //1ere étape
        Ecouteur ec = new Ecouteur();

        //2e étape
        boutonEnvoyer.setOnClickListener(ec);
        spinnerNomCompte.setOnItemSelectedListener(ec);


        //champCourriel.setText(spinnerNomCompte.getSelectedItem().toString());
    }

    public static boolean isValid(String email) { ///AJOUT PERSONNEL (Aucun lien avec le cours)

        // Regular expression to match valid email formats
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Compile the regex
        Pattern p = Pattern.compile(emailRegex);

        // Check if email matches the pattern
        return email != null && p.matcher(email).matches();
    }


    //3e étape : Classe interne
    private class Ecouteur implements View.OnClickListener, AdapterView.OnItemSelectedListener {

        @Override
        public void onClick(View source) { //Paramètre: Source de l'événement, boutons

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            String courriel = String.valueOf(champCourriel.getText());
            courriel = courriel.trim();
            //Log.i("test")

            if(courriel.matches("[\\w]+[.\\w]+@[\\w]+[.\\w]+")){
                String montant = String.valueOf(champTransfère.getText());
                double transfere = Double.parseDouble(montant);
                if(compteChoisi.tranfert(transfere)){
                    champSolde.setText(df.format(compteChoisi.getSolde()));
                    Toast.makeText(MainActivity.this, "Le transfert a eu lieu", Toast.LENGTH_SHORT).show();
                    champCourriel.setText("");
                    champTransfère.setText("");
                }
                else{

                    builder.setMessage("Manque de fonds").setTitle("Erreur");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    champTransfère.setText("");
                }
            }
            else {
                builder.setMessage("Ceci n'est pas un courriel valide").setTitle("Erreur");
                AlertDialog dialog = builder.create();
                dialog.show();
                champCourriel.setText("");
            }



        }


        @Override
        public void onItemSelected(AdapterView<?> parent, View itemSelectionner, int position, long id) {
            //Solution 1
            //Toast.makeText(MainActivity.this, choix.get(position), Toast.LENGTH_SHORT).show();
            //Solution 2
            //TextView choisi = (TextView)itemSelectionner;
            //Toast.makeText(MainActivity.this, choisi.getText().toString(), Toast.LENGTH_LONG).show();
            //Solution 3
            String cle = (String)spinnerNomCompte.getItemAtPosition(position);
            compteChoisi = ht.get(cle);
            champSolde.setText(df.format(compteChoisi.getSolde()));
            //Toast.makeText(MainActivity.this, temp, Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }



}