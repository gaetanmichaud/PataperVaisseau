package com.maccier.jc1.network.packet;

import com.maccier.jc1.game.Game;
import com.maccier.jc1.game.vaisseau.Vaisseau;
import com.maccier.jc1.network.ClientInfo;
import com.maccier.jc1.network.packet.Packet;

public class SendShootPacket extends Packet {


    private String id;

    public SendShootPacket(String id) {
        super("SendShoot");
        this.id = id;
    }


    @Override
    public synchronized String encode() {
        return this.getIdentifient()+","+this.id;
    }

    @Override
    public synchronized void decode(String msg, ClientInfo cli) {

    }

    @Override
    public synchronized Packet reponse(String msg) {
        return null;
    }
}
