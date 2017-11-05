package com.maccier.jc1.game;

import com.maccier.jc1.game.vaisseau.Missile;
import com.maccier.jc1.Vector2D;

import java.util.ArrayList;

public class Game  {

    private ArrayList<Player> players;
    private ArrayList<Missile> missiles;

    public void Game()
    {
        this.missiles = new ArrayList<>();
        this.players = new ArrayList<Player>();

    }


    public ArrayList<Player> getPlayers(){
        return this.players;
    }


    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }


    public ArrayList<Missile> getMissiles() {
        return missiles;
    }

    public void setMissiles(ArrayList<Missile> missiles) {
        this.missiles = missiles;
    }
}
