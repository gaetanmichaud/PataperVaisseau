package com.maccier.jc1.network.packet;

import com.maccier.jc1.exception.FailLoad;
import com.maccier.jc1.game.Game;
import com.maccier.jc1.game.Player;
import com.maccier.jc1.game.vaisseau.Missile;
import com.maccier.jc1.network.inter.NextTurn;
import com.maccier.jc1.network.ClientInfo;
import com.maccier.jc1.network.packet.Packet;
import com.maccier.jc1.network.packet.PrintStringPacket;

import java.util.ArrayList;

public class SendPlayersPacket extends Packet {

    private Game game;
    private NextTurn ia;

    public SendPlayersPacket(Game game, NextTurn ia){
        super("SendPlayers");
        this.game = game;
        this.ia = ia;
    }

    @Override
    public synchronized String encode() {

        String save = "";

        for(Player p : this.game.getPlayers()){
            save += p.save();
            if(p.getId().equals(this.game.getPlayers().get(this.game.getPlayers().size() - 1))){
                save += "/";
            }
        }

        return this.getIdentifient()+","+save;
    }

    @Override
    public synchronized void decode(String msg, ClientInfo cli) {
        System.out.println("recep");
        String[] data = msg.split(",");
        ArrayList<Player> players = new ArrayList<>();
        for(String s : data[1].split("/")){
            Player p = new Player();
            try {
                p.load(s);
            } catch (FailLoad failLoad) {
                failLoad.printStackTrace();
            }
            players.add(p);
        }

        ArrayList<Missile> missiles = new ArrayList<>();

        if(data.length >= 3){
            for(String s : data[2].split("/")){
                Missile p = new Missile();
                try {
                    p.load(s);
                } catch (FailLoad failLoad) {
                    failLoad.printStackTrace();
                }

                missiles.add(p);
            }
        }


        this.game.setPlayers(players);
        this.game.setMissiles(missiles);
        this.ia.play();
    }


    @Override
    public synchronized Packet reponse(String msg) {
        return null;
    }
}
