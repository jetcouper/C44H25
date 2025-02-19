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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
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
    DecimalFormat df = new DecimalFormat("0.00$");///AJOUT PERSONNEL (Aucun lien avec le cours)

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

        solde = 1400;
        choix = new Vector<>();
        choix.add("Chèque");
        choix.add("Épargne");
        choix.add("Épargne Plus");

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


//            if (source == boutonValider){
//                //Équivalant
//                //String nomCompte = chamNomCompte.getText().toString();
//                String nomCompte = String.valueOf(spinnerNomCompte.getText());
//                nomCompte = nomCompte.toUpperCase(); //Place en majuscule pour faire moins de comparaisons
//                nomCompte.trim();//Pour enlever les espace inutiles au début ou à la fin du champ texte
//
//                if (choix.contains(nomCompte)){
//                    champSolde.setText(df.format( solde ));
//
//                }
//                else{
//                    //context: sysnonyme de l'activité
//                    Toast.makeText(MainActivity.this, "Pas un bon nom de compte!", Toast.LENGTH_LONG).show();
//                }
//            }
            if (source == boutonEnvoyer){
                //bouton envoyer
                String nomCourriel = String.valueOf(champCourriel.getText());
                nomCourriel = nomCourriel.trim();



                if (nomCourriel.isEmpty()){
                    Toast.makeText(MainActivity.this, "Vous devez remplir le champ d'envoie!", Toast.LENGTH_LONG).show();
                }
                else if (!isValid(nomCourriel)){
                    Toast.makeText(MainActivity.this, "Ceci n'est pas un adresse Email valide!", Toast.LENGTH_LONG).show();

                }
                else{
                    double soldeDouble = Double.parseDouble(String.valueOf(champTransfère.getText()));

                    if(solde == 0 || solde < soldeDouble){
                        Toast.makeText(MainActivity.this, "La somme demander dépasse le solde du compte!", Toast.LENGTH_LONG).show();
                    }
                    else{
                        solde = solde - soldeDouble;
                        champSolde.setText(df.format( solde ));
                        Toast.makeText(MainActivity.this, "Transfère réussi!", Toast.LENGTH_LONG).show();
                    }


                }
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
            String temp = (String)spinnerNomCompte.getItemAtPosition(position);
            Toast.makeText(MainActivity.this, temp, Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }



}