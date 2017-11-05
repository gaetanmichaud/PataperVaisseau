package com.maccier.jc1;

import java.util.Vector;

import com.maccier.jc1.game.Player;

public class Attaque {
	Calculs calculs = new Calculs();


	// 0:addPowerEngine   1:rotate   2:addPowerShoot   3:addSpeedShoot   4:shoot   5:addPowerShield   6:stay




	public int[] attaqueRotative(){ // final flash, quand t'as plus bcp de vie
		int[] tab = new int[6];
		tab[0] = 1;
		tab[1] = 5;
		tab[2] = 3;
		tab[3] = 10;
		tab[4] = 4;
		tab[5] = 0;
		return tab;
	}


	public String choixEnnemi(Main main, Saves saves, Player moi){
		int nbVaisseauxTotal = main.getGame().getPlayers().size();

		int nbVaisseauxEncoreEnVie = 0;
		for(int i = 0 ; i < nbVaisseauxTotal ; i++)
			if(main.getGame().getPlayers().get(i).getVaisseau().getLife() > 0)
				nbVaisseauxEncoreEnVie += 1;

		int choix = 0;
		boolean choixValidee = false;

		if(nbVaisseauxEncoreEnVie > 2){
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> encore bcp de joueurs..<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			do{
				choix = (int)(Math.random()*nbVaisseauxTotal);
				if(main.getGame().getPlayers().get(choix).getVaisseau().getLife() > 0)
					if(!main.getGame().getPlayers().get(choix).getId().equals(saves.getIdBen()))
						if(!main.getGame().getPlayers().get(choix).getId().equals(moi.getId()))
							choixValidee = true;
			}while(!choixValidee);
		}
		else{
			if(nbVaisseauxEncoreEnVie == 2){
				System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< plus que 2 joueurs !!!>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				for(int i = 0 ; i < nbVaisseauxTotal ; i++)
					if(main.getGame().getPlayers().get(i).getVaisseau().getLife() > 0)
						if(!main.getGame().getPlayers().get(i).getId().equals(moi.getId()))
							choix = i;
			}
			else{
				System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< VICTORY !!! >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				return "V";
			}
		}

		return main.getGame().getPlayers().get(choix).getId();
	}

	public boolean ennemiExisteTjrs(Main main, String idEnnemi){
		System.out.println("id Ennemi : " + idEnnemi);
		for(int i = 0 ; i < main.getGame().getPlayers().size() ; i++){
			System.out.println("id Vaisseau : " + main.getGame().getPlayers().get(i).getId());
			System.out.println("vie : " + main.getGame().getPlayers().get(i).getVaisseau().getLife());
			if(main.getGame().getPlayers().get(i).getId().equals(idEnnemi) && main.getGame().getPlayers().get(i).getVaisseau().getLife() <= 0)
				return false;
		}
		return true;
	}

	public int sensDeRotation(Main main, Player moi, Vector2D vecteurEnnemi, Vector2D positionEnnemi){

		double[] equationEnnemi = calculs.equationDroite(vecteurEnnemi, positionEnnemi);
		double[] equationParalleleEnnemi = calculs.equationDroite(vecteurEnnemi, moi.getVaisseau().getPos());

		if(vecteurEnnemi.getX() == 0){
			if(positionEnnemi.getX() > moi.getVaisseau().getPos().getX()){ // sur le graphe, repère en haut à gauche, tu es à gauche
				if(vecteurEnnemi.getY() < 0){
					//					System.out.println("vertical, gauche, monte");
					return -1;
				}
				else{
					//					System.out.println("vertical, gauche, descend");
					return 1;
				}
			}
			else{
				if(vecteurEnnemi.getY() < 0){
					//					System.out.println("vertical, droite, monte");
					return 1;
				}
				else{
					//					System.out.println("vertical, droite, descend");
					return -1;
				}
			}
		}
		else{
			//			System.out.println("	>> eqEnnemi : " + equationEnnemi[0] + "x - (" + equationEnnemi[1] + ") + (" + equationEnnemi[2] + ")");
			//			System.out.println("	>> eqDeMoi  : " + equationParalleleEnnemi[0] + "x - (" + equationParalleleEnnemi[1] + ") + (" + equationParalleleEnnemi[2] + ")");
			//			System.out.println("	<< vecteur Ennemi  : " + vecteurEnnemi.toString());
			//			System.out.println("	<< position Ennemi : " + positionEnnemi.toString());
			//			System.out.println("	>> vecteur  De moi : " + moi.getVaisseau().getDir().toString());
			//			System.out.println("	>> position De moi : " + moi.getVaisseau().getPos().toString());
			if(equationEnnemi[1] < 0){
				equationEnnemi[2] = -equationEnnemi[2];
				equationParalleleEnnemi[2] = - equationParalleleEnnemi[2];
			}

			if(equationEnnemi[2] > equationParalleleEnnemi[2]){ // quand tu regarde le graphique, tu es au dessus
				//				System.out.println("sur");
				if(vecteurEnnemi.getX() < 0){
					//					System.out.println("sur, gauche");
					return 1;
				}
				else{
					//					System.out.println("sur, droite");
					return -1;
				}
			}
			else{ // dessous la droite de l'ennemi
				//				System.out.println("sous");
				if(vecteurEnnemi.getX() < 0){
					//					System.out.println("sous, gauche");
					return -1;
				}
				else{
					//					System.out.println("sous, droite");
					return 1;
				}
			}
		}



	}
	// 0:addPowerEngine   1:rotate   2:addPowerShoot   3:addSpeedShoot   4:shoot   5:addPowerShield   6:stay

