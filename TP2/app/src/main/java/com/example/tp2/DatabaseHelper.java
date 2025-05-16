package com.example.tp2;

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
        db.execSQL("CREATE TABLE IF NOT EXISTS pointage(_id INTEGER PRIMARY KEY AUTOINCREMENT,point INTEGER, date TEXT)");


    }
    public void ajouterPointage(Pointage ev){

        ContentValues cv = new ContentValues();
        cv.put("point", ev.getPoint());
        cv.put("date", String.valueOf(ev.getDate().getDayOfMonth() + "/" + ev.getDate().getMonth() + "/" + ev.getDate().getYear()));
        database.insert("pointage",null,cv);
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



    public int retournerMeilleurPointage(){
        int meilleur = 0;

        Cursor c = database.rawQuery("SELECT MAX(point) FROM pointage LIMIT 1",null);

        if(c.moveToFirst()){
            meilleur = c.getInt(0);
        }

        c.close();
        return meilleur;
    }

    public Vector<String> retourerPointages() throws Exception {
        Vector<String> pointage = new Vector<>();

        Cursor cursor = database.rawQuery("SELECT point + ' ' + date FROM pointage ORDER BY point DESC", null);
        //Remplir le vecteur avec les inventions

        //Tant qu'il y a des résultats
        while (cursor.moveToNext()) {
            //Je vais chercher la valeur du champ point
            String temp = cursor.getString(0);
            pointage.add(temp);
        }
        if(pointage.size() < 3){
            throw new Exception("Moins de 1 enregistrement");
        }

        //Je ferme le curseur
        cursor.close();
        //Je retourne le Vector rempli
        return pointage;

    }





}
