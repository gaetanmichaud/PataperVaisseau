package com.maccier.jc1.network.packet;

import com.maccier.jc1.Main;
import com.maccier.jc1.game.Game;
import com.maccier.jc1.network.ClientInfo;
import com.maccier.jc1.network.packet.Packet;

public class SendStayPacket extends Packet {


    private String id;

    public SendStayPacket(String id) {
        super("SendStay");
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
