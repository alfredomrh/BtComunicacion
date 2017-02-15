package com.example.alfredomartinromo.btcomunicacion.helpers;

import android.bluetooth.BluetoothDevice;

import java.util.Set;

import static com.example.alfredomartinromo.btcomunicacion.helpers.SetBluetooth.mBluetoothAdapter;

/**
 * Created by alfredo on 14/02/17.
 *
 * Clase que se encarga de obtener los dispositivos vinculados siempre que el adaptador bt esté
 * activo, los almacena en un conjunto llamado pairedDevices y si existen dispositivos vinculados,
 * los almacena en un array de BluetoothDevice, después cancela la busqueda y retorna el array.
 */

public class GetLinkedDevices {

    private BluetoothDevice[] devices;

    public BluetoothDevice[] GetLinkedDevices() {

        if (mBluetoothAdapter.isEnabled()) {
            // Listar los dispositivos emparejados
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
            // If there are paired devices
            if (pairedDevices.size() > 0) {

                BluetoothDevice[] devices = new BluetoothDevice[pairedDevices.size()];
                // Loop through paired devices
                int i = 0;

                for (BluetoothDevice device : pairedDevices) {
                    devices[ i++ ] = device;
                }

            }

        }

        return devices;
    }

}
