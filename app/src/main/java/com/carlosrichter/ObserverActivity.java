package com.carlosrichter;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.UUID;

public class ObserverActivity extends AppCompatActivity {
    BluetoothAdapter btAdapter;
    private TextView textView;
    private Button button;
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    private BluetoothSocket btSocket;
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observer);
        textView = (TextView)findViewById(R.id.txt_obs);
        button = (Button) findViewById(R.id.btn_obs);

        Intent intent = getIntent();
        String address = intent.getStringExtra(EXTRA_DEVICE_ADDRESS);
        textView.setText(address);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ObserverActivity.this, UserInterfaz.class);//<-<- PARTE A MODIFICAR >->->
                i.putExtra(EXTRA_DEVICE_ADDRESS, textView.getText());
                i.("btsocket", btSocket);
                startActivity(i);
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
        Intent intent = getIntent();
//        address = intent.getStringExtra(DispositivosBT.EXTRA_DEVICE_ADDRESS);
        String address = intent.getStringExtra(ObserverActivity.EXTRA_DEVICE_ADDRESS);
        //setea la direccion MAC

        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        try
        {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e){
            Toast.makeText(getBaseContext(),"la creacion del socket fallo",Toast.LENGTH_SHORT).show();
        }


    }
}
