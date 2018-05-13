package com.example.jsboygenius.sport_bt_test;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class SportSelectActivity extends AppCompatActivity {
    private ImageButton mBtBasketball;
    private ImageButton mBtSoccer;
    private String selectedSport;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sport_select);

        mBtBasketball = (ImageButton) findViewById(R.id.basketballButton);
        mBtSoccer = (ImageButton) findViewById(R.id.soccerButton);

        mBtBasketball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedSport = "basketball";
                launchLevelSelect();
            }
        });
        mBtSoccer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedSport = "soccer";
                launchLevelSelect();
            }
        });
    }

    private void launchLevelSelect() {
        Intent intent = new Intent(this, LevelSelectActivity.class);
        intent.putExtra("SELECTED_SPORT", selectedSport);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        //do absolutely nothing!
    }

}
