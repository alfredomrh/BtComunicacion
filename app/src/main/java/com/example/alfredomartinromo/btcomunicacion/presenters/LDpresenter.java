package com.example.alfredomartinromo.btcomunicacion.presenters;

import android.bluetooth.BluetoothDevice;
import android.view.View;
import android.widget.AdapterView;

import com.example.alfredomartinromo.btcomunicacion.interfaces.ILDinteractor;
import com.example.alfredomartinromo.btcomunicacion.interfaces.ILDpresenter;
import com.example.alfredomartinromo.btcomunicacion.interfaces.ILinkedDevices;
import com.example.alfredomartinromo.btcomunicacion.models.LDinteractor;

import static com.example.alfredomartinromo.btcomunicacion.views.activities.LinkedDevices.mBluetoothAdapter;

/**
 * Created by alfredo on 14/02/17.
 */

public class LDpresenter implements ILDpresenter{

    private ILinkedDevices linkeddevices;
    private ILDinteractor ilDinteractor;
    private BluetoothDevice[] devices;

    public LDpresenter(ILinkedDevices linkeddevices){

        this.linkeddevices = linkeddevices;
        ilDinteractor = new LDinteractor();

    }

    public void getLinkedDevices(){

        if (mBluetoothAdapter.isEnabled()) {

            devices = ilDinteractor.GetLinkedDevices();

            if (devices != null) showList();

            else linkeddevices.showMessage("No se encuentran dispositivos vinculados");

            }

    }

    @Override
    public void showList() {

        linkeddevices.createListAdapter(devices);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BluetoothDevice device = (BluetoothDevice) parent.getItemAtPosition( position );
        BluetoothDevice actual = mBluetoothAdapter.getRemoteDevice( device.getAddress() );

        linkeddevices.showMessage("Esta es la direccion: " + actual);


        //linkeddevices.goToIODevice(actual);

    }

}
