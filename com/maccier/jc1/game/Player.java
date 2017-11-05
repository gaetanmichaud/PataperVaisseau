package com.maccier.jc1.game;

import com.maccier.jc1.exception.FailLoad;
import com.maccier.jc1.game.vaisseau.Vaisseau;
import com.maccier.jc1.Vector2D;

public class Player {


    private Vaisseau vaisseau;
    private String id;

    public Player(String id) {
        this.id = id;
        this.vaisseau = new Vaisseau();
    }

    public Player(){
        this.vaisseau = new Vaisseau();
    }

    public Vaisseau getVaisseau() {
        return vaisseau;
    }

    public String getId() {
        return id;
    }

    public void load(String save) throws FailLoad {
        String[] data = save.split(":");

        try{
            this.id = data[0];
            this.vaisseau.load(data[1]);
        }
        catch (Exception e){
            throw new FailLoad();
        }
    }

    public String save(){
        String save = "";
        save += this.id+":";
        save += this.vaisseau.save();
        return save;
    }


}
