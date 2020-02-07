package com.carlosrichter;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;

public class Singleton {
    BluetoothAdapter btAdapter;
    private static Singleton INSTANCE = null;
    private String addressMAC = null;
    private ConnectedThread MyConexionBT;
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public BluetoothSocket getBtSocket() {
        return btSocket;
    }

    private BluetoothSocket btSocket = null;
     // other instance variables can be here

    private Singleton() {
    }

    public static synchronized Singleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Singleton();
        }
        return(INSTANCE);
    }

    public void setAddressMAC(String addressMAC) {
        this.INSTANCE.addressMAC = addressMAC;
    }

    public String getAddressMAC() {
        return INSTANCE.addressMAC;
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException
    {
        return device.createRfcommSocketToServiceRecord(BTMODULEUUID);
    }
    public ConnectedThread magic(){
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothDevice device = btAdapter.getRemoteDevice(INSTANCE.addressMAC);

        try
        {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e){
           // Toast.makeText(getBaseContext(),"la creacion del socket fallo",Toast.LENGTH_SHORT).show();
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
        return MyConexionBT;
    }

}
