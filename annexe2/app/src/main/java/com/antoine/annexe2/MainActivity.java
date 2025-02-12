package com.antoine.annexe2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    EditText chamNomCompte;
    TextView champSolde;
    Button boutonValider;

    Button boutonEnvoyer;
    EditText champCourriel;
    EditText champTransfère;

    Vector<String> choix;
    double solde;

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

        chamNomCompte = findViewById(R.id.txtDe);
        champSolde = findViewById(R.id.txtSolde);
        champCourriel = findViewById(R.id.txtA);
        champTransfère = findViewById(R.id.txtTransfere);
        boutonValider = findViewById(R.id.btnValider);
        boutonEnvoyer = findViewById(R.id.btnEnvoyer);

        solde = 1400;
        choix = new Vector<>();
        choix.add("CHEQUE");
        choix.add("EPARGNE");
        choix.add("EPARGNEPLUS");



        //1ere étape
        Ecouteur ec = new Ecouteur();

        //2e étape
        boutonValider.setOnClickListener(ec);
        boutonEnvoyer.setOnClickListener(ec);



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
    private class Ecouteur implements View.OnClickListener{


        @Override
        public void onClick(View source) { //Paramètre: Source de l'événement, boutons

            DecimalFormat df = new DecimalFormat("0.00$");///AJOUT PERSONNEL (Aucun lien avec le cours)
            if (source == boutonValider){
                //Équivalant
                //String nomCompte = chamNomCompte.getText().toString();
                String nomCompte = String.valueOf(chamNomCompte.getText());
                nomCompte = nomCompte.toUpperCase(); //Place en majuscule pour faire moins de comparaisons
                nomCompte.trim();//Pour enlever les espace inutiles au début ou à la fin du champ texte

                if (choix.contains(nomCompte)){
                    champSolde.setText(df.format( solde ));

                }
                else{
                    //context: sysnonyme de l'activité
                    Toast.makeText(MainActivity.this, "Pas un bon nom de compte!", Toast.LENGTH_LONG).show();
                }
            }
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


    }



}