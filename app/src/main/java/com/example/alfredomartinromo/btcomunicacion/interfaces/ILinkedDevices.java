package com.example.alfredomartinromo.btcomunicacion.interfaces;

import android.bluetooth.BluetoothDevice;
import android.view.View;
import android.widget.AdapterView;

import com.example.alfredomartinromo.btcomunicacion.views.adapter.MyBluetoothAdapter;

/**
 * Created by alfredo.martinromo on 14/02/2017.
 */

public interface ILinkedDevices {

    public void onItemClick(AdapterView<?> parent, View view, int position, long id);

    public void createList(MyBluetoothAdapter adapter);

    public MyBluetoothAdapter createListAdapter(BluetoothDevice[] btDevices);

    public void goToIODevice(BluetoothDevice btDevice);

    public void showMessage(String message);


}
