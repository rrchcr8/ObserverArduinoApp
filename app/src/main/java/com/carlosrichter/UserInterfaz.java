package com.carlosrichter;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.UUID;

public class UserInterfaz extends AppCompatActivity {

    Button IdEncender, IdApagar, IdDesconectar;
    TextView IdBufferIn;
    //-------------------------------------
    Handler bluetoothIn;
    final int handlerState = 0;
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder DataStringIN = new StringBuilder();
    private ConnectedThread MyConexionBT;
    //identificador unico de servicio - SPP UUID
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-800-00805F9B34FB");
    //declaraciond e variable string apra la direccion MAc
    private  static String address = null;
    //----------------------------------


    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interfaz);
        IdEncender = (Button) findViewById(R.id.IdEncender);
        IdApagar = (Button) findViewById(R.id.IdApagar);
        IdDesconectar = (Button) findViewById(R.id.IdDesconectar);
        IdBufferIn = (TextView) findViewById(R.id.IDBufferIn);

        bluetoothIn = new Handler(){
            public void handleMessage(android.os.Message msg) {
                if (msg.what == handlerState) {
                    String readMessage = (String) msg.obj;
                    DataStringIN.append(readMessage);

                    int endOfLineIndex = DataStringIN.indexOf("#");
                    if (endOfLineIndex > 0){
                        String dataPrint = DataStringIN.substring(0, endOfLineIndex);
                        IdBufferIn.setText("Dato:" + dataPrint);
                        DataStringIN.delete(0,DataStringIN.length());
                    }
                }
            }};





        IdEncender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        IdApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        IdDesconectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
