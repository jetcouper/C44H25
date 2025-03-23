package com.antoine.tp1;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.LinearLayout;

public class Pipette {

    private Bitmap bitmapImage;
    private LinearLayout layout;

    public Pipette(LinearLayout layout) {
        this.layout = layout;
    }

    public Bitmap getBitmapImage() {

        this.buildDrawingCache();
        bitmapImage = Bitmap.createBitmap(this.getDrawingCache());
        this.destroyDrawingCache();

        return bitmapImage;
    }
    private void buildDrawingCache() {

        layout.buildDrawingCache();

    }
    private Bitmap getDrawingCache() {

        Bitmap bitmapImage1 = layout.getDrawingCache();

        return bitmapImage1;
    }

    private void destroyDrawingCache() {
        layout.destroyDrawingCache();
    }






}
