package com.example.tp2;

import android.view.View;
import android.widget.Chronometer;
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
    private long tempsPrecedant;
    private int bonus;


    public Partie() {
        listCarte = Arrays.asList(-1, -1, -1, -1, -1, -1, -1, -1);
        listNombre = new ArrayList<>();
        score = 0;
        tempsPrecedant = 0;
        bonus = 100;
    }

    public void genererListe(){
        for (int i = 1; i < 10; i++) {
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
    public int appliquerPoint(long temps){
        int secondes = 0;

        secondes = ((int)temps/1000 - (int)tempsPrecedant/1000);

        if(secondes <= 10){
            score += bonus + 100;
        }
        else{
            score += 100;
        }
        tempsPrecedant = temps;
        return score;
    }

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
                                ((TextView) view).setText(String.valueOf(listCarte.get(Integer.parseInt(num))));
                                listNombre.remove(0);
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
                                ((TextView) view).setText(String.valueOf(listCarte.get(listCarteTemp.get(0))));
                                listNombre.remove(0);
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


        }
    }
    public int getScore() {
        return score;
    }

}
