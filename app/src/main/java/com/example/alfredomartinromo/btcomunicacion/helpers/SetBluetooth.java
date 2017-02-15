package com.example.alfredomartinromo.btcomunicacion.helpers;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

import com.example.alfredomartinromo.btcomunicacion.interfaces.ILinkedDevices;
import com.example.alfredomartinromo.btcomunicacion.views.activities.LinkedDevices;

import static android.app.Activity.RESULT_OK;

/**
 * Created by alfredo on 14/02/17.
 *
 * Clase que optiene la referencia al adaptador bluetooth y se encarga de comprobar que existe
 * y de activarlo en caso que esté apagado, solicitando el permiso al usuario.
 */

public class SetBluetooth {

    public static final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    public static final int REQUEST_ENABLE_BT = 1;
    private ILinkedDevices linkeddevices = new LinkedDevices();
    private Activity mcontext;

    public SetBluetooth(Activity activity) {

        this.mcontext = activity;
        //si el objeto resulta null es que el disp. no tiene bluetooth
        //si tiene bt, vemos si esta deshabilitado
        if (mBluetoothAdapter == null) {

            linkeddevices.showMessage("No se encuentra adaptador Bluetooth en este dispositivo");

        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                //si lo está, lanzamos un intent para habilitarlo aunque podriamos hacerlo sin
                //requerirselo al usuario

                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                mcontext.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

            } else { //si está presente y habilitado

                linkeddevices.showMessage("El adaptador Bluetooth esta preparado");

            }
        }
    }

    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        if( requestCode == REQUEST_ENABLE_BT && resultCode ==  RESULT_OK ) {

            linkeddevices.showMessage("El adaptador Bluetooth esta preparado");

        }
    }

}