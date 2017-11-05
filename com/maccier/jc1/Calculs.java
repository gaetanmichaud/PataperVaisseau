package com.maccier.jc1;

import java.util.Vector;

import com.maccier.jc1.game.Player;

public class Calculs {

	public Vector2D vecteurAvec2Points(Vector2D pt1, Vector2D pt2){
		Vector2D vecteur = new Vector2D(0, 0);

		vecteur.setX(pt2.getX() - pt1.getX());
		vecteur.setY(pt2.getY() - pt1.getY());

		return vecteur;
	}


	public double vecteursColineairesResultatCalculs(Vector2D vec1, Vector2D vec2){
		return vec1.getX() * vec2.getY() - vec1.getY() * vec2.getX();
	}

	public boolean vecteursMemeSens(Vector2D vec1, Vector2D vec2){
		if(vec1.getX() > 0.01 || vec1.getX() < -0.01)
			if(vec1.getX() < 0 && vec2.getX() < 0)
				return true;
			else
				if(vec1.getX() > 0 && vec2.getX() > 0)
					return true;
				else
					return false;
		else
			if(vec1.getY() < 0 && vec2.getY() < 0)
				return true;
			else
				if(vec1.getY() > 0 && vec2.getY() > 0)
					return true;
				else
					return false;
	}

	public Vector2D recupPositionVaisseauDepuisId(Main main, String id){
		for(int i = 0 ; i < main.getGame().getPlayers().size() ; i++){
			if(main.getGame().getPlayers().get(i).getId().equals(id))
				return main.getGame().getPlayers().get(i).getVaisseau().getPos();
		}
		return new Vector2D(0, 0);
	}

	public Vector2D recupVecteurVaisseauDepuisId(Main main, String id){
		for(int i = 0 ; i < main.getGame().getPlayers().size() ; i++){
			if(main.getGame().getPlayers().get(i).getId().equals(id))
				return main.getGame().getPlayers().get(i).getVaisseau().getDir();
		}
		return new Vector2D(0, 0);
	}



	public double[] equationDroite(Vector2D vecteur, Vector2D point){
		double[] abETc = new double[3];

		abETc[0] = vecteur.getY();
		abETc[1] = vecteur.getX();
		abETc[2] = vecteur.getX()*point.getY() - vecteur.getY()*point.getX();

		return abETc;
	}


	public double distanceEntre2Points(Vector2D pt1, Vector2D pt2){
		return Math.sqrt( Math.pow( pt2.getX() - pt1.getX(), 2 ) + Math.pow( pt2.getY() - pt1.getY(), 2 ) );
	}



	public double[] equationPerpendiculaireDirectionEnnemiPassantParLePoint(Vector2D vecteurEnnemi, Vector2D positionPoint){
		Vector2D vecOrtho = new Vector2D(-vecteurEnnemi.getY(), vecteurEnnemi.getX());
		double [] abETc = {vecOrtho.getY(), vecOrtho.getX(), vecOrtho.getX()*positionPoint.getY() - vecOrtho.getY()*positionPoint.getX()};
		return abETc;
	}


	public boolean estDerriereVaisseauEnnemi(Vector2D directionEnnemi, Vector2D positionEnnemi, Vector2D maPosition){
		double[] equationPerpendiculaireEnnemi = equationPerpendiculaireDirectionEnnemiPassantParLePoint(directionEnnemi, maPosition);
		double[] equationEnnemi = equationDroite(directionEnnemi, positionEnnemi);
		Vector2D pointIntersection = pointIntersectionAvecEquationsDroites(equationPerpendiculaireEnnemi, equationEnnemi);
		double longueurVec = (pointIntersection.getX() - positionEnnemi.getX())/directionEnnemi.getX();
		if(longueurVec > -60)
			return false;
		else
			return true;
	}


	public Vector2D pointIntersectionAvecEquationsDroites(double[] eq1, double[] eq2){
		Vector2D point = new Vector2D(0, 0);
		point.setX( ( ( eq2[1] * eq1[2] ) / eq1[1] - eq2[2] ) / ( eq2[0] - ( eq2[1] * eq1[0] ) / eq1[1] ) );
		point.setY( (eq1[0]*((eq2[1]*eq1[2])/eq1[1] - eq2[2]) / (eq2[0] - (eq2[1]*eq1[0])/eq1[1]) + eq1[2]) / eq1[1] );
		//		eq1 = ax - by +c = 0  --  eq2 = dx - ey + f = 0
		//		x = ( (   e    *   c    ) /   b    -   f    ) / (   d    - (   e    *   a    ) /   b    );
		//		y = (a*((e*c)/b - f) / (d - (e*a)/b) + c) / b;
		return point;
	}


	
	public boolean missileMeTouche(Main main, Player moi){
		for(int i = 0 ; i < main.getGame().getMissiles().size() ; i++)
			if(distanceEntre2Points(main.getGame().getMissiles().get(i).getPos(), moi.getVaisseau().getPos()) < 40)
				return true;		
		return false;
	}
	
	
	
	
	
	





