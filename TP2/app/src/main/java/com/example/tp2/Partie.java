package com.example.tp2;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Partie {

    private int score;
    private List<Integer> listCarte;
    private List<Integer> listNombre;
    private int temps;


    public Partie() {
        listCarte = Arrays.asList(-1, -1, -1, -1, -1, -1, -1, -1);
        listNombre = new ArrayList<>();
        score = 0;
        temps = 0;
    }

    public void genererListe(){
        for (int i = 1; i < 98; i++) {
            listNombre.add(i);
        }
        Collections.shuffle(listNombre);
    }

    public void retirerCarte(int carte){
        for (int i = 0; i < listCarte.size(); i++) {
            if (listCarte.get(i) == carte) {
                listCarte.set(i, -1);
                break; // On arrête après la première occurrence
            }
        }
    }




    public int retournerNombreCarte(){

        int count = 0;
        for (int i = 0; i < listCarte.size(); i++) {
            if(listCarte.get(i) != -1){
                count++;
            }
        }

        return listNombre.size()+ count;
    }

    public int insererNombre(){
        return listNombre.remove(0);
    }
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
    public int appliquerPoint(int point, int temps){


        return 0;
    }

    public boolean partieTerminer(){

        boolean fini = false;
        if(retournerNombreCarte() == 0){
            fini = true;
        }
        return fini;
    }

    public int compter8Carte(){
        int count = 0;

        for (int i = 0; i < listCarte.size(); i++) {
            if (listCarte.get(i) == -1) {
                count++;
            }
        }

        return count;
    }

    public void verifier2Carte(LinearLayout ligne1, LinearLayout ligne2){


        List<Integer> listPosition = new ArrayList<>();
        List<Integer> listCarteTemp = new ArrayList<>();
        int count = 0;


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
                        listCarteTemp.add(listCarte.remove(0));
                        count++;
                    }
                }
            }
        }

        if(count == 2){
            for (int i = 0; i < listCarte.size(); i++) {
                if(listCarte.get(i) == -1 && listPosition.contains(i)){
                    String nom = "";
                }

            }

        }
    }

}
