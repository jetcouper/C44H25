package com.example.projetradar;

public class EvaluationRadarAutoroute extends EvaluationRadar
{
  private String plaque;
  private int vitesse;
  
  public static final int MAXIMUM = 100;
  public static final int MINIMUM = 60;
  
  public EvaluationRadarAutoroute(String nomAgent, String plaque, int vitesse) throws RadarException {
    if(vitesse < MINIMUM){
      throw new RadarException("Vous n'aller pas assez vite.");
    } else if (vitesse > MAXIMUM + 10) {
      throw  new RadarException("Vous aller trop vite");
    }

    this.plaque = plaque;
    this.vitesse = vitesse;
  }


  public String getPlaque()
  {
    return plaque;
  }

  public int getVitesse()
  {
    return vitesse;
  }


}
