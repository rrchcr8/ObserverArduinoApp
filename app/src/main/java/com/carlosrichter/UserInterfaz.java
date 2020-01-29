package com.carlosrichter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserInterfaz extends AppCompatActivity {

    Button IdEncender, IdApagar, IdDesconectar;
    TextView IdBufferIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interfaz);
        IdEncender = (Button) findViewById(R.id.IdEncender);
        IdApagar = (Button) findViewById(R.id.IdApagar);
        IdDesconectar = (Button) findViewById(R.id.IdDesconectar);
        IdBufferIn = (TextView) findViewById(R.id.IDBufferIn);


        IdEncender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        IdApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        IdDesconectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
