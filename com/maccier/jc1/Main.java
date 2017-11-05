package com.maccier.jc1;


import com.maccier.jc1.game.Game;
import com.maccier.jc1.network.client.Client;
import com.maccier.jc1.network.packet.SendPlayerIdPacket;
import com.maccier.jc1.network.packet.SendPlayersPacket;

import com.maccier.jc1.network.packet.GestionPaquet;


/**
 * Created by Tony on 09/03/2017.
 */
public class Main {

    private static final int nbJ = 2;

    private Client client;
    private Game game;
    private IA ia;


    public Main(String ip, int port) {

        this.ia = new IA(this);

        GestionPaquet gestionPaquet = new GestionPaquet();
        gestionPaquet.addPacket(new SendPlayerIdPacket(this.ia));

        this.client = new Client(gestionPaquet,ip, port);
        this.client.connect();

        this.game = new Game();

        gestionPaquet.addPacket(new SendPlayersPacket(this.game, this.ia));
    }

    public Client getClient() {
        return client;
    }

    public Game getGame() {
        return game;
    }
}
