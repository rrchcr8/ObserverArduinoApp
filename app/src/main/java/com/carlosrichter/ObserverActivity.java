package com.carlosrichter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ObserverActivity extends AppCompatActivity {

    private TextView textView;
    private Button button;
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
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
                startActivity(i);
            }
        });

    }
}
