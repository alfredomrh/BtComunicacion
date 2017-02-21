package com.example.alfredomartinromo.btcomunicacion.views.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alfredomartinromo.btcomunicacion.R;
import com.example.alfredomartinromo.btcomunicacion.interfaces.IIODevice;
import com.example.alfredomartinromo.btcomunicacion.interfaces.IIOpresenter;
import com.example.alfredomartinromo.btcomunicacion.presenters.IOpresenter;

/**
 * Created by alfredo.martinromo on 14/02/2017.
 */

public class IODevice extends AppCompatActivity implements IIODevice{

    private IIOpresenter iOpresenter;

    private TextView MacAddress;
    private TextView Tlectura;
    private EditText eEscritura;

    public static Boolean mState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.io_devices);

        // UI
        Button btnRecibir     = (Button)findViewById(R.id.btnRecibir);
        Button btnEnviar      = (Button)findViewById(R.id.btnEnviar);
        Button btnDesconectar = (Button)findViewById(R.id.btnDesc);

        MacAddress = (TextView)findViewById(R.id.MacAddress);
        Tlectura = (TextView)findViewById(R.id.Tlectura);
        eEscritura = (EditText)findViewById(R.id.eEscritura);

        // Obtiene la direccion mac del dispositivo remoto
        String address = getIntent().getStringExtra("address");
        MacAddress.setText(address);

        iOpresenter = new IOpresenter(this, address);

        // Configuración de los botones
        btnRecibir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (mState) iOpresenter.recibirInfo();
               else showMessage("Conexión no establecida");
            }
        });

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mState) iOpresenter.enviarInfo(eEscritura.getText().toString());
                else showMessage("Conexión no establecida");
            }
        });

        btnDesconectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mState) {
                    iOpresenter.desconectarBT();
                    finish();
                }
                else showMessage("Conexión no establecida");
            }
        });
    }

    @Override
    protected void onPause(){
        iOpresenter.onPause();
        super.onPause();

    }

    @Override
    public void showMessage(String message) {

        try {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("ERROR:", e.getMessage());
        }
    }

    @Override
    public void mostrarLectura(String lectura) {

        Tlectura.setText(lectura);

    }
}