package com.ericlabonte.appmariage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


import java.util.ArrayList;
import java.util.Vector;

public class Helper extends SQLiteOpenHelper {

    private static Helper instance;

    private SQLiteDatabase database;

    public static Helper getInstance(Context context) {

        if(instance == null){
            instance = new Helper(context);
        }
        return instance;

    }


    private Helper(@Nullable Context context) {
        super(context, "bd", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table invite( _id INTEGER PRIMARY KEY AUTOINCREMENT, nom TEXT, noTable TEXT);");
        //ajouter les enregistrements
        ajouterInvite(new Invite ( "Thomas", "2"), db);
        ajouterInvite(new Invite ( "Alexiane", "3"), db);
        ajouterInvite(new Invite ( "Isabella", "1"), db);
        ajouterInvite(new Invite ( "Charlotte", "2"), db);
        ajouterInvite(new Invite ( "Paul-Edouard", "2"), db);
        ajouterInvite(new Invite ( "Marie-Joelle", "4"), db);
        ajouterInvite(new Invite ( "Francois", "2"), db);
        ajouterInvite(new Invite ( "Eddy", "1"), db);
        ajouterInvite(new Invite ( "Joel", "2"), db);
        ajouterInvite(new Invite ( "Karl", "3"), db);
        ajouterInvite(new Invite ( "Antoine", "5"), db);
        ajouterInvite(new Invite ( "Samuel", "2"), db);
        ajouterInvite(new Invite ( "Jacques Michel", "4"), db);
        ajouterInvite(new Invite ( "Loic", "2"), db);
        ajouterInvite(new Invite ( "Edgar", "2"), db);
        ajouterInvite(new Invite ( "Peterly", "1"), db);
        ajouterInvite(new Invite ( "Yuta", "3"), db);
        ajouterInvite(new Invite ( "Philippe", "2"), db);
        ajouterInvite(new Invite ( "Nathan", "5"), db);
        ajouterInvite(new Invite ( "Ugo", "2"), db);
        ajouterInvite(new Invite ( "Jean-Marc", "5"), db);
    }

    public void ajouterInvite ( Invite i, SQLiteDatabase database )
    {
        // Le ContentValues comprend les données à ajouter à la table
        ContentValues cv = new ContentValues();
        cv.put("nom", i.getNom());  // la clé doit être exactement le nom du champ de la table
        cv.put("noTable", i.getNoTable());
        database.insert("invite",null, cv);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists invite ");
        onCreate(db);
    }

    public void ouvrirConnexion(){
        database = this.getWritableDatabase();
    }

    public void fermerConnexion(){
        database.close();
    }


    public String retourneNumero(String nom) throws Exception {
        String[] tab = {nom};
        String noTable = "";
        Cursor c = database.rawQuery("SELECT noTable FROM invite WHERE nom = ?", tab);


        noTable = c.getString(0);

        if(noTable.equals("")){
            throw new Exception("Il n'y a aucune réservé à ce nom");
        }

        c.close();

        return noTable;

    }

    public Vector<String> retournerToutLesNom(String noTable){

        Vector<String> noms = new Vector<>();
        String[] tab = {noTable};

        Cursor c = database.rawQuery("SELECT nom FROM invite WHERE noTable = ?",tab);

        while (c.moveToNext()){
            String temp = c.getString(0);
            noms.add(temp);
        }
        c.close();
        return noms;

    }



}
