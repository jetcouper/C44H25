package com.example.projetradar;

public class EvaluationRadar
{
  
  private String nomAgent;
  
  public EvaluationRadar() 
  {
      
  }
  public EvaluationRadar(String nomAgent)
  {
    this.nomAgent = nomAgent;
  }

  public void setNomAgent(String nomAgent)
  {
    this.nomAgent = nomAgent;
  }

  public String getNomAgent()
  {
    return nomAgent;
  }
}
