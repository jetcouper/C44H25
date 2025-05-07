package com.example.tp2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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
        db.execSQL("CREATE TABLE IF NOT EXISTS pointage(_id INTEGER PRIMARY KEY AUTOINCREMENT,point INTEGER)");


    }
    public void ajouterPointage(Pointage ev){
        ContentValues cv = new ContentValues();
        cv.put("point", ev.getPoint());
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

        Cursor c = database.rawQuery("SELECT MAX(point) FROM pointage",null);

        return meilleur;
    }



}
