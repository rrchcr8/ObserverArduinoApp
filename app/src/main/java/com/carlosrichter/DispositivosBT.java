package com.carlosrichter;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

    mPairedDevicesArrayAdapater = new ArrayAdapter<String>(this, R.layout.nombre_dispositivos);

    }
}
