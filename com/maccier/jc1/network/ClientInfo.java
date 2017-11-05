package com.maccier.jc1.network;


import com.maccier.jc1.network.packet.Packet;

public interface ClientInfo {


    public void send(Packet p);

    public String getIp();
}
