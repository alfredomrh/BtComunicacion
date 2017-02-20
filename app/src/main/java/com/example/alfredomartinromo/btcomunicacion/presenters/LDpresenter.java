package com.example.alfredomartinromo.btcomunicacion.presenters;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.view.View;
import android.widget.AdapterView;

import com.example.alfredomartinromo.btcomunicacion.interfaces.ILDinteractor;
import com.example.alfredomartinromo.btcomunicacion.interfaces.ILDpresenter;
import com.example.alfredomartinromo.btcomunicacion.interfaces.ILinkedDevices;
import com.example.alfredomartinromo.btcomunicacion.models.LDinteractor;

/**
 * Created by alfredo on 14/02/17.
 */

public class LDpresenter implements ILDpresenter{

    private ILinkedDevices linkeddevices;
    private ILDinteractor ilDinteractor;
    private BluetoothAdapter mBluetoothAdapter;

    public LDpresenter(ILinkedDevices linkeddevices, BluetoothAdapter mBluetoothAdapter){

        this.linkeddevices = linkeddevices;
        this.mBluetoothAdapter = mBluetoothAdapter;
        ilDinteractor = new LDinteractor();

    }

    public void getLinkedDevices(){

        if (mBluetoothAdapter.isEnabled()) {

            if (ilDinteractor.GetLinkedDevices(mBluetoothAdapter) != null)

                linkeddevices.createListAdapter(ilDinteractor.GetLinkedDevices(mBluetoothAdapter));

            else linkeddevices.showMessage("No se encuentran dispositivos vinculados");

            }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        BluetoothDevice device = (BluetoothDevice) parent.getItemAtPosition( position );
        BluetoothDevice actual = mBluetoothAdapter.getRemoteDevice( device.getAddress() );

        linkeddevices.goToIODevice(actual);
    }

}
