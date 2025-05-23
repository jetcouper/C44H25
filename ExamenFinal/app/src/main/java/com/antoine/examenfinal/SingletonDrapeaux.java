package com.antoine.examenfinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.Vector;


public class SingletonDrapeaux extends SQLiteOpenHelper {

    private static SingletonDrapeaux instance;

    private SQLiteDatabase database;

    public static SingletonDrapeaux getInstance(Context context){
        if(instance == null){
            instance = new SingletonDrapeaux(context);
        }
        return instance;
    }


    public SingletonDrapeaux(@Nullable Context context) {
        super(context, "exfinal", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table drapeau ( _id INTEGER PRIMARY KEY AUTOINCREMENT, couleurG TEXT, couleurC TEXT, couleurD TEXT, pays TEXT);");
        insererDrapeau(db, new Drapeau("bleu", "blanc", "rouge", "France"));
        insererDrapeau(db, new Drapeau("rouge", "blanc", "rouge", "Pérou"));
        insererDrapeau(db, new Drapeau("bleu", "jaune", "rouge", "Roumanie"));
        insererDrapeau(db, new Drapeau("noir", "jaune", "rouge", "Belgique"));
    }
    public void insererDrapeau (SQLiteDatabase sqLiteDatabase, Drapeau d )
    {
        ContentValues cv = new ContentValues();
        cv.put("couleurG", d.getCouleurG());
        cv.put("couleurC", d.getCouleurC());
        cv.put("couleurD", d.getCouleurD());
        cv.put("pays", d.getPays());
        sqLiteDatabase.insert("drapeau", null, cv );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS drapeau");
        onCreate(db);
    }
    public void ouvrirConnexion(){
        database = this.getWritableDatabase();
    }
    public void fermerConnexion(){
        database.close();
    }

    public String retournerHasard(){
        Vector<String> pays = new Vector<>();
        String unPays = "";

        Cursor c = database.rawQuery("SELECT pays FROM drapeau", null);

        while (c.moveToNext()){
            String temp = c.getString(0);
            pays.add(temp);

        }
        Collections.shuffle(pays);
        unPays = pays.get(0);
        c.close();
        return unPays;
    }

    public boolean verifierPays(String couleurG, String couleurC, String couleurD, String pays){

        boolean reponse = false;
        String[] tab = {couleurG,couleurC,couleurD};
        String temp = "";

        Cursor c = database.rawQuery("SELECT pays FROM drapeau WHERE couleurG = ? AND couleurC = ? AND couleurD = ?", tab);

        while (c.moveToNext()){
            temp = c.getString(0);
        }
        if(temp.equals(pays)){
            reponse = true;
        }

        c.close();

        return reponse;
    }




}
