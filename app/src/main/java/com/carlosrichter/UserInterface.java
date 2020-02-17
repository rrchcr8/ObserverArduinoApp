package com.carlosrichter;

import android.bluetooth.BluetoothAdapter;
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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//import android.annotation.SuppressLint;

public class UserInterface extends AppCompatActivity {
    private Map<String,Integer> servoAngleValues = new HashMap<String, Integer>();

    Button IdEncender, IdApagar, IdDesconectar;
    Button btn_A_LEFT, btn_A_RIGHT, btn_B_LEFT, btn_B_RIGHT, btn_C_LEFT, btn_C_RIGHT,btn_D_LEFT, btn_D_RIGHT;
    TextView IdBufferIn;
    //-------------------------------------
    Handler bluetoothIn;
    final int handlerState = 0;
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;

    private StringBuilder DataStringIN = new StringBuilder();
    private com.carlosrichter.ConnectedThread MyConexionBT;
    //identificador unico de servicio - SPP UUID
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    //declaraciond e variable string apra la direccion MAc
    private  static String address = null;
    //----------------------------------


    //    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interface);
//        IdEncender = (Button) findViewById(R.id.IdEncender);
//        IdApagar = (Button) findViewById(R.id.IdApagar);
//        IdDesconectar = (Button) findViewById(R.id.IdDesconectar);
//        IdBufferIn = (TextView) findViewById(R.id.IdBufferIn);
            btn_A_LEFT = (Button)findViewById(R.id.btn_A_LEFT);
            btn_A_RIGHT = (Button)findViewById(R.id.btn_A_RIGHT);
            btn_B_LEFT = (Button)findViewById(R.id.btn_B_LEFT);
            btn_B_RIGHT = (Button)findViewById(R.id.btn_B_RIGHT);
            btn_C_LEFT = (Button)findViewById(R.id.btn_C_LEFT);
            btn_C_RIGHT = (Button)findViewById(R.id.btn_C_RIGHT);
            btn_D_LEFT = (Button)findViewById(R.id.btn_D_LEFT);
            btn_D_RIGHT = (Button)findViewById(R.id.btn_D_RIGHT);

        servoAngleValues.put("A_V",0 );
        servoAngleValues.put("A_H",0 );
        servoAngleValues.put("B_V",0 );
        servoAngleValues.put("B_H",0 );
        servoAngleValues.put("C_H",0 );
        servoAngleValues.put("C_V",0 );
        servoAngleValues.put("D_V",0 );
        servoAngleValues.put("D_H",0 );
        //VerificarEstadoBT();

        btn_A_LEFT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(servoAngleValues.get("A_H")<90){
                MyConexionBT.write("1");
                servoAngleValues.put("A_H",servoAngleValues.get("A_H")+45);
                }
            }
        });

        btn_A_RIGHT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(servoAngleValues.get("A_H")>0){
                    MyConexionBT.write("0");
                    servoAngleValues.put("A_H",servoAngleValues.get("A_H")-45);
                }
            }
        });

        btn_B_LEFT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(servoAngleValues.get("B_H")<90){
                    MyConexionBT.write("3");
                    servoAngleValues.put("B_H",servoAngleValues.get("B_H")+45);
                }
            }
        });

        btn_B_RIGHT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(servoAngleValues.get("B_H")>0){
                    MyConexionBT.write("2");
                    servoAngleValues.put("B_H",servoAngleValues.get("B_H")-45);
                }
            }
        });

        btn_C_LEFT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(servoAngleValues.get("C_H")<90){
                    MyConexionBT.write("5");
                    servoAngleValues.put("C_H",servoAngleValues.get("C_H")+45);
                }
            }
        });

        btn_C_RIGHT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(servoAngleValues.get("C_H")>0){
                    MyConexionBT.write("4");
                    servoAngleValues.put("C_H",servoAngleValues.get("C_H")-45);
                }
            }
        });

        btn_D_LEFT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(servoAngleValues.get("D_H")<90){
                    MyConexionBT.write("7");
                    servoAngleValues.put("D_H",servoAngleValues.get("D_H")+45);
                }
            }
        });

        btn_D_RIGHT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(servoAngleValues.get("D_H")>0){
                    MyConexionBT.write("6");
                    servoAngleValues.put("D_H",servoAngleValues.get("D_H")-45);
                }
            }
        });

//        IdEncender.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MyConexionBT.write("1");
////                IdBufferIn.setText("gato");
//            }
//        });
//
//        IdApagar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MyConexionBT.write("0");
//            }
//        });
//
//
//        IdDesconectar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                try {
//                    ConnectionManagerSingleton.getInstance().getBtSocket().close();
//
//                    } catch (IOException e){
//                        Toast.makeText(getBaseContext(),"Error",Toast.LENGTH_SHORT).show();
//                    }
//                Intent i = new Intent(UserInterface.this, DevicesBT.class);//<-<- PARTE A MODIFICAR >->->
//                startActivity(i);
//            }
//        });
    }

    @Override
    public void onResume() {

        super.onResume();
        MyConexionBT = ConnectionManagerSingleton.getInstance().magic2();
        MyConexionBT.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            //cuando se sale de la aplciacion esta aprte permite que no se deje  abierto el socket
            ConnectionManagerSingleton.getInstance().getBtSocket().close();
            //btSocket.close();
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


}
