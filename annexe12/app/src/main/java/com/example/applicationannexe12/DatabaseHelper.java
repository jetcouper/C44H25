package com.example.applicationannexe12;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.List;
import java.util.Vector;

public class DatabaseHelper extends SQLiteOpenHelper { //Singleton

    private static DatabaseHelper instance; //Pour le singleton

    //Autre variables
    private SQLiteDatabase database;

    public static DatabaseHelper getInstance(Context contexte) {
        if(instance == null){
            instance = new DatabaseHelper(contexte);
        }
        return instance;
    }

    private DatabaseHelper(@Nullable Context contexte) {
        super(contexte, "bd", null, 1);
    }

    //Appeler une seule fois lors de l'installation de l'app.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS inventeur(_id INTEGER PRIMARY KEY AUTOINCREMENT,nom TEXT,origine TEXT,invention TEXT,annee INTEGER)");
        ajouterInventeur(new Inventeur("Laszlo Biro","Hongrie", "Stylo à bille",1938),db);
        ajouterInventeur(new Inventeur("Benjamin Franklin","Etats-Unis", "Paratonnerre",1752),db);
        ajouterInventeur(new Inventeur("Mary Anderson","Etats-Unis", "Essuie-glace",1903),db);
        ajouterInventeur(new Inventeur("Grace Hopper","Etats-Unis", "Compilateur",1952),db);
        ajouterInventeur(new Inventeur("Benoit Rouquayrot","France", "Scaphandre",1864),db);
    }
    public void ajouterInventeur(Inventeur in , SQLiteDatabase db){
        ContentValues cv = new ContentValues();
        cv.put("nom", in.getNom());
        cv.put("origine", in.getOrigine());
        cv.put("invention", in.getInvention());
        cv.put("annee", in.getAnnee());
        db.insert("inventeur",null,cv);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Si jamais on demande une mise à jour, on va seulement detruire la table et la recréer
        db.execSQL("DROP TABLE IF EXISTS inventeur");
        onCreate(db);
    }
    public void ouvrirConnexion(){
        database = this.getWritableDatabase();
    }

    public void fermerConnexion(){
        database.close();
    }

    public Vector<String> retourerInvention()
    {
        Vector<String> inventions = new Vector<>();

        Cursor cursor = database.rawQuery("SELECT invention FROM inventeur", null);
        //Remplir le vecteur avec les inventions

        //Tant qu'il y a des résultats
        while (cursor.moveToNext()) {
            //Je vais chercher la valeur du champ invention
            String temp = cursor.getString(0);
            inventions.add(temp);
        }

        //Je ferme le curseur
        cursor.close();
        //Je retourne le Vector rempli
        return inventions;

    }

    public boolean aBonneReponse(String nomInventeur, String invention){


        String[] tab = {nomInventeur,invention};
        Cursor c = database.rawQuery("SELECT * FROM inventeur AS i WHERE i.nom = ? AND i.invention = ?", tab);

        if(c.moveToFirst()){
            c.close();
            return true;
        }
        else
            c.close();
            return false;

//        c.close();
//        return c.moveToFirst();
    }






}
