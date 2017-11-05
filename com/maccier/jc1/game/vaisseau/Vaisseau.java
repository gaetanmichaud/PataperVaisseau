package com.maccier.jc1.game.vaisseau;



import com.maccier.jc1.Main;
import com.maccier.jc1.exception.FailLoad;

import com.maccier.jc1.Vector2D;


public class Vaisseau {



    private Vector2D pos,dir;

    //TODO rendre sa configurable

    private double life; // max = 500
    private double shield; // max = 500;


    //TODO mettre en place les stats
    // les 3 cara elle peuvent etre config de 0 a 10  (arondie a l'inferieure sur les calcule)
    private int pshield; //regen le shield de x/10 * 3
    private int pengine; // vitesse = x/10
    private int pshoot; // reload shoot = 6 - (x/10 * 5)
    private int pPuissance; // dmg = 10 + x/10 * 50


    public Vaisseau(){
        this.pos = new Vector2D(0,0);
    }

    public void update()
    {
        this.dir.normalise();

        double vitesse = this.pengine;
        vitesse /= 10;
        this.dir.multiply(vitesse);

        pos.add(dir);
    }



    public void addLife(double nb){
        this.life += nb;
    }

    public void addShield(double nb){
        this.shield += nb;
    }

    public double getLife() {
        return life;
    }

    public double getShield() {
        return shield;
    }

    public int getPshield() {
        return pshield;
    }

    public void setPshield(int pshield) {
        this.pshield = Math.max(0, Math.min(10, pshield));
    }

    public int getPengine() {
        return pengine;
    }

    public void setPengine(int pengine) {
        this.pengine = Math.max(0, Math.min(10, pengine));
    }

    public int getPshoot() {
        return pshoot;
    }

    public void setPshoot(int pshoot) {
        this.pshoot = Math.max(0, Math.min(10, pshoot));
    }

    public int getpPuissance() {
        return pPuissance;
    }

    public void setpPuissance(int pPuissance) {
        this.pPuissance = Math.max(0, Math.min(10, pPuissance));
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
            this.life = Double.parseDouble(data[0]);
            this.shield = Double.parseDouble(data[1]);

            this.pshield = Integer.parseInt(data[2]);
            this.pengine = Integer.parseInt(data[3]);
            this.pshoot = Integer.parseInt(data[4]);
            this.pPuissance = Integer.parseInt(data[5]);
            this.pos = new Vector2D(Double.parseDouble(data[6]), Double.parseDouble(data[7]));
            this.dir = new Vector2D(Double.parseDouble(data[8]), Double.parseDouble(data[9]));
        }
        catch (Exception e){
            throw new FailLoad();
        }
    }

    public String save(){
        String save = "";
        save += this.life+";";
        save += this.shield+";";
        save += this.pshield+";";
        save += this.pengine+";";
        save += this.pshoot+";";
        save += this.pPuissance+ ";";
        save += this.pos.getX()+";";
        save += this.pos.getY()+";";
        save += this.dir.getX()+";";
        save += this.dir.getY();

        return save;
    }

    public void setDir(Vector2D dir) {
        this.dir = dir;
    }

    public void addRot(int i) {



        this.dir = Vector2D.getVectorWithAngle(this.dir.getAngle()+ i);
    }

}
