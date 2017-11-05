package com.maccier.jc1.network.client;

import com.maccier.jc1.network.ClientInfo;

import com.maccier.jc1.network.packet.GestionPaquet;
import com.maccier.jc1.network.packet.Packet;
import com.maccier.jc1.network.packet.PacketList;
import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by courtille on 09/05/17.
 */
public class Client implements ClientInfo {

    private int port;
    private String hote;

    private GestionPaquet gestionPaquet;
    private PacketList packets;

    private ClientRead clir;
    private  ClientWrite cliw;
    private Socket soc;

    public Client(GestionPaquet gestionPaquet, String hote, int port){
        this.gestionPaquet = gestionPaquet;
        this.packets = new PacketList();
        this.hote = hote;
        this.port = port;
    }



    public void connect() {


        try {

            this.soc = new Socket(this.hote, port);

            OutputStream flux = soc.getOutputStream();
            OutputStreamWriter sortie = new OutputStreamWriter(flux);

            this.cliw = new ClientWrite(this, sortie);
            this.cliw.start();

            InputStream reflux = soc.getInputStream();
            BufferedReader entre = new BufferedReader(new InputStreamReader(reflux));

            this.clir = new ClientRead(this, entre);
            this.clir.start();



        } catch (IOException e) {
            if(this.cliw != null)
                this.cliw.interrupt();
            if(this.clir != null)
                this.clir.interrupt();

            e.printStackTrace();
        }
    }

    public synchronized void disconect(){
        if(this.cliw != null)
            this.cliw.interrupt();
        if(this.clir != null)
            this.clir.interrupt();
        try {
            this.soc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized Packet pop(){
        return this.packets.pop();
    }

    public synchronized void send(Packet p){
        this.packets.add(p);
    }

    @Override
    public String getIp() {
        return null;
    }

    public synchronized Packet getPacket(String msg) {

        try{

            String[] list = msg.split(",");
            return this.gestionPaquet.getPacket(list[0]);
        }
        catch (Exception e){

        }
        return null;
    }
}
