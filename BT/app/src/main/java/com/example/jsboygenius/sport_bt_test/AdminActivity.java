package com.example.jsboygenius.sport_bt_test;

import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class AdminActivity extends AppCompatActivity {

    ImageView crossTickView;
    private Button mBtReconnect;
    private Button mBtStart;
    private Switch mBtToggle;
    //TODO Make sure the Raspberry Pi's address is always the following
    private final String address = "B8:27:EB:4A:3E:55";
    private boolean isBtConnected = false;
    private static boolean demoOverride = false;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    public static BluetoothSocket btSocket = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        crossTickView = (ImageView) findViewById(R.id.crosstick);
        mBtReconnect = (Button) findViewById(R.id.reconnectBtn);
        mBtStart = (Button) findViewById(R.id.startBtn);
        mBtToggle = (Switch) findViewById(R.id.demoToggleBtn);
        mBtReconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AdminActivity.ConnectBT().execute();
            }
        });
        mBtToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                demoOverride = b;
            }
        });
        mBtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(isBtConnected || demoOverride))
                {
                    return;
                }
                Intent intent = new Intent(AdminActivity.this, SportSelectActivity.class);
                startActivity(intent);
            }
        });

        mBtStart.setVisibility(View.INVISIBLE);
        new AdminActivity.ConnectBT().execute();
        final int[] stateSet = {android.R.attr.state_checked * (isBtConnected ? 1 : -1)};
        crossTickView.setImageState(stateSet, true);
    }
    private void Disconnect()
    {
        if (btSocket!=null) //If the btSocket is busy
        {
            try
            {
                btSocket.close(); //close connection
            }
            catch (IOException e)
            { msg("Error");}
            finally
            {
                mBtReconnect.setEnabled(true);
            }
        }
    }
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }


    public class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(AdminActivity.this, "SpORT is connecting...", "This might take a few seconds");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try {

                myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                BluetoothDevice device = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                btSocket =(BluetoothSocket) device.getClass().getMethod("createRfcommSocket", new Class[] {int.class}).invoke(device,1);
                BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                btSocket.connect();//start connection
                btSend("SYNCED", getApplicationContext());

            }
            catch (Exception e) {
                ConnectSuccess = demoOverride;
                e.printStackTrace();
            }
            //Alternative code that was used on different site. It uses INSECURE Rfcomm alternatively
            //btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
            return null;
        }
        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Connection failed. Re-connect in a few moments.");
                mBtReconnect.setEnabled(true);
            }
            else
            {
                msg("SpORT is online!");
                isBtConnected = true;
                mBtReconnect.setEnabled(false);
                mBtStart.setVisibility(View.VISIBLE);
                final int[] stateSet = {android.R.attr.state_checked * (isBtConnected ? 1 : -1)};
                crossTickView.setImageState(stateSet, true);
            }
            progress.dismiss();
        }
    }

    public static void btSend(String msg, Context context)
    {
        if(demoOverride)
        {
            return;
        }
        try {
            btSocket.getOutputStream().write(msg.getBytes());
        }
        catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Signal lost! Please force-close and launch again!", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed() {
        //do absolutely nothing!
    }
}
