package com.maccier.jc1.network.client;

import com.maccier.jc1.network.packet.GestionPaquet;
import com.maccier.jc1.network.packet.Packet;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by courtille on 09/05/17.
 */
public class ClientWrite extends Thread{



    private Client cli;
    private OutputStreamWriter sortie;

    public ClientWrite(Client cli, OutputStreamWriter sortie){
        this.cli = cli;
        this.sortie = sortie;
    }



    @Override
    public void run() {


        try {
        while(!this.isInterrupted()){

            Packet p = this.cli.pop();
            if(p != null) {
                sortie.write(p.encode() + "\n");
                sortie.flush();
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
