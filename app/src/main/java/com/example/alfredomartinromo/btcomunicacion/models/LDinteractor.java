package com.example.alfredomartinromo.btcomunicacion.models;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import com.example.alfredomartinromo.btcomunicacion.interfaces.ILDinteractor;

import java.util.Set;

/**
 * Created by alfredo.martinromo on 16/02/2017.
 */

public class LDinteractor implements ILDinteractor {

    private BluetoothDevice[] devices;
    private BluetoothAdapter mBluetoothAdapter;


    public BluetoothDevice[] GetLinkedDevices(BluetoothAdapter mBluetoothAdapter) {

        if (mBluetoothAdapter.isEnabled()) {
            // Listar los dispositivos emparejados
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
            // If there are paired devices
            if (pairedDevices.size() > 0) {

                devices = new BluetoothDevice[pairedDevices.size()];
                int i = 0;

                for (BluetoothDevice device : pairedDevices) {

                    devices[ i++ ] = device;
                }

            }

        }

        return devices;
    }

}
