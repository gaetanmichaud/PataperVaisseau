package com.maccier.jc1.network.packet;

import com.maccier.jc1.network.ClientInfo;

/**
 * Created by courtille on 09/05/17.
 */
public abstract class Packet {

    private String identifient;


    protected Packet(String identifient) {
        this.identifient = identifient;
    }


    /**
     * L'identifiant du packet
     * @return
     */
    public String getIdentifient() {
        return identifient;
    }


    /**
     * Encode les donnee dans un string, la premiere donnée est
     * l'identifient et est separé des autre par une virgule
     * @return msg a envoyer
     */
    public abstract String encode();


    /**
     * est appeler a la reception d'un packet
     */
    public abstract void decode(String msg, ClientInfo cli);

    /**
     * Packet a renvoyer apres reception de celuit ci
     * @return le packet a envoyer
     */
    public abstract Packet reponse(String msg);

}
