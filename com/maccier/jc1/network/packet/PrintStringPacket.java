package com.maccier.jc1.network.packet;

import com.maccier.jc1.network.ClientInfo;

/**
 * Created by courtille on 09/05/17.
 */
public class PrintStringPacket extends Packet{

    private String msg;

    public PrintStringPacket() {
        super("PrintStringPacket");
    }

    public PrintStringPacket(String msg){
        super("PrintStringPacket");
        this.msg = msg;
    }

    @Override
    public synchronized String encode() {
        return this.getIdentifient()+","+this.msg;
    }

    @Override
    public synchronized void decode(String msg, ClientInfo cli) {
        String text = msg.split(",")[1];
        System.out.println(text);
    }

    @Override
    public synchronized Packet reponse(String msg) {
        return null;
    }
}
