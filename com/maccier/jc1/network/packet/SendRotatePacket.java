package com.maccier.jc1.network.packet;

import com.maccier.jc1.exception.FailLoad;
import com.maccier.jc1.game.Game;
import com.maccier.jc1.game.Player;
import com.maccier.jc1.network.inter.NextTurn;
import com.maccier.jc1.network.ClientInfo;
import com.maccier.jc1.network.packet.Packet;

import java.util.ArrayList;

public class SendRotatePacket extends Packet {

    private Game game;
    private int rot;
    private String id;

    public SendRotatePacket(int rot, String id) {
        super("SendRotate");
        this.rot = Math.max(-5, Math.min(5, rot));
        this.id = id;
    }



    @Override
    public synchronized String encode() {
        return this.getIdentifient()+","+this.id+","+rot;
    }

    @Override
    public synchronized void decode(String msg, ClientInfo cli) {

    }

    @Override
    public synchronized Packet reponse(String msg) {
        return null;
    }
}
