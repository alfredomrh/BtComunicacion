package com.example.alfredomartinromo.btcomunicacion.interfaces;

/**
 * Created by alfredo.martinromo on 16/02/2017.
 */

public interface IIOpresenter {

    public void onPause();

    public void recibirInfo();

    public void enviarInfo(String info);

    public void desconectarBT();

}
