package com.example.alfredomartinromo.btcomunicacion.presenters;

import android.app.Activity;
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
    private GetLinkedDevices getlinkeddevices = new GetLinkedDevices();
    private BluetoothDevice[] devices;
    private Activity activity;

    public LDpresenter(ILinkedDevices linkeddevices, Activity activity){

        this.linkeddevices = linkeddevices;
        this.activity = activity;
        //aqui deberia instanciar en interactor
    }

    public void getLinkedDevices(){

        SetBluetooth setbluetooth = new SetBluetooth(activity);

        if (mBluetoothAdapter.isEnabled()) {

            devices = getlinkeddevices.GetLinkedDevices();

            if (devices != null) showList();

            else linkeddevices.showMessage("No se encuentran dispositivos vinculados");
        }

    }

    @Override
    public void showList() {

        linkeddevices.createList(linkeddevices.createListAdapter(devices));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BluetoothDevice device = (BluetoothDevice) parent.getItemAtPosition( position );
        BluetoothDevice actual = mBluetoothAdapter.getRemoteDevice( device.getAddress() );

        linkeddevices.goToIODevice(actual);

    }

}
