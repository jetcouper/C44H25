package com.example.projetradar;

public class EvaluationRadarScolaire extends EvaluationRadar
{
  
  private String plaque;
  private int vitesse;
  
  public static final int MAXIMUM = 30;
  
  public EvaluationRadarScolaire(String nomAgent, String plaque, int vitesse) throws RadarException {
    if(vitesse > MAXIMUM + 30){
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
