package com.antoine.annexe2;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
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

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.Hashtable;
import java.util.Vector;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    Spinner chamNomCompte;
    TextView champSolde;
    //Button boutonValider;

    Button boutonEnvoyer;
    EditText champCourriel;
    EditText champTransfère;

    ArrayAdapter<String> choix;
    double solde;
    DecimalFormat df = new DecimalFormat("0.00$");
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


        chamNomCompte = findViewById(R.id.txtDe);
        champSolde = findViewById(R.id.txtSolde);
        champCourriel = findViewById(R.id.txtA);
        champTransfère = findViewById(R.id.txtTransfere);
        //boutonValider = findViewById(R.id.btnValider);
        boutonEnvoyer = findViewById(R.id.btnEnvoyer);
        Compte compte1 = new Compte("Chèque",1500);
        Compte compte2 = new Compte("Épargne",2000);
        Compte compte3 = new Compte("ÉpargnePlus",4000);


        ht.put(compte1.getName(), compte1);
        ht.put(compte2.getName(), compte2);
        ht.put(compte3.getName(), compte3);

        //solde = 1400;
        choix = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        choix.add(compte1.getName());
        choix.add(compte2.getName());
        choix.add(compte3.getName());

        chamNomCompte.setAdapter(this.choix);


        //1ere étape
        Ecouteur ec = new Ecouteur();

        //2e étape
        //boutonValider.setOnClickListener(ec);
        boutonEnvoyer.setOnClickListener(ec);
        chamNomCompte.setOnItemSelectedListener(ec);

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
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {


            String temp = chamNomCompte.getSelectedItem().toString();

            if(temp.equals("Épargne")){
                solde = ht.get(temp).getSolde();
                champSolde.setText(df.format(solde));
            }
            else if(temp.equals("Chèque")){
                solde = ht.get(temp).getSolde();
                champSolde.setText(df.format(solde));
            }
            else if(temp.equals("ÉpargnePlus")){
                solde = ht.get(temp).getSolde();
                champSolde.setText(df.format(solde));
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }

        @Override
        public void onClick(View source) { //Paramètre: Source de l'événement, boutons

            String cham = chamNomCompte.getOnItemSelectedListener().toString();
            String temp = chamNomCompte.getSelectedItem().toString();
            solde = ht.get(temp).getSolde();
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            AlertDialog dialog;

            if (source == boutonEnvoyer){
                //bouton envoyer
                String nomCourriel = String.valueOf(champCourriel.getText());
                nomCourriel = nomCourriel.trim();
                if (nomCourriel.isEmpty()){
                    builder.setMessage("Vous devez remplir le champ d'envoie!").setTitle("Erreur");
                    dialog = builder.create();
                    dialog.show();
                    //Snackbar.make(MainActivity.this.getCurrentFocus(), "Vous devez remplir le champ d'envoie!", Snackbar.LENGTH_SHORT).show();
                    //Toast.makeText(MainActivity.this, "Vous devez remplir le champ d'envoie!", Toast.LENGTH_LONG).show();
                }
                else if (!isValid(nomCourriel)){
                    builder.setMessage("Ceci n'est pas un adresse Email valide!").setTitle("Erreur");
                    dialog = builder.create();
                    dialog.show();
                    //Toast.makeText(MainActivity.this, "Ceci n'est pas un adresse Email valide!", Toast.LENGTH_LONG).show();

                }
                else{
                    double soldeDouble = Double.parseDouble(String.valueOf(champTransfère.getText()));

                    if(solde < 0 || solde < soldeDouble){

                        builder.setMessage("La somme demander dépasse le solde du compte!").setTitle("Erreur");
                        dialog = builder.create();
                        dialog.show();
                        //Toast.makeText(MainActivity.this, "La somme demander dépasse le solde du compte!", Toast.LENGTH_LONG).show();
                    } else if (champTransfère.getText().toString().equals("0")) {
                        builder.setMessage("Entrer un montant plus gros que 0!").setTitle("Erreur");
                        dialog = builder.create();
                        dialog.show();
                    } else{
                        ht.get(temp).mofifierSolde(soldeDouble);
                        champSolde.setText(df.format(ht.get(temp).getSolde()));
                        builder.setMessage("Transfère réussi!").setTitle("Réussi");
                        dialog = builder.create();
                        dialog.show();
                        //Toast.makeText(MainActivity.this, "Transfère réussi!", Toast.LENGTH_LONG).show();
                    }


                }
            }



        }



    }



}