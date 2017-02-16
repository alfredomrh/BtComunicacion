package com.example.alfredomartinromo.btcomunicacion.models;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;

import com.example.alfredomartinromo.btcomunicacion.interfaces.IIOinteractor;
import com.example.alfredomartinromo.btcomunicacion.interfaces.ILinkedDevices;
import com.example.alfredomartinromo.btcomunicacion.views.activities.LinkedDevices;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;


/**
 * Created by alfredo.martinromo on 16/02/2017.
 */

public class IOinteractor implements IIOinteractor{

    private ILinkedDevices linkeddevices = new LinkedDevices();

    // Unique UUID for this application
    private static final UUID APP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // Comunicación con bluetooth
    private BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
    private BluetoothSocket btSocket;

    // Control asíncrono
    private ConnectAsyncTask connectAsyncTask;

    public IOinteractor(String address) {

        BluetoothDevice device  = btAdapter.getRemoteDevice(address);

        connectAsyncTask = new ConnectAsyncTask();
        connectAsyncTask.execute( device );
    }

    public String recibirInfo() {

        linkeddevices.showMessage("Entrando a leer el sensor");
        String message = "";
        int bufferSize = 256;
        byte[] buffer = new byte[bufferSize];

        if (btSocket != null) {

            try {

                InputStream instream = btSocket.getInputStream();
                int bytesRead = -1;

                while (true) {

                    bytesRead = instream.read(buffer);

                    if (bytesRead != -1) {

                        while ((bytesRead == bufferSize) && (buffer[bufferSize - 1] != 0)) {
                            message = message + new String(buffer, 0, bytesRead);
                            bytesRead = instream.read(buffer);
                        }

                        message = message + new String(buffer, 0, bytesRead - 1);

                    }
                }

            }catch(IOException e){
                linkeddevices.showMessage("Error de comunicación con Bluetooth." + e.getMessage());
            }

        }

        return message;
    }

    public void enviarInfo(byte[] bytes) {
        if (this.btSocket != null) {
            try {

                btSocket.getOutputStream().write(bytes);

            } catch (IOException e) {
                linkeddevices.showMessage("Error de comunicación con Bluetooth." + e.getMessage());
            }
        }
    }

    public void desconectarBT() {
        if (btSocket != null) {
            try {
                btSocket.close();
            } catch (IOException e) {
                linkeddevices.showMessage("Error de comunicación con Bluetooth." + e.getMessage());
            }
        }
    }

    /**
     * proceso de conexion bluetooth como cliente, primero se utiliza el metodo del devide.createInsecure...
     * para obtener el UUID y después se llama al metodo connect del socket, asi mmSocket.connet()
     */
    private class ConnectAsyncTask extends AsyncTask<BluetoothDevice, Integer, BluetoothSocket> {
        private BluetoothSocket mmSocket;
        private BluetoothDevice mmDevice;

        @Override
        protected BluetoothSocket doInBackground(BluetoothDevice... device) {
            mmDevice = device[0];

            try {

                mmSocket = mmDevice.createInsecureRfcommSocketToServiceRecord(APP_UUID);
                mmSocket.connect();

            } catch (Exception e) {
                linkeddevices.showMessage("ERR: " + e.getMessage());
            }

            return mmSocket;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            setProgressPercent(progress[0]);
        }

        @Override
        protected void onPostExecute(BluetoothSocket result) {
            btSocket = result;
            try {
                btSocket.getOutputStream().write("2".toString().getBytes());
                InputStream is = btSocket.getInputStream();

                char data = (char)is.read( );
                if( data == '1' ) {
                    linkeddevices.showMessage("Data Uno");
                }
            } catch (IOException e) {
                linkeddevices.showMessage("Error de comunicación con Bluetooth." + e.getMessage());
            }
        }

        public void setProgressPercent(Integer progressPercent) {
            // Para utilizar el progreso
        }
    }

}
