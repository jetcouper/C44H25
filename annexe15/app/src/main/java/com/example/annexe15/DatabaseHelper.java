package com.example.annexe15;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Vector;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper instance;

    private SQLiteDatabase database;

    public static DatabaseHelper getInstance(Context context) {
        if(instance == null){
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    public DatabaseHelper(@Nullable Context context) {
        super(context, "bd", null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS equipe(_id INTEGER PRIMARY KEY AUTOINCREMENT,nom TEXT,division TEXT,arena TEXT, capacite INTEGER)");
        ajouterEquipe(new Equipe("Tigres de Victoriavillle","Est","Colisée Desjardins",1900),db);
        ajouterEquipe(new Equipe("Cataractes de Shawinigan","Est","Centre Gervais Auto",4000),db);
        ajouterEquipe(new Equipe("Olympiques de Gatineau","Ouest","Centre Slush Puppie",4200),db);
        ajouterEquipe(new Equipe("Foreurs de Val d’Or","Ouest","Centre Agnico Eagle",2600),db);
        ajouterEquipe(new Equipe("Armada de Blainville","Ouest","Centre Rousseau",3000),db);

    }

    public void ajouterEquipe(Equipe ev,SQLiteDatabase db){ //db en paramètre car on apelle le onCreate avant ouvrir connection et tout le reste
        ContentValues cv = new ContentValues();
        cv.put("nom", ev.getNom());
        cv.put("division", ev.getDivision());
        cv.put("arena", ev.getArena());
        cv.put("capacite", ev.getCapacite());
        db.insert("equipe",null,cv);
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS equipe");
        onCreate(db);
    }

    public void ouvrirConnexion(){
        database = this.getWritableDatabase();
    }

    public void fermerConnexion(){
        database.close();
    }


    public int trouverNombre(String division){
        int count = 0;
        String[] tab = {division};

        Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM equipe WHERE division = ?",tab);

        if (cursor.moveToFirst()) {
            count = cursor.getInt(0); // Récupère la première colonne de la première ligne
        }
        cursor.close();
        return count;
    }

    public String trouverMoyenne(){

        int moyenne = 0;
        Cursor cursor = database.rawQuery("SELECT AVG(capacite) FROM equipe",null);

        if (cursor.moveToFirst()) {
            moyenne = cursor.getInt(0); // Récupère la première colonne de la première ligne
        }
        return moyenne + " sièges";
    }

    public String trouverEquipe(String arena){

        String rep = "";
        String[] tab = {arena};
        Cursor cursor = database.rawQuery("SELECT nom FROM equipe WHERE arena = ?", tab);
        if (cursor.moveToFirst()) {
            rep = cursor.getString(0); // Récupère la première colonne de la première ligne
        }


        return rep;
    }



    public Vector<String> retourerArena() throws Exception {
        Vector<String> equipe = new Vector<>();

        Cursor cursor = database.rawQuery("SELECT arena FROM equipe", null);
        //Remplir le vecteur avec les inventions

        //Tant qu'il y a des résultats
        while (cursor.moveToNext()) {
            //Je vais chercher la valeur du champ invention
            String temp = cursor.getString(0);
            equipe.add(temp);
        }
        if(equipe.size() < 3){
            throw new Exception("Moins de 3 enregistrement");
        }

        //Je ferme le curseur
        cursor.close();
        //Je retourne le Vector rempli
        return equipe;

    }

}
