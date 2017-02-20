package com.example.alfredomartinromo.btcomunicacion.helpers;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import com.example.alfredomartinromo.btcomunicacion.views.activities.IODevice;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by alfredo on 20/02/17.
 */

public class BtConection {

    // Unique UUID for this application
    private static final UUID APP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // Comunicación con bluetooth
    private BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();

    // Control asíncrono
    private ConnectThread connectThread;
    private ConnectedThread connectedThread;

    private String lectura;

    public BtConection(String address) {

        BluetoothDevice device  = btAdapter.getRemoteDevice(address);

        connectThread = new ConnectThread(device);
        connectThread.start();

    }

    public void writeFlow(byte[] out){

        // Create temporary object
        ConnectedThread r;
        // Synchronize a copy of the ConnectedThread
        synchronized (this) {
            if (IODevice.mState != true) return;
            r = connectedThread;
        }
        // Perform the write unsynchronized
        r.write(out);

    }

    public String readFlow(){

        ConnectedThread r;
        // Synchronize a copy of the ConnectedThread
        synchronized (this) {
            if (IODevice.mState != true) return "No Conectado";
            r = connectedThread;
        }

        //r.start();
        return lectura;
    }

    public void cancel(){

        // Create temporary object
        ConnectedThread r;
        // Synchronize a copy of the ConnectedThread
        synchronized (this) {
            if (IODevice.mState != true) return;
            r = connectedThread;
        }
        // Perform the write unsynchronized
        r.cancel();

    }

    /**
     * This thread runs while attempting to make an outgoing connection
     * with a device. It runs straight through; the connection either
     * succeeds or fails.
     */
    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            mmDevice = device;
            BluetoothSocket tmp = null;

            // Get a BluetoothSocket for a connection with the
            // given BluetoothDevice
            try {

                tmp = device.createRfcommSocketToServiceRecord(APP_UUID);

            } catch (IOException e) {}

            mmSocket = tmp;

        }

        public void run() {

            // Always cancel discovery because it will slow down a connection
            btAdapter.cancelDiscovery();

            // Make a connection to the BluetoothSocket
            try {
                // This is a blocking call and will only return on a
                // successful connection or an exception
                mmSocket.connect();
            } catch (IOException e) {
                // Close the socket
                try {
                    mmSocket.close();
                } catch (IOException e2) { }

                return;
            }

            IODevice.mState = true;
            // Start the connected thread
            connectedThread = new ConnectedThread(mmSocket);
            connectedThread.start();
        }

        public void cancel() {
            try {
                mmSocket.close();
                IODevice.mState = false;
            } catch (IOException e) {

            }
        }
    }

    /**
     * This thread runs during a connection with a remote device.
     * It handles all incoming and outgoing transmissions.
     */
    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {

            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the BluetoothSocket input and output streams
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {

            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;

        }

        public void run() {

            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int bytesRead = -1;

            // Keep listening to the InputStream while connected
            while (true) {
                try {
                    // Read from the InputStream
                    bytesRead = mmInStream.read(buffer);
                    lectura = "";


                    if (bytesRead != -1) {

                        while ((bytesRead == bufferSize) && (buffer[bufferSize - 1] != 0)) {
                            lectura = lectura + new String(buffer, 0, bytesRead);
                            bytesRead = mmInStream.read(buffer);
                        }

                        lectura = lectura + new String(buffer, 0, bytesRead - 1);

                    }

                    // Send the obtained bytes to the UI Activity
                    //mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
                    //       .sendToTarget();

                } catch (IOException e) {
                    // Log.e(TAG, "disconnected", e);
                    // connectionLost();
                    break;
                }
            }
        }

        /**
         * Write to the connected OutStream.
         *
         * @param buffer The bytes to write
         */
        public void write(byte[] buffer) {
            try {
                mmOutStream.write(buffer);

                // Share the sent message back to the UI Activity
                //mHandler.obtainMessage(Constants.MESSAGE_WRITE, -1, -1, buffer)
                // .sendToTarget();
            } catch (IOException e) {
                //Log.e(TAG, "Exception during write", e);
            }
        }

        public void cancel() {
            try {
                mmSocket.close();
                IODevice.mState = false;
            } catch (IOException e) {
                //Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }
}

