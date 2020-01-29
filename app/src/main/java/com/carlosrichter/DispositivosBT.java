package com.carlosrichter;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
        IdLista.setOnItemClickListener(mDeviceClickListener);
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

    // configura un (on-click) para la lista

    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView av, View v, int arg2, long arg3) {
            //Obtener la direccion MAC del dispositivo, que son los ultimos 17 caracteres en la vista
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length()-17);

            Intent i = new Intent(DispositivosBT.this, UserInterfaz.class);
            i.putExtra(EXTRA_DEVICES_ADDRES,address);
            startActivity(i);
        }
    };

    //----------------------------------------------------------------------------------------------

    //Metodo para que el dispositivo BT esta encendido y que el dispositivo movil soporte el servicio
    private void VerificarEstadoBT(){
        //Comprueba que el dispositivo tiene Bluetooth y que esta encendido
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBtAdapter == null){
            Toast.makeText(getBaseContext(), "El dispositivo no soporta Bluetooth", Toast.LENGTH_SHORT).show();
        }else{
            if (mBtAdapter.isEnabled()){
                Toast.makeText(getBaseContext(), "...Bluetooth Activado...", Toast.LENGTH_SHORT).show();
            }else{
                //Solicita al usuario que active Bluetooth
                Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBTIntent,1);
            }
        }
    }


}