	public double[] pointsEntreeEtSortieDeLaZone(double[] pointIntersection, double[] vecteur){
		double[] pointsEntreeSortie = new double[4]; // [x1,y1,x2,y2]

		try{
			double coeff = vecteur[1]/vecteur[0];


			// x2 = x1 + AB * cos(arctan(coeff))
			// y2 = y1 + AB * sin(arctan(coeff))

			pointsEntreeSortie[0] = pointIntersection[0] + 63*Math.cos(Math.atan(coeff)); // 126 : diagonale vaisseau + diagonale missile
			pointsEntreeSortie[1] = pointIntersection[1] + 63*Math.sin(Math.atan(coeff));
			pointsEntreeSortie[2] = pointIntersection[0] - 63*Math.cos(Math.atan(coeff));
			pointsEntreeSortie[3] = pointIntersection[1] - 63*Math.sin(Math.atan(coeff));

		}
		catch(ArithmeticException e){
			pointsEntreeSortie[0] = pointIntersection[0];
			pointsEntreeSortie[1] = pointIntersection[1] + 63;
			pointsEntreeSortie[2] = pointIntersection[0];
			pointsEntreeSortie[3] = pointIntersection[1] - 63;
		}

		return pointsEntreeSortie;
	}


	public double[] inversionTableauDe2(double[] tab){
		double tmp = tab[1];
		tab[1] = tab[0];
		tab[0] = tmp;
		return tab;
	}


	public double[] tpsEntreeEtSortieDeLaZone(double[] vecteurObjet, double[] pointsEntreeSortie, double[] pointObjet){
		double[] tpsEntreeEtSortieZone = new double[2]; // [0] => cb de tics pr arriver à la zone ; [1] => cb de tics pr sortir de la zone

		try {
			tpsEntreeEtSortieZone[0] = Math.abs((pointObjet[0] - pointsEntreeSortie[0]) / vecteurObjet[0]);
			tpsEntreeEtSortieZone[1] = Math.abs((pointObjet[0] - pointsEntreeSortie[2]) / vecteurObjet[0]);
		} catch (ArithmeticException e) {
			tpsEntreeEtSortieZone[0] = Math.abs((pointObjet[1] - pointsEntreeSortie[1]) / vecteurObjet[1]);
			tpsEntreeEtSortieZone[1] = Math.abs((pointObjet[1] - pointsEntreeSortie[3]) / vecteurObjet[1]);
		}

		if(tpsEntreeEtSortieZone[0] > tpsEntreeEtSortieZone[1]) // inverser pr avoir l'entrée en 1er et la sortie en 2eme
			tpsEntreeEtSortieZone = inversionTableauDe2(tpsEntreeEtSortieZone);

		return tpsEntreeEtSortieZone;
	}



	public Vector2D pointIntersectionAvecVecteursEtPoints(double[] vecteur1, double[] point1, double[] vecteur2, double[] point2){
		return pointIntersectionAvecEquationsDroites(equationDroiteOld(vecteur1, point1), equationDroiteOld(vecteur2, point2));
	}


	public double[] equationDroiteOld(double[] vecteur, double[] point){
		double[] abETc = new double[3];

		abETc[0] = vecteur[1];
		abETc[1] = vecteur[0];
		abETc[2] = vecteur[0]*point[1] - vecteur[1]*point[0];

		return abETc;
	}


	//	public boolean colision(Vector2D vcoordonneesVaisseau, Vector2D vvecteurVaisseau, Vector2D vcoordonneesMissile, Vector2D vvecteurMissile){
	//		
	//		double[] coordonneesVaisseau = {vcoordonneesVaisseau.getX(), vcoordonneesVaisseau.getY()};
	//		double[] vecteurVaisseau = {vvecteurVaisseau.getX(), vvecteurVaisseau.getY()};
	//		double[] coordonneesMissile = {vcoordonneesMissile.getX(), vcoordonneesMissile.getY()};
	//		double[] vecteurMissile = {vvecteurMissile.getX(), vvecteurMissile.getY()};
	//		
	//		Vector2D pointIntersec = pointIntersectionAvecVecteursEtPoints(vecteurVaisseau, coordonneesVaisseau, vecteurMissile, coordonneesMissile);
	//		Vector2D pointsEntreeSortieVaisseau = pointsEntreeEtSortieDeLaZone(pointIntersec, vecteurVaisseau);
	//		double[] pointsEntreeSortieMissile = pointsEntreeEtSortieDeLaZone(pointIntersec, vecteurMissile);
	//		double[] tpsEntreeSortieVaisseau = tpsEntreeEtSortieDeLaZone(vecteurVaisseau, pointsEntreeSortieVaisseau, coordonneesVaisseau);
	//		double[] tpsEntreeSortieMissile = tpsEntreeEtSortieDeLaZone(vecteurMissile, pointsEntreeSortieMissile, coordonneesMissile);
	//		
	//		if(tpsEntreeSortieMissile[0] < tpsEntreeSortieVaisseau[1] && tpsEntreeSortieVaisseau[0] < tpsEntreeSortieMissile[1])
	//			return true;
	//		return false;
	//	}
	//	



}
