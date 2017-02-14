package com.example.alfredomartinromo.btcomunicacion.helpers;

import android.bluetooth.BluetoothDevice;

import java.util.Set;

import static com.example.alfredomartinromo.btcomunicacion.helpers.SetBluetooth.mBluetoothAdapter;

/**
 * Created by alfredo on 14/02/17.
 */

public class GetLinkedDevices {

    private BluetoothDevice[] devices;

    public BluetoothDevice[] GetLinkedDevices( ) {

        if (mBluetoothAdapter.isEnabled()) {
            // Listar los dispositivos emparejados
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
            // If there are paired devices
            if (pairedDevices.size() > 0) {
                devices = new BluetoothDevice[pairedDevices.size()];
                // Loop through paired devices
                int i = 0;
                for (BluetoothDevice device : pairedDevices) {
                    devices[ i++ ] = device;
                }

                mBluetoothAdapter.cancelDiscovery();

            }

        }

        return devices;
    }

}
