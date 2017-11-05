package com.maccier.jc1.game.vaisseau;



import com.maccier.jc1.Main;
import com.maccier.jc1.exception.FailLoad;
import com.maccier.jc1.game.Player;

import com.maccier.jc1.Vector2D;

public class Missile {


    private Vector2D pos,dir;

    //TODO rendre sa configurable


    //TODO mettre en place les stats
    // les 3 cara elle peuvent etre config de 0 a 10  (arondie a l'inferieure sur les calcule)
    private boolean dead;
    private String p;


    public Missile(){

    }

    public Missile(Vector2D pos, Vector2D dir, Player p){


        this.dir = dir;
        this.p = p.getId();
        this.pos = pos;
    }

    public void update()
    {
        this.dir.normalise();
        this.dir.multiply( 4);

        pos.add(dir);

        if(this.pos.getX() < -32){
            this.dead = true;
        }

        if(this.pos.getY() < -32){
            this.dead = true;
        }

        if(this.pos.getX() > 1472){
            this.dead = true;
        }

        if(this.pos.getY() > 1000){
            this.dead = true;
        }
    }




    public boolean isDead() {
        return dead;
    }

    public String getP() {
        return p;
    }

    public Vector2D getPos() {
        return pos;
    }

    public Vector2D getDir() {
        return dir;
    }


    public void load(String save) throws FailLoad {
        String[] data = save.split(";");

        try{
            this.dir = new Vector2D(Double.parseDouble(data[0]), Double.parseDouble(data[1]));
            this.pos = new Vector2D(Double.parseDouble(data[2]), Double.parseDouble(data[3]));
            this.p = data[4];
        }
        catch (Exception e){
            throw new FailLoad();
        }
    }

    public String save(){
        String save = "";
        save += this.dir.getX()+";";
        save += this.dir.getY()+";";
        save += this.pos.getX()+";";
        save += this.pos.getY()+";";
        save += this.p;
	//TODO ajouter les x et y
		
        return save;
    }

    public void setDir(Vector2D dir) {
        this.dir = dir;
    }

    public void addRot(int i) {
        this.dir = Vector2D.getVectorWithAngle(this.dir.getAngle()+ i);
    }

}
