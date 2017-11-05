package com.maccier.jc1;

import java.util.ArrayList;

import com.maccier.jc1.game.Player;
import com.maccier.jc1.network.inter.NextTurn;
import com.maccier.jc1.network.inter.ReceiveId;
import com.maccier.jc1.network.packet.SendPShieldPacket;
import com.maccier.jc1.network.packet.SendPShootPacket;
import com.maccier.jc1.network.packet.SendPenginePacket;
import com.maccier.jc1.network.packet.SendPpuissancePacket;
import com.maccier.jc1.network.packet.SendRotatePacket;
import com.maccier.jc1.network.packet.SendShootPacket;
import com.maccier.jc1.network.packet.SendStayPacket;

public class IA  implements NextTurn, ReceiveId{

	Calculs calculs = new Calculs();
	Saves saves = new Saves();
	Attaque attaque = new Attaque();

	public static void main(String[] args){

		Main main = new Main("localhost",25565);

	}

	private String id;
	private Main main;



	private int pm;
	private int t;

	public IA(Main main) {
		this.main = main;

		t = 100;
		pm = 5;
	}


	private void addPowerEngine(int energie){
		this.main.getClient().send(new SendPenginePacket(energie, this.id));
	}

	private void addPowerShield(int energie){
		this.main.getClient().send(new SendPShieldPacket(energie, this.id));
	}

	private void addPowerShootSpeed(int energie){
		this.main.getClient().send(new SendPShootPacket(energie, this.id));
	}

	private void addPowerShoot(int energie){
		this.main.getClient().send(new SendPpuissancePacket(energie, this.id));
	}

	private void stay(){
		this.main.getClient().send(new SendStayPacket(this.id));
	}

	private void shoot(){
		this.main.getClient().send(new SendShootPacket(this.id));
	}

	private void rotate(int rot){
		this.main.getClient().send(new SendRotatePacket(rot, this.id));
	}

	private ArrayList<Player> getOtherPlayer(){
		ArrayList<Player> list = new ArrayList<>();
		for(Player p : this.main.getGame().getPlayers()){
			if(!p.getId().equals(this.id)){
				list.add(p);
			}
		}
		return list;
	}

	private Player getPlayer(){

		for(Player p : this.main.getGame().getPlayers()){
			if(p.getId().equals(this.id)){
				return p;
			}
		}
		return null;
	}

	@Override
	public void play() {

		saves.ajoutAuCompteurDeTour();
		System.out.println("numero du tour : " + saves.getNumeroDuTour());




		if(saves.getNumeroDuTour() < 3)
			reconnaissance();

		else
			envoieInfosTableau(attaque.poursuite(main, saves, this.getPlayer()));


	}


	public void reconnaissance(){ // signe de reconnaissance : shoot = 3, puissance = 2, shield = 1

		if(saves.getNumeroDuTour() == 1){
			this.addPowerShootSpeed(3);
			this.addPowerShoot(2);
			this.addPowerShield(1);
		}
		else{
			this.stay();
			this.stay();
			this.stay();
		}

		if(saves.getIdBen().equals(""))
			for(int i = 0 ; i < main.getGame().getPlayers().size() ; i++)
				if(!main.getGame().getPlayers().get(i).getId().equals(this.getPlayer().getId())) // récupère pas mon id mais bien celui de Ben
					if(main.getGame().getPlayers().get(i).getVaisseau().getPshoot() == 3)
						if(main.getGame().getPlayers().get(i).getVaisseau().getpPuissance() == 2)
							if(main.getGame().getPlayers().get(i).getVaisseau().getPshield() == 1)
								saves.setIdBen(main.getGame().getPlayers().get(i).getId());
	}


	public void chercheVaisseauDepuisId(){
		Vector2D moiABen = calculs.vecteurAvec2Points(this.getPlayer().getVaisseau().getPos(), calculs.recupPositionVaisseauDepuisId(main, saves.getIdBen()));
		if(Math.abs(calculs.vecteursColineairesResultatCalculs(moiABen, this.getPlayer().getVaisseau().getDir())) < 10){
			if(calculs.vecteursMemeSens(moiABen, this.getPlayer().getVaisseau().getDir())){
				this.shoot();
				this.stay();
				this.stay();
			}
			else{
				this.rotate(5);
				this.stay();
				this.stay();
			}
		}
		else{
			this.rotate(5);
			this.stay();
			this.stay();
		}
	}
	// 0:addPowerEngine   1:rotate   2:addPowerShoot   3:addSpeedShoot   4:shoot   5:addPowerShield   6:stay
	public void envoieInfosTableau(int tab[]){
		for(int i = 0 ; i < 3 ; i++){
			switch(tab[i*2]){
			case 0 :
				modifPowerEngine(tab[i*2+1]);
				break;
			case 1 :
				this.rotate(tab[i*2+1]);
				break;
			case 2 :
				modifPowerShootPuissance(tab[i*2+1]);
				break;
			case 3 :
				modifPowerShootSpeed(tab[i*2+1]);
				break;
			case 4 :
				this.shoot();
				break;
			case 5 :
				modifPowerShield(tab[i*2+1]);
				break;
			case 6 :
				this.stay();
				break;
			default :
				this.shoot();
				break;
			}
		}
	}

	public void modifPowerEngine(int valeurVoulu){
		int x = valeurVoulu - this.getPlayer().getVaisseau().getPengine();
		this.addPowerEngine(x);
	}

	public void modifPowerShootSpeed(int valeurVoulu){
		int x = valeurVoulu - this.getPlayer().getVaisseau().getPshoot();
		this.addPowerShootSpeed(x);
	}

	public void modifPowerShootPuissance(int valeurVoulu){
		int x = valeurVoulu - this.getPlayer().getVaisseau().getpPuissance();
		this.addPowerShoot(x);
	}

	public void modifPowerShield(int valeurVoulu){
		int x = valeurVoulu - this.getPlayer().getVaisseau().getPshield();
		this.addPowerShield(x);
	}



	@Override
	public void setId(String id) {
		this.id = id;
	}
}

