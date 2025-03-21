package com.antoine.tp1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.provider.Settings;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class Enregistrer {


    public Enregistrer() {
    }


    public void enregistrerImage(Context context, LinearLayout vue){

        Bitmap bitmap = Bitmap.createBitmap(vue.getWidth(),vue.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vue.draw(canvas);

        File chemin = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),"monPain");

        if(!chemin.exists()){
            boolean estCréer = chemin.mkdirs();
            if (!estCréer){
                Toast.makeText(context, "Erreur : Impossible de créer le dossier", Toast.LENGTH_LONG).show();
                return;
            }
        }

        File imageFichier = new File(chemin, Calendar.getInstance().getTime().toString()+".png");


        try{
            FileOutputStream sortie = new FileOutputStream(imageFichier);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, sortie);
            sortie.flush();
            Toast.makeText(context, "Image sauvegarder dans " + imageFichier.getAbsolutePath(), Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Erreur lors de l'enregistrement de l'image", Toast.LENGTH_LONG).show();
        }
    }



}
