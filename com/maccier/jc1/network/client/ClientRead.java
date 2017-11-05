package com.maccier.jc1.network.client;

import com.maccier.jc1.network.packet.GestionPaquet;
import com.maccier.jc1.network.packet.Packet;
import com.maccier.jc1.network.packet.PacketList;

import java.io.*;
import java.net.Socket;

/**
 * Created by courtille on 09/05/17.
 */
public class ClientRead extends Thread{



    private BufferedReader entre;
    private Client cli;

    public ClientRead(Client cli, BufferedReader entre){
        this.cli = cli;
        this.entre = entre;
    }



    @Override
    public void run() {


        try {
        while(!this.isInterrupted()){


                String msg = entre.readLine();

                Packet p = this.cli.getPacket(msg);
                if(p != null){
                    p.decode(msg,this.cli);
                    Packet rep = p.reponse(msg);
                    if(rep != null){
                        this.cli.send(rep);
                    }
                }
                sleep(10);


        }


        } catch (IOException e) {
            this.cli.disconect();
        } catch (InterruptedException e) {
            //e.printStackTrace();
        }
    }


}
