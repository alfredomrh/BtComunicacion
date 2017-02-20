package com.example.alfredomartinromo.btcomunicacion.models;

import com.example.alfredomartinromo.btcomunicacion.helpers.BtConection;
import com.example.alfredomartinromo.btcomunicacion.interfaces.IIOinteractor;

/**
 * Created by alfredo.martinromo on 16/02/2017.
 */

public class IOinteractor implements IIOinteractor{

    private BtConection btConection;

    public IOinteractor(String address) {

        btConection = new BtConection(address);

    }

    @Override
    public String recibirInfo() { //read(byte[])

        return btConection.readFlow();

    }

    @Override
    public void enviarInfo(byte[] bytes) { //write(byte[])

       btConection.writeFlow(bytes);

    }

    @Override
    public void desconectarBT() {

        btConection.cancel();

    }

}
