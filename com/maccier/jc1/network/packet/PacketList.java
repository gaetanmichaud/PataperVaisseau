package com.maccier.jc1.network.packet;

import java.util.ArrayList;

/**
 * Created by courtille on 09/05/17.
 */
public class PacketList {

    private ArrayList<Packet> packets;

    public PacketList(){
        this.packets = new ArrayList<Packet>();
    }

    public void add(Packet p){
        this.packets.add(p);
    }

    public int size(){
        return this.packets.size();
    }

    public Packet pop(){

        if(this.packets.size() > 0){
            Packet p = this.packets.get(0);
            this.packets.remove(0);
            return p;
        }

        return null;
    }

}
