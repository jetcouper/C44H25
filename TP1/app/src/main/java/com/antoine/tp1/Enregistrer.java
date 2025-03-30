package com.antoine.tp1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class Enregistrer {


    public Enregistrer() {
    }


    public void enregistrerImage(Context context, LinearLayout vue){

        //Convertie ma vue en Bitmap
        Bitmap bitmap = Bitmap.createBitmap(vue.getWidth(),vue.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vue.draw(canvas);

        //Va créer le chemin(Dir) vers mon image sauvegarder
        File chemin = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),"monPaint");

        //Pour vérifier si le chemin vers le fichier existe.
        if(!chemin.exists()){
            boolean estCréer = chemin.mkdirs();
            if (!estCréer){
                Toast.makeText(context, "Erreur : Impossible de créer le dossier", Toast.LENGTH_LONG).show();
                return;
            }
        }

        //Va donner un nom au fichier avec son extension(PNG)
        File imageFichier = new File(chemin, Calendar.getInstance().getTime().toString()+".png");

        //Va essayer d'écrire l'image Bitmap dans le fichier .PNG, s'il ne reussi pas, il va renvoyer un message d'érreur.
        try{
            FileOutputStream sortie = new FileOutputStream(imageFichier);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, sortie);
            sortie.flush();//Ça ou l'autre
            //sortie.close();
            Toast.makeText(context, "Image sauvegarder dans " + imageFichier.getAbsolutePath(), Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            e.printStackTrace();
            //Message d'érreur en cas d'échec.
            Toast.makeText(context, "Erreur lors de l'enregistrement de l'image", Toast.LENGTH_LONG).show();
        }
    }



}
