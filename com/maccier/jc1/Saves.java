package com.maccier.jc1;

public class Saves {
	private int tour = 0;
	private String idBen = "";
	
	private String idEnnemi = "";
	private Vector2D ancienVecteurDemiTour = new Vector2D(0, 0);
	private int etat = 0; // 1:rechercheAdversaire   2:poursuite
	private boolean dejaInit = true;
	
	public void setDejaInit(boolean dejaInit){
		this.dejaInit = dejaInit;
	}
	
	public boolean isDejaInit(){
		return dejaInit;
	}
	
	public String getIdBen(){
		return idBen;
	}
	
	public void setIdBen(String idBen){
		this.idBen = idBen;
	}

	public String getIdEnnemi(){
		return idEnnemi;
	}
	
	public void setIdEnnemi(String idEnnemi){
		this.idEnnemi = idEnnemi;
	}

	public void ajoutAuCompteurDeTour(){
		tour += 1;
	}

	public int getNumeroDuTour(){
		return tour;
	}
	
	public int getEtat(){
		return etat;
	}
	
	public void setEtat(int etat){
		this.etat = etat;
	}
}
