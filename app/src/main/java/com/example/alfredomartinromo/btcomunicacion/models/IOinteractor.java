package com.example.alfredomartinromo.btcomunicacion.models;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;

import com.example.alfredomartinromo.btcomunicacion.interfaces.IIOinteractor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by alfredo.martinromo on 16/02/2017.
 */

public class IOinteractor implements IIOinteractor{

    // Unique UUID for this application
    private static final UUID APP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // Comunicación con bluetooth
    private BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
    private BluetoothSocket btSocket;

    // Control asíncrono
    private ConnectAsyncTask connectAsyncTask;
    private ConnectedThread mThread;

    public IOinteractor(String address) {

        BluetoothDevice device  = btAdapter.getRemoteDevice(address);

        connectAsyncTask = new ConnectAsyncTask();
        connectAsyncTask.execute(device);
    }

    @Override
    public String recibirInfo() { //read(byte[])

        if (this.btSocket != null) return mThread.read();

        else {

            //showMessage("No hay conexion establecida");
            return null;
        }
    }

    @Override
    public void enviarInfo(byte[] bytes) { //write(byte[])

        if (this.btSocket != null) mThread.write(bytes);

        //else showMessage("No hay conexion establecida");

    }

    @Override
    public void desconectarBT() {

        if (btSocket != null) mThread.cancel();

       // else showMessage("No hay conexion establecida");

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
                //showMessage("ERR: " + e.getMessage());
            }

            return mmSocket;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            setProgressPercent(progress[0]);
        }

        @Override
        protected void onPostExecute(BluetoothSocket result) {

            mThread = new ConnectedThread(result);

        }

        public void setProgressPercent(Integer progressPercent) {
            // Para utilizar el progreso
        }
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public String read() {
            String message = "";
            int bufferSize = 256;
            byte[] buffer = new byte[bufferSize];

            if (btSocket != null) {

                try {

                    int bytesRead = -1;

                    while (true) {

                        bytesRead = mmInStream.read(buffer);

                        if (bytesRead != -1) {

                            while ((bytesRead == bufferSize) && (buffer[bufferSize - 1] != 0)) {
                                message = message + new String(buffer, 0, bytesRead);
                                bytesRead = mmInStream.read(buffer);
                            }

                            message = message + new String(buffer, 0, bytesRead - 1);

                        }
                    }

                }catch(IOException e){
                    //showMessage("Error de comunicación con Bluetooth." + e.getMessage());
                }

            }

            return message;
        }

        /* Call this from the main activity to send data to the remote device */
        public void write(byte[] bytes) {
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) { }
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }

}
