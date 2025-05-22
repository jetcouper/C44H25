package com.example.projetradar;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerEvaluation;
    TextView vitesse, plaque,agent;
    Vector<String> evaluation;
    Button btnOk;
    String evaluationChoisi;

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
        vitesse = findViewById(R.id.txtVitesse);
        plaque = findViewById(R.id.txtPlaque);
        agent = findViewById(R.id.txtAgent);
        spinnerEvaluation = findViewById(R.id.spinner);
        btnOk = findViewById(R.id.btnOk);
        evaluation = new Vector<>();

        evaluation.add("route");
        evaluation.add("autoroute");
        evaluation.add("scolaire");

        ArrayAdapter<String> adapterEvaluation = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,evaluation);
        spinnerEvaluation.setAdapter(adapterEvaluation);

        Ecouteur ec = new Ecouteur();

        btnOk.setOnClickListener(ec);
        spinnerEvaluation.setOnItemSelectedListener(ec);


    }

    private class Ecouteur implements View.OnClickListener, AdapterView.OnItemSelectedListener {
        @Override
        public void onClick(View v) {

            if(evaluationChoisi.equals("route")){
                try {
                    EvaluationRadarRoute ev = new EvaluationRadarRoute(agent.getText().toString(),plaque.getText().toString(),Integer.parseInt(vitesse.getText().toString()));
                } catch (RadarException e) {
                    new AlertDialog.Builder(MainActivity.this).setMessage(e.getMessage()).show();
                    //throw new RuntimeException(e);
                }


            } else if (evaluationChoisi.equals("autoroute")) {
                try {
                    EvaluationRadarAutoroute ev = new EvaluationRadarAutoroute(agent.getText().toString(),plaque.getText().toString(),Integer.parseInt(vitesse.getText().toString()));
                } catch (RadarException e) {
                    new AlertDialog.Builder(MainActivity.this).setMessage(e.getMessage()).show();
                    //throw new RuntimeException(e);
                }


            } else if (evaluationChoisi.equals("scolaire")) {
                try {
                    EvaluationRadarScolaire ev = new EvaluationRadarScolaire(agent.getText().toString(),plaque.getText().toString(),Integer.parseInt(vitesse.getText().toString()));
                } catch (RadarException e) {
                    new AlertDialog.Builder(MainActivity.this).setMessage(e.getMessage()).show();
                    //throw new RuntimeException(e);
                }


            }


        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            evaluationChoisi = "";
            evaluationChoisi = (String)spinnerEvaluation.getItemAtPosition(position);

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}