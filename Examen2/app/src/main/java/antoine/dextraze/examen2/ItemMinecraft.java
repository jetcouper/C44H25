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

        if(type.equals("Bambou")){
            canvas.drawLine(500,100,500,700,ligneDessin);

        }
        else if(type.equals("Echelle")){
            canvas.drawLine(400,50,400,700,ligneDessin);

            for(int i =0; i < 13; i++){
                canvas.drawLine(400,70+(i*50),600,70+(i*50),ligneDessin);
            }

            canvas.drawLine(600,50,600,700,ligneDessin);

        } else if (type.equals("Champignon")) {

            ligneDessin.setStyle(Paint.Style.FILL);
            canvas.drawArc(350,30,750,400,180,180,true,ligneDessin);
            canvas.drawRect(350,200,750,400,ligneDessin);
            canvas.drawRect(520,200,580,600,ligneDessin);

        }


    }


}
