package com.maccier.jc1.network.packet;

import com.maccier.jc1.network.ClientInfo;

/**
 * Created by courtille on 09/05/17.
 */
public class VoidPacket extends Packet{


    public VoidPacket() {
        super("VoidPacket");
    }

    @Override
    public synchronized String encode() {
        return this.getIdentifient()+", ";
    }

    @Override
    public synchronized void decode(String msg, ClientInfo cli) {

    }

    @Override
    public synchronized Packet reponse(String msg) {
        return null;
    }
}
