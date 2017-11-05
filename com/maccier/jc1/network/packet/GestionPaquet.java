package com.maccier.jc1.network.packet;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by courtille on 09/05/17.
 */
public class GestionPaquet {

    private HashMap<String,Packet> packets;


    public GestionPaquet(){
        this.packets = new HashMap<String, Packet>();
        this.addPacket(new VoidPacket());
        this.addPacket(new PrintStringPacket());
        this.addPacket(new PingPacket());
    }

    public void addPacket(Packet p){
        this.packets.put(p.getIdentifient(),p);
    }

    public Packet getPacket(String id){
        if(this.packets.containsKey(id))
            return this.packets.get(id);
        return null;
    }

}
