package com.example.tp2;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Partie {

    int score;
    List<Integer> listCarte;
    List<Integer> listNombre;


    public Partie() {
        listCarte = new ArrayList<>();
        listNombre = new ArrayList<>();
    }

    public void genererListe(){
        for (int i = 1; i < 98; i++) {
            listNombre.add(i);
        }
        Collections.shuffle(listNombre);
    }

    public int retournerNombreCarte(){

        return listNombre.size();
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
                }
            }
        }
    }
    public int appliquerPoint(int point, int temps){

        return 0;
    }

    public boolean partieTerminer(){

        return false;
    }



    public void verifier2Carte(LinearLayout ligne1, LinearLayout ligne2){

        int count = 0;
        TextView v1 = null;
        TextView v2 = null;
        for (int i = 0; i < ligne1.getChildCount(); i++) {
            LinearLayout v = (LinearLayout)ligne1.getChildAt(i);
            for (int j = 0; j < v.getChildCount(); j++) {
                View view = v.getChildAt(j);
                if(view instanceof TextView){

                    if(((TextView) view).getText().equals("")){
                        if(v1 == null){

                        }
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


//                    Integer premier = listNombre.remove(0);
//                    String s = String.valueOf(premier);
//                    ((TextView) view).setText(s);
                }
            }
        }

        if(count == 2){



        }
    }

}
