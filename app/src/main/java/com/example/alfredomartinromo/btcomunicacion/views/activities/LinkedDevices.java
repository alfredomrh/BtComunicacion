package com.example.alfredomartinromo.btcomunicacion.views.activities;

import android.app.ListActivity;
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
   // private BluetoothDevice[] devices;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ldpresenter = new LDpresenter(this,this);
        ldpresenter.getLinkedDevices();
    }

    @Override
    public void createListAdapter(String[] devices){

        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, devices));

        //return new MyBluetoothAdapter(this, devices);
    }

    /*@Override
    public void createList(MyBluetoothAdapter adapter){

        final ListView listview = (ListView) findViewById(R.layout.list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
    }*/
    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        ldpresenter.onItemClick(listView, view, position, id);
    }

   /* @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }*/

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
