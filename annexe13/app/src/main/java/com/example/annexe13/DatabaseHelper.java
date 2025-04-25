package com.example.annexe13;

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
    private DatabaseHelper(@Nullable Context context) {
        super(context, "bd", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS evaluation(_id INTEGER PRIMARY KEY AUTOINCREMENT,nom TEXT,microbrasserie TEXT,evaluation REAL)");


    }
    public void ajouterEvaluation(Evaluation ev){
        ContentValues cv = new ContentValues();
        cv.put("nom", ev.getNom());
        cv.put("microbrasserie", ev.getMicrobrasserie());
        cv.put("evaluation", ev.getEvaluation());
        database.insert("evaluation",null,cv);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS evaluation");
        onCreate(db);
    }

    public void ouvrirConnexion(){
        database = this.getWritableDatabase();
    }

    public void fermerConnexion(){
        database.close();
    }

    public Vector<String> retourerEvaluation() throws Exception {
        Vector<String> evaluation = new Vector<>();

        Cursor cursor = database.rawQuery("SELECT nom FROM evaluation ORDER BY evaluation DESC LIMIT 3", null);
        //Remplir le vecteur avec les inventions

        //Tant qu'il y a des résultats
        while (cursor.moveToNext()) {
            //Je vais chercher la valeur du champ invention
            String temp = cursor.getString(0);
            evaluation.add(temp);
        }
        if(evaluation.size() < 3){
            throw new Exception("Moins de 3 enregistrement");
        }

        //Je ferme le curseur
        cursor.close();
        //Je retourne le Vector rempli
        return evaluation;

    }


}
