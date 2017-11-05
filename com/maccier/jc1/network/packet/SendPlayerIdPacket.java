package com.maccier.jc1.network.packet;

import com.maccier.jc1.exception.FailLoad;
import com.maccier.jc1.game.Game;
import com.maccier.jc1.game.Player;
import com.maccier.jc1.network.inter.ReceiveId;
import com.maccier.jc1.network.ClientInfo;
import com.maccier.jc1.network.packet.Packet;

import java.util.ArrayList;

public class SendPlayerIdPacket extends Packet {

    private String id;
    private ReceiveId rec;

    public SendPlayerIdPacket(ReceiveId rec) {
        super("SendPlayerId");
        this.rec = rec;
    }

    public SendPlayerIdPacket(String id){
        super("SendPlayerId");
        this.id = id;
    }

    @Override
    public synchronized String encode() {
        return this.getIdentifient()+","+id;
    }

    @Override
    public synchronized void decode(String msg, ClientInfo cli) {
        String[] data = msg.split(",");
        this.rec.setId(data[1]);
    }

    @Override
    public synchronized Packet reponse(String msg) {
        return null;
    }
}
