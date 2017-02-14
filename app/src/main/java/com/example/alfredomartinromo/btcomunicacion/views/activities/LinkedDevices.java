package com.example.alfredomartinromo.btcomunicacion.views.activities;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.alfredomartinromo.btcomunicacion.R;
import com.example.alfredomartinromo.btcomunicacion.interfaces.ILDpresenter;
import com.example.alfredomartinromo.btcomunicacion.interfaces.ILinkedDevices;
import com.example.alfredomartinromo.btcomunicacion.views.adapter.MyBluetoothAdapter;

/**
 * Created by alfredo.martinromo on 14/02/2017.
 */

public class LinkedDevices extends Activity implements ILinkedDevices, AdapterView.OnItemClickListener{

    private ListView listview;
    private ILDpresenter ldpresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //listview = (ListView) findViewById(R.id.listview);

        ldpresenter.onCreate(this);
        ldpresenter.getLinkedDevices();
    }

    @Override
    public void createList(MyBluetoothAdapter adapter){

        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ldpresenter.onItemClick(parent, view, position, id);
    }

    @Override
    public MyBluetoothAdapter createListAdapter(BluetoothDevice[] btDevices){
        return new MyBluetoothAdapter(getApplicationContext(), btDevices);
    }

    @Override
    public void goToIODevice(BluetoothDevice btDevice) {
        Intent i = new Intent(LinkedDevices.this, IODevice.class);
        i.putExtra("address", btDevice.getAddress());
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
}
