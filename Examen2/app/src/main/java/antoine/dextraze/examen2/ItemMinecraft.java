package antoine.dextraze.examen2;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class ItemMinecraft {

    private int couleur,largeurTrait;

    private String type;
    private Paint ligneDessin;

    public ItemMinecraft(int couleur, int largeurTrait, String type) {
        this.couleur = couleur;
        this.largeurTrait = largeurTrait;
        this.type = type;



        ligneDessin = new Paint(Paint.ANTI_ALIAS_FLAG);
        ligneDessin.setColor(this.couleur);
        ligneDessin.setStrokeWidth(this.largeurTrait);
        ligneDessin.setAntiAlias(true);
        ligneDessin.setStyle(Paint.Style.STROKE);
    }

    public void dessiner(Canvas canvas){

        if(type == "Bambou"){
            canvas.drawLine(500,0,500,400,ligneDessin);

        }

    }


}
