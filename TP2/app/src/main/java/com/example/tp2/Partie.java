package com.example.tp2;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Partie {



    //Mon score actuel
    private int score;
    //Mon score précédant
    private int scorePrecedant;
    //La liste des 8 carte présente physiquement
    private List<Integer> listCarte;
    //La liste de mes cartes(nombre) non jouer
    private List<Integer> listNombre;
    //Le temps précédant mon ajout de score.
    private long tempsPrecedant;
    //Mes points bonus
    private int bonus;
    //Le numéro de carte précédement jouer.
    private int coupPrecedant;
    //La position de carte précédament jouer de ma liste
    private int positionPrecedant;
    //Le layout précédant de ma carte(Pour la remettre à sa place.)
    private View cartePilePrecedant;
    //Le numéro de carte précédement jouer(En string pour les données).
    private String noCartePilePrecedant;
    //???
    private boolean uneCarte;


    //Constructeur de la classe Partie
    public Partie() {
        listCarte = new ArrayList<>(Collections.nCopies(8, -1));
        listNombre = new ArrayList<>();
        score = 0;
        tempsPrecedant = 0;
        bonus = 100;
        uneCarte = false;
    }

    //Va générer une liste de nombre au début de chaque partie.
    public void genererListe(){
        for (int i = 1; i < 97; i++) {
            listNombre.add(i);
        }
        Collections.shuffle(listNombre);
    }
    //Va confirmer l'emplacement si elle est valide et va placer la carte en conséquences
    public boolean essayerPlacerCarte(View pileView, String nomDestination, View carte, String noCarteOrigine, long temps, TextView nbCarte, TextView score, Button reverse){
        if (!(pileView instanceof LinearLayout)) return false;

        TextView pileString = (TextView) ((LinearLayout) pileView).getChildAt(0);
        String textePile = pileString.getText().toString();


        int valeurPile;
        if (textePile.isEmpty()) {
            valeurPile = -1;
        } else {
            valeurPile = Integer.parseInt(textePile);
        }


        int valeurCarte = Integer.parseInt(noCarteOrigine);


        int differencielle;
        if (valeurPile != -1) {
            differencielle = valeurPile - valeurCarte;
        } else {
            differencielle = 0;
        }

        // Initialisation si la pile est vide
        if (textePile.isEmpty()) {
            if (nomDestination.equals("lCarte1") || nomDestination.equals("lCarte2")) {
                valeurPile = 0;
                pileString.setText("0");
            } else if (nomDestination.equals("lCarte3") || nomDestination.equals("lCarte4")) {
                valeurPile = 98;
                pileString.setText("98");
            }
        }

        // Vérification des conditions de placement des cartes
        boolean placementValide = (
                (nomDestination.equals("lCarte1") || nomDestination.equals("lCarte2")) && valeurPile < valeurCarte ||
                        (nomDestination.equals("lCarte3") || nomDestination.equals("lCarte4")) && valeurPile > valeurCarte ||
                        Math.abs(differencielle) == 10
        );

        if (!placementValide){
            return false;
        }


        // Enregistrer l’état précédent pour l’annulation
        setCartePilePrecedant(pileView);
        setNoCartePilePrecedant(textePile);

        // Mise à jour de Jeu_Activity et de l’état
        pileString.setText(noCarteOrigine);
        retirerCarte(valeurCarte);
        nbCarte.setText(String.valueOf(retournerNombreCarte()));
        appliquerPoint(temps);

        // Éffacer la valeur de la carte choisi parmis les 8 pour y acceuillir un autre plus tard.
        if (carte instanceof LinearLayout) {
            TextView c = (TextView) ((LinearLayout) carte).getChildAt(0);
            c.setText("");
        }

        // Autoriser le bouton annuler dernier coup
        reverse.setEnabled(true);
        score.setText(String.valueOf(getScore()));

        return true;
    }
    //Va retirer la carte avec la valeur passer en paramètre
    public void retirerCarte(int carte){
        for (int i = 0; i < listCarte.size(); i++) {
            Integer current = listCarte.get(i);
            if (listCarte.get(i) == carte) {
                coupPrecedant = current;
                positionPrecedant = i;
                listCarte.set(i, -1);
                break; // On arrête après la première occurrence
            }
        }
    }
    //Va retourner le nombre de carte total encore présent dans le jeux.
    public int retournerNombreCarte(){

        int count = 0;
        for (int i = 0; i < listCarte.size(); i++) {
            if(listCarte.get(i) != -1){
                count++;
            }
        }

        return listNombre.size()+ count;
    }
    //Va retourner le nombre de carte présente en bas.(Les 8 cartes)
    public int retournerNombre8Carte(){

        int count = 0;
        for (int i = 0; i < listCarte.size(); i++) {
            if(listCarte.get(i) != -1){
                count++;
            }
        }
        return count;
    }

    //Annuler le dernier coup de carte.
    public void annulerDernierCoup(LinearLayout ligne1, LinearLayout ligne2, View vue){


        if(positionPrecedant <=4){
            for (int i = 0; i < ligne1.getChildCount(); i++) {
                LinearLayout v = (LinearLayout)ligne1.getChildAt(i);
                for (int j = 0; j < v.getChildCount(); j++) {
                    if(i == positionPrecedant){
                        View view = v.getChildAt(j);
                        if(view instanceof TextView){
                            ((TextView) view).setText(String.valueOf(coupPrecedant));
                            listCarte.set(positionPrecedant,coupPrecedant);
                            listNombre.add(0,coupPrecedant);
                            v.setVisibility(View.VISIBLE);
                            break;
                        }
                    }

                }
            }
        } else {
            for (int i = 0; i < ligne2.getChildCount(); i++) {
                LinearLayout v = (LinearLayout)ligne2.getChildAt(i);
                for (int j = 0; j < v.getChildCount(); j++) {
                    if(i + 4 == positionPrecedant){
                        View view = v.getChildAt(j);
                        if(view instanceof TextView){
                            ((TextView) view).setText(String.valueOf(coupPrecedant));
                            listCarte.set(positionPrecedant,coupPrecedant);
                            listNombre.add(0,coupPrecedant);
                            v.setVisibility(View.VISIBLE);
                            break;
                        }
                    }
                }
            }
        }
        LinearLayout li = (LinearLayout)getCartePilePrecedant();
        for (int i = 0; i < li.getChildCount(); i++) {
            View w = li.getChildAt(i);
            if(w instanceof TextView){
                String noCarte = "";
                noCarte = noCartePilePrecedant;
                View vueTrouvee = trouverVueAvecMemeNom(vue, cartePilePrecedant);



                if (vueTrouvee != null) {
                    // Si la vue est trouver on peut travailler dedans.
                    LinearLayout vi = (LinearLayout) vueTrouvee;
                    for (int j = 0; j < vi.getChildCount(); j++) {
                        View vt = vi.getChildAt(j);
                        if(vt instanceof TextView){
                            ((TextView) vt).setText(noCarte);
                        }
                    }
                }

            }
        }
        uneCarte = false;

    }

    //Va retourner la vue rechercher par le nom.
    private View trouverVueAvecMemeNom(View racine, View reference) {
        if (reference == null) return null;

        int refId = reference.getId();
        if (refId == View.NO_ID) return null;

        String nomReference = racine.getResources().getResourceEntryName(refId);
        return chercherVueParNom(racine, nomReference);
    }

    //Va rechercher une vue par le nom pour identifier plus facilement la vue.
    private View chercherVueParNom(View racine, String nomRecherche) {
        if (racine == null || nomRecherche == null) return null;

        int id = racine.getId();
        if (id != View.NO_ID) {
            String nom = racine.getResources().getResourceEntryName(id);
            if (nomRecherche.equals(nom)) {
                return racine;
            }
        }

        if (racine instanceof ViewGroup) {
            ViewGroup groupe = (ViewGroup) racine;
            for (int i = 0; i < groupe.getChildCount(); i++) {
                View trouve = chercherVueParNom(groupe.getChildAt(i), nomRecherche);
                if (trouve != null) return trouve;
            }
        }

        return null;
    }


    //Va prendre des nombre de la liste et les insérer dans les carte(LinearLayout)
    public int insererNombre(){
        return listNombre.remove(0);
    }

    //Initialisation des carte au début de la partie.
    public void insererNombreDansCarte(LinearLayout ligne1, LinearLayout ligne2){


        for (int i = 0; i < ligne1.getChildCount(); i++) {
            LinearLayout v = (LinearLayout)ligne1.getChildAt(i);
            for (int j = 0; j < v.getChildCount(); j++) {
                View view = v.getChildAt(j);
                if(view instanceof TextView){
                    Integer premier = insererNombre();
                    String s = String.valueOf(premier);
                    ((TextView) view).setText(s);
                    listCarte.set(i,premier);
                }
            }
        }
        for (int i = 0; i < ligne2.getChildCount(); i++) {
            LinearLayout v = (LinearLayout)ligne2.getChildAt(i);
            for (int j = 0; j < v.getChildCount(); j++) {
                View view = v.getChildAt(j);
                if(view instanceof TextView){
                    Integer premier = insererNombre();
                    String s = String.valueOf(premier);
                    ((TextView) view).setText(s);
                    listCarte.set(i+4,premier);
                }
            }
        }
    }
    //Application des points et des bonus.
    public int appliquerPoint(long temps){
        scorePrecedant = score;
        int secondes = 0;

        secondes = ((int)temps/1000 - (int)tempsPrecedant/1000);
        //Si le bonus est appliqué
        if(secondes <= 10){
            score += (int)(1000*(Math.exp(-0.1 * secondes))/(1+0.2*Math.sin(Math.PI/10)));
        }
        //Si le bonus n'est pas appliqué
        else{
            score += 1000;
        }
        tempsPrecedant = temps;
        return score;
    }

    //Va évaluer si la partie est fini.
    public boolean partieTerminer(String carte1,String carte2,String carte3,String carte4){

        int c1 = Integer.parseInt(carte1);
        int c2 = Integer.parseInt(carte2);
        int c3 = Integer.parseInt(carte3);
        int c4 = Integer.parseInt(carte4);
        int nombre = 0;
        boolean ca1 = false;
        boolean ca2 = false;
        boolean ca3 = false;
        boolean ca4 = false;

        boolean fini = false;
        if(retournerNombreCarte() == 0){
            fini = true;
        } else if (retournerNombreCarte() != 0) {

            for (int i = 0; i < listCarte.size(); i++) {
                nombre = listCarte.get(i);
                if(listCarte.get(i) != -1){
                    if(nombre > c1){
                        ca1 = true;
                        break;
                    }
                }
            }
            for (int i = 0; i < listCarte.size(); i++) {
                nombre = listCarte.get(i);
                if(listCarte.get(i) != -1){
                    if (nombre > c2) {
                        ca2 = true;
                        break;
                    }
                }
            }
            for (int i = 0; i < listCarte.size(); i++) {
                nombre = listCarte.get(i);
                if(listCarte.get(i) != -1){
                    if (nombre < c3) {
                        ca3 = true;
                        break;
                    }
                }
            }
            for (int i = 0; i < listCarte.size(); i++) {
                nombre = listCarte.get(i);
                if(listCarte.get(i) != -1){
                    if ( nombre < c4) {
                        ca4 = true;
                        break;
                    }
                }
            }
            if(!ca1 && !ca2  && !ca3  && !ca4){
                fini = true;
            }
        }
        return fini;
    }
    //Va vérifier le nombre de carte présent dans le deck de dessous
    public int compter8Carte(){
        int count = 0;

        for (int i = 0; i < listCarte.size(); i++) {
            if (listCarte.get(i) == -1) {
                count++;
            }
        }

        return count;
    }
    //Voir si parmis les layouts, il nous manque 2 carte. Si oui, en replacer 2 autres en bas parmis les 8.
    public void verifier2Carte(LinearLayout ligne1, LinearLayout ligne2){


        List<Integer> listPosition = new ArrayList<>();
        List<Integer> listCarteTemp = new ArrayList<>();
        int count = 0;

        if(listNombre.size() > 1){
            for (int i = 0; i < ligne1.getChildCount(); i++) {
                LinearLayout v = (LinearLayout)ligne1.getChildAt(i);
                for (int j = 0; j < v.getChildCount(); j++) {
                    View view = v.getChildAt(j);
                    if(view instanceof TextView){
                        if(((TextView) view).getText().equals("")){
                            String vnom = view.getResources().getResourceEntryName(view.getId());
                            String num = vnom.substring(3);
                            listPosition.add(Integer.parseInt(num));
                            listCarteTemp.add(listNombre.remove(0));
                            uneCarte = true;
                            count++;
                        }
                    }
                }
            }
            for (int i = 0; i < ligne2.getChildCount(); i++) {
                LinearLayout v = (LinearLayout)ligne2.getChildAt(i);
                for (int j = 0; j < v.getChildCount(); j++) {
                    View view = v.getChildAt(j);
                    if(view instanceof TextView){
                        if(((TextView) view).getText().equals("")){
                            String vnom = view.getResources().getResourceEntryName(view.getId());
                            String num = vnom.substring(3);
                            listPosition.add(Integer.parseInt(num));
                            listCarteTemp.add(listNombre.remove(0));
                            uneCarte = true;
                            count++;
                        }
                    }
                }
            }
        }
        else {
            int count1 = 0;
            for (int i = 0; i < listCarte.size(); i++) {
                if (listCarte.get(i) == -1) {
                    count1++;
                }
            }

            if(!listNombre.isEmpty() && count1 < 2){
                for (int i = 0; i < ligne1.getChildCount(); i++) {
                    LinearLayout v = (LinearLayout)ligne1.getChildAt(i);
                    for (int j = 0; j < v.getChildCount(); j++) {
                        View view = v.getChildAt(j);
                        if(view instanceof TextView){
                            if(((TextView) view).getText().equals("") && !listNombre.isEmpty()){
                                String vnom = view.getResources().getResourceEntryName(view.getId());
                                String num = vnom.substring(3);
                                listCarte.set(Integer.parseInt(num) -1,listNombre.remove(0));
                                ((TextView) view).setText(String.valueOf(listCarte.get(Integer.parseInt(num)-1)));
                                v.setVisibility(View.VISIBLE);
                                break;
                            }
                            else{
                                break;
                            }
                        }
                    }
                }
                for (int i = 0; i < ligne2.getChildCount(); i++) {
                    LinearLayout v = (LinearLayout)ligne2.getChildAt(i);
                    for (int j = 0; j < v.getChildCount(); j++) {
                        View view = v.getChildAt(j);
                        if(view instanceof TextView){
                            if(((TextView) view).getText().equals("") && !listNombre.isEmpty()){
                                String vnom = view.getResources().getResourceEntryName(view.getId());
                                String num = vnom.substring(3);
                                listCarte.set(Integer.parseInt(num) -1,listNombre.remove(0));
                                ((TextView) view).setText(String.valueOf(listCarte.get(Integer.parseInt(num)-1)));
                                v.setVisibility(View.VISIBLE);
                                break;
                            }
                            else{
                                break;
                            }
                        }
                    }
                }
            }
        }


        if(count == 2){
            List<Integer> temp = new ArrayList<>();
            temp.addAll(listPosition);
            for (int i = 0; i < listCarte.size(); i++) {
                if(listCarte.get(i) == -1){
                    listCarte.set(listPosition.remove(0) -1 ,listCarteTemp.remove(0));
                }

            }
            for (int i = 0; i < ligne1.getChildCount(); i++) {
                LinearLayout v = (LinearLayout)ligne1.getChildAt(i);
                for (int j = 0; j < v.getChildCount(); j++) {
                    View view = v.getChildAt(j);
                    if(view instanceof TextView){
                        if(((TextView) view).getText().equals("")){
                            ((TextView) view).setText(String.valueOf(listCarte.get(temp.remove(0) -1)));
                            count++;
                        }
                    }
                }
                v.setVisibility(View.VISIBLE);
            }
            for (int i = 0; i < ligne2.getChildCount(); i++) {
                LinearLayout v = (LinearLayout)ligne2.getChildAt(i);
                for (int j = 0; j < v.getChildCount(); j++) {
                    View view = v.getChildAt(j);
                    if(view instanceof TextView){
                        if(((TextView) view).getText().equals("")){
                            ((TextView) view).setText(String.valueOf(listCarte.get(temp.remove(0) -1)));
                            count++;
                        }
                    }
                }
                v.setVisibility(View.VISIBLE);
            }
            uneCarte = false;

        }
    }
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScorePrecedant() {
        return scorePrecedant;
    }

    public boolean isUneCarte() {
        return uneCarte;
    }

    public void setCartePilePrecedant(View cartePilePrecedant) {
        this.cartePilePrecedant = cartePilePrecedant;
    }

    public View getCartePilePrecedant() {
        return cartePilePrecedant;
    }

    public void setNoCartePilePrecedant(String noCartePilePrecedant) {
        this.noCartePilePrecedant = noCartePilePrecedant;
    }
}
