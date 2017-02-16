package com.example.alfredomartinromo.btcomunicacion.views.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.alfredomartinromo.btcomunicacion.R;
import com.example.alfredomartinromo.btcomunicacion.interfaces.ILDpresenter;
import com.example.alfredomartinromo.btcomunicacion.interfaces.ILinkedDevices;
import com.example.alfredomartinromo.btcomunicacion.presenters.LDpresenter;

/**
 * Created by alfredo.martinromo on 14/02/2017.
 */

public class LinkedDevices extends ListActivity implements ILinkedDevices{

    private ILDpresenter ldpresenter;
    private static final int REQUEST_ENABLE_BT = 3;
    public static BluetoothAdapter mBluetoothAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linked_devices);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {

            showMessage("Bluetooth no soportado");
            this.finish();
        } else if (!mBluetoothAdapter.isEnabled()) {

            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);

        } else {

            ldpresenter = new LDpresenter(this);
            ldpresenter.getLinkedDevices();
        }
    }

    @Override
    public void createListAdapter(BluetoothDevice[] devices){

        setListAdapter(new ArrayAdapter<BluetoothDevice>(this, android.R.layout.simple_list_item_1, devices));

    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        ldpresenter.onItemClick(listView, view, position, id);
    }

    @Override
    public void goToIODevice(BluetoothDevice btDevice) {
        Intent i = new Intent(LinkedDevices.this, IODevice.class);
        i.putExtra("address", btDevice.getAddress());

        mBluetoothAdapter.cancelDiscovery();

        startActivity(i);
    }

    @Override
    public void showMessage(String message) {

        try {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("ERROR:", e.getMessage());
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if( requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_OK ) {

            ldpresenter = new LDpresenter(this);
            ldpresenter.getLinkedDevices();
        }
    }
}
