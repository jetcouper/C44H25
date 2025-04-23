package com.example.applicationannexe12;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    ListView liste;
    DatabaseHelper instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        liste = findViewById(R.id.listInventaire);

        instance = DatabaseHelper.getInstance(getApplicationContext()); // Pas this car le singleton est vivant pour toute l'application

        instance.ouvrirConnexion(); //Très important
        Vector<String> v = instance.retourerInvention();
        //Remplir le ListView

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,v);

        liste.setAdapter(adapter);

        Ecouteur ec = new Ecouteur();

        liste.setOnItemClickListener(ec);


    }
    private class Ecouteur implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            


        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        instance.fermerConnexion();

    }


}








