package com.example.alfredomartinromo.btcomunicacion.models;

import android.bluetooth.BluetoothDevice;

import com.example.alfredomartinromo.btcomunicacion.interfaces.ILDinteractor;

import java.util.Set;

import static com.example.alfredomartinromo.btcomunicacion.views.activities.LinkedDevices.mBluetoothAdapter;

/**
 * Created by alfredo.martinromo on 16/02/2017.
 */

public class LDinteractor implements ILDinteractor {

    private BluetoothDevice[] devices;

    public BluetoothDevice[] GetLinkedDevices() {

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
