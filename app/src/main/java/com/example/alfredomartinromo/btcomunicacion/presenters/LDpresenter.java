package com.example.alfredomartinromo.btcomunicacion.presenters;

import android.bluetooth.BluetoothDevice;
import android.view.View;
import android.widget.AdapterView;

import com.example.alfredomartinromo.btcomunicacion.helpers.GetLinkedDevices;
import com.example.alfredomartinromo.btcomunicacion.helpers.SetBluetooth;
import com.example.alfredomartinromo.btcomunicacion.interfaces.ILDpresenter;
import com.example.alfredomartinromo.btcomunicacion.interfaces.ILinkedDevices;

import static com.example.alfredomartinromo.btcomunicacion.helpers.SetBluetooth.mBluetoothAdapter;

/**
 * Created by alfredo on 14/02/17.
 */

public class LDpresenter implements ILDpresenter{

    private ILinkedDevices linkeddevices;
    private GetLinkedDevices getlinkeddevices;
    private BluetoothDevice[] devices;

    public void onCreate(ILinkedDevices view){

        this.linkeddevices = view;

    }

    public void getLinkedDevices(){

        SetBluetooth setbluetooth = new SetBluetooth();

        if (mBluetoothAdapter.isEnabled()) {

            getlinkeddevices = new GetLinkedDevices();

            devices = getlinkeddevices.GetLinkedDevices();

        }
    }

    @Override
    public void showList() {

        linkeddevices.createList(linkeddevices.createListAdapter(devices));// Inversion Dependency

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BluetoothDevice device = (BluetoothDevice) parent.getItemAtPosition( position );
        BluetoothDevice actual = mBluetoothAdapter.getRemoteDevice( device.getAddress() );

        linkeddevices.goToIODevice(actual);

    }

}
