package com.carlosrichter;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;

import base.Sensor;

public class ConnectionManagerSingleton extends Sensor {
    public boolean isFlag() {
        return flag;
    }

    private boolean flag= false;
    BluetoothAdapter btAdapter;
    private static ConnectionManagerSingleton INSTANCE = null;
    private String addressMAC = null;

    private ConnectedThread MyConexionBT;
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public BluetoothSocket getBtSocket() {
        return btSocket;
    }

    private BluetoothSocket btSocket = null;
     // other instance variables can be here

    private ConnectionManagerSingleton() {
    }

    public static synchronized ConnectionManagerSingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConnectionManagerSingleton();
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

    public void magic(){
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
            notifyAllDevices(new ConectionMessage(true));
            flag=true;
        } catch (IOException e){
            try
            {
                btSocket.close();
            } catch (IOException e2){}
        }
//       return MyConexionBT;
    }

    public ConnectedThread magic2() {
        MyConexionBT = new ConnectedThread(btSocket);
        return MyConexionBT;
    }


    public ConnectedThread getMyConexionBT() {
        return MyConexionBT;
    }
}
