package com.carlosrichter;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

//import android.annotation.SuppressLint;

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
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    //declaraciond e variable string apra la direccion MAc
    private  static String address = null;
    //----------------------------------


    //    @SuppressLint("HandlerLeak")
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

        btAdapter = BluetoothAdapter.getDefaultAdapter();
        VerificarEstadoBT();



        IdEncender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyConexionBT.write("1");
//                IdBufferIn.setText("gato");
            }
        });

        IdApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyConexionBT.write("0");
            }
        });


        IdDesconectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btSocket!=null)
                {
                    try {btSocket.close();

                    } catch (IOException e){
                        Toast.makeText(getBaseContext(),"Error",Toast.LENGTH_SHORT).show();
                    }
                }
                finish();
            }
        });
    }
    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException
    {
        return device.createRfcommSocketToServiceRecord(BTMODULEUUID);
    }

    @Override
    public void onResume() {

        super.onResume();
        //consigue la direccion MAC desde DEviceListActivity via Intent
        Intent intent = getIntent();
        address = intent.getStringExtra(DispositivosBT.EXTRA_DEVICE_ADDRESS);
        //setea la direccion MAC
        BluetoothDevice device = btAdapter.getRemoteDevice(address);



        try
        {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e){
            Toast.makeText(getBaseContext(),"la creacion del socket fallo",Toast.LENGTH_SHORT).show();
        }

        try
        {
            btSocket.connect();
        } catch (IOException e){
            try
            {
                btSocket.close();
            } catch (IOException e2){}
        }
        MyConexionBT = new ConnectedThread(btSocket);
        MyConexionBT.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            //cuando se sale de la aplciacion esta aprte permite que no se deje  abierto el socket
            btSocket.close();
        } catch (IOException e){
        }
    }

    private void VerificarEstadoBT(){
        //Comprueba que el dispositivo tiene Bluetooth y que esta encendido
        if (btAdapter == null){
            Toast.makeText(getBaseContext(), "El dispositivo no soporta Bluetooth", Toast.LENGTH_SHORT).show();
        }else{
            if (btAdapter.isEnabled()){
                Toast.makeText(getBaseContext(), "...Bluetooth Activado DEÑ USER INTERFACE...", Toast.LENGTH_LONG).show();
            }else{
                //Solicita al usuario que active Bluetooth
                Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBTIntent,1);
            }
        }
    }

    //crea la clase que permite crear el evento de conexion
    private class ConnectedThread extends Thread
    {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket)
        {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            try{
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            }catch (IOException e){

            }
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run(){
            byte[] buffer = new byte[256];
            int bytes;

            //Se mantiene en modo escucha para determinar el ingreso de datos
            while (true){
                try{
                    bytes = mmInStream.read(buffer);
                    String readMessage = new String(buffer,0,bytes);
                    //Envia los datos obtenidos hacia el evento via handler
                    bluetoothIn.obtainMessage(handlerState,bytes,-1,readMessage).sendToTarget();
                }catch (IOException e){
                    break;
                }
            }
        }
        //Envio de trama
        public void write(String input){
            try{
                mmOutStream.write(input.getBytes());
            }catch (IOException e){
                //si no es posible enviar los datos cierra la conexion
                Toast.makeText(getBaseContext(), "La Conexion fallo PERRP", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
