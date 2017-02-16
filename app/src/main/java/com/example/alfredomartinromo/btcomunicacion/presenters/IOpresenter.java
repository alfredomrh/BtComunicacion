package com.example.alfredomartinromo.btcomunicacion.presenters;

import com.example.alfredomartinromo.btcomunicacion.interfaces.IIODevice;
import com.example.alfredomartinromo.btcomunicacion.interfaces.IIOinteractor;
import com.example.alfredomartinromo.btcomunicacion.interfaces.IIOpresenter;
import com.example.alfredomartinromo.btcomunicacion.models.IOinteractor;

/**
 * Created by alfredo.martinromo on 16/02/2017.
 */

public class IOpresenter implements IIOpresenter {

    private IIODevice ioDevice;
    private IIOinteractor iOinteractor;

    public IOpresenter(IIODevice ioDevice, String address) {

        this.ioDevice = ioDevice;
        iOinteractor = new IOinteractor(address);

    }

    @Override
    public String recibirInfo() {

        return iOinteractor.recibirInfo();
    }

    @Override
    public void enviarInfo(String info) {


        iOinteractor.enviarInfo(info.getBytes());

    }

    @Override
    public void desconectarBT() {

        iOinteractor.desconectarBT();

    }
}

