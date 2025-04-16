package com.example.applicationannexe12;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper instance;

    public static DatabaseHelper getInstance(Context contexte) {
        if(instance == null){
            instance = new DatabaseHelper(contexte);
        }
        return instance;
    }

    private DatabaseHelper(@Nullable Context contexte) {
        super(contexte, "bd", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS 'Inventeur'('_id INTEGER' PRIMARY KEY AUTOINCREMENT,'nom' TEXT,'origine' TEXT,'invention' TEXT,'annee' INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
