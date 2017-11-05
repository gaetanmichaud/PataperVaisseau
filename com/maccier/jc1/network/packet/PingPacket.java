package com.maccier.jc1.network.packet;

import com.maccier.jc1.network.ClientInfo;

/**
 * Created by courtille on 09/05/17.
 */
public class PingPacket extends Packet{

    private String msg;

    public PingPacket() {
        super("PingPacket");
    }


    @Override
    public synchronized String encode() {
        return this.getIdentifient()+",ping,pong";
    }

    @Override
    public synchronized void decode(String msg, ClientInfo cli) {
        String text = msg.split(",")[1];
        System.out.println(text);
    }

    @Override
    public synchronized Packet reponse(String msg) {
        String text = msg.split(",")[2];
        return new PrintStringPacket(text);
    }
}
