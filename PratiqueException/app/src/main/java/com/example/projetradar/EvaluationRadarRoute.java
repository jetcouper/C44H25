package com.example.projetradar;

public class EvaluationRadarRoute extends EvaluationRadar
{
  private String plaque;
  private int vitesse;
  
  public static final int MAXIMUM = 90;
  
  public EvaluationRadarRoute(String nomAgent, String plaque, int vitesse) throws RadarException {
    if(vitesse > MAXIMUM + 10){
      throw new RadarException("Vous allez trop vite.");
    }
    this.plaque = plaque;
    this.vitesse = vitesse;
  }

  public String getPlaque() {
    return plaque;
  }

  public int getVitesse() {
    return vitesse;
  }
}
