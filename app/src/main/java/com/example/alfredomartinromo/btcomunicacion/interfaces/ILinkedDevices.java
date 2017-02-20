package com.example.alfredomartinromo.btcomunicacion.interfaces;

import android.bluetooth.BluetoothDevice;

/**
 * Created by alfredo.martinromo on 14/02/2017.
 */

public interface ILinkedDevices {

    public void createListAdapter(BluetoothDevice[] devices);

    public void goToIODevice(BluetoothDevice btDevice);

    public void showMessage(String message);


}
