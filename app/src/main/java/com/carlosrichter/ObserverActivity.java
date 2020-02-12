package com.carlosrichter;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import base.Device;
import base.Message;

public class ObserverActivity extends AppCompatActivity implements Device {

    private TextView textView, Indicator;
    private com.carlosrichter.ConnectedThread MyConexionBT;
    private Button button;
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    private BluetoothSocket btSocket;
    private boolean CONNECTION = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observer);

        textView = (TextView)findViewById(R.id.txt_obs);
        button = (Button) findViewById(R.id.btn_obs);
        Indicator = (TextView) findViewById(R.id.txt_indicator);
//        button.setEnabled(false);

        Intent intent = getIntent();
        String address = intent.getStringExtra(EXTRA_DEVICE_ADDRESS);
        textView.setText(address);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ObserverActivity.this, UserInterface.class);
                startActivity(i);
            }
        });


    }


    @Override
    public void onResume() {

        super.onResume();
        ConnectionManagerSingleton.getInstance().subscribe(this);
        Intent intent = getIntent();
       String address = intent.getStringExtra(ObserverActivity.EXTRA_DEVICE_ADDRESS);
        //setea la direccion MAC
        ConnectionManagerSingleton.getInstance().setAddressMAC(address);
        TimerTask task = new TimerTask() {
            public void run() {
                ConnectionManagerSingleton.getInstance().magic();
                if(CONNECTION){
                    cancel();
                }
            }
        };
        Timer timer = new Timer("Timer");
        long delay = 1000L;
        long period = 5000L;
        timer.schedule(task, delay, period);

    }

    @Override
    public void update(Message message) {
        if (message instanceof ConectionMessage) {
            if (((ConectionMessage) message).isConextionStatus()){
                Indicator.setText("READY TO USE!!!!!!!");
                this.CONNECTION = true;
            }
        }

    }
}
