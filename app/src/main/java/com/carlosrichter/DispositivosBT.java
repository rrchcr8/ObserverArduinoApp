package com.carlosrichter;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Set;

public class DispositivosBT extends AppCompatActivity {

    private static final String TAG = "DispositivosBT";
    ListView IdLista;
    public  static String EXTRA_DEVICES_ADDRES = "device_address";

    private BluetoothAdapter mBtAdapter;
    private ArrayAdapter<String> mPairedDevicesArrayAdapater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispositivos_bt);
    }
    @Override
    public void onResume()
    {
        super.onResume();
        //inicializa el array que contendra la lista de dispositivos bluetooth vinculados
        mPairedDevicesArrayAdapater = new ArrayAdapter<String>(this, R.layout.nombre_dispositivos);
        //Presentalosdispositivos vinculados en el ListView
        IdLista = (ListView) findViewById(R.id.IdLista);
        IdLista.setAdapter(mPairedDevicesArrayAdapater);
        IdLista.setOnClickListener(mDeviceClickListener);
        //Obtiene el adaptador localBluetooth Adapter
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
        //adciona un dispositivoprevio  emparejado al Array
        if(pairedDevices.size()>0){
            for(BluetoothDevice device : pairedDevices){
                mPairedDevicesArrayAdapater.add(device.getName()+"\n"+device.getAddress());
            }
        }
    }




}
