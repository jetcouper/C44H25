package com.ericlabonte.exdrapeaux;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class SingletonDrapeaux extends SQLiteOpenHelper {





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


}
