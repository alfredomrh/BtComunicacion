package com.example.alfredomartinromo.btcomunicacion.interfaces;

/**
 * Created by alfredo.martinromo on 16/02/2017.
 */

public interface IIOinteractor {

    public String recibirInfo();

    public void enviarInfo(byte[] bytes);

    public void desconectarBT();

}
