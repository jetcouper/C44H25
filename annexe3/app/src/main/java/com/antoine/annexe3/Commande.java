package com.antoine.annexe3;

import java.util.Vector;

public class Commande {

    private Vector<Produit> listeCommande;

    public Commande ( )
    {
        listeCommande = new Vector();
    }

    public void ajouterProduit ( Produit p )
    {
        listeCommande.add(p);
    }

    public double total ()
    {
	double total =0;
        // compléter : total de la commande
        for (Produit i : listeCommande){
            total += i.getPrix();
        }

	return total;
    }

    public double taxes()
    {
        double taxes = 0;
        double total = 0;
        total = total();


        taxes += total * 0.05;
        taxes += total * 0.0975;


        // tps sur le montant avant taxes ( 5% )

        
        //tvq sur le montant avant taxes ( 9.975% )
        
        // taxes total = tps + tvq



        return taxes;
    }

    public double grandTotal(){
	
	double grTotal = 0;

    grTotal = total() + taxes();

	// compléter


	return grTotal;
	

    }
}
