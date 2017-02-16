package com.example.alfredomartinromo.btcomunicacion.interfaces;

import android.bluetooth.BluetoothDevice;

/**
 * Created by alfredo.martinromo on 14/02/2017.
 */

public interface ILinkedDevices {

   // public void onItemClick(AdapterView<?> parent, View view, int position, long id);

    //protected void onListItemClick(ListView listView, View view, int position, long id);


        //public void createList(MyBluetoothAdapter adapter);

    public void createListAdapter(BluetoothDevice[] devices);

    public void goToIODevice(BluetoothDevice btDevice);

    public void showMessage(String message);


}