	public int[] reinitialisation(Player moi, Saves saves){
		saves.setDejaInit(false);

		int tab[] = new int[6];

		for(int i = 0 ; i < 3 ; i++){
			if(moi.getVaisseau().getPengine() != 0){
				tab[i*2] = 0;
				tab[i*2+1] = 0;
			}
			else
				if(moi.getVaisseau().getpPuissance() != 0){
					tab[i*2] = 2;
					tab[i*2+1] = 0;
				}
				else
					if(moi.getVaisseau().getPshield() != 0){
						tab[i*2] = 5;
						tab[i*2+1] = 0;
					}
					else
						if(moi.getVaisseau().getPshoot() != 0){
							tab[i*2] = 3;
							tab[i*2+1] = 0;
						}
		}

		if(moi.getVaisseau().getPshoot() == 0)
			saves.setDejaInit(true);

		return tab;
	}

	public int recupVitesseEnnemi(Saves saves, Main main){
		for(int i = 0 ; i < main.getGame().getPlayers().size() ; i++)
			if(main.getGame().getPlayers().get(i).getId().equals(saves.getIdEnnemi()))
				return main.getGame().getPlayers().get(i).getVaisseau().getPengine();
		return 0;
	}


	public int[] poursuite(Main main, Saves saves, Player moi){
		if(!saves.isDejaInit())
			return reinitialisation(moi, saves);

		int tab[] = new int[6];

		if(!saves.getIdEnnemi().equals("V")){
			if(saves.getIdEnnemi().equals("")){
				System.out.println("pas encore d'ennemi");
				saves.setEtat(0);
				saves.setIdEnnemi(choixEnnemi(main, saves, moi));
				System.out.println("nouvel ennemi : " + saves.getIdEnnemi());
				return reinitialisation(moi, saves);
			}
			else
				if(!ennemiExisteTjrs(main, saves.getIdEnnemi())){
					System.out.println("ennemi mort...");
					saves.setEtat(0);
					saves.setIdEnnemi(choixEnnemi(main, saves, moi));
					if(saves.getIdEnnemi().equals("V"))
						saves.setEtat(4);
					System.out.println("nouvel ennemi : " + saves.getIdEnnemi());
					return reinitialisation(moi, saves);
				}
		}


		// 0:addPowerEngine   1:rotate   2:addPowerShoot   3:addSpeedShoot   4:shoot   5:addPowerShield   6:stay

		Vector2D positionEnnemi = calculs.recupPositionVaisseauDepuisId(main, saves.getIdEnnemi());
		Vector2D directionEnnemi = calculs.recupVecteurVaisseauDepuisId(main, saves.getIdEnnemi());

		switch(saves.getEtat()){

		case 0 : // rejoins l'ennemi
			System.out.println("			case 0, rejoindre");
//			if(calculs.missileMeTouche(main, moi)){
//				saves.setEtat(5);
//				return initBouclier(moi);
//			}
			
			if(calculs.distanceEntre2Points(moi.getVaisseau().getPos(), positionEnnemi) < 60){
				saves.setEtat(2);
				return reinitialisation(moi, saves);
			}

			Vector2D moiAEnnemi0 = calculs.vecteurAvec2Points(moi.getVaisseau().getPos(), positionEnnemi);

			if(!calculs.vecteursMemeSens(moiAEnnemi0, moi.getVaisseau().getDir())){
				saves.setEtat(1);
				return reinitialisation(moi, saves);
			}

			if(Math.abs(calculs.vecteursColineairesResultatCalculs(moiAEnnemi0, moi.getVaisseau().getDir())) < 40){
				if(calculs.vecteursMemeSens(moiAEnnemi0, moi.getVaisseau().getDir())){
					tab[0] = 0;
					tab[1] = 10;
					tab[2] = 2;
					tab[3] = 5;
					tab[4] = 6;
					tab[5] = 0;
				}
				else{
					tab[0] = 0;
					tab[1] = 5;
					tab[2] = 1;
					tab[3] = 5 * sensDeRotation(main, moi, directionEnnemi, positionEnnemi);
					tab[4] = 6;
					tab[5] = 0;
				}
			}
			else{
				tab[0] = 0;
				tab[1] = 5;
				tab[2] = 1;
				tab[3] = 5 * sensDeRotation(main, moi, directionEnnemi, positionEnnemi);
				tab[4] = 6;
				tab[5] = 0;
			}

			break;

		case 1 : // demi tour
			System.out.println("			case 1, demi tour");
//			if(calculs.missileMeTouche(main, moi)){
//				saves.setEtat(5);
//				return initBouclier(moi);
//			}
			
			Vector2D moiAEnnemi1 = calculs.vecteurAvec2Points(moi.getVaisseau().getPos(), positionEnnemi);
			
			if(calculs.vecteursMemeSens(moiAEnnemi1, moi.getVaisseau().getDir())){
				saves.setEtat(0);
				return reinitialisation(moi, saves);
			}
			
			tab[0] = 1;
			tab[1] = 5 * sensDeRotation(main, moi, directionEnnemi, positionEnnemi);
			tab[2] = 6;
			tab[3] = 0;
			tab[4] = 6;
			tab[5] = 0;

			break;

		case 2 : // passe derrière
			System.out.println("			case 2, passe derrière");
//			if(calculs.missileMeTouche(main, moi)){
//				saves.setEtat(5);
//				return initBouclier(moi);
//			}
			if(calculs.estDerriereVaisseauEnnemi(directionEnnemi, positionEnnemi, moi.getVaisseau().getPos())){
				saves.setEtat(3);
				return reinitialisation(moi, saves);
			}
			if(calculs.distanceEntre2Points(moi.getVaisseau().getPos(), positionEnnemi) > 100){
				saves.setEtat(0);
				return reinitialisation(moi, saves);
			}

			Vector2D positionEnnemiDecalee2 = positionEnnemi;
			positionEnnemiDecalee2.addToX((int) (directionEnnemi.getX()*60));
			positionEnnemiDecalee2.addToY((int) (directionEnnemi.getY()*60));

			// 0:addPowerEngine   1:rotate   2:addPowerShoot   3:addSpeedShoot   4:shoot   5:addPowerShield   6:stay
			Vector2D moiAEnnemi2 = calculs.vecteurAvec2Points(moi.getVaisseau().getPos(), positionEnnemiDecalee2);
			if(Math.abs(calculs.vecteursColineairesResultatCalculs(moiAEnnemi2, moi.getVaisseau().getDir())) < 40){
				if(calculs.vecteursMemeSens(moiAEnnemi2, moi.getVaisseau().getDir())){
					tab[0] = 2;
					tab[1] = 10;
					tab[2] = 3;
					tab[3] = 5;
					tab[4] = 4;
					tab[5] = 0;
				}
				else{
					tab[0] = 1;
					tab[1] = 5 * sensDeRotation(main, moi, directionEnnemi, positionEnnemiDecalee2);
					tab[2] = 4;
					tab[3] = 0;
					tab[4] = 6;
					tab[5] = 0;
				}
			}
			else{
				tab[0] = 1;
				tab[1] = 5 * sensDeRotation(main, moi, directionEnnemi, positionEnnemiDecalee2);
				tab[2] = 4;
				tab[3] = 0;
				tab[4] = 6;
				tab[5] = 0;
			}
			break;


		case 3 : // attaque
			System.out.println("			case 3, poursuite");
//			if(calculs.missileMeTouche(main, moi)){
//				saves.setEtat(5);
//				return initBouclier(moi);
//			}
			if(calculs.distanceEntre2Points(moi.getVaisseau().getPos(), positionEnnemi) > 90){
				saves.setEtat(0);
				return reinitialisation(moi, saves);
			}
			if(!calculs.estDerriereVaisseauEnnemi(directionEnnemi, positionEnnemi, moi.getVaisseau().getPos())){
				saves.setEtat(2);
				return reinitialisation(moi, saves);
			}

			Vector2D positionEnnemiDecalee = positionEnnemi;
			Vector2D vecteurEnnemi = directionEnnemi;

			positionEnnemiDecalee.addToX((int) (vecteurEnnemi.getX()*60));
			positionEnnemiDecalee.addToY((int) (vecteurEnnemi.getY()*60));

			// 0:addPowerEngine   1:rotate   2:addPowerShoot   3:addSpeedShoot   4:shoot   5:addPowerShield   6:stay

			Vector2D moiAEnnemi3 = calculs.vecteurAvec2Points(moi.getVaisseau().getPos(), positionEnnemiDecalee);
			if(Math.abs(calculs.vecteursColineairesResultatCalculs(moiAEnnemi3, moi.getVaisseau().getDir())) < 30){
				if(calculs.vecteursMemeSens(moiAEnnemi3, moi.getVaisseau().getDir())){
					tab[0] = 0;
					tab[1] = recupVitesseEnnemi(saves, main) - 1;
					tab[2] = 2;
					tab[3] = Math.min(10, 16-recupVitesseEnnemi(saves, main));
					tab[4] = 4;
					tab[5] = 0;
				}
				else{
					tab[0] = 1;
					tab[1] = 5 * sensDeRotation(main, moi, directionEnnemi, positionEnnemiDecalee);
					tab[2] = 6;
					tab[3] = 0;
					tab[4] = 6;
					tab[5] = 0;
				}
			}
			else{
				tab[0] = 1;
				tab[1] = 5 * sensDeRotation(main, moi, directionEnnemi, positionEnnemiDecalee);
				tab[2] = 6;
				tab[3] = 0;
				tab[4] = 6;
				tab[5] = 0;
			}

			break;


			// 0:addPowerEngine   1:rotate   2:addPowerShoot   3:addSpeedShoot   4:shoot   5:addPowerShield   6:stay
		case 4 : // Victoire !
			System.out.println("			case 4, victoire");
			tab[0] = 1;
			tab[1] = 5;
			tab[2] = 3;
			tab[3] = 10;
			tab[4] = 4;
			tab[5] = 0;
			break;


		case 5 : // bouclier
			System.out.println("			case 5, bouclier");

			if(!calculs.missileMeTouche(main, moi)){
				saves.setEtat(0);
				return reinitialisation(moi, saves);
			}

			tab[0] = 0;
			tab[1] = moi.getVaisseau().getPengine();
			tab[2] = 5;
			tab[3] = 10;
			tab[4] = 6;
			tab[5] = 0;
			
			break;
		}

		return tab;

	}


	public int[] initBouclier(Player moi){
		int tab[] = new int[6];

		int total = moi.getVaisseau().getPshield();

		for(int i = 0 ; i < 3 ; i++){
			if(total < 10){
				if(moi.getVaisseau().getpPuissance() != 0){
					tab[i*2] = 0;
					tab[i*2+1] = 0;
					total += moi.getVaisseau().getpPuissance();
				}
				else
					if(moi.getVaisseau().getPshoot() != 0){
						tab[i*2] = 2;
						tab[i*2+1] = 0;
						total += moi.getVaisseau().getPshoot();
					}
					else
						if(moi.getVaisseau().getPengine() != 0){
							tab[i*2] = 3;
							tab[i*2+1] = 10-total;
							total = 10;
						}
			}
			else{
				tab[i*2] = 6;
				tab[i*2+1] = 0;
			}
		}
		return tab;
	}


}
