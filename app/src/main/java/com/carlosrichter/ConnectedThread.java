package com.carlosrichter;


import android.bluetooth.BluetoothSocket;
import android.os.Handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class ConnectedThread extends Thread{

    private StringBuilder DataStringIN = new StringBuilder();
    final int handlerState = 0;


    Handler bluetoothIn = new Handler(){
        public void handleMessage(android.os.Message msg) {
            if (msg.what == handlerState) {
                String readMessage = (String) msg.obj;
                DataStringIN.append(readMessage);

                int endOfLineIndex = DataStringIN.indexOf("#");
                if (endOfLineIndex > 0){
                    String dataPrint = DataStringIN.substring(0, endOfLineIndex);
//                    IdBufferIn.setText("Dato:" + dataPrint);
                    DataStringIN.delete(0,DataStringIN.length());
                }
            }
        }};



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
//            Toast.makeText(getBaseContext(), "La Conexion fallo PERRP", Toast.LENGTH_SHORT).show();
          //  finish();
        }
    }
}
