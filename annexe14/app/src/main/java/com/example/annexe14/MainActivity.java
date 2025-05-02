package com.example.annexe14;

import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.chip.Chip;

public class MainActivity extends AppCompatActivity {

    LinearLayout main;


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

        main = findViewById(R.id.main);

        Ecouteur ec = new Ecouteur();

        for(int i = 0; i < main.getChildCount(); i++){
            View petit = main.getChildAt(i);
            if(petit instanceof LinearLayout){
                (main.getChildAt(i)).setOnDragListener(ec);
            } else if (petit instanceof ImageView) {
                (main.getChildAt(i)).setOnTouchListener(ec);
            }
        }
    }

    private class Ecouteur implements View.OnDragListener, View.OnTouchListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
//            switch (event.getAction()) {
//                case DragEvent.ACTION_DRAG_STARTED:
//                    return true;
//
//                case DragEvent.ACTION_DRAG_ENTERED:
//                    v.setBackgroundColor(Color.LTGRAY);
//                    return true;
//
//                case DragEvent.ACTION_DRAG_EXITED:
//                    v.setBackgroundColor(Color.DKGRAY);
//                    return true;
//
//                case DragEvent.ACTION_DROP:
//                    // Récupérer la vue déplacée (ici l'ImageView)
//                    View draggedView = (View) event.getLocalState();
//                    ViewGroup oldParent = (ViewGroup) draggedView.getParent();
//                    oldParent.removeView(draggedView);
//
//                    ViewGroup newParent = (ViewGroup) v;
//                    newParent.addView(draggedView);
//                    draggedView.setVisibility(View.VISIBLE);
//
//                    return true;
//
//                case DragEvent.ACTION_DRAG_ENDED:
//                    v.setBackgroundColor(Color.TRANSPARENT);
//                    return true;
//
//                default:
//                    break;
//            }
            return false;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {



            return false;
        }
    }
}